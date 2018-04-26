package View;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class KorrekteurView extends MainWindow {
	private StackPane stack;
	private VBox rightListViewBox;
	private VBox buttonBox;

	private ListView<String> modulListView;
	private ListView<String> testListView;
	private ListView<String> studentListView;

	private Button logoutButton;
	private Button correctButton;

	public KorrekteurView(StackPane stack) {
		this.stack = stack;
		setTitle("Hauptfenster");
		createKorrekteurView();
		this.stack.getChildren().add(mainPane());
	}

	private void createKorrekteurView() {
		createComponents();
		addComponents();
		configComponents();
	}

	private void createComponents() {
		createListViews();
		createVBoxes();
		createButton();
	}

	private void createListViews() {
		modulListView = new ListView<String>();
		testListView = new ListView<String>();
		studentListView = new ListView<String>();
	}

	private void createVBoxes() {
		rightListViewBox = new VBox();
		buttonBox = new VBox();
	}

	private void createButton() {
		logoutButton = new Button("Ausloggen");
		correctButton = new Button("Korrigieren");
	}

	private void addComponents() {
		rightListViewBox.getChildren().addAll(testListView, studentListView);
		buttonBox.getChildren().addAll(correctButton, logoutButton);
		mainPane().getChildren().addAll(modulListView, rightListViewBox, buttonBox);
	}

	private void configComponents() {
		rightListViewBox.setSpacing(mainPane().getMinHeight() * 0.01);
		configButtonBox();
		setAnchors();
		setupModulListView();
		setupTestListView();
		setupStudentListView();
		setActions();
		setStyle();
	}

	private void configButtonBox() {
		buttonBox.setMinHeight(mainPane().getMinHeight());
		buttonBox.setSpacing(mainPane().getMinHeight() * 0.8 - (2 * logoutButton.getMinHeight()));
		buttonBox.setAlignment(Pos.CENTER);
	}

	private void setAnchors() {
		AnchorPane.setLeftAnchor(modulListView, 0.0);
		AnchorPane.setTopAnchor(modulListView, mainPane().getMinHeight() * 0.06);
		AnchorPane.setBottomAnchor(modulListView, mainPane().getMinHeight() * 0.01);
		AnchorPane.setLeftAnchor(rightListViewBox, mainPane().getMinWidth() * 0.4 + 10.0);
		AnchorPane.setTopAnchor(rightListViewBox, mainPane().getMinHeight() * 0.06);
		AnchorPane.setBottomAnchor(rightListViewBox, mainPane().getMinHeight() * 0.06);
		AnchorPane.setTopAnchor(buttonBox, mainPane().getMinHeight() * 0.1);
		AnchorPane.setRightAnchor(buttonBox, 0.0);
		AnchorPane.setBottomAnchor(buttonBox, mainPane().getMinHeight() * 0.06);
	}

	private void setupModulListView() {
		modulListView.getItems().add("Modul");

		modulListView.setMaxSize(mainPane().getMinWidth() * 0.4, mainPane().getMinHeight());
		modulListView.setMinSize(mainPane().getMinWidth() * 0.4, mainPane().getMinHeight());
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

	private void setupTestListView() {
		if(modulListView.getSelectionModel().getSelectedItem() != null)
			testListView.getItems().add("Test");

		testListView.setMaxSize(mainPane().getMinWidth() * 0.4, mainPane().getMinHeight() * 0.45);
		testListView.setMinSize(mainPane().getMinWidth() * 0.4, mainPane().getMinHeight() * 0.45);
		testListView.setPlaceholder(new Label("No Data available"));
		testListView.setCellFactory(param -> new ListCell<String>() {
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					testListView.setPlaceholder(new Label("No Data available"));
				} else {
					setText(item);
				}
			}
		});
	}

	private void setupStudentListView() {
		if(testListView.getSelectionModel().getSelectedItem() != null)
			studentListView.getItems().add("Student");

		studentListView.setMaxSize(mainPane().getMinWidth() * 0.4, mainPane().getMinHeight() * 0.45);
		studentListView.setMinSize(mainPane().getMinWidth() * 0.4, mainPane().getMinHeight() * 0.45);
		studentListView.setPlaceholder(new Label("No Data available"));
		studentListView.setCellFactory(param -> new ListCell<String>() {
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					studentListView.setPlaceholder(new Label("No Data available"));
				} else {
					setText(item);
				}
			}
		});
	}
	
	private void setActions() {
		modulListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent event) {
	        	testListView.getItems().add("Test");
	        }
	    });
		testListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent event) {
	        	studentListView.getItems().add("Student");
	        }
	    });
	}

	private void setStyle() {
		correctButton.getStyleClass().add("mainButton");
		logoutButton.getStyleClass().add("mainButton");
	}
}
