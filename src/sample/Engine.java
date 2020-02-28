package sample;



import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sample.PostSettings.Post;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Engine  {

    private PromoSettings promoes;
    private BOT bot;
    private int NumberLoop = 0;
    private Timer MyTimer;

    public synchronized void setPromoSettings(PromoSettings promoes){
        this.promoes = promoes;
    }

    public void setBotSettings(BOT bot){
        this.bot = bot;
    }

    public void Start(){
        MyTimer = new Timer();
        NumberLoop = 0;
        MyTimer.schedule(new DoPost(),new Date(new Date().getTime() + 5000));

    }
    public void Stop(){
        if(MyTimer != null) {
            MyTimer.cancel();
        }
    }
    public void one_step(){
        MyTimer.schedule(new DoPost(),new Date(new Date().getTime() + promoes.getTime()));

    }

    class DoPost extends TimerTask{

    @Override
    public void run() {
            if(bot != null && promoes != null && !promoes.getPosts().isEmpty()){
                Post post = promoes.GetPost(NumberLoop);
                post.Refresh();
                if(promoes.GroupsLinks() != null && !promoes.GroupsLinks().isEmpty()) {
                    ArrayList<String> groupsUrls = promoes.GroupsLinks();
                    for(String group_url: groupsUrls) {
                        post.setChatId(group_url);
                        try {
                            bot.sendPost(post);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                            Controller.AddError("Telegram ex");
                        }
                    }
                }
            }
            nextStep();

        }

    }
    void nextStep(){
        if(NumberLoop == (promoes.getPosts().size() -1)) {
            NumberLoop = 0;
        } else {
            NumberLoop++;
        }
        if(promoes.isForever()){
            one_step();
        } else {
            int loop = (promoes.getNum_loops() -1);
            promoes.setNum_loops(loop);
            if(loop > 0){
                one_step();
            } else {
                Stop();
            }
        }
    }
}
