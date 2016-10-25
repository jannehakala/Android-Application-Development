package fi.jamk.sgkygolfcourses;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by H3298 on 10/25/2016.
 */

public class FetchDataTask extends AsyncTask<String, ArrayList<String>, JSONObject> {
    private List<Employee> employeeList;
    private JSONArray golfCourses;

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

    protected ArrayList<String> onPostExecute(JSONObject json) {
        try {
            golfCourses = json.getJSONArray("kentat");
            employeeList = new ArrayList<>();
            for (int i = 0; i < golfCourses.length(); i++) {
                JSONObject golfJson = golfCourses.getJSONObject(i);
                employeeList.add(new Employee(golfJson.getString("Kentta"), golfJson.getString("Osoite"),golfJson.getString("Sahkoposti"),golfJson.getString("Puhelin"),golfJson.getString("Kuva")));
            }
            return employeeList;
        } catch (JSONException e) {
            Log.e("JSON", "Error getting data.");
        }
    }
}

