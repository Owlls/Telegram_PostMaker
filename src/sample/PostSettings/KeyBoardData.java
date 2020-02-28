package sample.PostSettings;


import java.io.Serializable;
import java.util.ArrayList;

public class KeyBoardData implements Serializable {



    public String getInfo(){
        StringBuilder str = new StringBuilder();
        str.append("כמות כפתורים: " + number_buttons.length);
        str.append("\n");
        for (int i = 0; i < text_for_rows.length; i++){
            str.append("כפתור מס. " + i);
            str.append("\n");
            str.append("Text: " + text_for_rows[i][0]);
            str.append("\n");
            if(Urldata_for_ibuttons.length > i) {
                str.append("Url: " + Urldata_for_ibuttons[i][0]);
                str.append("\n");
            }
            str.append("\n");
            str.append("_____________________");
            str.append("\n");
        }
        return str.toString();
    }
    private String [][] text_for_rows;
    private int [] number_buttons;
    private String [][] Urldata_for_ibuttons;



    public boolean IsHave_URLData(){
        if(Urldata_for_ibuttons == null){
            return false;
        }else {
            return true;
        }
    }

    public String[][] getUrldata_for_ibuttons() {
        return Urldata_for_ibuttons;
    }


    public String[][] getText_for_rows() {
        return text_for_rows;
    }

    public int[] getNumber_buttons() {
        return number_buttons;
    }



    public KeyBoardData(String [][] text_for_rows, int[]number_buttons,String[][] URL_Data){//Коструктор 4-массива Текст,Дата,Ссылки, расп-кнопок
        this.text_for_rows = text_for_rows;
        this.number_buttons = number_buttons;
        this.Urldata_for_ibuttons = URL_Data;
    }
    public KeyBoardData(ArrayList<ArrayList<String>>  text_for_row, int [] number_buttons, ArrayList<ArrayList<String>> data){
        this.Urldata_for_ibuttons  = new String[data.size()][2];
        this.text_for_rows = new String[text_for_row.size()][2];
        this.number_buttons = number_buttons;
        //this.number_buttons = number_buttons;
        for(int i = 0; i < text_for_row.size(); i++){
            for (int j = 0; j < text_for_row.get(i).size(); j ++){
                text_for_rows[i][j] = text_for_row.get(i).get(j);
                if(data.size() > i) {
                    Urldata_for_ibuttons[i][j] = (data.get(i).get(j));
                }
            }
        }
    } //Коструктор принима

}
