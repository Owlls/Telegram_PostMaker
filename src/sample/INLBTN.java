package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class INLBTN {
    private String Line = "______|_____";
    private ArrayList<ArrayList<String>> Text_Data = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> Url_Data = new ArrayList<ArrayList<String>>();



    public String getThis_line(){
        return Line;
    }

    public void addText(String text){
        ArrayList<String> line = new ArrayList<>();
        line.add(text);
        Text_Data.add(line);
        Line = String.format("%s_|_",text);
    }
    public void addUrl(String text){
        ArrayList<String> line = new ArrayList<>();
        line.add(text);
        Url_Data.add(line);
        Line = (Line + text);
    }


}
