package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
    private Controller controller;
    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = (AnchorPane)loader.load();
        controller = loader.getController();
        primaryStage.setTitle("פרסום קבועה");
        primaryStage.setScene(new Scene(root, 944, 665));
        primaryStage.show();
        stage = primaryStage;
        setController();
    }
    public void setController(){
        controller.SetStage(stage);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
