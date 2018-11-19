package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import java.util.ArrayList;
import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.helper.Database;
import cs.ualberta.ca.medlog.entity.MapLocation;
import cs.ualberta.ca.medlog.entity.Record;

/**
 * <p>
 *     Description: <br>
 *         The Activity for the view map locations screen, this presents the gui of a Mapbox map
 *         from which a user can see markers at the passed map locations.
 * </p>
 * <p>
 *     Issues: <br>
 *         Should set marker titles to record timestamps.
 *
 * </p>
 *
 * @author Calvin Chomyc
 * @version 0.6
 * @see PatientRecordViewActivity
 * @see ProviderRecordViewActivity
 * @see PatientProfileActivity
 * @see ProviderPatientProfileActivity
 */
public class ViewMapLocationActivity extends AppCompatActivity {
    private MapView mapView;
    private ArrayList<MapLocation> mapLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Intent intent = getIntent();
        //int recordIndex = intent.getIntExtra("recordIndex", 0);
        //Database database = new Database(this);

        Mapbox.getInstance(this, getString(R.string.mapboxAccessToken));
        setContentView(R.layout.activity_view_map_location);

        retrieveMapLocations((ArrayList<Record>)getIntent().getSerializableExtra("RECORDS"));

        mapView = findViewById(R.id.activityViewMapLocation_MapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                for(MapLocation location: mapLocations) {
                    MarkerOptions marker1 = new MarkerOptions();
                    LatLng recordLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    marker1.position(recordLocation);
                    marker1.title("RECORD TITLE"); //TODO Should put record timestamp here.
                    mapboxMap.addMarker(marker1);
                    mapboxMap.moveCamera(CameraUpdateFactory.newLatLng(recordLocation));
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    private void retrieveMapLocations(ArrayList<Record> records) {
        mapLocations = new ArrayList<>();
        for(Record record: records) {
            if (record.getMapLocation() != null) {
                mapLocations.add(record.getMapLocation());
            }
        }
    }
}
