/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.saga.luis.stock.bot;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * @author summers
 */
public class StockClient {

    private final OkHttpClient client;

    private static final String STOCK_TEMPLATE = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=%s&interval=1min&apikey=%s";
    private static final String STOCK_KEY = System.getenv("stock_key");

    public StockClient() {
        this.client = new OkHttpClient();
    }

    public String priceOf(String ticker) throws IOException {
        String stockUrl = String.format(STOCK_TEMPLATE, ticker, STOCK_KEY);
        Request stockRequest = new Request.Builder().get().url(stockUrl).build();
        Response stockResponse = client.newCall(stockRequest).execute();
        String stockResponseJson = stockResponse.body().string();

        JsonParser parser = new JsonParser();
        JsonElement json = parser.parse(stockResponseJson);
        JsonObject timeSeries = json.getAsJsonObject().get("Time Series (1min)").getAsJsonObject();

        JsonElement quote = timeSeries.entrySet().stream().findFirst().get().getValue();
        String price = quote.getAsJsonObject().get("4. close").getAsString();
        return price;
    }
}
