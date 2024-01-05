package utils;

import Adapters.LocalDateAdapter;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.Forecast;
import model.Local;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WeatherAPI {

    public static List<Forecast> getForecastList(int localGlobalId){
        List<Forecast> listForecast = new ArrayList<>();
        // Create the client to make the call
        OkHttpClient client = new OkHttpClient();
        // Retrieve the local id;
        // 2. Create the request to obtain info from API
        Request getForecastRequest = new Request.Builder()
                .url("https://api.ipma.pt/open-data/forecast/meteorology/cities/daily/"+localGlobalId+".json")
                .build();
        try{

            // 1.Leave notes
            Response response = client.newCall(getForecastRequest).execute();
            String json = response.body().string();

            JsonParser parser = new JsonParser();
            JsonObject object = (JsonObject) parser.parse(json);
            JsonArray data = object.getAsJsonArray("data");

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();

            // Defines the object type(continue notes)
            Type listType = new TypeToken<ArrayList<Forecast>>(){}.getType();

            listForecast = gson.fromJson(data.toString(),listType);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return listForecast;
    }

    public static List<Local> getLocalList(){


        List<Local> listLocals = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();

        // 2. Leave notes
        Request getLocalRequest = new Request.Builder()
                .url("https://api.ipma.pt/open-data/distrits-islands.json")
                .build();

        try{

            // 1.Leave notes
            Response response = client.newCall(getLocalRequest).execute();
            String json = response.body().string();

            JsonParser parser = new JsonParser();
            JsonObject object = (JsonObject) parser.parse(json);
            JsonArray data = object.getAsJsonArray("data");

            Gson gson = new GsonBuilder().create();

            // Defines the object type(continue notes)
            Type listType = new TypeToken<ArrayList<Local>>(){}.getType();

            listLocals  = gson.fromJson(data.toString(),listType);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listLocals;
    }
}
