package View;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import Model.HTMLFile;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class TestEndView extends MainWindow {
	private StackPane stack;
	private VBox content;
	private HBox buttonBox;

	private Label textLabel;
	private Button mainPageButton;
	private Button pdfButton;

	private String test;

	public TestEndView(StackPane stack, String test) {
		this.stack = stack;
		this.test = test;
		setTitle("Test " + test + " abgeschlossen");
		createTestEndView();
		stack.getChildren().add(mainPane());
	}
	private void createTestEndView() {
		createComponents();
		addComponents();
		configComponents();
	}

	private void createComponents() {
		content = new VBox();
		buttonBox = new HBox();

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.YYYY");
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");

		textLabel = new Label("Sie haben den Test " + test + " am " + sdf.format(date) + " um " + sdfTime.format(date) + "\nbeendet.\n"
				+ "Über die Schaltfäche \"Bestätigung\" können Sie sich die Bestätigung,\n"
				+ "dass Sie erfolgreich am Test teilgenommen haben runterladen.\nDiese brauchen Sie als Nachweis.");
		mainPageButton = new Button("Hauptseite");
		pdfButton = new Button("Bestätigung");
	}

	private void addComponents() {
		content.getChildren().addAll(textLabel, pdfButton);
		buttonBox.getChildren().add(mainPageButton);
		mainPane().getChildren().addAll(content, buttonBox);
	}

	private void configComponents() {
		content.setAlignment(Pos.TOP_CENTER);
		content.setPadding(new Insets(40.0));
		pdfButton.setMinWidth(mainPane().getMinWidth() * 0.2);
		setAnchors();
		setSpacing();
		setStyle();
		setActions();
	}

	private void setAnchors() {
		AnchorPane.setTopAnchor(content, mainPane().getMinHeight() * 0.06);
		AnchorPane.setLeftAnchor(content, 0.0);
		AnchorPane.setRightAnchor(content, 0.0);
		AnchorPane.setBottomAnchor(content, mainPane().getMinHeight() * 0.06);
		AnchorPane.setBottomAnchor(buttonBox, 0.0);
		AnchorPane.setRightAnchor(buttonBox, 0.0);
	}

	private void setSpacing() {
		content.setSpacing(40.0);
	}

	private void setStyle() {
		pdfButton.getStyleClass().add("mainButton");
		mainPageButton.getStyleClass().add("mainButton");
		textLabel.setStyle("-fx-font-size: 18px; -fx-font-family: \"Arial\";");
	}

	private void setActions() {
		mainPageButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				changeTop();
			}
		});
		pdfButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				HTMLFile htmlFile = new HTMLFile(new File("src/PDFs/Bescheinigung.xhtml").getAbsolutePath());

				FileChooser dirChooser = new FileChooser();
				dirChooser.setTitle("Select Save Directory");
				dirChooser.setInitialDirectory(new File(System.getProperty("user.home")));
				dirChooser.getExtensionFilters().add(new ExtensionFilter("PDF File", "*.pdf"));
				File file = dirChooser.showSaveDialog(mainPane().getScene().getWindow());
				if(file != null) {
					Notification noti = new Notification();
					if(htmlFile.generatePDF(file.getAbsolutePath())) {
						noti.information("PDF gespeichert", "Die Bescheinigung wurde gespeichert.");
						changeTop();
					} else {
						noti.error("Speicherfehler", "Fehler beim Speichern der Bescheinigung.");
					}
				}
			}
		});
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
