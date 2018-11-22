package cs.ualberta.ca.medlog.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.entity.Problem;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.helper.Database;
import cs.ualberta.ca.medlog.singleton.AppStatus;

/**
 * <p>
 *     Description: <br>
 *         The patient problem searching screen activity for the Application, this presents the
 *         gui for the patient to search through their problems and records utilizing keywords.
 *         Additional searching information in terms of searching around a specified region or body
 *         location is also present through buttons to proceed to selection activities.
 *         The results of these searches can be viewed by clicking on their entries.
 * </p>
 * <p>
 *     Issues: <br>
 *         Transfer to a Map Location Selector Fragment must be added.
 *         Transfer to a Body Location Selector Fragment must be added.
 *         Transfer to a Problem or Record View by clicking them must be added.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.3
 * @see PatientMenuActivity
 * @see PatientProblemViewActivity
 * @see PatientRecordViewActivity
 */
public class PatientSearchActivity extends AppCompatActivity {
    private SearchAdapter adapter;

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

        ListView resultsListView = findViewById(R.id.activityPatientSearch_ResultsListView);
        resultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openItemView(position);
            }
        });

        adapter = new SearchAdapter(this, new ArrayList<Problem>());
        resultsListView.setAdapter(adapter);
    }

    private void initiateSearch() {
        Database db = new Database(this);
        EditText et = findViewById(R.id.activityPatientSearch_KeywordEditText);

        ArrayList<String> keywords = new ArrayList<>(Arrays.asList(et.getText().toString().split(" ")));
        adapter.clear();

        //TODO: Map and Body Location need to be selectable.
        adapter.addAll(db.searchPatient((Patient)AppStatus.getInstance().getCurrentUser(), keywords, null,  null));
        adapter.notifyDataSetChanged();
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
