package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.entity.MapLocation;
import cs.ualberta.ca.medlog.entity.Record;
import cs.ualberta.ca.medlog.singleton.AppStatus;

/**
 * <p>
 *     Description: <br>
 *         The patient record viewing activity screen for the Application, this presents the gui
 *         for the patient to view one of their record's data of the title of the problem that owns
 *         it, the timestamp of its creation and the user that created it.
 *         Additional information is present in the fragments/activities the patient can proceed to
 *         where they can view the record title & comment, body location, map location or any
 *         attached photos in a slideshow.
 * </p>
 * <p>
 *     Issues: <br>
 *         Transfer to a body location fragment must be added
 *
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.7
 * @see PatientViewRecordsActivity
 * @see PatientSearchActivity
 * @see SlideshowActivity
 * @see ViewMapLocationActivity
 * @see PopupWindow
 */
public class PatientRecordViewActivity extends AppCompatActivity {
    private Record record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_record_view);

        record = AppStatus.getInstance().getViewedRecord();

        ColorStateList colorStateList = new ColorStateList(new int[][]{new int[0]}, new int[]{getResources().getColor(R.color.app_buttonColourInvalid)});
        Button titleCommentButton = findViewById(R.id.activityPatientRecordView_TitleCommentButton);
        Button bodyLocationButton = findViewById(R.id.activityPatientRecordView_BodyLocationButton);
        Button mapLocationButton = findViewById(R.id.activityPatientRecordView_MapLocationButton);
        Button slideshowButton = findViewById(R.id.activityPatientRecordView_SlideshowButton);
        titleCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTitleCommentFragment();
            }
        });
        if (record.getTitle() == null && record.getComment() == null) {
            titleCommentButton.setClickable(false);
            titleCommentButton.setBackgroundTintList(colorStateList);
        }
        bodyLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBodyLocationFragment();
            }
        });
        if (record.getBodyLocation() == null) {
            bodyLocationButton.setClickable(false);
            bodyLocationButton.setBackgroundTintList(colorStateList);
        }
        mapLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapLocationFragment();
            }
        });
        if (record.getMapLocation() == null) {
            mapLocationButton.setClickable(false);
            mapLocationButton.setBackgroundTintList(colorStateList);
        }
        slideshowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSlideshowFragment();
            }
        });
        if (record.getPhotos().isEmpty()) {
            slideshowButton.setClickable(false);
            slideshowButton.setBackgroundTintList(colorStateList);
        }

        TextView recordTitleView = findViewById(R.id.activityPatientRecordView_TitleView);
        recordTitleView.setText(record.getTitle());
        TextView problemTitleView = findViewById(R.id.activityPatientRecordView_ProblemTitleView);
        problemTitleView.setText(AppStatus.getInstance().getViewedProblem().getTitle());
        TextView creatorView = findViewById(R.id.activityPatientRecordView_CreatorView);
        creatorView.setText(record.getUsername());
        TextView timestampView = findViewById(R.id.activityPatientRecordView_TimestampView);
        timestampView.setText(record.getTimestamp().toString());
    }

    private void openTitleCommentFragment() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View titleCommentLayout = inflater.inflate(R.layout.fragment_title_comment,null);
        TextView titleView = titleCommentLayout.findViewById(R.id.fragmentTitleComment_TitleView);
        titleView.setText(record.getTitle());
        TextView commentView = titleCommentLayout.findViewById(R.id.fragmentTitleComment_CommentView);
        commentView.setText(record.getComment());

        int width = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        int height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        PopupWindow titleCommentPopup = new PopupWindow(titleCommentLayout,width,height,true);
        titleCommentPopup.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.popup_background));
        titleCommentPopup.showAtLocation(titleCommentLayout,Gravity.CENTER,0,0);
    }

    private void openBodyLocationFragment() {
        //TODO Add transfer to body location fragment
    }

    private void openMapLocationFragment() {
        Intent intent = new Intent(this, ViewMapLocationActivity.class);
        ArrayList<MapLocation> newList = new ArrayList<>();
        newList.add(record.getMapLocation());
        intent.putExtra("LOCATIONS", newList);
        startActivity(intent);
    }

    private void openSlideshowFragment() {
        Intent intent = new Intent(this, SlideshowActivity.class);
        intent.putExtra("PHOTOS", record.getPhotos());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        AppStatus.getInstance().setViewedRecord(null);
        super.onBackPressed();
    }
}
