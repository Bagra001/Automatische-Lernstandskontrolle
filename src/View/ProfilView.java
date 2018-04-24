package View;

import java.util.Optional;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Text;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

public class ProfilView extends MainWindow {
	private StackPane stack;
	private BorderPane content;
	private VBox vBox;
	
	private Image logo;
	
	private Button backButton;
	private Button changeButton;
	private Button changePasswordButton;
	
	private TextField matrikelTextField;
	private TextField eMailTextField;
	private TextField vornameTextField;
	private TextField nachnameTextField;
	
	public ProfilView(StackPane stack) {
		super();
		setTitle("Profil");
		this.stack = stack;
		createProfilView();
		this.stack.getChildren().add(mainPane());
	}

	private void createProfilView() {
		createComponents();
		fhswfLogo();
		addComponents();
		configComponents();
	}

	private void createComponents() {
		content = new BorderPane();	
		vBox = new VBox();
		logo = new Image(getClass().getResourceAsStream("/Bilder/FH-SWF_Logo-RGB.jpg"));
		createButtons();
		createTextFields();
	}
	
	private void createButtons() {
		backButton = new Button("Zurück");
		changeButton = new Button("Ändern");
		changePasswordButton = new Button("Passwort ändern");
	}
	
	private void createTextFields() {
		matrikelTextField = new TextField();
		eMailTextField = new TextField();
		vornameTextField = new TextField();
		nachnameTextField = new TextField();
	}
	
	private void addComponents() {
		vBox.getChildren().addAll(matrikelTextField, eMailTextField, vornameTextField, nachnameTextField, changePasswordButton);
		content.setCenter(vBox);
		content.setLeft(backButton);
		content.setRight(changeButton);
		mainPane().getChildren().add(content);
	}
	
	private void configComponents() {
		setSizes();
		setAlignment();
		setAnchors();
		setStyle();
		setPromptText();
		vBox.setSpacing(content.getMinHeight() * 0.09);
		setActions();
	}
	
	private void setPromptText() {
		matrikelTextField.setPromptText("Matrikelnummer");
		eMailTextField.setPromptText("EMail");
		vornameTextField.setPromptText("Vorname");
		nachnameTextField.setPromptText("Nachname");
	}
	
	private void fhswfLogo() {
		ImageView imageView = new ImageView(logo);
		imageView.setFitWidth(screenWidth() * 0.2);
		imageView.setFitHeight(screenHeight() * 0.15);
		vBox.getChildren().add(imageView);
	}
	
	private void setSizes() {
		content.setMaxSize(mainPane().getMinWidth() * 0.9, mainPane().getMinHeight() * 0.9);
		content.setMinSize(mainPane().getMinWidth() * 0.9, mainPane().getMinHeight() * 0.9);
		setButtonWidths(content.getMinWidth() * 0.15);
		setTextFieldWidth(content.getMinWidth() * 0.4);
	}
	
	private void setButtonWidths(double width) {
		backButton.setMinWidth(width);
		backButton.setMaxWidth(width);
		changeButton.setMinWidth(width);
		changeButton.setMaxWidth(width);
	}
	private void setTextFieldWidth(double width) {
		matrikelTextField.setMinWidth(width);
		matrikelTextField.setMaxWidth(width);
		eMailTextField.setMinWidth(width);
		eMailTextField.setMaxWidth(width);
		vornameTextField.setMinWidth(width);
		vornameTextField.setMaxWidth(width);
		nachnameTextField.setMinWidth(width);
		nachnameTextField.setMaxWidth(width);
	}
	
	private void setAlignment() {
		BorderPane.setAlignment(vBox, Pos.TOP_CENTER);
		BorderPane.setAlignment(backButton, Pos.BOTTOM_CENTER);
		BorderPane.setAlignment(changeButton, Pos.BOTTOM_CENTER);
		VBox.setVgrow(mainPane(), Priority.ALWAYS);
		vBox.setAlignment(Pos.TOP_CENTER);
	}
	
	private void setAnchors() {
		AnchorPane.setTopAnchor(content, 30.0);
		AnchorPane.setLeftAnchor(content, 10.0);
		AnchorPane.setRightAnchor(content, 10.0);
	}
	
	private void setStyle() {
		setWhiteMainStyleColor();
		backButton.getStyleClass().add("mainButton");
		changeButton.getStyleClass().add("mainButton");
		changePasswordButton.getStyleClass().add("mainButton");
		matrikelTextField.getStyleClass().add("mainTextField");
		eMailTextField.getStyleClass().add("mainTextField");
		vornameTextField.getStyleClass().add("mainTextField");
		nachnameTextField.getStyleClass().add("mainTextField");
	}
	
	private void setActions() {
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				changeTop();
			}
		});
		
		changePasswordButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				changePasswordDialog("Passwort ändern","Wollen Sie das Passwort ändern?");
			}
		});
		changeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				createAndShowComfirmAlert("Daten ändern","Wollen Sie die Daten ändern?");
			}
		});
	}
	
	protected void changePasswordDialog(String title, String header) {
		Alert dialog = decideWindow();
		dialog = new Alert(AlertType.CONFIRMATION);
		dialog.initModality(Modality.APPLICATION_MODAL); // only on top
		dialog.setTitle(title);
		dialog.setHeaderText(header);
		
		VBox box = new VBox();
		box.setSpacing(mainPane().getMinHeight() * 0.05);
		
		TextField oldPwTF = new TextField();
		configTextField(oldPwTF, "Altes Passwort");
		TextField passwordTF = new TextField();
		configTextField(passwordTF, "Passwort");
		TextField confirmPwTF = new TextField();
		configTextField(confirmPwTF, "Passwort wiederholen");
		
		box.getChildren().addAll(oldPwTF, passwordTF, confirmPwTF);
		box.setAlignment(Pos.CENTER);
		
		dialog.getDialogPane().setContent(box);
		dialog.getButtonTypes().clear();
		dialog.getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);
		Optional<ButtonType> button = dialog.showAndWait(); // get result
	}
	
	private void configTextField(TextField tf, String prompt) {
		tf.setPromptText(prompt);
		tf.getStyleClass().add("mainTextField");
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
