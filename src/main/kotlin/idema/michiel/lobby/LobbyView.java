package idema.michiel.lobby;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

        stage.setScene(scene);
        stage.show();
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
