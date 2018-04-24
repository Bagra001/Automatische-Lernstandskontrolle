package View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class StudentTestView extends MainWindow {
	private StackPane stack;
	private BorderPane content;
	private ScrollPane scrollContent;
	private VBox questionBox;

	private Button submitButton;

	private List<TextField> textFieldsList;
	private TreeMap<String, String> questionMap;

	public StudentTestView(StackPane stack, String test) {
		this.stack = stack;
		setTitle("Test " + test);
		createStudentTestView();
		stack.getChildren().add(mainPane());
	}

	private void createStudentTestView() {
		createComponents();
		addComponents();
		configComponents();
	}

	private void createComponents() {
		textFieldsList = new ArrayList<TextField>();
		questionMap  = new TreeMap<String, String>();
		questionBox = new VBox();
		content = new BorderPane();
		submitButton = new Button("Abgeben");
		scrollContent = new ScrollPane();
		createQuestionDialog(10);
	}

	private void addComponents() {
		content.setBottom(submitButton);
		content.setCenter(scrollContent);
		scrollContent.setContent(questionBox);
		mainPane().getChildren().add(content);
	}

	private void configComponents() {
		setAnchors();
		setAlignments();
		setStyle();
		setSpacing();
		setActions();
	}

	private void setAnchors() {
		AnchorPane.setTopAnchor(content, mainPane().getMinHeight() * 0.06);
		AnchorPane.setLeftAnchor(content, 0.0);
		AnchorPane.setRightAnchor(content, 0.0);
		AnchorPane.setBottomAnchor(content, 0.0);
	}

	private void setAlignments() {
		questionBox.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(submitButton, Pos.CENTER_RIGHT);
	}

	private void setSpacing() {
		questionBox.setSpacing(mainPane().getMinHeight() * 0.1);
	}

	private void setStyle() {
		submitButton.getStyleClass().add("mainButton");
		scrollContent.getStyleClass().add("mainScrollPane");
		questionBox.getStyleClass().add("transparentBackground");
		content.getStyleClass().add("transparentBackground");
		BorderPane.setMargin(submitButton, new Insets(10.0, 0.0, 0.0, 0.0));
	}

	private void setActions() {
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				Optional<ButtonType> opt = createComfirmAlert("Abgabe des Tests", "Möchten Sie den Test wirklich abgeben?");
				if(opt.get().getButtonData() == ButtonData.YES) {
					changeTop();
					TestEndView tEndView = new TestEndView(stack, "TEST");
				}
			}
		});
	}

	private void createQuestionDialog(int amount) {
		fillQuestions(amount);
		createTextFields(amount);

		int it = 0; // for access on TextFields
		Set <String>questionKeys = questionMap.keySet();
		for(Iterator<String> i = questionKeys.iterator(); i.hasNext();) {
			VBox innerQuestionBox = new VBox();
			innerQuestionBox.setAlignment(Pos.CENTER_LEFT);
			innerQuestionBox.getStyleClass().add("transparentBackgroundWithPadding");
			innerQuestionBox.setSpacing(mainPane().getMinHeight() * 0.05);
			String key = i.next();
			Label value = new Label(questionMap.get(key));
			Label keyLabel = new Label(key);
			keyLabel.setStyle("-fx-font-size: 16px;");
			value.setStyle("-fx-font-size: 14px;");
			innerQuestionBox.getChildren().addAll(keyLabel, value, textFieldsList.get(it));
			questionBox.getChildren().add(innerQuestionBox);
			it++;
		}
	}

	private void createTextFields(int amount) {
		for(int i = 0; i < amount; i++) {
			TextField tf = new TextField();
			tf.setPromptText("Antwort zu Frage "+(i+1));
			tf.getStyleClass().add("mainScrollPaneTextField");
			tf.setMinWidth(mainPane().getMinWidth() * 0.8);
			textFieldsList.add(tf);
		}
	}

	private void fillQuestions(int amount) {
		for(int i = 0; i < amount; i++)
			questionMap.put(("Frage "+(i+1)), ("Dies ist eine Beispielfrage Nr "+ (i+1)));
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
