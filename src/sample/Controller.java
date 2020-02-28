package sample;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import sample.PostSettings.KeyBoardData;
import sample.PostSettings.Post;


import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

public class Controller {


    private String [] niceBro = {"טוב אחי!","מצויין!!!","נהדיר","כול הכבוד","מעולה","הכי טוב","כמו שצריך!!!","יופי טופי","מקצוען!"};
    private Desktop desktop = Desktop.getDesktop();
    final FileChooser fileChooser = new FileChooser();
    final FileChooser NfileChooser = new FileChooser();
    final DirectoryChooser directoryChooser = new DirectoryChooser();
    private Post post_from_table;
    private Post new_post;
    private String current_groupId;
    private int current_numOfBtnPare = -1;
    private String current_NameOfbtnPare;
    private Stage stage;
    private static StringProperty errorsStr = new SimpleStringProperty("");
    private ObservableList<String> KeyBoardsData = FXCollections.observableArrayList();
    private ObservableList<String> List_target_groups = FXCollections.observableArrayList();
    private ObservableList<Post> posts = FXCollections.observableArrayList();
    private ArrayList<ArrayList<String>> Text_Data = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> Url_Data = new ArrayList<ArrayList<String>>();
    private PromoSettings promoSettings = new PromoSettings();
    private Engine engine = new Engine();
    Thread thread;
    private BOT bot;
    BotSession botSession;


