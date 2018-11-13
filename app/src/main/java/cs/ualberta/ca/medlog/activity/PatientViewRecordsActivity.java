package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.entity.Problem;
import cs.ualberta.ca.medlog.entity.Record;

/**
 * <p>
 *     Description: <br>
 *         The patient view records screen for the Application, this presents a gui for a patient
 *         to view a list of the records attached to a problem and proceed to view a given one
 *         further by clicking on them.
 * </p>
 * <p>
 *     Issues: <br>
 *         System call to retrieve the records of a specific patient problem index must be added.
 *         Setting the records arrayList to what was returned must be added.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.3
 * @see PatientProblemViewActivity
 * @see PatientRecordViewActivity
 */
public class PatientViewRecordsActivity extends AppCompatActivity {


    private ArrayList<Record> records;
    int problemIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view_records);

        Intent intent = getIntent();
        problemIndex = intent.getIntExtra("problemIndex",0);
        //TODO Call to the system to retrieve the records associated with the given problem.
        //TODO Setting the records ArrayList to the returned problem's records list

        ListView recordsListView = findViewById(R.id.activityPatientViewRecords_RecordsListView);
        recordsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openRecordView(position);
            }
        });

        ArrayAdapter<Record> recordArrayAdapter = new ArrayAdapter<Record>(this,0,records);
        recordsListView.setAdapter(recordArrayAdapter);
    }

    private void openRecordView(int index) {
        Intent intent = new Intent(this, PatientRecordViewActivity.class);
        intent.putExtra("problemIndex",problemIndex);
        intent.putExtra("recordIndex",index);
        startActivity(intent);
    }
}
