package View;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.stage.PopupWindow;

public class MainWindow {
	// vars for movement
	private double xOffset = 0;
	private double yOffset = 0;

	// for resizing
	private int screenWidth;
	private int screenHeight;

	private AnchorPane mainPane;
	private HBox buttonBox;
	private Label title;
	
	private Button closeButton;
	private Button minimizeButton;
	
	private Image imageClose;
	private Image imageMin;
	
	private Alert decideWindow;

	public MainWindow() {
		createDecoration();
		makeMovable();
	}

	private void createDecoration() {
		createWindowComponents();
		addDecoToButtonBox();
		setButtonIcons();
		alignment();
		setMainStyle();
		mainPane.getChildren().add(buttonBox);
		getScreenSize();
		setMainPaneSize(screenWidth * 0.6, screenHeight * 0.65);
		setDecoActions();
	}
	
	private void createWindowComponents() {
		mainPane = new AnchorPane();
		buttonBox = new HBox();
		closeButton = new Button();
		minimizeButton = new Button();
		imageClose = new Image(getClass().getResourceAsStream("/Bilder/close.png"));
		imageMin = new Image(getClass().getResourceAsStream("/Bilder/min.png"));
		title = new Label();
	}
	
	private void addDecoToButtonBox() {
		HBox leftPart = new HBox();
		leftPart.getChildren().add(title);
		leftPart.setStyle("-fx-padding: 5 0 0 20");
		mainPane.getChildren().add(leftPart);
		buttonBox.getChildren().addAll(minimizeButton, closeButton);
	}
	
	private void setButtonIcons() {
		closeButton.setGraphic(createAndSizeImageView(imageClose));
		minimizeButton.setGraphic(createAndSizeImageView(imageMin));
	}
	
	protected ImageView createAndSizeImageView(Image img) {
		ImageView imageView = new ImageView(img);
		imageView.setFitHeight(16.0);
		imageView.setFitWidth(16.0);
		return imageView;
	}
	
	private void setMainPaneSize(double width, double height) {
		mainPane.setMinSize(width, height);
		mainPane.setMaxSize(width, height);
	}
	
	private void setMainStyle() {
		mainPane.setStyle("-fx-background-color: #bbd3f9; -fx-padding: 5");
		buttonBox.getStyleClass().add("buttonBox");
		closeButton.getStyleClass().add("buttonBoxButton");
		minimizeButton.getStyleClass().add("buttonBoxButton");
		title.setStyle("-fx-font-size: 16px;");
	}
	
	protected void setWhiteMainStyleColor() {
		mainPane.setStyle("-fx-background-color: white; -fx-padding: 5");
	}
	
	protected void setDefaultMainStyleColor() {
		mainPane.setStyle("-fx-background-color: #bbd3f9; -fx-padding: 5");
	}
	
	private void alignment() {
		AnchorPane.setTopAnchor(buttonBox, 0.0);
		AnchorPane.setRightAnchor(buttonBox, 0.0);
		buttonBox.setAlignment(Pos.CENTER_LEFT);
	}
	
	private void setDecoActions() {
		closeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				final Node source = (Node) e.getSource(); // Hole das Objekt auf welchen der Event ausgelöst wurde
				final Stage stage = (Stage) source.getScene().getWindow(); // Hole das Fenster
				stage.close(); // Schliesse es
			}
		});
		minimizeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				final Node source = (Node) e.getSource(); // Hole das Objekt auf welchen der Event ausgelöst wurde
				final Stage stage = (Stage) source.getScene().getWindow(); // Hole das Fenster
				if(!stage.isIconified())
					stage.setIconified(true); // maximiere
			}
		});
	}

	// makes the window moveable
	private void makeMovable() {
		mainPane.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});
		mainPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mainPane.getScene().getWindow().setX(event.getScreenX() - xOffset);
				mainPane.getScene().getWindow().setY(event.getScreenY() - yOffset);
			}
		});
	}
	
	protected Tooltip makeBubble(Tooltip tooltip) {
        tooltip.getStyleClass().add("noTestBubble");
        tooltip.setAnchorLocation(PopupWindow.AnchorLocation.WINDOW_BOTTOM_LEFT);

        return tooltip;
    }
	
	protected void createAndShowComfirmAlert(String title, String header) {
		decideWindow = new Alert(AlertType.CONFIRMATION);
		decideWindow.initModality(Modality.APPLICATION_MODAL); // on top
		decideWindow.setTitle(title);
		decideWindow.setHeaderText(header);
		decideWindow.getButtonTypes().clear();
		decideWindow.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
		decideWindow.showAndWait();
	}
	
	protected Optional<ButtonType> createComfirmAlert(String title, String header) {
		decideWindow = new Alert(AlertType.CONFIRMATION);
		decideWindow.initModality(Modality.APPLICATION_MODAL); // on top
		decideWindow.setTitle(title);
		decideWindow.setHeaderText(header);
		decideWindow.getButtonTypes().clear();
		decideWindow.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
		return decideWindow.showAndWait();
	}
	
	protected Alert decideWindow() {
		return decideWindow;
	}

	private void getScreenSize() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		screenWidth = gd.getDisplayMode().getWidth();
		screenHeight = gd.getDisplayMode().getHeight();
	}
	
	protected void setTitle(String title) {
		this.title.setText(title);
	}
	
	protected AnchorPane mainPane() {
		return mainPane;
	}
	
	protected int screenWidth() {
		return screenWidth;
	}
	
	protected int screenHeight() {
		return screenHeight;
	}
}
