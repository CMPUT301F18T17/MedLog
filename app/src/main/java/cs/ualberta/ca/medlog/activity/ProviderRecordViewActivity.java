package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cs.ualberta.ca.medlog.R;

/**
 * <p>
 *     Description: <br>
 *         The Activity for the Care Provider patient record view screen, this presents the gui for
 *         the Provider to view one of their patient's records data in terms of the problem that
 *         owns it, the timestamp of its creation and the user that created it.
 *         Further ability is present to open fragments to view the four fields a problem can
 *         contain of a title & comment, a body location, a map location and an attached
 *         slideshow.
 *         Additionally if the creator is a Patient the username can be clicked to travel to the
 *         patient profile.
 * </p>
 * <p>
 *     Issues: <br>
 *         Actual code to read a record and present it must be added.
 *         Transfer to a title & comment fragment must be added
 *         Transfer to a body location fragment must be added
 *         Transfer to a map location fragment must be added
 *         Transfer to a photos slideshow fragment must be added
 *         Actual code to pass the specific patient owner as an argument must be added on username click.
 *
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.1
 * @see ProviderPatientViewRecordsActivity
 * @see ProviderSearchActivity
 * @see ProviderPatientProfileActivity
 */
public class ProviderRecordViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_record_view);
        Button titleCommentButton = findViewById(R.id.activityProviderRecordView_TitleCommentButton);
        Button bodyLocationButton = findViewById(R.id.activityProviderRecordView_BodyLocationButton);
        Button mapLocationButton = findViewById(R.id.activityProviderRecordView_MapLocationButton);
        Button slideshowButton = findViewById(R.id.activityProviderRecordView_SlideshowButton);
        titleCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTitleCommentFragment();
            }
        });
        bodyLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBodyLocationFragment();
            }
        });
        mapLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapLocationFragment();
            }
        });
        slideshowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSlideshowFragment();
            }
        });

        TextView creatorView = findViewById(R.id.activityProviderRecordView_CreatorView);
        creatorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPatientProfile();
            }
        });

        //TODO Add code to receive a provided record object and set the related fields to its data.
    }

    private void openTitleCommentFragment() {
        //TODO Add transfer to title & comment fragment
    }

    private void openBodyLocationFragment() {
        //TODO Add transfer to body location fragment
    }

    private void openMapLocationFragment() {
        //TODO Add transfer to map location fragment
    }

    private void openSlideshowFragment() {
        //TODO Add transfer to slideshow fragment
    }

    private void openPatientProfile() {

        Intent intent = new Intent(this, ProviderPatientProfileActivity.class);

        //TODO Add argument code to pass the problems record list.

        startActivity(intent);
    }
}
