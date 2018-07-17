/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.saga.luis.stock.bot;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author summers
 */
public class Chat {
    private final Main main;
    private final List<Message> chatLog = new ArrayList<>();
    private final Bot bot;
    
    public Chat(Main main) {
        this.main = main;
        bot = new Bot(this);
    }

    void submit(String message) {
        Message chatMessage = new Message();
        chatMessage.from = Message.From.USER;
        chatMessage.text = message;
        chatLog.add(chatMessage);
        bot.reply(message);
        main.updateChat();
    }

    void botSubmit(String message) {
        Message chatMessage = new Message();
        chatMessage.from = Message.From.BOT;
        chatMessage.text = message;
        chatLog.add(chatMessage);
        main.updateChat();
    }

    List<Message> getMessages() {
        return new ArrayList<>(chatLog);
    }
    
    
    
    
    public static class Message {
        enum From {USER, BOT};
        
        public From from;
        public String text;
        
    }
    
}
