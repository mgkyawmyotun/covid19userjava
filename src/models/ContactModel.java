package models;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONObject;

import java.io.IOException;

public class ContactModel {
    public static final String URI = "http://stark-crag-00731.herokuapp.com/";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient okHttpClient;
    public ContactModel(){
        okHttpClient = new OkHttpClient();
    }
    public JSONObject sendMessage(String json) {
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(URI+"send/message" ).post(requestBody)
                .build();
        String response = null;
        try {
            response = okHttpClient.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JSONObject(response);
    }


}
