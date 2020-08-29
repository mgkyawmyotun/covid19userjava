package models;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class StateModel {


    public static final String URI = "https://stark-crag-00731.herokuapp.com/api/states";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public JSONArray getStates() {
        return states;
    }

    JSONArray states ;
    OkHttpClient okHttpClient;

    public StateModel(){
        okHttpClient =new OkHttpClient();
    }

    public void refreshStates(){
        Request request = new Request.Builder().url(URI).build();
        String response = null;
        try {
            response = okHttpClient.newCall(request).execute().body().string();
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        states =new JSONArray(response);
    }

}
