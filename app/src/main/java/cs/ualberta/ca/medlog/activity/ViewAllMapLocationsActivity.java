package cs.ualberta.ca.medlog.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.entity.MapLocation;
import cs.ualberta.ca.medlog.entity.Problem;
import cs.ualberta.ca.medlog.entity.Record;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.singleton.AppStatus;

/**
 * <p>
 *     Description: <br>
 *         The Activity for the view all map locations screen. This presents the gui of a MapBox map
 *         from which a user can see markers at all of the current patient's map locations.
 * </p>
 * <p>
 *     Issues: <br>
 *         None
 *
 * </p>
 *
 * @author Calvin Chomyc
 * @version 1.0
 * @see PatientProfileActivity
 * @see ProviderPatientProfileActivity
 */

public class ViewAllMapLocationsActivity extends AppCompatActivity {
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapboxAccessToken));
        setContentView(R.layout.activity_view_all_map_locations);
        mapView = findViewById(R.id.activityViewAllMapLocations_mapView);
        mapView.onCreate(savedInstanceState);

        Patient currentPatient;
        if (AppStatus.getInstance().getCurrentUser() instanceof Patient) {
            currentPatient = (Patient)AppStatus.getInstance().getCurrentUser();
        }
        else {
            currentPatient = AppStatus.getInstance().getViewedPatient();
        }

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                for (Problem problem : currentPatient.getProblems()) {
                    for (Record record : problem.getRecords()) {
                        MapLocation location = record.getMapLocation();
                        if (location != null) {
                            LatLng recordLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(recordLocation);
                            markerOptions.title(record.getTimestamp().toString());
                            mapboxMap.addMarker(markerOptions);
                            mapboxMap.moveCamera(CameraUpdateFactory.newLatLng(recordLocation));
                        }
                    }
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
}
