package idema.michiel.javafxtryout.canvas;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    public void start(Stage primaryStage){
        VBox root = new VBox();
        Canvas canvas = new Canvas(800,500);
        GraphicsContext ctx = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        draw(ctx);
    }

    private void draw(GraphicsContext ctx){
        ctx.setFill(Color.FIREBRICK);
        ctx.setStroke(Color.BISQUE);

        ctx.fillRect(20,20,100,100);
    }

}
