package models;

import com.sun.javaws.exceptions.ErrorCodeResponseException;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.*;

public class UserModel {
    public static final String URI = "https://stark-crag-00731.herokuapp.com/user/";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient okHttpClient;

    public UserModel() {
        okHttpClient = new OkHttpClient();
    }
    JSONObject user;
    public JSONObject getUser() {
        Request request = new Request.Builder().url(URI).build();
        String response = null;
        try {
            response = okHttpClient.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new JSONObject(response);

    }

    public JSONObject createUser(String json) {
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(URI + "register").post(requestBody).build();
        String response = null;
        try {
            response = okHttpClient.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JSONObject(response);
    }

    public  JSONObject loginUser(String json){
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(URI + "login").post(requestBody).build();
        String response = null;

        try {
          Response response1=  okHttpClient.newCall(request).execute();


          if(response1.code()>=400) {
                new JSONObject().put("error","Invalid Email or Password");
          };

          response = response1.body().string();
        } catch (IOException e) {
            throw new Error();
        }
        System.out.println(response);
        this.user =new JSONObject(response);
        System.out.println(response);
        return new JSONObject(response);
    }
    public void saveToken(){

    }
    public  String getToken(){
        return  "";
    }
    public  String getUserName(){
        return  "";
    }
    public  String getEmail(){
        return  "";
    };

}
