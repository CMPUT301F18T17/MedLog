package cs.ualberta.ca.medlog.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import cs.ualberta.ca.medlog.R;

/**
 * <p>
 *     Description: <br>
 *         The Activity for a Patient problem searching screen, this presents the gui for the
 *         Patient to search through their problems and records using keywords.
 *         Additional functionality is present to assign a certain region or body location to
 *         search around with the given keywords.
 * </p>
 * <p>
 *     Issues: <br>
 *         Add code for an array adapter to connect to a provided search results list.
 *         Add code to query data for a new list to display
 *         Transfer to a Map Location Selector Fragment must be added.
 *         Transfer to a Body Location Selector Fragment must be added.
 *         Transfer to a Problem or Record View by clicking them must be added.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.1
 * @see PatientMenuActivity
 */
public class PatientSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_search);
        Button searchButton = findViewById(R.id.activityPatientSearch_SearchButton);
        Button mapLocationButton = findViewById(R.id.activityPatientSearch_MapLocationButton);
        Button bodyLocationButton = findViewById(R.id.activityPatientSearch_BodyLocationButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiateSearch();
            }
        });
        mapLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapLocationSelector();
            }
        });
        bodyLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBodyLocationSelector();
            }
        });

        //TODO Add code for an array adapter to connect to a provided search results list

        ListView resultsListView = findViewById(R.id.activityPatientSearch_ResultsListView);
        resultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openItemView(position);
            }
        });
    }

    private void initiateSearch() {
        //TODO Add code for performing the search and updating the array adapter of the results list.
    }

    private void openMapLocationSelector() {
        //TODO Add code to open a map location selector fragment.
    }

    private void openBodyLocationSelector() {
        //TODO Add code to open a body location selector fragment.
    }

    private void openItemView(int index) {
        //TODO Add code to open the given problem or records view.
    }
}
