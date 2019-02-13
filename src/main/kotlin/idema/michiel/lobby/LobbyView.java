package idema.michiel.lobby;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LobbyView extends Application {

    private final TableView table = new TableView();

    public static void launch(){
        Application.launch();
    }

    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Lobby");

        table.setEditable(true);

        TableColumn PlayerColumn = new TableColumn("First Name");

        table.getColumns().addAll(PlayerColumn);

        final VBox vbox = new VBox();
        vbox.getChildren().addAll(table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

}
