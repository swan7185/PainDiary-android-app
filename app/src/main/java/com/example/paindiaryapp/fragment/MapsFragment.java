package com.example.paindiaryapp.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.paindiaryapp.R;
import com.example.paindiaryapp.databinding.FragmentHomeBinding;
import com.example.paindiaryapp.databinding.FragmentMapBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.markerview.MarkerView;
import com.mapbox.mapboxsdk.plugins.markerview.MarkerViewManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


public class MapsFragment extends Fragment {
    private FragmentMapBinding binding;
    private LayoutInflater layoutInflater;
    private ViewGroup container;

    private MarkerView markerView;
    private MarkerViewManager markerViewManager;

    private void setMap(double lat, double lot)
    {
        binding.mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {

                final LatLng latLng= new LatLng(lat, lot);


                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        CameraPosition position = new CameraPosition.Builder().target(latLng)
                                .zoom(13)
                                .build();
                        mapboxMap.setCameraPosition(position);
                        markerViewManager = new MarkerViewManager(binding.mapView, mapboxMap);
                        View customView = LayoutInflater.from(getContext()).inflate(
                                R.layout.marker_view_bubble, null);

                        customView.setLayoutParams(new FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
                        markerView = new MarkerView(latLng, customView);
                        markerViewManager.addMarker(markerView);
                    }
                });
            }


        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.layoutInflater = inflater;
        this.container = container;
        super.onCreate(savedInstanceState);

        String token = getString(R.string.mapbox_access_token);
        Mapbox.getInstance(this.getContext(), token);
        binding = FragmentMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//lat and long are hardcoded here but could be provided at run time
        final LatLng latLng= new LatLng(-37.876823, 145.045837);

        binding.mapView.onCreate(savedInstanceState);

        setMap(latLng.getLatitude(), latLng.getLongitude());

        binding.mapSearchButton.setOnClickListener((View view) ->{
            String address = binding.mapViewEdit.getText().toString();
            if(address == null) {
                binding.addressEditorLayout.setError("please enter your address");
            }
            //123 Main St Boston MA 02111
            MapboxGeocoding mapboxGeocoding = MapboxGeocoding.builder()
                    .accessToken(token)
                    .query(address)
                    .build();

            mapboxGeocoding.enqueueCall(new Callback<GeocodingResponse>() {
                @Override
                public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {

                    List<CarmenFeature> results = response.body().features();

                    if (results.size() > 0) {

                        // Log the first results Point.
                        Point firstResultPoint = results.get(0).center();
                        setMap(firstResultPoint.latitude(), firstResultPoint.longitude());
                        //Log.d(TAG, "onResponse: " + firstResultPoint.toString());

                    } else {
                        binding.addressEditorLayout.setError("wrong address");
                    }
                }

                @Override
                public void onFailure(Call<GeocodingResponse> call, Throwable throwable) {
                    binding.addressEditorLayout.setError("wrong address");
                    //throwable.printStackTrace();
                }
            });
        });
        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        markerViewManager.onDestroy();
        binding.mapView.onDestroy();
    }


    @Override
    public void onStart() {
        super.onStart();
        binding.mapView.onStart(); }
    @Override
    public void onResume() {
        super.onResume();
        binding.mapView.onResume(); }
    @Override
    public void onPause() {
        super.onPause();
        binding.mapView.onPause(); }
    @Override
    public void onStop() {
        super.onStop();
        binding.mapView.onStop(); }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        binding.mapView.onSaveInstanceState(outState); }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        binding.mapView.onLowMemory(); }


    private void makeGeocodeSearch(final LatLng latLng) {
        try {
        }
        catch (Exception e)
        {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        markerViewManager.onDestroy();
        binding.mapView.onDestroy();
    }
}
