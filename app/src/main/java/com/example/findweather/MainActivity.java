package com.example.findweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AsyncResponse {

    private static final String TAG = "MainActivity";
    private Button searchButton;
    private EditText searchField;
    private TextView cityName;
    private String searchUserCity;
    private Toast toastError;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchField = findViewById(R.id.searchField);
        cityName = findViewById(R.id.cityName);

        searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(this);
    }

    @Override
    public void processFinish(String output) {
        Log.d(TAG, "processFinish: " + output);

        if (output == null) {
            int duration = Toast.LENGTH_LONG;
            if (toastError != null) {
                toastError.cancel();
            }
            toastError = Toast.makeText(this, R.string.city_not_found, duration);
            toastError.show();
            return;
        } else {
            try {
                Helper helper = new Helper();

                JSONObject resultJson = new JSONObject(output);
                JSONObject weather = resultJson.getJSONObject("main");
                JSONObject sys = resultJson.getJSONObject("sys");
                JSONObject weather_description = ((JSONObject) resultJson.getJSONArray("weather").get(0));

                TextView weather_description_in_city = findViewById(R.id.weatherValue);
                String weather_city = (String) weather_description.get("description");
                weather_description_in_city.setText(weather_city);

                TextView temp = findViewById(R.id.tempValue);
                String tempC = weather.getString("temp");
                temp.setText(tempC + " C");

                TextView pressure = findViewById(R.id.pressureValue);
                pressure.setText(weather.getString("pressure"));

                //TODO перевести дату в LocalDate
                TextView sunrise = findViewById(R.id.timeSunrise);
                String timeSunrise = sys.getString("sunrise");
                sunrise.setText(helper.epochTimeToLocaleDataTime(timeSunrise));

                //TODO перевести дату в LocalDate
                TextView sunset = findViewById(R.id.timeSunSet);
                String timeSunset = sys.getString("sunset");
                sunset.setText(helper.epochTimeToLocaleDataTime(timeSunset));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {
        searchUserCity = searchField.getText().toString();
        UrlBuilder urlBuilder = new UrlBuilder();

        URL url = urlBuilder.builderUrl(searchUserCity);
        new GetData(this).execute(url);

        cityName.setText(searchUserCity);
    }
}