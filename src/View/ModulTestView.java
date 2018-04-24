package View;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;


public class ModulTestView extends MainWindow {
	private StackPane stack;
	private BorderPane leftPane;
	private BorderPane rightPane;
	private HBox buttonBox;

	private Button backButton;
	private Button scoreButton;

	private ListView<String> modulListView;

	ObservableList<PieChart.Data> pieChartData =
			FXCollections.observableArrayList(
					new PieChart.Data("Richtig", 12),
					new PieChart.Data("Falsch", 7));
	private PieChart resultChart;

	private ListView <String>testListView;

	public ModulTestView(StackPane stack, ListView<String> modulLV) {
		this.stack = stack;
		this.modulListView = modulLV;
		setTitle("Teste des Moduls " + modulLV.getSelectionModel().getSelectedItem());
		createTextView();
		this.stack.getChildren().add(mainPane());
	}

	private void createTextView() {
		createComponents();
		addComponents();
		configComponents();
		setupListView();
	}

	private void createComponents() {
		leftPane = new BorderPane();
		rightPane = new BorderPane();
		buttonBox = new HBox();
		testListView = new ListView<String>();
		createButtons();
		createAndConfigResultChart();
	}

	private void createButtons() {
		backButton = new Button("Zurück");
		scoreButton = new Button("Ergebnis");
	}

	private void createAndConfigResultChart() {
		resultChart = new PieChart(pieChartData);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.YYYY");
		resultChart.setTitle("MODUL TEST\nDatum: "+sdf.format(date));

		// for percentage
		//double firstValue = pieChartData.get(0).getPieValue();
		//final double sum = pieChartData.get(1).getPieValue() + firstValue;

		// install a tooltip on every node
		resultChart.getData().stream().forEach(data -> {
			Tooltip tooltip = new Tooltip();
			tooltip.setText((int)data.getPieValue() + " Aufgaben " + data.getName());
			Tooltip.install(data.getNode(), tooltip);
			data.pieValueProperty().addListener((observable, oldValue, newValue) -> 
			tooltip.setText(newValue + "%"));
		});

		rightPane.setCenter(resultChart);
	}

	private void addComponents() {
		buttonBox.getChildren().addAll(backButton, scoreButton);
		mainPane().getChildren().addAll(buttonBox, leftPane, rightPane);
	}

	private void configComponents() {
		setAnchors();
		setSizes();
		buttonBox.setSpacing(mainPane().getMinWidth() * 0.845);
		setStyles();
		setActions();
	}

	private void setAnchors() {
		AnchorPane.setLeftAnchor(leftPane, 0.0);
		AnchorPane.setTopAnchor(leftPane, mainPane().getMinHeight() * 0.06);
		AnchorPane.setTopAnchor(rightPane, mainPane().getMinHeight() * 0.06);
		AnchorPane.setRightAnchor(rightPane, 0.0);
		AnchorPane.setBottomAnchor(buttonBox, 0.0);
		AnchorPane.setLeftAnchor(buttonBox, 0.30);
		AnchorPane.setRightAnchor(buttonBox, 0.30);
	}

	private void setSizes() {
		rightPane.setMinSize(mainPane().getMinWidth() * 0.5, mainPane().getMinHeight() * 0.85);
		rightPane.setMaxSize(mainPane().getMinWidth() * 0.5, mainPane().getMinHeight() * 0.85);
		leftPane.setMinHeight(mainPane().getMinHeight() * 0.85);
		leftPane.setMinWidth(mainPane().getMinWidth() * 0.5);
		buttonBox.setMaxWidth(mainPane().getMinWidth());
	}

	private void setStyles() {
		backButton.getStyleClass().add("mainButton");
		scoreButton.getStyleClass().add("mainButton");
	}

	private void setupListView() {
		leftPane.setCenter(testListView);
		testListView.getItems().add("TestThema");

		// set first as selected
		if(testListView.getItems().size() >= 0)
			testListView.getSelectionModel().select(testListView.getItems().get(0));
		testListView.setMaxSize(leftPane.getMinWidth(), leftPane.getMinHeight());
		testListView.setMinSize(leftPane.getMinWidth(), leftPane.getMinHeight());
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

	private void setActions() {
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				modulListView.getSelectionModel().clearSelection();
				changeTop();
			}
		});
		
		scoreButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				// html ändern udn öffnen
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
