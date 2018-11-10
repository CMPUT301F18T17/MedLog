package cs.ualberta.ca.medlog.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import cs.ualberta.ca.medlog.R;

import static android.app.Activity.RESULT_OK;

/**
 * <p>
 *     Description: <br>
 *         Handles the creation of a DialogFragment that provides a gui for a user to add photos to
 *         either be used in a record or as body photos.
 *         Arguments are used to set an initial guiding image and any existing already added images
 *         to be displayed in the grid. As well an editor id is allwoed as an argument to
 *         differentiate between different variants returns.
 * </p>
 * <p>
 *     Issues: <br>
 *         Add ability to read existing photos.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.1
 */
public class PhotoSelectorFragment extends DialogFragment {
    PhotoSelectorFragment.OnPhotosSetListener mCallback;

    public interface OnPhotosSetListener {
        void onPhotoSet(ArrayList<Bitmap> photos, int editorId);
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_LOAD_IMAGE = 2;

    int editorId = 0;
    ArrayList<Bitmap> photos = new ArrayList<Bitmap>();

    @Override
    public @NonNull Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View photoSelectorView = inflater.inflate(R.layout.fragment_photo_selector, null);
        Bitmap guidePhoto = null;


        Bundle arguments = getArguments();
        if (arguments != null) {
            editorId = arguments.getInt("argEditorId",0);
            guidePhoto = arguments.getParcelable("argGuidePhoto");
            //TODO Add code to load the passed existing images.
        }

        if (guidePhoto != null) {
            ImageView guideImageView = photoSelectorView.findViewById(R.id.fragmentPhotoSelector_GuideImage);
            guideImageView.setImageBitmap(guidePhoto);
        }

        final GridView photoGrid = photoSelectorView.findViewById(R.id.fragmentPhotoSelector_PhotoGridView);
        ArrayAdapter<Bitmap> gridAdapter = new PhotoBitmapAdapter(getContext(),photos);
        gridAdapter.setNotifyOnChange(true);
        photoGrid.setAdapter(gridAdapter);

        Button takeNewButton = photoSelectorView.findViewById(R.id.fragmentPhotoSelector_TakeNewButton);
        Button addExistingButton = photoSelectorView.findViewById(R.id.fragmentPhotoSelector_AddExistingButton);
        takeNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        addExistingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchChoosePictureIntent();
            }
        });

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(photoSelectorView)
                .setPositiveButton(R.string.fragmentPhotoSelector_ConfirmButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCallback.onPhotoSet(photos, editorId);

                    }
                })
                .setNegativeButton(R.string.fragmentPhotoSelector_CancelButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PhotoSelectorFragment.this.getDialog().cancel();
                    }
                });

        return dialogBuilder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (PhotoSelectorFragment.OnPhotosSetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement OnPhotosSetListener");
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void dispatchChoosePictureIntent() {
        Intent choosePictureIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (choosePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(choosePictureIntent, REQUEST_LOAD_IMAGE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(getContext(),"Photo Added",Toast.LENGTH_SHORT);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            photos.add(imageBitmap);
        }
        else if (requestCode == REQUEST_LOAD_IMAGE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            Bitmap imageBitmap = null;
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
            }
            catch (IOException e) {
                Toast.makeText(getContext(),"Couldn't Load Image",Toast.LENGTH_SHORT);
            }
            if (photos != null) {
                photos.add(imageBitmap);
            }
        }
    }
}