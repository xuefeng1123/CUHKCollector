package hk.edu.cuhk.ie.iems5722.cuhkcollector.ui.eventMap;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.List;

import hk.edu.cuhk.ie.iems5722.cuhkcollector.Entity.MyEvent;
import hk.edu.cuhk.ie.iems5722.cuhkcollector.MainActivity;
import hk.edu.cuhk.ie.iems5722.cuhkcollector.MapsActivity;
import hk.edu.cuhk.ie.iems5722.cuhkcollector.R;
import hk.edu.cuhk.ie.iems5722.cuhkcollector.Service.CloudAnchorService;
import hk.edu.cuhk.ie.iems5722.cuhkcollector.databinding.FragmentEventMapBinding;
import hk.edu.cuhk.ie.iems5722.cuhkcollector.network.Client;

public class EventMapFragment extends Fragment implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {

    private EventMapViewModel eventMapViewModel;
    public FragmentEventMapBinding binding;

    public NavController navController;
    static public GoogleMap mMap;
    public MapView mMapView;
    // The entry point to the Places API.
    private PlacesClient placesClient;
    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient fusedLocationProviderClient;

    public List<Marker> markers;

    static public Location lastKnownLocation;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        eventMapViewModel =
                new ViewModelProvider(this).get(EventMapViewModel.class);

        binding = FragmentEventMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        NavHostFragment navHostFragment =
                (NavHostFragment) EventMapFragment.this.getActivity().getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment_activity_main);
        navController = navHostFragment.getNavController();

        //初始化两个静态变量
        markers = new ArrayList<>();
        lastKnownLocation = new Location("curr");
        lastKnownLocation.setLatitude(defaultLocation.latitude);
        lastKnownLocation.setLongitude(defaultLocation.longitude);

        mMapView = binding.eventMap.findViewById(R.id.event_map);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();// needed to get the map to display immediately
        try {
            MapsInitializer.initialize(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        int errorCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this.getActivity());

        if (ConnectionResult.SUCCESS != errorCode) {
            GooglePlayServicesUtil.getErrorDialog(errorCode,
                    this.getActivity(), 0).show();
        } else {
            mMapView.getMapAsync(this::onMapReady);
        }


        // Construct a PlacesClient
        Places.initialize(getActivity().getApplicationContext(), getString(R.string.maps_api_key));
        placesClient = Places.createClient(getActivity());

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        return root;
    }

    @Override
    public void onStart() {
        setHasOptionsMenu(true);
        super.onStart();
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(getString(R.string.title_event_map));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public void showAllMarker() {
        MyEvent m1 = new MyEvent(22.4135636, 114.2092091, 20, "大学站", "地铁站");
        MyEvent m2 = new MyEvent(22.4180471, 114.206987, 140, "何善衡工程学大楼", "教学楼");
        MyEvent m3 = new MyEvent(22.4231945, 114.2007411, 300, "逸夫书院", "教学楼，住宿区");

        List<MyEvent> myEvents = new ArrayList<>();
        myEvents.add(m1);
        myEvents.add(m2);
        myEvents.add(m3);

        myEvents.forEach(myEvent -> {
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(myEvent.latitude, myEvent.longitude))
                    .title(myEvent.title)
                    .snippet(myEvent.snippet)
                    .icon(BitmapDescriptorFactory.defaultMarker(myEvent.colorIndex)));
            markers.add(marker);
        });


    }

    public void updateEventMarker(List<MyEvent> events){
        //先清除地图上现有的标记
        markers.forEach(Marker::remove);
        markers.clear();
        //添加更新的标记
        events.forEach(event ->{
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(event.latitude, event.longitude))
                    .title(event.title)
                    .snippet(event.snippet)
                    .icon(BitmapDescriptorFactory.defaultMarker(event.colorIndex)));
            markers.add(marker);
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap mMap) {
        this.mMap = mMap;
        updateLocationUI();

        getDeviceLocation();
        this.mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(@NonNull Marker marker) {
                Bundle bundle = new Bundle();
                bundle.putString("eventId", marker.getId());
                navController.navigate(R.id.eventDetailFragment, bundle);
            }
        });

        this.mMap.setOnMyLocationClickListener(new GoogleMap.OnMyLocationClickListener() {
            @Override
            public void onMyLocationClick(@NonNull Location location) {
                Toast.makeText(EventMapFragment.this.getActivity(), "Current location:\n" + location, Toast.LENGTH_LONG).show();
            }
        });

        this.mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                Toast.makeText(EventMapFragment.this.getActivity(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        Client.getEventRequest(getActivity());
        //启动定时服务,检测与事件锚点的距离
        getActivity().startService(new Intent(getContext(), CloudAnchorService.class));
    }

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean locationPermissionGranted;
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(requireActivity().getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        locationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    private void updateLocationUI() {

        if (mMap == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private static final int DEFAULT_ZOOM = 15;
    private static final String TAG = MapsActivity.class.getSimpleName();
    private final LatLng defaultLocation = new LatLng(22.4257427,114.2033727);
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this.getActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
//                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
//                                        new LatLng(defaultLocation.latitude,
//                                                defaultLocation.longitude), DEFAULT_ZOOM));
                            }

                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(new LatLng(lastKnownLocation.getLatitude(),
                                            lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                        showAllMarker();
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }
}