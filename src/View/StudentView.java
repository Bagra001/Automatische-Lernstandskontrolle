package View;

import com.sun.rowset.providers.RIOptimisticProvider;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class StudentView extends MainWindow {
	private StackPane stack;
	private BorderPane leftPane;
	private VBox rightPane;
	private HBox buttonBox;
	
	private ListView<String> modulListView;
	
	private Button logoutButton;
	private Button removeButton;
	private Button addButton;
	private Button startButton;
	private Button profilButton;
	private ComboBox<String> addModulComboBox;
	
	private Label hasTests;
	private ComboBox<String> testComboBox;
	
	private Image startImg;
	private Image addImg;
	private Image remImg;
	
	public StudentView(StackPane stack) {
		super();
		this.stack = stack;
		setTitle("Hauptfenster");
		createStudentView();
		this.stack.getChildren().add(mainPane());
	}
	
	private void createStudentView() {
		createComponents();
		configComponents();
		setupListView();
		addComponents();
		setAnchors();
		setStyle();
		setupToolTip();
	}
	
	private void createComponents() {
		modulListView = new ListView<String>();
		modulListView.getItems().add("TestModul");
		createButtons();
		createBoxes();
		leftPane = new BorderPane();
		hasTests = new Label("Es stehen keine Tests an");
		createChoiceBoxes();
		createImages();
	}
	
	private void createChoiceBoxes() {
		testComboBox = new ComboBox<String>();
		addModulComboBox = new ComboBox<String>();
	}

	private void createImages() {
		startImg = new Image(getClass().getResourceAsStream("/Bilder/play.png"));
		addImg = new Image(getClass().getResourceAsStream("/Bilder/add.png"));
		remImg = new Image(getClass().getResourceAsStream("/Bilder/rem.png"));
	}

	private void createButtons() {
		logoutButton = new Button("Ausloggen");
		removeButton = new Button();
		addButton = new Button();
		startButton = new Button("Test starten");
		profilButton = new Button("Profil");
	}
	
	private void createBoxes() {
		rightPane = new VBox();
		buttonBox = new HBox();
	}
	
	private void addComponents() {
		buttonBox.getChildren().addAll(logoutButton, profilButton);
		
		HBox box2 = new HBox(addModulComboBox, addButton, removeButton);
		box2.setSpacing(mainPane().getMinWidth() * 0.05);
		box2.setAlignment(Pos.CENTER);
		
		rightPane.getChildren().addAll(box2,hasTests,testComboBox,startButton, buttonBox);
		mainPane().getChildren().addAll(leftPane, rightPane);
		setSpacings();
	}
	
	private void configComponents() {
		startButton.setGraphic(createAndSizeImageView(startImg));
		setSizes();
		setRightComponentSize(rightPane.getMinWidth() * 0.4);
		rightPane.setAlignment(Pos.TOP_CENTER);
		setActions();
		addButton.setGraphic(createAndSizeImageView(addImg));
		removeButton.setGraphic(createAndSizeImageView(remImg));
		addModulComboBox.getItems().add("Modul");
		testComboBox.getItems().add("Test");
	}
	
	private void setupToolTip() {
		Tooltip.install(hasTests, makeBubble(new Tooltip("Info:\nEs stehen keine Test an.\n")));
	}
	
	private void setupListView() {
		leftPane.setCenter(modulListView);
		modulListView.setMaxSize(leftPane.getMinWidth(), leftPane.getMinHeight());
		modulListView.setMinSize(leftPane.getMinWidth(), leftPane.getMinHeight());
		modulListView.setPlaceholder(new Label("No Data available"));
		modulListView.setCellFactory(param -> new ListCell<String>() {
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					modulListView.setPlaceholder(new Label("No Data available"));
				} else {
					setText(item);
				}
			}
		});
	}
	
	private void setRightComponentSize(double width) {
		removeButton.setMinWidth(width * 0.3);
		removeButton.setMaxWidth(width * 0.3);
		startButton.setMinWidth(width);
		startButton.setMaxWidth(width);
		addButton.setMinWidth(width * 0.3);
		addButton.setMaxWidth(width * 0.3);
		profilButton.setMinWidth(width * 0.8);
		profilButton.setMaxWidth(width * 0.8);
		logoutButton.setMinWidth(width * 0.8);
		logoutButton.setMaxWidth(width * 0.8);
		testComboBox.setMinWidth(width);
		testComboBox.setMaxWidth(width);
		addModulComboBox.setMaxWidth(width);
		addModulComboBox.setMinWidth(width);
	}
	
	private void setSizes() {
		rightPane.setMinSize(mainPane().getMinWidth() * 0.4, mainPane().getMinHeight() * 0.9);
		rightPane.setMaxSize(mainPane().getMinWidth() * 0.4, mainPane().getMinHeight() * 0.9);
		leftPane.setMinHeight(mainPane().getMinHeight() * 0.9);
		leftPane.setMinWidth(mainPane().getMinWidth() * 0.5);
		buttonBox.setMaxWidth(mainPane().getMinWidth() * 0.4);
		addModulComboBox.setVisibleRowCount(8);
		testComboBox.setVisibleRowCount(8);
	}
	
	private void setSpacings() {
		buttonBox.setSpacing(mainPane().getMinWidth() * 0.15);
		rightPane.setSpacing(mainPane().getMinHeight() * 0.15);
	}
	
	private void setAnchors() {
		AnchorPane.setLeftAnchor(leftPane, 0.0);
		AnchorPane.setTopAnchor(leftPane, mainPane().getMinHeight() * 0.06);
		AnchorPane.setTopAnchor(rightPane, mainPane().getMinHeight() * 0.1);
		AnchorPane.setLeftAnchor(rightPane, leftPane.getMinWidth() + 30);
	}
	
	private void setStyle() {
		logoutButton.getStyleClass().add("mainButton");
		removeButton.getStyleClass().add("mainButton");
		addButton.getStyleClass().add("mainButton");
		profilButton.getStyleClass().add("mainButton");
		startButton.getStyleClass().add("mainButton");
		hasTests.setStyle("-fx-font-size: 16px;");
	}
	
	private void setActions() {
		logoutButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				changeTop();
			}
		});
		
		profilButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				ProfilView profilV = new ProfilView(stack);
			}
		});
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				StudentTestView stTestView = new StudentTestView(stack, "TEST des MODULS");
			}
		});
		
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				createAndShowComfirmAlert("Modul hinzufügen", "Möchten Sie wirklich MODUL\nzu deinen Modulen hinzufügen?");
			}
		});
		
		removeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				createAndShowComfirmAlert("Modul löschen", "Möchten Sie wirklich MODUL\nvon deinen Modulen löschen?");
			}
		});
		
		modulListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		    	ModulTestView testView = new ModulTestView(stack, modulListView);
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
