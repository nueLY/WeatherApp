import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.ForecastViewTemplate;

import javax.swing.*;

public class WeatherApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        ForecastViewTemplate test = new ForecastViewTemplate();


        Scene scene = new Scene(test, 650, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Weather app");
        primaryStage.show();
    }
}
