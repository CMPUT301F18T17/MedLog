package cs.ualberta.ca.medlog.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import cs.ualberta.ca.medlog.R;

/**
 * <p>
 *     Description: <br>
 *         The Activity for adding a map location to a record. It presents a Mapbox map and the user
 *         can select a location on the map. Once the user selects a location, the location is sent
 *         back to the PatientAddRecordActivity and the PatientAddMapLocationActivity closes.
 *
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Calvin Chomyc
 * @version 0.2
 * @see PatientAddRecordActivity
 */

//TODO Add a button to confirm a location choice.
// If a chosen location is confirmed, we should save it. clickedPosition contains the last clicked position.
//TODO We should set the string values properly.
//TODO At the moment, there is one marker placed by default that is unrelated to clicks.
// This is just for testing, although we should have a marker placed by default when viewing a record.

public class PatientAddMapLocationActivity extends AppCompatActivity {
    private MapView mapView;
    private boolean clickMarkerSet = false;
    private Marker clickMarker;
    private LatLng clickedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoidmVyeXJhbmRvbXBlcnNvbiIsImEiOiJjam9nZmxxY3QwZW53M3FscGhzZTg4ZnA2In0.IEYLgLCNOfnIczHn1kaycQ");
        setContentView(R.layout.activity_patient_add_map_location);

        Button selectButton = findViewById(R.id.selectButton);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (clickMarkerSet) {
                    intent.putExtra("Latitude", clickedPosition.getLatitude());
                    intent.putExtra("Longitude", clickedPosition.getLongitude());
                    setResult(Activity.RESULT_OK, intent);
                }
                else {
                    setResult(Activity.RESULT_CANCELED, intent);
                }
                finish();
            }
        });

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                MarkerOptions marker1 = new MarkerOptions();
                LatLng markerPosition = new LatLng(53.5232, -113.5263);
                marker1.position(markerPosition);
                marker1.title("Testing");
                mapboxMap.addMarker(marker1);

                mapboxMap.addOnMapClickListener(new MapboxMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng point) {
                        mapClicked(point);
                    }
                });
            }
        });
    }

    public void mapClicked(@NonNull LatLng point) {
        final MarkerOptions newMarkerOptions = new MarkerOptions();
        clickedPosition = new LatLng(point.getLatitude(), point.getLongitude());
        newMarkerOptions.position(clickedPosition);
        newMarkerOptions.title("Click testing");

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                if (clickMarkerSet) { // If we already have a click marker, remove it
                    mapboxMap.removeMarker(clickMarker);
                }
                clickMarker = mapboxMap.addMarker(newMarkerOptions);
                clickMarkerSet = true;
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
