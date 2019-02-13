package idema.michiel.javafxtryout.tableview;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    private TableView table = new TableView();

    public ObservableList<Person> data = FXCollections.observableArrayList(
            new Person("Jacob", "Smith", "jacob.smith@example.com"),
            new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
            new Person("Ethan", "Williams", "ethan.williams@example.com"),
            new Person("Emma", "Jones", "emma.jones@example.com"),
            new Person("Michael", "Brown", "michael.brown@example.com")
    );


    public void start(Stage primaryStage){
        VBox root = new VBox();
        Scene scene = new Scene(root);

        Label label = new Label("Address book");
        label.setFont(new Font("Arial",20));
        root.getChildren().add(label);

        TableColumn firstNameColumn = new TableColumn("First name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
        TableColumn lastNameColumn = new TableColumn("Last name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
        TableColumn emailColumn = new TableColumn("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));
        table.getColumns().addAll(firstNameColumn, lastNameColumn, emailColumn);

        table.setItems(data);
        table.setEditable(true);
        table.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                data.add(new Person("gekke","harry", "gekkeharry@example.com"));
            }
        });

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        root.getChildren().add(table);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
