package sample.PostSettings;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class Inline_KeyBoard {




    private List<List<InlineKeyboardButton>> rowList;
    private KeyBoardData data;

    public Inline_KeyBoard(KeyBoardData data){
        this.data = data;
        init();
    }

    private void init(){
        rowList = new ArrayList<List<InlineKeyboardButton>>();
        if(data.IsHave_URLData()){
            set_URLRows(data.getNumber_buttons(), data.getText_for_rows(),data.getUrldata_for_ibuttons());
        } else{

        }
    }


    private void set_DataRows(int [] number_row,String [][] text){ //Клавиатуру которую пользователь видеит после нажатия на кнопку продавец
        for(int i = 0 ; i < number_row.length; i++){
            List<InlineKeyboardButton> row = new ArrayList<InlineKeyboardButton>();
            for(int j = 0; j < number_row[i]; j++){
                row.add(make_Button(text[i][j]));
                System.out.println(" Data2 " + text[i][j]);
            }
            rowList.add(row);
        }
    }

    private InlineKeyboardButton make_Button(String text){ //Клавиатурa
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(text);
        return inlineKeyboardButton;
    }

    private void set_URLRows(int [] number_row,String [][] text, String[][] url_data){ //Клавиатуру которую пользователь видеит после нажатия на кнопку продавец
        for(int i = 0 ; i < number_row.length; i++){
            List<InlineKeyboardButton> row = new ArrayList<InlineKeyboardButton>();
            for(int j = 0; j < number_row[i]; j++){
                if((url_data.length) > i){
                    if( url_data[i] != null) {
                        row.add(make_Button(text[i][j], url_data[i][j]));
                    }
                } else {
                    row.add(make_Button(text[i][j]));
                }
                System.out.println(" Url data " + text[i][j]);
            }
                rowList.add(row);
        }
    }

    private InlineKeyboardButton make_Button(String text,String url_data){ //
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setUrl(url_data);
        return inlineKeyboardButton;
    }

    public List<List<InlineKeyboardButton>> getRowList(){
        return rowList;
    }



}

