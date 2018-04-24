package View;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Notification {
	private int screenWidth;
	private int screenHeight;

	private Timeline fadeOutTimeline; 

	private Scene scene;
	private Stage stage;
	private AnchorPane content;
	private BorderPane leftPane;
	private BorderPane rightPane;
	
	private Label msg;
	private Label title;
	
	private Image informationImage;
	private Image warningImage;
	private Image errorImage;
	
	public Notification() {
		getScreenSize();
		createNotification();
		initStage();
		stage.setScene(scene);
		setSizes(screenWidth * 0.25, screenHeight);
	}
	
	private void createNotification() {
		createComponents();
		initImages();
		setStyle();
		setPositions();
	}

	private void createComponents() {
		content = new AnchorPane();
		scene = new Scene(content, screenWidth * 0.25, screenHeight * 0.15);
		stage = new Stage();
		scene.getStylesheets().add(getClass().getResource("Notification.css").toExternalForm());
		leftPane = new BorderPane();
		rightPane = new BorderPane();
		msg = new Label();
		title = new Label();
		fadeOutTimeline = new Timeline();
	}

	private void initStage() {
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setMinHeight(screenWidth * 0.25);
		stage.setMinWidth(screenHeight * 0.15);
		setContentMouseEventHandling();
		titleLabel();
	}
	
	private void initImages() {
		informationImage = new Image(getClass().getResourceAsStream("/Bilder/information.png"));
		errorImage = new Image(getClass().getResourceAsStream("/Bilder/error.png"));
		warningImage = new Image(getClass().getResourceAsStream("/Bilder/warning.png"));
	}
	
	private ImageView createAndSizeImageView(Image img) {
		ImageView imageView = new ImageView(img);
		imageView.setFitHeight(60.0);
		imageView.setFitWidth(60.0);
		return imageView;
	}

	private void setSizes(double width, double height) {
		leftPane.setMinSize(width * 0.2, height * 0.15);
		leftPane.setMaxSize(width * 0.2, height * 0.15);
		rightPane.setMinSize(width * 0.8, height * 0.15);
		rightPane.setMaxSize(width * 0.8, height * 0.15);
	}

	private void setStyle() {
		content.getStyleClass().add("content");
		rightPane.getStyleClass().add("rightPane");
		title.getStyleClass().add("title");
		scene.setFill(Color.TRANSPARENT);
	}

	private void setPositions() {
		AnchorPane.setLeftAnchor(leftPane, 0.0);
		AnchorPane.setTopAnchor(leftPane, 0.0);
		AnchorPane.setRightAnchor(rightPane, 0.0);
		AnchorPane.setTopAnchor(rightPane, 0.0);
		BorderPane.setAlignment(msg, Pos.CENTER_LEFT);
		BorderPane.setAlignment(title, Pos.TOP_LEFT);
	}
	
	public void titleLabel() {
		rightPane.setTop(title);
	}

	public void warning(String title, String msg) {
		this.msg.setText(msg);
		this.title.setText(title);
		leftPane.setCenter(createAndSizeImageView(warningImage));
		rightPane.setCenter(this.msg);
		content.getChildren().addAll(leftPane, rightPane);
		leftPane.getStyleClass().add("warning");
		showNotification(4);
	}

	public void information(String title, String msg) {
		this.msg.setText(msg);
		this.title.setText(title);
		leftPane.setCenter(createAndSizeImageView(informationImage));
		rightPane.setCenter(this.msg);
		content.getChildren().addAll(leftPane, rightPane);
		leftPane.getStyleClass().add("info");
		showNotification(2);
	}

	public void error(String title, String msg) {
		this.msg.setText(msg);
		this.title.setText(title);
		leftPane.setCenter(createAndSizeImageView(errorImage));
		rightPane.setCenter(this.msg);
		content.getChildren().addAll(leftPane, rightPane);
		leftPane.getStyleClass().add("error");
		showNotification(6);
	}

	private void showNotification(int seconds) {
		setPosition();
		stage.show();
		Timeline timeline = new Timeline();
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(seconds), // nach sekunden verblasse stage
				new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FadeOut();
			}
		}));
		timeline.play();
	}

	private void FadeOut() {
		// innerhalb von 2 Sekunden transparent machen und schließen
		fadeOutTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2), new KeyValue(stage.getScene().getRoot().opacityProperty(), 0)));
		fadeOutTimeline.setOnFinished((actionEvent)->{
			stage.close();
		});
		fadeOutTimeline.play();
	}
	
	private void setContentMouseEventHandling() {
		content.addEventHandler(MouseEvent.ANY, e -> {
			if(((e.getScreenX()-content.getWidth()) > e.getX()) && ((e.getScreenY()-content.getHeight()) > e.getY())) {
				fadeOutTimeline.jumpTo(Duration.seconds(0)); // starte zeit neu, stoppe und starte neu, nach verzögerung
				fadeOutTimeline.stop();
				fadeOutTimeline.setDelay(Duration.seconds(3));
				fadeOutTimeline.play();
				stage.setOpacity(1.0);
			}
		});
	}

	private void setPosition() {
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX((primScreenBounds.getMaxX() - content.getWidth())); 
		stage.setY((primScreenBounds.getMaxY() - content.getHeight()));  
	}

	private void getScreenSize() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		screenWidth = gd.getDisplayMode().getWidth();
		screenHeight = gd.getDisplayMode().getHeight();
	}
}
