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
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 1.0
 * @see PatientProblemViewActivity
 * @see PatientRecordViewActivity
 */
public class PatientViewRecordsActivity extends AppCompatActivity {
    private String problemTitle;
    private ArrayList<Record> records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view_records);

        Intent intent = getIntent();
        problemTitle = intent.getStringExtra("PROBLEM_TITLE");
        records = (ArrayList<Record>) intent.getSerializableExtra("RECORDS");

        ListView recordsListView = findViewById(R.id.activityPatientViewRecords_RecordsListView);
        recordsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openRecordView(position);
            }
        });

        ArrayAdapter<Record> recordArrayAdapter = new ArrayAdapter<>(this,0,records);
        recordsListView.setAdapter(recordArrayAdapter);
    }

    private void openRecordView(int index) {
        Intent intent = new Intent(this, PatientRecordViewActivity.class);
        intent.putExtra("PROBLEM_TITLE",problemTitle);
        intent.putExtra("RECORD",records.get(index));
        startActivity(intent);
    }
}
