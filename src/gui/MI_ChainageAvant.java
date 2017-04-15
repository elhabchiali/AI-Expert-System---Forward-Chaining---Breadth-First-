package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MI_ChainageAvant extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        try {
            FXMLLoader loader = new FXMLLoader(MI_ChainageAvant.class.getResource("MainWindowView.fxml"));
            GridPane pane = loader.load();
            MainWindowViewController mainWindowViewController = loader.getController();
            Scene scene = new Scene(pane);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Chainage avant - recherche en largeur d'abord");
            primaryStage.show();

        } catch (Exception e) {
            System.err.print("View 'MainWindowView' probleme :: ");
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
