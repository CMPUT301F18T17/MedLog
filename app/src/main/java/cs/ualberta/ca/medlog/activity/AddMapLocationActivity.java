package cs.ualberta.ca.medlog.activity;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
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
 *         back to the parent activity and the AddMapLocationActivity closes.
 *
 * </p>
 * <p>
 *     Issues: <br>
 *         May be possible to force the users location to update.
 * </p>
 *
 * @author Calvin Chomyc
 * @version 1.2
 * @see PatientAddRecordActivity
 * @see PatientSearchActivity
 */
public class AddMapLocationActivity extends AppCompatActivity {
    private LatLng userLocation;
    private MapView mapView;
    private Marker clickMarker;
    private LatLng clickedPosition;
    private FusedLocationProviderClient locationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapboxAccessToken));
        setContentView(R.layout.activity_add_map_location);

        mapView = findViewById(R.id.activityAddMapLocation_MapView);
        mapView.onCreate(savedInstanceState);

        Button selectButton = findViewById(R.id.activityAddMapLocation_AddButton);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (clickMarker != null) {
                    intent.putExtra("LATITUDE", clickedPosition.getLatitude());
                    intent.putExtra("LONGITUDE", clickedPosition.getLongitude());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),R.string.activityAddMapLocation_NoLocationSet,Toast.LENGTH_SHORT).show();
                }
            }
        });

        try { // Set location to user's last known location
            locationClient = LocationServices.getFusedLocationProviderClient(this);
            locationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    }
                }
            });
        }
        catch(SecurityException e) { // If the location permission is not enabled, use default location
        }

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                if (userLocation != null) {
                   mapboxMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
                }

                mapboxMap.addOnMapClickListener(new MapboxMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng point) {
                        placeMarker(point);
                    }
                });
            }
        });
    }

    /**
     * <p>Places a marker on the map at the specified location. If a marker already exists, moves the marker to a new location.</p>
     * @param point An object that contains latitude and longitude coordinates specifying where to place the marker.
     */
    public void placeMarker(@NonNull LatLng point) {
        clickedPosition = new LatLng(point.getLatitude(), point.getLongitude());
        final MarkerOptions newMarkerOptions = new MarkerOptions();
        newMarkerOptions.position(clickedPosition);
        String markerTitle = getString(R.string.activityAddMapLocation_LocationTitleDefault);
        newMarkerOptions.title(markerTitle);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                if (clickMarker != null) { // If we already have a click marker, remove it
                    clickMarker.setPosition(clickedPosition);
                }
                else {
                    clickMarker = mapboxMap.addMarker(newMarkerOptions);
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
