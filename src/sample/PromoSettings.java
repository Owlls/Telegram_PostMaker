package sample;

import sample.PostSettings.Post;

import java.io.Serializable;
import java.util.ArrayList;

public class PromoSettings implements Serializable {

    private boolean IsForever = true;
    private long Time = 300000;
    private int num_loops;
    private ArrayList<Post> posts = new ArrayList<Post>();
    private ArrayList<String> groupsLinks = new ArrayList<>();


    public synchronized int getNum_loops() {
        return num_loops;
    }

    public synchronized void setNum_loops(int num_loops) {
        this.num_loops = num_loops;
    }

    public void addGroupLink(String link){
        groupsLinks.add(link);
    }

    public void deleteGroupLink(String link){
        groupsLinks.remove(link);
    }

    public synchronized ArrayList<String> GroupsLinks(){
        return groupsLinks;
    }


    public synchronized ArrayList<Post> getPosts(){
        return posts;
    }

    public Post GetPost(int i){
        return posts.get(i);
    }



    public void Refresh_Num_Loops(){

    }
    public int get_number_posts(){
        return posts.size();
    }

    public boolean deletePost(Post post){
        return posts.remove(post);
    }

    public boolean addPost(Post post){
        post.setNumberPost(posts.size() + 1);
        return posts.add(post);
    }


    public boolean isForever() {
        return IsForever;
    }

    public void setForever(boolean forever) {
        IsForever = forever;
    }

    public long getTime() {
        return Time;
    }

    public synchronized void setTime(long time) {
        this.Time = (time * 1000);
    }
}
