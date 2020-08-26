package models;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.*;

public class UserModel {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient okHttpClient;
    public UserModel() {
        okHttpClient =new OkHttpClient();
    }
    public JSONObject getUser(){
        Request request =new Request.Builder().url("https://disease.sh/v3/covid-19/all").build();
        String response =null;
        try {
             response =okHttpClient.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response);

        return  new JSONObject(response);

    }
    public  JSONObject createUser(){

        return  null;
    }



}
