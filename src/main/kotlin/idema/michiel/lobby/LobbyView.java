package idema.michiel.lobby;

import idema.michiel.game.GameMode;
import idema.michiel.lobby.dto.ChooseGameModeToServerDTO;
import idema.michiel.lobby.dto.ChooseNameToServerDTO;
import idema.michiel.lobby.dto.StartGameToServerDTO;
import idema.michiel.network.WebSocketClientEndPoint;
import idema.michiel.newspaper.MessageType;
import idema.michiel.newspaper.lobby.LobbyNewsPaper;
import idema.michiel.utilities.DTO;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.stage.WindowEvent;

import java.util.Optional;

public class LobbyView extends Application {

    private LobbyProxy proxy = new LobbyProxy(this);

    public Stage stage;
    public Scene scene;

    private final TableView table = new TableView();
    public ObservableList<TablePlayer> tableData = FXCollections.observableArrayList();
    public static final int MAX_LENGTH_PLAYER_NAME = 10;

    private Label lobbyLabel = new Label(GameMode.SPACE_BALLS.getValue());
    public SimpleStringProperty lobbyLabelProperty = new SimpleStringProperty(GameMode.SPACE_BALLS.getValue());


    public void start(Stage stage){
        this.stage = stage;
        VBox root = new VBox();
        String sheet = LobbyView.class.getResource("/css/lobby.css").toExternalForm();
        root.getStylesheets().add(sheet);
        root.setPadding(new Insets(10,200,20,200));
        scene = new Scene(root);
        Image img = new Image(LobbyView.class.getResource("/images/space.jpg").toExternalForm());
        root.setBackground(new Background(new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));

        lobbyLabel(root);
        playerTable(root);
        chooseNameForm(root);
        chooseGameButton(root);
        playButton(root);

        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
                System.out.println("hallo");
                System.exit(0);
            }
        });
        stage.show();
        new WebSocketClientEndPoint();
    }

    private void chooseGameButton(VBox root) {
        Button chooseGameButton = new Button("GameMode");
        chooseGameButton.setPrefSize(275, 50);
        chooseGameButton.getStyleClass().add("chooseGameButton");
        chooseGameButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                System.out.println("Choose Game button clicked");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Choose GameMode");
                alert.setHeaderText("Choose a GameMode");
                alert.setGraphic(null);

                ButtonType buttonSpaceBalls = new ButtonType("Space Balls");
                ButtonType Game2 = new ButtonType("Game2");

                alert.getButtonTypes().setAll(buttonSpaceBalls, Game2);

                Optional<ButtonType> result = alert.showAndWait();

                DTO dto = null;
                if (result.get() == buttonSpaceBalls){
                    dto = new ChooseGameModeToServerDTO(GameMode.SPACE_BALLS.getValue(), MessageType.CHOOSE_GAMEMODE_TO_SERVER.getValue());
                } else if (result.get() == Game2) {
                    dto = new ChooseGameModeToServerDTO(GameMode.GAME2.getValue(), MessageType.CHOOSE_GAMEMODE_TO_SERVER.getValue());
                }
                LobbyNewsPaper.INSTANCE.broadcast(dto);
            }
        });
        root.getChildren().add(chooseGameButton);
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
        lobbyLabel.setFont(new Font("Arial Black", 30));
        lobbyLabel.setStyle("-fx-text-fill: white;");
        lobbyLabel.setPadding(new Insets(10,10,10,10));
        lobbyLabel.setPrefWidth(275);
        lobbyLabel.setAlignment(Pos.CENTER);
        lobbyLabel.textProperty().bind(lobbyLabelProperty);
        root.getChildren().add(lobbyLabel);
    }

    private void playerTable(VBox root) {
        TableColumn playerColumn = new TableColumn("Players");
        playerColumn.setCellValueFactory(new PropertyValueFactory<TablePlayer, String>("name"));
        table.getColumns().add(playerColumn);

        TableColumn statusColumn = new TableColumn("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<TablePlayer, String>("status"));
        table.getColumns().add(statusColumn);

        root.getChildren().add(table);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefWidth(275);
        table.setItems(tableData);
    }

}
