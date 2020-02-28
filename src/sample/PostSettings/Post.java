package sample.PostSettings;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.io.File;
import java.io.Serializable;

public class Post extends SendPhoto implements Serializable {
    private int NumberPost = 0;
    private String text;
    private File file;
    private KeyBoardData data;

    public String InfoAboutPost(){
        StringBuilder str = new StringBuilder();
        str.append("Text on post: ");
        str.append("\n");
        str.append(text);
        str.append("\n");
        str.append("-------------------------");
        str.append("TButtons: ");
        str.append("\n");
        str.append(data.getInfo());
        return str.toString();
    }


    public int getNumberPost() {
        return NumberPost;
    }

    public void setNumberPost(int numberPost) {
        NumberPost = numberPost;
    }

    public void setData(KeyBoardData data){
        this.data = data;
    }

    public String getText() {
        return text;
    }

    public void setTexttoPost(String text) {

        this.text = text;
    }

    public void Refresh(){
        this.setCaption(text);
        setPhoto(file);
        this.set_Inline_keybord();
    }
    public File getFile() {
        return file;
    }


    public void setFile(File file) {

        this.file = file;
    }

    public Post(){

    }

    public boolean makePost(){
        boolean Succes = false;
        if(text == null){
            //Выводим, что у него нет Текста, а текст обязательныый
            return false;
        } else {
            Succes = true;

        }
        if(file == null){
            //Говорим, что ему нужно поставить картинку - иначе никак
            return false;
        } else {
            Succes = true;
            //this.setPhoto(file);
        }
        if(data == null){
            //
        } else {

        }
        return Succes;
    }

    private void SetKeyboard(){

    }
    private void set_Inline_keybord() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        Inline_KeyBoard inline_keyboard = new Inline_KeyBoard(data);
        inlineKeyboardMarkup.setKeyboard(inline_keyboard.getRowList());
        this.setReplyMarkup(inlineKeyboardMarkup);

    }
}
