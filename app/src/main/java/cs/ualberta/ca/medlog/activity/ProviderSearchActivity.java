package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.entity.BodyLocation;
import cs.ualberta.ca.medlog.entity.MapLocation;
import cs.ualberta.ca.medlog.entity.Problem;
import cs.ualberta.ca.medlog.entity.SearchResult;
import cs.ualberta.ca.medlog.entity.user.CareProvider;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.helper.Database;
import cs.ualberta.ca.medlog.singleton.AppStatus;

/**
 * <p>
 *     Description: <br>
 *         The Activity for a Care Provider problem searching screen, this presents the gui for the
 *         Care Provider to search through their Patients problems and records using keywords.
 *         Additional functionality is present to assign a certain region or body location to
 *         search around with the given keywords.
 * </p>
 * <p>
 *     Issues: <br>
 *         Using a map location in the search must be added.
 *         Using a body location in the search must be added.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.6
 * @see ProviderMenuActivity
 * @see ProviderPatientProfileActivity
 * @see ProviderProblemViewActivity
 * @see ProviderRecordViewActivity
 * @see ProviderSearchActivity
 * @see AddMapLocationActivity
 * @see AddBodyLocationActivity
 */
public class ProviderSearchActivity extends AppCompatActivity {
    final int MAP_LOCATION_REQUEST = 1;
    final int BODY_LOCATION_REQUEST = 2;

    private ArrayList<SearchResult> searchResults;
    private SearchAdapter adapter;

    private MapLocation mapLocation;
    private BodyLocation bodyLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_search);

        Button searchButton = findViewById(R.id.activityProviderSearch_SearchButton);
        Button mapLocationButton = findViewById(R.id.activityProviderSearch_MapLocationButton);
        Button bodyLocationButton = findViewById(R.id.activityProviderSearch_BodyLocationButton);
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

        ListView resultsListView = findViewById(R.id.activityProviderSearch_ResultsListView);
        resultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openItemView(position);
            }
        });

        adapter = new SearchAdapter(this, new ArrayList<SearchResult>());

        resultsListView.setAdapter(adapter);
    }

    private void initiateSearch() {
        Database db = new Database(this);
        EditText et = findViewById(R.id.activityProviderSearch_KeywordEditText);

        ArrayList<String> keywords = new ArrayList<>(Arrays.asList(et.getText().toString().split(" ")));

        // Clear the adapter
        adapter.clear();

        //TODO: Map and Body Location need to be selectable.
        searchResults = db.searchCareProvider((CareProvider) AppStatus.getInstance().getCurrentUser(), keywords, null,  null);
        adapter.addAll(searchResults);
        adapter.notifyDataSetChanged();
    }

    private void openMapLocationSelector() {
        Intent intent = new Intent(this, AddMapLocationActivity.class);
        startActivityForResult(intent,MAP_LOCATION_REQUEST);
    }

    private void openBodyLocationSelector() {
        //TODO We might need more here
        Toast.makeText(this,"No body photos present",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == MAP_LOCATION_REQUEST) {
            if (resultCode == RESULT_OK) { // If a map location was selected
                double latitude = data.getDoubleExtra("Latitude", -1);
                double longitude = data.getDoubleExtra("Longitude", -1);
                Toast.makeText(this, R.string.activityPatientAddRecordActivity_MapLocationAdded, Toast.LENGTH_SHORT).show();
                mapLocation = new MapLocation(latitude,longitude);
            }
            else { // If the select location button was tapped, but the user never selected a position on the map
                Toast.makeText(this, R.string.activityPatientAddRecordActivity_NoLocationAdded, Toast.LENGTH_LONG).show();
            }
        }
        else if (requestCode == BODY_LOCATION_REQUEST) {
            if (resultCode == RESULT_OK) {
                bodyLocation = (BodyLocation)data.getSerializableExtra("BODY_LOCATION");
                Toast.makeText(this,"Body Location added",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openItemView(int index) {
        SearchResult selected = searchResults.get(index);
        AppStatus.getInstance().setViewedPatient(selected.getPatient());

        if (selected.getRecord() == null) {
            Intent intent = new Intent(this,ProviderProblemViewActivity.class);
            AppStatus.getInstance().setViewedProblem(selected.getProblem());
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this,ProviderRecordViewActivity.class);
            AppStatus.getInstance().setViewedProblem(selected.getProblem());
            AppStatus.getInstance().setViewedRecord(selected.getRecord());
            startActivity(intent);
        }
    }
}