    public void SavePosts(){
        File file = directoryChooser.showDialog(stage);
        File newFile = new File(file + "\\Promo.ser");
        System.out.println(newFile);
        FileOutputStream OtpostsStream = null;
        try {
            OtpostsStream = new FileOutputStream(newFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectOutputStream Otposts = null;
        try {
            Otposts = new ObjectOutputStream(OtpostsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Otposts.writeObject(promoSettings);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Otposts.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void LoadPosts() throws IOException, ClassNotFoundException {
        File file = NfileChooser.showOpenDialog(stage);
        if(file != null) {
            FileInputStream OtpostsStream = new FileInputStream(file);
            ObjectInputStream Otposts = new ObjectInputStream(OtpostsStream);
            promoSettings = ((PromoSettings) Otposts.readObject());
            refreshGroupsList();
            RefreshTable();
            Otposts.close();
        }
        REFRESHALL();
       // GroupsId.close();
    }



    class botmanger  implements Runnable{
        private void init() {

            ApiContextInitializer.init();
            System.out.println(Thread.currentThread().getName());
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
            try {
                botSession = telegramBotsApi.registerBot(bot = new BOT());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        public synchronized void  StartBOT(){

            botSession.start();
        }

        public synchronized void StopBot(){
            botSession.stop();
        }
        @Override
        public void run() {
            init();
            System.out.println("   START    ");


        }
    }

    private String getNice_bro(){
        Random random = new Random();
        int num = (random.nextInt(8) +1);
        return niceBro[num];
    }


    @FXML
    Pane PImagePane;

    @FXML
    TextArea PTextArea;

    @FXML
    ListView<String> List_of_groups;

    @FXML
    TextField PNum_loops_textfield;

    @FXML
    TextField InsertGroup_field;

    @FXML
    RadioButton Persum_forever;

    @FXML
    RadioButton Persum_Num;

    @FXML
    TextField Persum_seconds;

    @FXML
    Button Persum_secondsBtn;

    @FXML
    ToggleGroup persumSet;

    @FXML
    TableView<Post> PList_tableview;

    @FXML
    TableColumn<Post, String> Ptext_tablecolumn;

    @FXML
    TableColumn<Post, String> Ppic_tablecolumn;

    @FXML
    TableColumn<Post, Integer> Ppic_btncolumn;

    @FXML
    TextField ABText_textarea;

    @FXML
    TextField ABUrl_textarea;

    @FXML
    private ListView<String> ABText_Data_listview;

    @FXML
    TextArea Errors_textarea;

    @FXML
    Button CPic_button;

    @FXML
    TextArea AText_textarea;

    @FXML
    Button AText_button;




    private void REFRESHALL(){
        KeyBoardsData.clear();
        Text_Data.clear();
        Url_Data.clear();
        post_from_table = null;
        current_groupId = null;
        current_numOfBtnPare = -1;
        AText_textarea.setText("");
        ABUrl_textarea.setText("");
        ABText_textarea.setText("");
        ABUrl_textarea.setText("");
        ABUrl_textarea.setText("");
        refreshButtonsList();

    }

    public synchronized static void AddError(String a) {
        errorsStr.setValue(a);
    }


    private void InitController() {
        thread = new Thread(new botmanger());
        thread.start();
        initFileChoser();
        initErrors();
        initTable();
        initPesumPanel();
        new_post = new Post();
    }


    public void SetStage(Stage stage) {
        this.stage = stage;
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
                try {
                    thread.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        InitController();
    }


    @FXML
    public void MP_makePost() {
        if (new_post.getText() != null && !new_post.getText().equals(" ") && new_post.getFile() != null) {
            int[] mas = new int[Text_Data.size()];
            for (int i = 0; i < Text_Data.size(); i++) {
                mas[i] = 1;
            }
            new_post.setData(new KeyBoardData(Text_Data, mas, Url_Data));
            new_post.makePost();
            promoSettings.addPost(new_post);
            new_post = new Post();
            REFRESHALL();
            RefreshTable();
        } else {
            AddError("אצל פוסט חסר או טקסת,או תמונה!");
        }
    }


    @FXML
    public void SetTextbtn() { //Метод через, который мы добавляем текст в пост
        if (AText_textarea.getText() != null && !AText_textarea.getText().equals("") && !AText_textarea.getText().equals(" ")) {
            new_post.setTexttoPost(AText_textarea.getText());
            AText_textarea.setText("");
            AddError(getNice_bro());
        } else {
            AddError("לה הכנסת טקסט !!! ");
        }
    }
    @FXML
    public void SetText(KeyEvent event){
        switch (event.getCode()) {
            case ENTER:
                break;
            case DELETE:
                AText_textarea.setText("");
            default:
                break;
        }
    }

    private void initFileChoser() { //инициализируем механизм выбора картнки
        CPic_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    new_post.setFile(file);
                    AddError(getNice_bro());
                } else {
                    AddError("משוה לא נכון אם תמונה שבחרת!!!");
                }
            }
        });
        fileChooser.getExtensionFilters().addAll(//
                new FileChooser.ExtensionFilter("JPG", "*.jpg"), //
                new FileChooser.ExtensionFilter("PNG", "*.png"));

    }

    private void initErrors() {
        errorsStr.addListener(changeListener);
    }

    private void initPesumPanel() { //Инициализируем панельку с настройками
        //persumSet;
        Persum_forever.setOnAction(actionEvent -> {
            RadioButton radioButton = (RadioButton) persumSet.getSelectedToggle();
            if (radioButton.equals(Persum_forever)) {
                //Постоянная реклама
                promoSettings.setForever(true);
                AddError(getNice_bro());
            } else if (radioButton.equals(Persum_Num)) {
                //Проверяем если он ввел номер, если нет говорим ввести
                if (promoSettings.getTime() != 0) {
                    promoSettings.setForever(false);
                } else {
                    AddError("תכניס זמן בשניות לשדה +\n שנימצה למעתה !!! ");
                }
            }
        });
        Persum_Num.setOnAction(actionEvent -> {
            RadioButton radioButton = (RadioButton) persumSet.getSelectedToggle();
            if (radioButton.equals(Persum_forever)) {
                //Постоянная реклама
                promoSettings.setForever(true);
            } else if (radioButton.equals(Persum_Num)) {
                //Проверяем если он ввел номер, если нет говорим ввести
                if (promoSettings.getTime() != 0) {
                    promoSettings.setForever(false);
                } else {
                    AddError("תכניס זמן בשניות לשדה +\n שנימצה למעתה !!! ");
                }
            }
        });
    }

    private void initTable() {
        Ptext_tablecolumn.setCellValueFactory(new PropertyValueFactory<Post, String>("text"));
        Ppic_tablecolumn.setCellValueFactory(new PropertyValueFactory<Post, String>("file"));
        Ppic_btncolumn.setCellValueFactory(new PropertyValueFactory<Post, Integer>("NumberPost"));
    }


    final ChangeListener changeListener = new ChangeListener() { //Слушатель для ошибок
        @Override
        public void changed(ObservableValue observableValue, Object o, Object t1) {
            Text text = new Text(t1 + "\n");
            text.setFill(Color.RED);
            Errors_textarea.setText(Errors_textarea.getText() + "\n" + text.getText());
        }
    };


    @FXML
    public void ABText_Data_input(KeyEvent event){
        switch (event.getCode()) {
            case ENTER:
                ABText_Data_inputt();
                break;
            case DELETE:
                ABUrl_textarea.setText("");
            default:
                break;
        }
    }
    @FXML
    public void ABText_input(KeyEvent event){
        switch (event.getCode()) {
            case DELETE:
                ABText_textarea.setText("");
            default:
                break;
        }
    }
    @FXML
    public void ABText_Data_inputt() { //Вносим кнопки
        String Line = "______|______";
        if (ABText_textarea.getText() != null && !ABText_textarea.getText().equals("") && !ABText_textarea.getText().equals(" ")) {
            String text = ABText_textarea.getText();
            ArrayList<String> line = new ArrayList<>();
            line.add(text);
            Text_Data.add(line);
            Line = String.format("%s_|_", text);
            ABText_textarea.setText("");
            AddError(getNice_bro());
        } else {
            AddError("אתה חייב להקליד משוה, אם \nאתה רוצה כבתור מתחת הפוסט");
        }
        if (ABUrl_textarea.getText() != null && !ABUrl_textarea.getText().equals("") && !ABUrl_textarea.getText().equals(" ")) {
            String text = "https://t.me/" + ABUrl_textarea.getText();
            ArrayList<String> line = new ArrayList<>();
            line.add(text);
            Url_Data.add(line);
            Line = (Line + text);
            ABUrl_textarea.setText("");
            AddError(getNice_bro());
        } else {
            AddError("אתה חייב להקליד משוה, אם \nאתה רוצה לינק על הפוסט");
        }
        KeyBoardsData.add(Line);
        refreshButtonsList();
    }

    private void refreshButtonsList(){
        ABText_Data_listview.setItems(KeyBoardsData);
        ABText_Data_listview.refresh();
    }
    @FXML
    public void ClickOn_ButtonsListt(KeyEvent event){
        switch (event.getCode()) {
            case LEFT:
            case ENTER:

                break;
            case RIGHT:

                break;
            case DOWN:
                ClickOn_ButtonsList();
                break;
            case UP:
                ClickOn_ButtonsList();
                break;
            case DELETE:
                if(current_numOfBtnPare != -1) {
                    KeyBoardsData.remove(current_numOfBtnPare);
                    if(!Text_Data.isEmpty()) {
                        Text_Data.remove(current_numOfBtnPare);
                    }
                    if(!Url_Data.isEmpty()) {
                        Url_Data.remove(current_numOfBtnPare);
                    }
                    current_numOfBtnPare = -1;
                    current_NameOfbtnPare = null;
                }
                break;
            default:
                break;
        }
    }
    @FXML
    public void ClickOn_ButtonsList(MouseEvent event){
        if(ABText_Data_listview.getSelectionModel() != null) {
            current_numOfBtnPare = ABText_Data_listview.getSelectionModel().getSelectedIndex();
            System.out.println("Num Of Pare : " + current_numOfBtnPare);
            current_NameOfbtnPare = ABText_Data_listview.getSelectionModel().getSelectedItem();
        }
    }

    private void ClickOn_ButtonsList(){
        if(ABText_Data_listview.getSelectionModel() != null) {
            current_numOfBtnPare = ABText_Data_listview.getSelectionModel().getSelectedIndex();
            current_NameOfbtnPare = ABText_Data_listview.getSelectionModel().getSelectedItem();
        }
    }



    public void RefreshTable() { //Обновляем таблицу постов
        posts.clear();
        posts.addAll(promoSettings.getPosts());
        PList_tableview.setItems(posts);
        PList_tableview.refresh();
    }



    @FXML
    public void Posts_TableWorkbtn(KeyEvent event){
        switch (event.getCode()) {
            case LEFT:
            case ENTER:

                break;
            case RIGHT:

            case DOWN:
                if(PList_tableview.getSelectionModel().getSelectedItem() != null){
                    post_from_table = (Post) PList_tableview.getSelectionModel().getSelectedItem();
                    viewPost();
                }
            case UP:
                if(PList_tableview.getSelectionModel().getSelectedItem() != null){
                    post_from_table = (Post) PList_tableview.getSelectionModel().getSelectedItem();
                    viewPost();
                }
                break;
            case DELETE:

            default:
                break;
        }
    }
    @FXML
    public void Posts_TableWork(MouseEvent event){
        AddError("Working ");
        if(PList_tableview.getSelectionModel().getSelectedItem() != null){
            post_from_table = (Post) PList_tableview.getSelectionModel().getSelectedItem();
            viewPost();
        }
    }

    @FXML
    public void DeletePost(){
        if(post_from_table != null){
            promoSettings.deletePost(post_from_table);
            RefreshTable();
            AddError("Post was been deleted");
        } else {
            Random random = new Random();
            int i = (random.nextInt(100) + 1232);
            AddError("Error: " + i);
        }
    }//удалять пост из таблицы

    public void viewPost(){
        if(post_from_table != null){
            PTextArea.setText(post_from_table.InfoAboutPost());
            try {
                String locurl = post_from_table.getFile().toURI().toURL().toString();
                BackgroundImage bgImage = new BackgroundImage(
                        new Image(locurl),                                                 // image
                        BackgroundRepeat.NO_REPEAT,                            // repeatX
                        BackgroundRepeat.NO_REPEAT,                            // repeatY
                        BackgroundPosition.CENTER,                             // position
                        new BackgroundSize(-1, -1, false, false, true, false)  // size
                );
                PImagePane.setBackground(new Background(bgImage));

            } catch (MalformedURLException e) {
                AddError("301 Can`t find Post`s Image ");
                e.printStackTrace();
            }
        }
    }
//----------------------------------------------Панель с настройками рекламы

    @FXML
    public void set_ef_Timee() { //Метод, который выстравляет время в настройках рекламы
        String text = Persum_seconds.getText();
        if (text != null && text != "" && text != " ") {
            long secs = Long.decode(text);
            promoSettings.setTime(secs);
            AddError(getNice_bro());
            Persum_seconds.setText("");
        } else {
            AddError("זמן - מפר שניות של אפסקה בין הפוסטים");
        }
    }

    @FXML
    public void set_ef_Time(KeyEvent event) { //Метод который помгоет работать с клавиатурой
        switch (event.getCode()) {
            case ENTER:
                set_ef_Timee();
                break;
            case DELETE:
                Persum_seconds.setText("");
            default:
                break;
        }
    }


    @FXML
    public void InsertGroup(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
            case ENTER:
                InsertGroupbtn();
                break;
            case RIGHT:

            case DOWN:

            case UP:

                break;
            case DELETE:
                Persum_seconds.setText("");
            default:
                break;
        }
    }



    @FXML
    public void ClickOn_ListGroupskey(KeyEvent event){
        switch (event.getCode()) {
            case DOWN:
                ClickOn_ListGroups();
            case UP:
                ClickOn_ListGroups();
                break;
            case DELETE:
                if(current_groupId !=  null) {
                    promoSettings.deleteGroupLink(current_groupId);
                    refreshGroupsList();
                }
            default:
                break;
        }

    }
    @FXML
    void ClickOn_ListGroups(MouseEvent event){
        if(List_of_groups.getSelectionModel().getSelectedItem() != null){
            current_groupId = List_of_groups.getSelectionModel().getSelectedItem();
        }
    }

    private void ClickOn_ListGroups(){
        if(List_of_groups.getSelectionModel().getSelectedItem() != null){
            current_groupId = List_of_groups.getSelectionModel().getSelectedItem();
        }
    }
    @FXML
    public void InsertGroupbtn() {
       String str = InsertGroup_field.getText();
        if (str != null && str != "" && str != " ") {
           promoSettings.addGroupLink(str);
            AddError(getNice_bro());
            refreshGroupsList();
            InsertGroup_field.setText("");
        } else {
            AddError("תכניס לינק תהתחיל אותו מ@");
        }
    }
    private void refreshGroupsList(){
        List_target_groups.clear();
        List_target_groups.addAll(promoSettings.GroupsLinks());
        List_of_groups.setItems(List_target_groups);
        List_of_groups.refresh();
    }



    @FXML
    public void StopEngine(){
        engine.Stop();
    }

    @FXML
    public void StartEngine(){ // Запускаем двигатель перед этим проверив все настройки
        boolean go = false;
        if(!promoSettings.getPosts().isEmpty()){
            engine.setPromoSettings(promoSettings);
            go = true;
        } else {
            AddError("אין פוסטים!");
        }
        if(!promoSettings.GroupsLinks().isEmpty()){
           go = true;
        } else {
            go = false;
            AddError("אין לינקים של הקבוצות!");
        }
        if(go){
            engine.setBotSettings(bot);
            engine.Start();
            //engine.one_step();
        }
    }

    @FXML
    public void set_num_loopss(){ //Устранавливаем количество повторенний
        String num = PNum_loops_textfield.getText();
        if(num != null && num != ""){
            try {
                int number = Integer.parseInt(num);
                promoSettings.setNum_loops(number);
                PNum_loops_textfield.setText("");
                AddError(getNice_bro());
            } catch (NumberFormatException ex){
                AddError("תקליד רק מספר - בלי אותיות וסימנים ");
            }
        } else {
            AddError(" תקליד רק מספר");
        }
    }

    @FXML
    public void set_num_loops(KeyEvent event){ //Устранавливаем количество повторенний всп.м. для клавиатуры
        switch (event.getCode()) {
            case ENTER:
                set_num_loopss();
                break;
            case DELETE:
                PNum_loops_textfield.setText("");
            default:
                break;
        }
    }


}