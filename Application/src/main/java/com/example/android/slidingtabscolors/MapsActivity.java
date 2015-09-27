package com.example.android.slidingtabscolors;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    LocationManager lm;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Location location;
    GoogleApiClient mGoogleApiClient;
    private OkHttpClient client = new OkHttpClient();
    private ArrayList<MarkerOptions> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        list = new ArrayList<>();


    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location newLoc) {
        location = newLoc;
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
//        mMap.addMarker(new MarkerOptions().position(new LatLng(50, 200)).title("Marker"));

        /////----------------------------------Zooming camera to position user-----------------
        mMap.setMyLocationEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(38, -80), 3.0f));
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            Log.e("error", "GPS NOT ENABLED");
            return;
        }
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Toast.makeText(MapsActivity.this, "Loading map...", Toast.LENGTH_SHORT).show();

        String provider = lm.getBestProvider(new Criteria(), false);

        lm.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 0, 0, new android.location.LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {

                        if (getLocation() == null) {

                            Toast.makeText(MapsActivity.this, "Finding your location...", Toast.LENGTH_SHORT).show();

                            setLocation(location);
                            try {
                                Log.e("starting", "run()");
                                run();
                            } catch (Exception e) {
                                Log.e("Error in", "run():" + e);
                            }


//
//                            CameraPosition cameraPosition = new CameraPosition.Builder()
//                                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
//                                    .zoom(14)                   // Sets the zoom
////                                .bearing(0)                // Sets the orientation of the camera to east
////                                .tilt(45)                   // Sets the tilt of the camera to 30 degrees
//                                    .build();                   // Creates a CameraPosition from the builder
//
//                            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                            Log.e("location after update", location + "");
                            Log.e("moving", "camera");
                        }

                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    @Override
                    public void onProviderDisabled(String s) {

                    }
                });
        location = lm.getLastKnownLocation(provider); // TODO: Handle denied permission

//        mMap.getMyLocation().toString(); <---- NULLPOINTER AND I DON'T KNOW HOW TO FIX IT...


        Log.e("location", location + "");


        //get a ton of data from the googles....


    }

    double lon;
    double lat;
    String title;
    int j;

    public void run() throws Exception {
        String url = "";
        try {
            url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + location.getLatitude() + "," + location.getLongitude() + "&radius=7500&keyword=urgent hospital health&key=AIzaSyD96AdOiiTK_TerD-CNT-mLpEw2zXuq8ak";
        } catch (NullPointerException e) {
            Log.e("NULL CAUGHT", "FUCK");
            return;
        }
        Log.e("url:", url);


        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("Error", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.e("response", response.body().string());
                    throw new IOException("Unexpected code " + response);
                }

                Log.e("response", "received");
//                Headers responseHeaders = response.headers();
//                for (int i = 0; i < responseHeaders.size(); i++) {
//                    Log.e("headers",responseHeaders.name(i) + ": " + responseHeaders.value(i));
//                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MapsActivity.this, "Finding nearby medical help...", Toast.LENGTH_SHORT).show();
                    }
                });

                try {
                    JSONObject json = new JSONObject(response.body().string());
                    Log.e("gets", "past here");
                    JSONArray jArray = json.getJSONArray("results");

                    for (int i = 0; i < jArray.length(); i++) {

                        lon = Double.valueOf(((JSONObject) jArray.get(i)).getJSONObject("geometry").getJSONObject("location").getString("lng"));
                        lat = Double.valueOf(((JSONObject) jArray.get(i)).getJSONObject("geometry").getJSONObject("location").getString("lat"));


                        title = ((JSONObject) jArray.get(i)).getString("name");
                        Log.e(i + ". " + title, lat + ", " + lon);
                        list.add(new MarkerOptions()
                                .position(new LatLng(lat, lon))
                                .title(title));
                    }


                } catch (JSONException e) {
                    Log.e("JSONException", e.getMessage());
                }

                Log.e("list size", list.size() + "");
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... voids) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < list.size(); i++) {

                                    mMap.addMarker(list.get(i));
                                }
                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                                        .zoom(11)                   // Sets the zoom
//                                .bearing(0)                // Sets the orientation of the camera to east
//                                .tilt(45)                   // Sets the tilt of the camera to 30 degrees
                                        .build();                   // Creates a CameraPosition from the builder

                                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                            }
                        });
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        Log.e("Complete", "markers");
                    }
                }.execute();


            }
        });
    }

    @Override
    public void onConnected(Bundle bundle) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("blabla", "connection suspended");
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e("error", "Connection failed.");
    }


    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.e("Exception dl'ing url", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }

        return data;
    }
}
