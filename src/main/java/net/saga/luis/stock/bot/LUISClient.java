/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.saga.luis.stock.bot;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * @author summers
 */
public class LUISClient {
    
    private final OkHttpClient client;
    private static final String LUIS_TEMPLATE = "https://westus.api.cognitive.microsoft.com/luis/v2.0/apps/%s?subscription-key=%s&verbose=true&timezoneOffset=0&q=%s";
    private static final String LUIS_APP_ID = System.getenv("luis_app_id");
    private static final String LUIS_APP_KEY = System.getenv("luis_key");

    public LUISClient() {
        this.client = new OkHttpClient();
    }

    LuisResponse call(String message) throws UnsupportedEncodingException, IOException, IOException {
                    String url = String.format(LUIS_TEMPLATE, LUIS_APP_ID, LUIS_APP_KEY, URLEncoder.encode(message, "UTF-8"));
                    Request request = new Request.Builder().get().url(url).build();

                    Response response = client.newCall(request).execute();
                    String responseJson = response.body().string();

                    Gson gson = new Gson();
                    LuisResponse luisResponse = gson.fromJson(responseJson, LuisResponse.class);
                    return luisResponse;
    }
    
    
    
}
