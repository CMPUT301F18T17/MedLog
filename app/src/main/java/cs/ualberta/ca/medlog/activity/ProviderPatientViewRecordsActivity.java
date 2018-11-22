package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.entity.Record;
import cs.ualberta.ca.medlog.entity.user.CareProvider;
import cs.ualberta.ca.medlog.singleton.AppStatus;

/**
 * <p>
 *     Description: <br>
 *         The Activity for the Care Provider patient view records screen, this presents the gui for
 *         a Provider to view a list of the records attached to a problem and proceed to a given one
 *         by clicking them.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 1.0
 * @see ProviderProblemViewActivity
 */
public class ProviderPatientViewRecordsActivity extends AppCompatActivity {
    private ArrayList<Record> records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_patient_view_records);

        records = AppStatus.getInstance().getViewedProblem().getRecords();

        ListView recordsListView = findViewById(R.id.activityProviderPatientViewRecords_RecordsListView);
        recordsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openRecordView(position);
            }
        });

        RecordAdapter recordArrayAdapter = new RecordAdapter(this,records);
        recordsListView.setAdapter(recordArrayAdapter);
    }

    private void openRecordView(int index) {
        Intent intent = new Intent(this, ProviderRecordViewActivity.class);
        AppStatus.getInstance().setViewedRecord(records.get(index));
        startActivity(intent);
    }
}
