package idema.michiel.lobby;

import idema.michiel.lobby.dto.StartGameToServerDTO;
import idema.michiel.network.WebSocketClientEndPoint;
import idema.michiel.newspaper.lobby.LobbyNewsPaper;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LobbyView extends Application {

    private LobbyProxy proxy = new LobbyProxy(this);

    private final TableView table = new TableView();
    public ObservableList<TablePlayer> tableData = FXCollections.observableArrayList();

    public static void launch(){
        Application.launch();
    }

    public void start(Stage stage) {
        VBox root = new VBox();
        Scene scene = new Scene(root);

        playerTableLabel(root);
        playerTable(root);
        playButton(root);
        testButton(root);

        stage.setScene(scene);
        stage.show();
        new WebSocketClientEndPoint();
    }

    private void playButton(VBox root) {
        Button playButton = new Button("Play");
        playButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                System.out.println("Play button clicked");
                LobbyNewsPaper.INSTANCE.broadcast(new StartGameToServerDTO());
            }
        });
        root.getChildren().add(playButton);
    }

    private void testButton(VBox root) {
        Button testButton = new Button("test");
        testButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                System.out.println("test button clicked");
            }
        });
        root.getChildren().add(testButton);
    }

    private void playerTableLabel(VBox root) {
        Label playerTableLabel = new Label("Connected players");
        playerTableLabel.setFont(new Font("Arial", 20));
        root.getChildren().add(playerTableLabel);
    }

    private void playerTable(VBox root) {
        TableColumn playerColumn = new TableColumn("Player");
        playerColumn.setCellValueFactory(new PropertyValueFactory<TablePlayer, String>("name"));
        table.getColumns().add(playerColumn);
        root.getChildren().add(table);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setItems(tableData);
    }

}
