package idema.michiel.javafxtryout.loginform;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginForm extends Application {

    public void start(Stage primaryStage){
        primaryStage.setTitle("JavaFX Welcome");
        primaryStage.setResizable(false);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        Text sceneTitle = new Text("Welcome");
        sceneTitle.setId("welcome");
        sceneTitle.setFill(Color.WHITE);
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridPane.add(sceneTitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        gridPane.add(userName, 0, 1);

        TextField userTextField = new TextField();
        gridPane.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        gridPane.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        gridPane.add(pwBox, 1, 2);

        final Text actionTarget = new Text();
        gridPane.add(actionTarget, 0, 4, 2, 1);

        Button button = new Button("Login");
        button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                actionTarget.setText("Invalid username password combination.");
                actionTarget.setFill(Color.FIREBRICK);
            }
        });
        HBox hbox = new HBox();
        hbox.getChildren().add(button);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        gridPane.add(hbox, 0, 3, 2, 1);

        Scene scene = new Scene(gridPane);
        scene.getStylesheets().add(LoginForm.class.getResource("/javafxtryout/Login.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
