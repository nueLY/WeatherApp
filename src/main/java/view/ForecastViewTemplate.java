package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import model.Forecast;
import model.Local;
import utils.WeatherAPI;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ForecastViewTemplate extends BorderPane {


    private Button buttonFilter;
    private Button buttonForecast;
    private ObservableList<Local> listLocals;
    private ComboBox<Local> comboLocals;
    private GridPane gridForecasts;
    private ToggleGroup groupRegion;

    public ForecastViewTemplate() {

        doLayout();
        getStylesheets().add("styleSheet.css");
        this.setPadding(new Insets(30));
    }


    private void doLayout() {

        Image image = new Image("25501.jpg");
        ImageView imageView = new ImageView(image);

        this.getChildren().add(imageView);

        // INITIALIZE FILTER BUTTON
        buttonFilter = new Button("Filtrar");
        // INITIALIZE FORECAST BUTTON
        buttonForecast = new Button("Ver Previsão");
        // INITIALIZE AN OBSERVABLE LIST FOR LOCALS
        listLocals = FXCollections.observableArrayList();
        // INITIALIZE THE COM BOX FOR LOCAL LIST
        comboLocals = new ComboBox<>(listLocals);
        // INITIALIZE TOGGLE_GROUP TO GROUP THE RADIO BUTTON;
        groupRegion = new ToggleGroup();

        // CREATE THE RADIO BUTTONS AND ADD THEN TO THE groupRegion TOGGLE GROUP
        RadioButton rb1 = new RadioButton("Todos");
        rb1.setToggleGroup(groupRegion);
        rb1.setSelected(true);

        RadioButton rb2 = new RadioButton("Continente");
        rb2.setToggleGroup(groupRegion);

        RadioButton rb3 = new RadioButton("Açores");
        rb3.setToggleGroup(groupRegion);

        RadioButton rb4 = new RadioButton("Madeira");
        rb4.setToggleGroup(groupRegion);

        // CREATE VBOX & SET PADDING
        VBox topBar = new VBox(20);
        topBar.setPadding(new Insets(20,20,20,20));

        // CREATE REQUIRED HBOXES TO ADD THE ELEMENTS
        HBox filterBar = new HBox(20);
        filterBar.setAlignment(Pos.CENTER);

        HBox localBar = new HBox(20);
        localBar.setAlignment(Pos.CENTER);

        // INSERT IN localBar THE COMBOOX comboLocals AND BUTTON buttonForecast
        localBar.getChildren().addAll(comboLocals, buttonForecast);
        // INSERT REQUIRED NODES;
        filterBar.getChildren().addAll(rb1, rb2, rb3, rb4, buttonFilter);
        // INSERT INSIDE THE VBOX, THE HBOXES;
        topBar.getChildren().addAll(localBar, filterBar);

        // ARRANGE THE topBar VBO+X TO START FROM THE TOP;
        setTop(topBar);

        // Main grid to display forecast
        gridForecasts = new GridPane();
        // Setting size for the pane
        gridForecasts.setMinSize(600, 400);
        gridForecasts.setPadding(new Insets(10));
        // Setting columns size in percent
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(20);
        gridForecasts.getColumnConstraints().add(column);
        gridForecasts.getColumnConstraints().add(column);
        gridForecasts.getColumnConstraints().add(column);
        gridForecasts.getColumnConstraints().add(column);
        gridForecasts.getColumnConstraints().add(column);
        //Setting the padding
        gridForecasts.setPadding(new Insets(10,10,10,10));
        //Setting the vertical and horizontal gaps between the columns
        gridForecasts.setVgap(5);
        gridForecasts.setHgap(5);

        this.setCenter(gridForecasts);

        // Update the localities at the beginning og the program
        updateLocal();
        // Call the method to set the radio buttons on action
        // to have the list of places right when the program is started

        // CREATE THE BOX WITH WEEK DAYS
        setCenter(gridForecasts);

        buttonFilter.setOnAction(event -> {
            getRegionFilter();
        });

        buttonForecast.setOnAction(event -> {
            updatePrevision();
        });
    }

    /***
     * Method used update the forecast list in the grid;
     */
    public void updatePrevision() {
        // Retrieve the local:
        Local local = getSelectedLocal();
        //Check if the local is valid;
        if (local != null){
            // get the globalIdLocal(needed to build url);
            int localGlobalId = local.getGlobalIdLocal();
            // Get the forecast list;
            List<Forecast> listForecasts = WeatherAPI.getForecastList(localGlobalId);
            // Add the forecast to the grid;
            populateGrid(listForecasts);
        }
        else {
            showError("Error","Please select a valid locality");
        }
    }

    // Method used

    /***
     * Method used update tpopulate the forecast grid
     * @param forecastList -> List<Forecast>
     */
    private void populateGrid (List<Forecast> forecastList){
        // Required to clear any info present on the grid to avoid conflict
        gridForecasts.getChildren().clear();

        // Populate the grid with forecast data
        int column = 0;
        int row = 0;
        for (Forecast forecast : forecastList){
            VBox test = buildForecastColumn(forecast);
            gridForecasts.add(test,column,row);
            column++;
        }

    }

    /***
     * Method used to insert each forecast attribute into a vbox
     * @param forecast Forecast
     * @return Vbox
     */
    private VBox buildForecastColumn(Forecast forecast){

        VBox infoBox = new VBox();
        infoBox.setSpacing(20);
        infoBox.setAlignment(Pos.CENTER);

        // Get the week day;
        String englishWeekDay = String.valueOf(forecast.getForecastDate().getDayOfWeek());

        // Create label to set the weekday
        Label dayOfWeek = new Label(translateWeekDays(englishWeekDay));
        dayOfWeek.getStyleClass().add("label-dayOfWeek");

        // Create label for date
        Label dateLabel = new Label(String.valueOf(forecast.getForecastDate()));

        // Create label for minimum temperature
        Label tMin = new Label(String.valueOf(forecast.gettMin()));
        tMin.getStyleClass().add("label-tMin");

        Label tMax = new Label(String.valueOf(forecast.gettMax()));
        tMax.getStyleClass().add("label-tMax");

        // Create label for precipitaProb;
        Label precipitaProb = new Label(String.valueOf(forecast.getPrecipitaProb()));
        precipitaProb.getStyleClass().add("label-precipitation");

        // Insert the labels to the box;
        infoBox.getChildren().addAll(dayOfWeek,dateLabel, tMin,tMax,precipitaProb);
        return infoBox;
    }

    private String translateWeekDays(String weekDay){

        return switch (weekDay) {
            case "MONDAY" -> "Seg";
            case "TUESDAY" -> "Ter";
            case "WEDNESDAY" -> "Qua";
            case "THURSDAY" -> "Qui";
            case "FRIDAY" -> "Sex";
            case "SATURDAY" -> "Sab";
            case "SUNDAY" -> "Dom";
            default -> null;
        };
    }

    public void updateLocal() {
        // Method to retrieve the local list
        // How can to make the listLocals have the local name instead of full instance?
        try{
            List<Local> locals = WeatherAPI.getLocalList();
            listLocals.setAll(locals);

        }catch (Exception e) {
            showError("Error", "Failed to fetch locals from the API.");
        }

    }

    public void getRegionFilter() {

        final int CONTINENTE_CODE = 1;
        final int MADEIRA_CODE = 2;
        final int ACORES_CODE = 3;

        RadioButton selected = (RadioButton) groupRegion.getSelectedToggle();
        String option = selected.getText();

        List<Local> locals = WeatherAPI.getLocalList();
        List<Local> newLocals = new ArrayList<>();

        switch (option) {
            case "Continente" -> {
                for (Local local : locals) {
                    if (local.getIdRegion() == CONTINENTE_CODE) {
                        newLocals.add(local);

                    }
                    listLocals.setAll(newLocals);
                }
            }
            case "Madeira" -> {
                for (Local local : locals) {
                    if (local.getIdRegion() == MADEIRA_CODE) {
                        newLocals.add(local);

                    }
                    listLocals.setAll(newLocals);
                }
            }
            case "Açores" -> {
                for (Local local : locals) {
                    if (local.getIdRegion() == ACORES_CODE) {
                        newLocals.add(local);

                    }
                    listLocals.setAll(newLocals);
                }
            }
            default -> listLocals.setAll(locals);
        }

        //return selected.getText();
    }

    public Local getSelectedLocal() {
        return comboLocals.getSelectionModel().getSelectedItem();
    }

    public void showError(String title, String message) {
        System.err.println(String.format("%s - %s", title, message));
    }

}
