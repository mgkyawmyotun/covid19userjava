package utils;


import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpService {
    public static String getCaseByClients() throws IOException {
        String json = Jsoup.connect("https://disease.sh/v3/covid-19/countries").ignoreContentType(true).execute().body();
        return  json;
    }
}
