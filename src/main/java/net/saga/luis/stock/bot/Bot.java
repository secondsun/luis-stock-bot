/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.saga.luis.stock.bot;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author summers
 */
class Bot {

    private final Chat chat;

    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();
    private final LUISClient luisClient;
    private final StockClient stockClient = new StockClient();

    Bot(Chat chat) {
        this.chat = chat;
        this.luisClient = new LUISClient();
    }

    void reply(String message) {
        EXECUTOR.submit(() -> {
            try {

                LuisResponse luisResponse = luisClient.call(message);

                if (luisResponse.topScoringIntent.intent.equals("Lookup Stock")) {

                    if (luisResponse.entities != null && luisResponse.entities.size() > 0) {
                        String ticker = luisResponse.entities.get(0).entity.toUpperCase();
                        chat.botSubmit("I will lookup " + ticker + ".");
                        try {

                            String price = stockClient.priceOf(ticker);
                            chat.botSubmit("The price of " + ticker + " is " + price);
                        } catch (Exception ex) {
                            Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
                            chat.botSubmit("I'm sorry I'm having trouble with that request");
                        }
                    } else {
                        chat.botSubmit("I think you were asking me to look up a stock, but I think you forgot to add a ticker.");
                    }
                } else {
                    chat.botSubmit("I'm sorry, I don't know how to help you.");
                }

            } catch (IOException ex) {
                Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
                chat.botSubmit(ex.getMessage());
            }
        });

    }

}
