package idema.michiel.lobby;

import com.sun.javafx.css.Stylesheet;
import idema.michiel.lobby.dto.ChooseNameToServerDTO;
import idema.michiel.lobby.dto.StartGameToServerDTO;
import idema.michiel.network.WebSocketClientEndPoint;
import idema.michiel.newspaper.MessageType;
import idema.michiel.newspaper.lobby.LobbyNewsPaper;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LobbyView extends Application {

    private LobbyProxy proxy = new LobbyProxy(this);

    public Stage stage;

    private final TableView table = new TableView();
    public ObservableList<TablePlayer> tableData = FXCollections.observableArrayList();
    public static final int MAX_LENGTH_PLAYER_NAME = 10;

    public static void launch(){
        Application.launch();
    }

    public void start(Stage stage){
        this.stage = stage;
        VBox root = new VBox();
        String sheet = LobbyView.class.getResource("/css/lobby.css").toExternalForm();
        root.getStylesheets().add(sheet);
        root.setPadding(new Insets(10,200,20,200));
        Scene scene = new Scene(root);
        Image img = new Image(LobbyView.class.getResource("/images/space.jpg").toExternalForm());
        root.setBackground(new Background(new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));

        lobbyLabel(root);
        playerTable(root);
        chooseNameForm(root);
        playButton(root);

        stage.setScene(scene);
        stage.show();
        new WebSocketClientEndPoint();
    }

    private void chooseNameForm(VBox root) {
        HBox chooseNameFormDiv = new HBox();
        TextField textField = chooseNameTextField(chooseNameFormDiv);
        chooseNameButton(chooseNameFormDiv, textField);
        chooseNameFormDiv.setPadding(new Insets(10,0,10,0));
        root.getChildren().add(chooseNameFormDiv);
    }

    private void chooseNameButton(HBox div, final TextField textField) {
        Button button = new Button("Choose name");
        button.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if(isChosenNameValid(textField.getText())){
                    ChooseNameToServerDTO dto = new ChooseNameToServerDTO(null,
                            textField.getText(),
                            MessageType.CHOOSE_NAME_TO_SERVER.getValue());
                    LobbyNewsPaper.INSTANCE.broadcast(dto);
                }else{
                    String error = "Invalid name, too long (max 10)";
                    Alert alert = new Alert(Alert.AlertType.ERROR, error);
                    alert.showAndWait();
                }
            }
        });
        div.getChildren().add(button);
    }

    private boolean isChosenNameValid(String text) {
        return text.length() <= MAX_LENGTH_PLAYER_NAME;
    }

    private TextField chooseNameTextField(HBox div) {
        TextField textField = new TextField();
        textField.setPrefWidth(200);
        textField.setPrefHeight(25);
        textField.setPadding(new Insets(0,5,0,0));
        div.getChildren().add(textField);
        return textField;
    }

    private void playButton(VBox root) {
        Button playButton = new Button("Play");
        playButton.setPrefSize(275, 50);
        playButton.getStyleClass().add("playButton");
        playButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                System.out.println("Play button clicked");
                LobbyNewsPaper.INSTANCE.broadcast(new StartGameToServerDTO());
            }
        });
        root.getChildren().add(playButton);
    }

    private void lobbyLabel(VBox root) {
        Label playerTableLabel = new Label("Space balls");
        playerTableLabel.setFont(new Font("Arial Black", 30));
        playerTableLabel.setStyle("-fx-text-fill: white;");
        playerTableLabel.setPadding(new Insets(10,10,10,10));
        playerTableLabel.setPrefWidth(275);
        playerTableLabel.setAlignment(Pos.CENTER);
        root.getChildren().add(playerTableLabel);
    }

    private void playerTable(VBox root) {
        TableColumn playerColumn = new TableColumn("Players");
        playerColumn.setCellValueFactory(new PropertyValueFactory<TablePlayer, String>("name"));
        table.getColumns().add(playerColumn);
        root.getChildren().add(table);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefWidth(275);
        table.setItems(tableData);
    }

}
