package View;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class DozentView extends MainWindow {
	private StackPane stack;
	private AnchorPane modulContent;
	private HBox modulBox;
	private HBox katalogBox;
	private HBox frageButtonBox;
	private VBox testBox;
	private VBox topRightBox;
	private HBox bottomRightBox;
	
	private TabPane tabsPane;
	private Tab modulTab;
	private Tab archivTab;
	
	private Button logoutButton;
	private Button addFrageButton;
	private Button addModulButton;
	private Button remModulButton;
	private Button editModulButton;
	private Button addKatalogButton;
	private Button remKatalogButton;
	private Button editKatalogButton;
	private Button addTestButton;
	private Button archivTestButton;
	private Button editTestButton;
	
	private Label modulLabel;
	private Label katalogLabel;
	private Label testLabel;
	
	private Image editImg;
	private Image addImg;
	private Image remImg;
	private Image archivImg;
	
	private ListView<String> modulListView;
	private ListView<String> testListView;
	
	public DozentView(StackPane stack) {
		this.stack = stack;
		setTitle("Hauptfenster");
		createDozentView();
		this.stack.getChildren().add(mainPane());
	}

	private void createDozentView() {
		createComponents();
		addComponents();
		configComponents();
	}

	private void createComponents() {
		createTabs();
		createContainers();
		testLabel = new Label("Test");
		modulLabel = new Label("Modul");
		katalogLabel = new Label("Fragenkatalog");
		createButtons();
		createImages();
		createListViews();
	}

	private void createTabs() {
		modulTab = new Tab("Module");
		archivTab = new Tab("Archiv");
		tabsPane = new TabPane(modulTab, archivTab);
	}
	
	private void createContainers() {
		modulBox = new HBox();
		katalogBox = new HBox();
		frageButtonBox = new HBox();
		testBox = new VBox();
		bottomRightBox = new HBox();
		topRightBox = new VBox();
		modulContent = new AnchorPane();
	}

	private void createButtons() {
		logoutButton = new Button("Ausloggen");
		addFrageButton = new Button("Frage hinzufügen");
		addModulButton = new Button();
		remModulButton = new Button();
		editModulButton = new Button();
		addTestButton = new Button();
		editTestButton = new Button();
		archivTestButton = new Button();
		addKatalogButton = new Button();
		remKatalogButton = new Button();
		editKatalogButton = new Button();
	}
	
	private void createImages() {
		editImg = new Image(getClass().getResourceAsStream("/Bilder/edit.png"));
		addImg = new Image(getClass().getResourceAsStream("/Bilder/add.png"));
		remImg = new Image(getClass().getResourceAsStream("/Bilder/rem.png"));
		archivImg = new Image(getClass().getResourceAsStream("/Bilder/archiv.png"));
	}
	
	private void createListViews() {
		modulListView = new ListView<String>();
		testListView = new ListView<String>();
	}
	
	private void configComponents() {
		tabsPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		setupModulListView();
		setupTestListView();
		configButtons();
		setAnchors();
		setAligment();
		setSize();
		setSpacings();
		setStyle();
		setActions();
	}
	
	private void configButtons() {
		addModulButton.setGraphic(createAndSizeImageView(addImg));
		addKatalogButton.setGraphic(createAndSizeImageView(addImg));
		addTestButton.setGraphic(createAndSizeImageView(addImg));
		editKatalogButton.setGraphic(createAndSizeImageView(editImg));
		editModulButton.setGraphic(createAndSizeImageView(editImg));
		editTestButton.setGraphic(createAndSizeImageView(editImg));
		remModulButton.setGraphic(createAndSizeImageView(remImg));
		remKatalogButton.setGraphic(createAndSizeImageView(remImg));
		archivTestButton.setGraphic(createAndSizeImageView(archivImg));
	}
	
	private void addComponents() {
		modulBox.getChildren().addAll(modulLabel, addModulButton, editModulButton, remModulButton);
		testBox.getChildren().addAll(testLabel, addTestButton, editTestButton, archivTestButton);
		topRightBox.getChildren().addAll(modulBox, katalogBox, frageButtonBox);
		katalogBox.getChildren().addAll( katalogLabel, addKatalogButton, editKatalogButton, remKatalogButton);
		frageButtonBox.getChildren().addAll(addFrageButton);
		bottomRightBox.getChildren().addAll(testListView, testBox);
		modulContent.getChildren().addAll(topRightBox, bottomRightBox, modulListView);
		modulTab.setContent(modulContent);
		mainPane().getChildren().add(tabsPane);
		mainPane().getChildren().add(logoutButton);
	}
	
	private void setAnchors() {
		AnchorPane.setLeftAnchor(modulListView, 0.0);
		AnchorPane.setTopAnchor(modulListView, 0.0);
		AnchorPane.setBottomAnchor(modulListView, mainPane().getMinHeight() * 0.01);
		
		AnchorPane.setLeftAnchor(topRightBox, mainPane().getMinWidth() * 0.5);
		AnchorPane.setRightAnchor(topRightBox,mainPane().getMinWidth() * 0.09);
		AnchorPane.setTopAnchor(topRightBox, mainPane().getMinHeight() * 0.03);
		
		AnchorPane.setRightAnchor(bottomRightBox, 0.0);
		AnchorPane.setTopAnchor(bottomRightBox, mainPane().getMinHeight() * 0.4);
		AnchorPane.setBottomAnchor(bottomRightBox, 0.0);
		
		AnchorPane.setTopAnchor(logoutButton, mainPane().getMinHeight() * 0.065);
		AnchorPane.setRightAnchor(logoutButton, 0.0);
		
		AnchorPane.setTopAnchor(tabsPane, mainPane().getMinHeight() * 0.06);
		AnchorPane.setBottomAnchor(tabsPane, 0.0);
		AnchorPane.setRightAnchor(tabsPane, 0.0);
		AnchorPane.setLeftAnchor(tabsPane, 0.0);
	}
	
	private void setAligment() {
		topRightBox.setAlignment(Pos.CENTER);
		bottomRightBox.setAlignment(Pos.CENTER);
		modulBox.setAlignment(Pos.CENTER_RIGHT);
		katalogBox.setAlignment(Pos.CENTER_RIGHT);
		frageButtonBox.setAlignment(Pos.CENTER_RIGHT);
		testBox.setAlignment(Pos.TOP_CENTER);
	}
	
	private void setSize() {
		modulBox.setMinWidth(topRightBox.getMinWidth());
		modulBox.setMaxWidth(topRightBox.getMinWidth());
		modulContent.setMinWidth(tabsPane.getMinWidth());
		modulContent.setMaxWidth(tabsPane.getMinWidth());
	}
	
	private void setSpacings() {
		topRightBox.setSpacing(mainPane().getMinHeight() * 0.08);
		bottomRightBox.setSpacing(mainPane().getMinWidth() * 0.02);
		modulBox.setSpacing(mainPane().getMinWidth() * 0.04);
		katalogBox.setSpacing(mainPane().getMinWidth() * 0.04);
		testBox.setSpacing(mainPane().getMinWidth() * 0.04);
	}
	
	private void setStyle() {
		logoutButton.getStyleClass().add("mainButton");
		addModulButton.getStyleClass().add("mainButton");
		addKatalogButton.getStyleClass().add("mainButton");
		addFrageButton.getStyleClass().add("mainButton");
		addTestButton.getStyleClass().add("mainButton");
		editKatalogButton.getStyleClass().add("mainButton");
		editModulButton.getStyleClass().add("mainButton");
		editTestButton.getStyleClass().add("mainButton");
		remKatalogButton.getStyleClass().add("mainButton");
		remModulButton.getStyleClass().add("mainButton");
		archivTestButton.getStyleClass().add("mainButton");
	}
	
	private void setActions() {
		modulListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent event) {
	        	testListView.getItems().add("Test");
	        }
	    });
		
		logoutButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent event) {
	        	changeTop();
	        }
	    });
	}
	
	private void setupModulListView() {
		modulListView.getItems().add("Modul");

		modulListView.setMaxSize(mainPane().getMinWidth() * 0.5, tabsPane.getMinHeight());
		modulListView.setMinSize(mainPane().getMinWidth() * 0.5, tabsPane.getMinHeight());
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

		testListView.setMaxSize(mainPane().getMinWidth() * 0.4, mainPane().getMinHeight() * 0.4);
		testListView.setMinSize(mainPane().getMinWidth() * 0.4, mainPane().getMinHeight() * 0.4);
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
	
	private void changeTop() {
		ObservableList<Node> childs = this.stack.getChildren();
		if (childs.size() > 1) {
			Node topNode = childs.get(childs.size()-1);
			topNode.setVisible(false);
			topNode.toBack();
		}
	}
}
