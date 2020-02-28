package sample;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sample.PostSettings.Post;

public class BOT extends TelegramLongPollingBot {
    private String botToken = "962719249:AAFxhk25cWcTSWBHYLgZR-Zpb1Ht86NDLUo";
    private String Username = "PostMaker";


    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    @Override
    public void onUpdateReceived(Update update) {

    }

    public void sendPost(Post post) throws TelegramApiException {
        execute(post);
    }

    @Override
    public String getBotUsername() {
        return Username;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
