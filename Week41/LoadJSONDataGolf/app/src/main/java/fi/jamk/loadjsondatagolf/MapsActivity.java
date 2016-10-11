package fi.jamk.loadjsondatagolf;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private JSONArray golfCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        FetchDataTask task = new FetchDataTask();
        task.execute("http://ptm.fi/jamk/android/golf_courses.json");

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        // store map object to member variable
        mMap = googleMap;
        // set map type
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


    }

    class FetchDataTask extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... urls) {
            HttpURLConnection urlConnection = null;
            JSONObject json = null;
            try {
                URL url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                json = new JSONObject(stringBuilder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) urlConnection.disconnect();
            }

            return json;
        }

        protected void onPostExecute(JSONObject json) {
            try {
                BitmapDescriptor bitmapDescriptor = null;
                golfCourses = json.getJSONArray("kentat");
                for (int i = 0; i < golfCourses.length(); i++) {
                    JSONObject golfJson = golfCourses.getJSONObject(i);
                    LatLng latlng = new LatLng(golfJson.getDouble("lat"), golfJson.getDouble("lng"));
                    if (golfJson.getString("Tyyppi").contains("?")) {
                        bitmapDescriptor = (BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                    }
                    if (golfJson.getString("Tyyppi").contains("Etu")) {
                        bitmapDescriptor = (BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    }
                    if (golfJson.getString("Tyyppi").contains("Kulta")) {
                        bitmapDescriptor = (BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
                    }
                    if (golfJson.getString("Tyyppi").contains("Kulta/Etu")) {
                        bitmapDescriptor = (BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    }
                    final Marker markers = mMap.addMarker(new MarkerOptions()
                            .position(latlng)
                            .title(golfJson.getString("Kentta"))
                            .snippet(golfJson.getString("Osoite") + "\n" + golfJson.getString("Puhelin") + "\n"
                                    + golfJson.getString("Sahkoposti") + "\n" + golfJson.getString("Webbi"))
                            .icon(bitmapDescriptor));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 5));


                    //Toast.makeText(getApplicationContext(), golfJson.getString("Tyyppi"), Toast.LENGTH_SHORT).show();
                }
                mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                    @Override
                    public View getInfoWindow(Marker arg0) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {

                        Context context = getApplicationContext(); //or getActivity(), YourActivity.this, etc.

                        LinearLayout info = new LinearLayout(context);
                        info.setOrientation(LinearLayout.VERTICAL);

                        TextView title = new TextView(context);
                        title.setTextColor(Color.BLACK);
                        title.setGravity(Gravity.CENTER);
                        title.setTypeface(null, Typeface.BOLD);
                        title.setText(marker.getTitle());

                        TextView snippet = new TextView(context);
                        snippet.setTextColor(Color.GRAY);
                        snippet.setText(marker.getSnippet());

                        info.addView(title);
                        info.addView(snippet);

                        return info;
                    }
                });
            } catch (JSONException e) {
                Log.e("JSON", "Error getting data.");
            }
        }
    }
}
