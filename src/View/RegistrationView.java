package View;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegistrationView {
	private StackPane stack;
	private Notification noti;
	
	private AnchorPane mainPane;
	private HBox buttonBox;
	
	private BorderPane rightPane;
	private TextField eMailTextField;
	private PasswordField passwordTextField;
	private PasswordField compPasswordTextField;
	private TextField matrikelTextField;
	private TextField vornameTextField;
	private TextField nachnameTextField;
	private HBox textfieldBox;
	private Button signUpButton;
	private Button ejectButton;
	private CheckBox isDozent;
	
	private BorderPane leftPane;
	private Button loginButton;

	private VBox overlayBox;
	private Image logo;
	private Label welcomeLabel;
	
	private Button closeButton;
	private Button minimizeButton;
	private Image imageClose;
	private Image imageMin;
	
	// vars for movement
	private double xOffset = 0;
	private double yOffset = 0;
	
	// for resizing
	private int screenWidth;
	private int screenHeight;
	
	public RegistrationView(StackPane stack) {
		this.stack = stack;
		createRegistrationView();
		this.stack.getChildren().add(mainPane);
	}
	private void createRegistrationView() {
		createComponents();
		addComponents();
		configAll();
		configLoginTextFields();
		configLeftSide();
	}

	private void createComponents() {
		createPanes();
		createTextFields();
		createImages();
		createLabels();
		createButtons();
	}
	
	private void createPanes() {
		mainPane = new AnchorPane();
		overlayBox = new VBox();
		buttonBox = new HBox();
		rightPane = new BorderPane();
		textfieldBox = new HBox();
		leftPane = new BorderPane();
	}
	
	private void createButtons() {
		closeButton = new Button();
		minimizeButton = new Button();
		signUpButton = new Button("Registrieren");
		loginButton = new Button("Einloggen");
		ejectButton = new Button("Verwerfen");
		isDozent = new CheckBox("Dozent");
	}
	
	private void createLabels() {
		welcomeLabel = new Label("Willkommen,\nauf der Seite zur Registrierung.\n\n*Die EMailadresse wird als Benutzername\nbenutzt.");
	}
	
	private void createImages() {
		imageClose = new Image(getClass().getResourceAsStream("/Bilder/close.png"));
		imageMin = new Image(getClass().getResourceAsStream("/Bilder/min.png"));
		logo = new Image(getClass().getResourceAsStream("/Bilder/FH-SWF_Logo-RGB.jpg"));
	}
	
	private void createTextFields() {
		eMailTextField = new TextField();
		passwordTextField = new PasswordField();
		compPasswordTextField = new PasswordField();
		matrikelTextField = new TextField();
		vornameTextField = new TextField();
		nachnameTextField = new TextField();
	}

	private void addComponents() {
		buttonBox.getChildren().addAll(minimizeButton,closeButton);
		rightPane.setTop(buttonBox);
		rightPane.setCenter(textfieldBox);
		mainPane.getChildren().addAll(leftPane, overlayBox, rightPane);
	}

	private void configAll() {
		setSizes();
		setButtonIcons();
		fhswfLogo();
		setStyle();
		setAnchor();
		setAlignments();
		setActions();
		makeMovable();
	}
	
	private void configLeftSide() {
		VBox content = new VBox();
		content.setAlignment(Pos.CENTER);
		content.setStyle("-fx-spacing: 20;");
		content.getChildren().addAll(loginButton);
		leftPane.setCenter(content);
	}
	
	private void fhswfLogo() {
		ImageView imageView = new ImageView(logo);
		imageView.setFitWidth(screenWidth * 0.2);
		imageView.setFitHeight(screenHeight * 0.1);
		overlayBox.getChildren().add(imageView);
		overlayBox.getChildren().add(welcomeLabel);
	}

	private void setSizes() {
		getScreenSize();
		setMainPaneSize(screenWidth * 0.6, screenHeight * 0.5);
		setOverlayBoxSize(screenWidth * 0.23, screenHeight * 0.62);
		setRightPaneSize(screenWidth * 0.25, screenHeight * 0.5);
		setWindowButtonSize(25.0, 25.0);
		setLeftPaneSize(screenWidth * 0.12, screenHeight * 0.5);
		setLoginTextFieldWidth(screenWidth * 0.1);
	}
	
	private void setMainPaneSize(double width, double height) {
		mainPane.setMinSize(width, height);
		mainPane.setMaxSize(width, height);
	}
	
	private void setOverlayBoxSize(double width, double height) {
		overlayBox.setMinSize(width, height);
		overlayBox.setMaxSize(width, height);
	}
	
	private void setRightPaneSize(double width, double height) {
		rightPane.setMinSize(width, height);
		rightPane.setMinSize(width, height);
	}
	
	private void setLeftPaneSize(double width, double height) {
		leftPane.setMinSize(width, height);
		leftPane.setMaxSize(width, height);
	}
	
	private void setWindowButtonSize(double width, double height) {
		closeButton.setMaxSize(width, height);
		closeButton.setMinSize(width, height);
		minimizeButton.setMinSize(width, height);
		minimizeButton.setMaxSize(width, height);
	}
	
	private void setLoginTextFieldWidth(double width) {
		eMailTextField.setMaxWidth(width);
		eMailTextField.setMinWidth(width);
		passwordTextField.setMaxWidth(width);
		passwordTextField.setMinWidth(width);
		compPasswordTextField.setMaxWidth(width);
		compPasswordTextField.setMinWidth(width);
		matrikelTextField.setMinWidth(width);
		matrikelTextField.setMaxWidth(width);
		vornameTextField.setMinWidth(width);
		vornameTextField.setMaxWidth(width);
		nachnameTextField.setMinWidth(width);
		nachnameTextField.setMaxWidth(width);
	}
	
	private void configLoginTextFields() {
		eMailTextField.setPromptText("EMail*");
		passwordTextField.setPromptText("Passwort");
		compPasswordTextField.setPromptText("Wiederhole Passwort");
		nachnameTextField.setPromptText("Nachname");
		vornameTextField.setPromptText("Vorname");
		matrikelTextField.setPromptText("Matrikelnummer");
		
		VBox box1 = new VBox();
		box1.setSpacing(screenHeight * 0.05);
		box1.getChildren().addAll(vornameTextField, nachnameTextField, matrikelTextField, isDozent);
		
		VBox box2 = new VBox();
		box2.setSpacing(screenHeight * 0.05);
		box2.getChildren().addAll(eMailTextField, passwordTextField, compPasswordTextField);
		
		HBox buttonBox = new HBox();
		buttonBox.getChildren().addAll(ejectButton, signUpButton);
		buttonBox.setSpacing(screenWidth * 0.08);
		
		textfieldBox.getChildren().addAll(box1, box2);
		rightPane.setBottom(buttonBox);
	}

	private ImageView createAndSizeImageView(Image img) {
		ImageView imageView = new ImageView(img);
		imageView.setFitHeight(16.0);
		imageView.setFitWidth(16.0);
		return imageView;
	}

	private void setButtonIcons() {
		closeButton.setGraphic(createAndSizeImageView(imageClose));
		minimizeButton.setGraphic(createAndSizeImageView(imageMin));
	}

	private void setStyle() {
		mainPane.setStyle("-fx-background-color: #01509b;");
		overlayBox.setStyle("-fx-background-color: white;");
		rightPane.setStyle("-fx-background-color: transparent; -fx-padding: 10");
		leftPane.setStyle("-fx-background-color: transparent; -fx-padding: 20;");
		textfieldBox.setStyle("-fx-spacing: 30;-fx-padding: 60 0 0 0;");
		closeButton.getStyleClass().add("buttonBoxButton");
		minimizeButton.getStyleClass().add("buttonBoxButton");
		buttonBox.getStyleClass().add("buttonBox");
		overlayBox.getStyleClass().add("overlayBox");
		eMailTextField.getStyleClass().add("loginTextField");
		passwordTextField.getStyleClass().add("loginTextField");
		compPasswordTextField.getStyleClass().add("loginTextField");
		matrikelTextField.getStyleClass().add("loginTextField");
		vornameTextField.getStyleClass().add("loginTextField");
		nachnameTextField.getStyleClass().add("loginTextField");
		loginButton.getStyleClass().add("mainButton");
		signUpButton.getStyleClass().add("mainButton");
		ejectButton.getStyleClass().add("mainButton");
	}

	private void setAnchor() {
		AnchorPane.setLeftAnchor(overlayBox, 150.0);
		AnchorPane.setTopAnchor(overlayBox, -50.0);
		AnchorPane.setTopAnchor(rightPane, 0.0);
		AnchorPane.setRightAnchor(rightPane, 0.0);
		AnchorPane.setLeftAnchor(leftPane, 0.0);
		AnchorPane.setTopAnchor(leftPane, 0.0);
	}

	private void setAlignments() {
		buttonBox.setAlignment(Pos.TOP_RIGHT);
		overlayBox.setAlignment(Pos.TOP_CENTER);
		textfieldBox.setAlignment(Pos.TOP_CENTER);
		BorderPane.setAlignment(textfieldBox, Pos.CENTER);
	}

	private void setActions() {
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
		
		loginButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				changeTop();
			}
		});
		
		signUpButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				changeTop();
			}
		});
		
		ejectButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				noti = new Notification();
				noti.warning("Verwerfung", "Daten wurden verworfen");
			}
		});
		
		isDozent.selectedProperty().addListener(new ChangeListener<Boolean>() {
	        public void changed(ObservableValue<? extends Boolean> ov,
	                Boolean old_val, Boolean new_val) {
	                    matrikelTextField.setVisible(new_val ? false : true);
	            }
	        });
	}

	// makes the loginpage moveable
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
	
	private void getScreenSize() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		screenWidth = gd.getDisplayMode().getWidth();
		screenHeight = gd.getDisplayMode().getHeight();
	}
	
	private void changeTop() {
		ObservableList<Node> childs = this.stack.getChildren();
		if (childs.size() > 1) {
			Node topNode = childs.get(childs.size()-1);
			topNode.setVisible(false);
			topNode.toBack();
		}
	}
}
