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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class KorrekteurKorrekturView extends MainWindow {
	private StackPane stack;

	private BorderPane content;
	private ScrollPane scrollContent;
	private VBox questionBox;

	private Label mainText;
	private Button cancelButton;
	private Button submitButton;
	private HBox buttonBox;

	private List<TextField> dozentTFList;
	private List<TextField> correctTFList;
	private List<HBox> toggleGroupList;
	private TreeMap<String, String> questionAnswerMap;

	public KorrekteurKorrekturView(StackPane stack) {
		this.stack = stack;
		setTitle("Korrektur");
		createKorrekteurKorrekturView();
		this.stack.getChildren().add(mainPane());
	}

	private void createKorrekteurKorrekturView() {
		createComponents();
		addComponents();
		configComponents();
	}

	private void createComponents() {
		createContainers();
		questionBox = new VBox();
		content = new BorderPane();
		submitButton = new Button("Absenden");
		cancelButton = new Button("Zurück");
		scrollContent = new ScrollPane();
		createQuestionAnswerDialog(10);
	}

	private void createContainers() {
		buttonBox = new HBox();
		dozentTFList = new ArrayList<TextField>();
		correctTFList = new ArrayList<TextField>();
		questionAnswerMap  = new TreeMap<String, String>();
		toggleGroupList = new ArrayList<HBox>();
	}

	private void addComponents() {
		buttonBox.getChildren().addAll(cancelButton, submitButton);
		content.setBottom(buttonBox);
		content.setCenter(scrollContent);
		scrollContent.setContent(questionBox);
		mainPane().getChildren().add(content);
	}

	private void configComponents() {
		scrollContent.setMaxHeight(mainPane().getMinHeight() * 0.85);
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
		buttonBox.setSpacing(mainPane().getMinWidth() * 0.83 - (submitButton.getMinWidth() * 2));
	}

	private void setStyle() {
		cancelButton.getStyleClass().add("mainButton");
		submitButton.getStyleClass().add("mainButton");
		scrollContent.getStyleClass().add("mainScrollPane");
		questionBox.getStyleClass().add("transparentBackground");
		content.getStyleClass().add("transparentBackground");
		BorderPane.setMargin(submitButton, new Insets(10.0, 0.0, 0.0, 0.0));
	}

	private void setActions() {
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				Optional<ButtonType> opt = createComfirmAlert("Korrekturabgabe", "Möchten Sie die Korrektur wirklich abgeben?");
				if(opt.get().getButtonData() == ButtonData.YES) {
					changeTop();
				}
			}
		});

		cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				changeTop();
			}
		});
	}

	private void createQuestionAnswerDialog(int amount) {
		createToggleGroup(amount);
		fillQuestionsAnswer(amount);
		createTextFields(amount);

		int it = 0; // for access on TextFields
		Set <String>questionAnswerKeys = questionAnswerMap.keySet();
		for(Iterator<String> i = questionAnswerKeys.iterator(); i.hasNext();) {
			VBox innerQuestionAnswerBox = new VBox();
			innerQuestionAnswerBox.setAlignment(Pos.CENTER_LEFT);
			innerQuestionAnswerBox.getStyleClass().add("transparentBackgroundWithPadding");
			innerQuestionAnswerBox.setSpacing(mainPane().getMinHeight() * 0.05);
			String key = i.next();
			Label value = new Label(questionAnswerMap.get(key));
			Label keyLabel = new Label(key);
			keyLabel.setStyle("-fx-font-size: 16px;");
			value.setStyle("-fx-font-size: 14px;");
			innerQuestionAnswerBox.getChildren().addAll(keyLabel, value, toggleGroupList.get(it), dozentTFList.get(it), correctTFList.get(it));
			questionBox.getChildren().add(innerQuestionAnswerBox);
			it++;
		}
	}

	private void createTextFields(int amount) {
		for(int i = 0; i < amount; i++) {
			TextField dozentAnswerTF = new TextField();
			TextField answerTF = new TextField();

			answerTF.setPromptText("Antwort des Dozenten zu Frage "+(i+1));
			answerTF.getStyleClass().add("mainScrollPaneTextField");
			answerTF.setMinWidth(mainPane().getMinWidth() * 0.8);

			dozentAnswerTF.setPromptText("Korrekturantwort zu Frage "+(i+1));
			dozentAnswerTF.getStyleClass().add("mainScrollPaneTextField");
			dozentAnswerTF.setMinWidth(mainPane().getMinWidth() * 0.8);

			correctTFList.add(answerTF);
			dozentTFList.add(dozentAnswerTF);
		}
	}

	private void createToggleGroup(int amount) {
		for(int i = 0; i < amount; i++) {
			ToggleGroup tg = new ToggleGroup();

			RadioButton rb1 = new RadioButton("Richtig");
			RadioButton rb2 = new RadioButton("Falsch");

			rb1.setToggleGroup(tg);
			rb2.setToggleGroup(tg);

			HBox radioButtonBox = new HBox();
			radioButtonBox.getChildren().addAll(rb1, rb2);
			radioButtonBox.setSpacing(mainPane().getMinWidth() * 0.1);

			toggleGroupList.add(radioButtonBox);
		}
	}

	private void fillQuestionsAnswer(int amount) {
		for(int i = 0; i < amount; i++)
			questionAnswerMap.put(("Frage "+(i+1)), ("Antwort des Studenten zu Nr "+ (i+1)));
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
