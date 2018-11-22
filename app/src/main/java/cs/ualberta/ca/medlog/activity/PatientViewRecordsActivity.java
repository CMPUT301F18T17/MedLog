package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.entity.Record;
import cs.ualberta.ca.medlog.singleton.AppStatus;

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
    private ArrayList<Record> records;
    private RecordAdapter recordArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view_records);

        records = AppStatus.getInstance().getViewedProblem().getRecords();

        ListView recordsListView = findViewById(R.id.activityPatientViewRecords_RecordsListView);
        recordsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openRecordView(position);
            }
        });

        recordArrayAdapter = new RecordAdapter(this,records);
        recordsListView.setAdapter(recordArrayAdapter);
    }

    @Override
    protected void onStart() {
        recordArrayAdapter.notifyDataSetChanged();
        super.onStart();
    }

    private void openRecordView(int index) {
        Intent intent = new Intent(this, PatientRecordViewActivity.class);
        AppStatus.getInstance().setViewedRecord(records.get(index));
        startActivity(intent);
    }
}
