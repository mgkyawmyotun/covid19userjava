package models;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.json.JSONObject;
import org.json.JSONObject;

import java.io.IOException;

public class LocalModel {
    public static final String URI = "http://stark-crag-00731.herokuapp.com/api/";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient okHttpClient;
    public LocalModel(){
        okHttpClient = new OkHttpClient();
    }
    public JSONObject getTotal() {
        Request request = new Request.Builder().url(URI+"getTotal" ).build();
        String response = null;
        try {
            response = okHttpClient.newCall(request).execute().body().string();
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new JSONObject(response);
    }

}
