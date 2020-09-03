package models;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class GlobalModel {
    public static final String URI = "https://disease.sh/v3/covid-19/";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    JSONObject state;
    JSONArray states;
    OkHttpClient okHttpClient;
    public GlobalModel(){
        okHttpClient = new OkHttpClient();
    }
    public JSONArray getCasesByCountries() {
        Request request = new Request.Builder().url(URI+"countries" ).build();
        String response = null;
        try {
            response = okHttpClient.newCall(request).execute().body().string();
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new JSONArray(response);
    }
    public  JSONObject getAllCases(){
        Request request = new Request.Builder().url(URI+"all" ).build();
        String response = null;
        try {
            response = okHttpClient.newCall(request).execute().body().string();
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new JSONObject(response);
    };
}
