package application;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import View.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	private int screenWidth;
	private int screenHeight;
	
	LoginView loginView;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			StackPane stack = new StackPane();
			stack.setStyle("-fx-background-color: transparent;");
			loginView = new LoginView(stack); 
			getScreenSize();
			Scene scene = new Scene(stack,screenWidth*0.7, screenHeight * 0.7);
			scene.setFill(Paint.valueOf("#FFFFFF00")); // make scene transparent
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			initPrimaryStage(primaryStage);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initPrimaryStage(Stage primaryStage) {
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setMinHeight(screenWidth * 0.6);
		primaryStage.setMinWidth(screenHeight * 0.7);
		primaryStage.setTitle("Login");
	}
	
	private void getScreenSize() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		screenWidth = gd.getDisplayMode().getWidth();
		screenHeight = gd.getDisplayMode().getHeight();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
