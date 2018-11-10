package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cs.ualberta.ca.medlog.R;

/**
 * <p>
 *     Description: <br>
 *         The Activity for a photo slideshow screen, this presents the gui for a user to view a
 *         group of photos in a slideshow format.
 * </p>
 * <p>
 *     Issues: <br>
 *         Must have code to retrieve photos that are sent to view.
 *         Must have code that actually slides through a given photos list.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.1
 * @see SlideshowActivity
 */
public class SlideshowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideshow);

        Button previousButton = findViewById(R.id.activitySlideshow_PreviousButton);
        Button nextButton = findViewById(R.id.activitySlideshow_NextButton);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPreviousPhoto();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNextPhoto();
            }
        });

        //TODO Retrieve and display the first provided photo in a given photo list.
    }

    private void displayPreviousPhoto() {
        //TODO Transition to next photo.
    }

    private void displayNextPhoto() {
        //TODO Transition to previous photo.
    }
}
