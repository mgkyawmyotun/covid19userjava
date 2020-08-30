package models;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.Helper;

import java.io.IOException;

public class TownShipModel {
    public static final String URI = "https://stark-crag-00731.herokuapp.com/api/";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public JSONArray getTownShips() {
        return townships;
    }

    JSONObject township;
    JSONArray townships;
    OkHttpClient okHttpClient;

    public TownShipModel() {
        okHttpClient = new OkHttpClient();
    }

    public void refreshTownShips() {
        Request request = new Request.Builder().url(URI + "townships").build();
        String response = null;
        try {
            response = okHttpClient.newCall(request).execute().body().string();
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        townships = new JSONArray(response);
    }

    public void editTownShip(String json, String _id) {
        RequestBody requestBody = RequestBody.create(JSON, json);

        Request request = new Request.Builder().url(URI + "township/" + _id).put(requestBody)
                .addHeader("Authorization", "Bearer " + Helper.getToken()).build();
        String response = null;

        try {
            Response response1 = okHttpClient.newCall(request).execute();


            if (response1.code() >= 400) {
                new JSONObject().put("error", " LOL i don't have time ");
            }
            ;

            response = response1.body().string();
        } catch (IOException e) {
            throw new Error();
        }

        this.township = new JSONObject(response);


    }

    public  void deleteTownShip(String _id){

        Request request = new Request.Builder().url(URI + "township/" + _id).delete()
                .addHeader("Authorization", "Bearer " + Helper.getToken()).build();

        String response = null;

        try {
            Response response1 = okHttpClient.newCall(request).execute();


            if (response1.code() >= 400) {
                new JSONObject().put("error", " LOL i don't have time ");
            }
            ;

            response = response1.body().string();
        } catch (IOException e) {
            throw new Error();
        }
    }
    public  void addTownShip(String json){
        RequestBody requestBody = RequestBody.create(JSON, json);

        Request request = new Request.Builder().url(URI + "township" ).post(requestBody)
                .addHeader("Authorization", "Bearer " + Helper.getToken()).build();

        String response = null;

        try {
            Response response1 = okHttpClient.newCall(request).execute();


            if (response1.code() >= 400) {
                new JSONObject().put("error", " LOL i don't have time ");
            }
            ;

            response = response1.body().string();
            System.out.println(response);
        } catch (IOException e) {
            throw new Error();
        }
    }
}
