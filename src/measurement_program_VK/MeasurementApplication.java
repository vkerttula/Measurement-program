package measurement_program_VK;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

/*
 * Application for handling measurements. Features:
 * Create new measurement and add new values to it
 * Show selected measurement as graph (ScatterChart)
 * Remove selected measurement
 * Read measurements to file
 * Save measurements to file
 */


public class MeasurementApplication extends Application {
	
	private final TextField idField = new TextField();
	private final TextField titleField = new TextField();
	private final TextField xAxisField = new TextField();
	private final TextField yAxisField = new TextField();
	private final TextField yValueField = new TextField();
	private final TextField xValueField = new TextField();
	private final TextArea textArea = new TextArea();
	private final ComboBox<Integer> measurementidCmb = new ComboBox<>();
	private final FileChooser fileChooser = new FileChooser();
	
	private ArrayList<Measurement> measurements = new ArrayList<>();
	private Measurement currentMeasurement;
	
	final NumberAxis xAxis = new NumberAxis(0, 140, 10); //Start value, max value and space between
    final NumberAxis yAxis = new NumberAxis(0, 140, 5);  //--.--
	public ScatterChart<Number,Number> sc = new
	        ScatterChart<Number,Number>(xAxis,yAxis);

	@Override
	public void start(Stage primaryStage)  {
		
	    sc.setPadding(new Insets(25, 25, 25, 25));
		
		BorderPane mainpanel = new BorderPane();
		mainpanel.setRight(sc);

		GridPane grid1 = new GridPane();
		grid1.setAlignment(Pos.CENTER);
		grid1.setHgap(10); //Horizontal space between columns
		grid1.setVgap(10); //Vertical space between rows
		grid1.setPadding(new Insets(25, 25, 25, 25));
		grid1.setStyle("-fx-font: 13 poppins");
		
		GridPane grid2 = new GridPane();
		grid2.setAlignment(Pos.CENTER);
		grid2.setHgap(5); //Horizontal space between columns
		grid2.setVgap(5); //Vertical space between rows
		grid2.setPadding(new Insets(5, 5, 5, 5));
		Text instrFooter = new Text("© Kerttula");
		grid2.add(instrFooter, 0, 0, 2, 1);
		
		Text instrText = new Text("Enter a new measurement:\n\t"
				+ "- ID\n\t"
				+ "- Title\n\t"
				+ "- Quantity and unit of the x-axis and y-axis\n");
		
		grid1.add(instrText, 0, 0, 2, 1);

		Label label1 = new Label("Measurement ID:");
		grid1.add(label1, 0, 1);
		grid1.add(idField, 1, 1);

		Label label2 = new Label("Title:");
		grid1.add(label2, 0, 2);
		grid1.add(titleField, 1, 2);
		
		Label label3 = new Label("X-axis quantity and unit:");
		grid1.add(label3, 0, 3);
		grid1.add(xAxisField, 1, 3);
		
		Label label4 = new Label("Y-axis quantity and unit:");
		grid1.add(label4, 0, 4);
		grid1.add(yAxisField, 1, 4);

		Button createBtn = new Button("Create measurement");
		grid1.add(createBtn, 1, 5);
		
		//Controls to add values
		Text valuesText = new Text("Enter a new values to current measurement:\n");
		grid1.add(valuesText, 0, 7, 2, 1);
		
		Label label5 = new Label("X-axis:");
		grid1.add(label5, 0, 8);
		grid1.add(xValueField, 1, 8);

		Label label6 = new Label("Y-axis:");
		grid1.add(label6, 0, 9);
		grid1.add(yValueField, 1, 9);

		Button addBtn = new Button("Add");
		grid1.add(addBtn, 1, 10);
		
		//Controls to choose and remove measurement
		Text instrText3 = new Text("Choose measurement to view it."
								+ "\nYou can also remove selected measurement.");
		grid1.add(instrText3, 0, 12, 2, 1);
		Label label7 = new Label("Show:");
		grid1.add(label7, 0, 13);
		grid1.add(measurementidCmb, 1, 13);
				
		Button removeBtn = new Button("Remove");
		grid1.add(removeBtn, 1, 14);
		
		mainpanel.setLeft(grid1);
		mainpanel.setBottom(grid2);
		
		//Create menu-bar and File menu
		MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        menuBar.getMenus().add(menuFile);

        MenuItem open = new MenuItem("Open");
        MenuItem saveAs = new MenuItem("Save As");
        MenuItem exit = new MenuItem("Exit");
        menuFile.getItems().addAll(open, saveAs, new SeparatorMenuItem(),  exit);
		mainpanel.setTop(menuBar);
	        
			
		Scene scene = new Scene(mainpanel, 900, 700);
		
		primaryStage.getIcons().add(new Image("https://cdn3.iconfinder.com/data/icons/biochemistry-20/64/nstrument-science-laboratory-container-measurement-512.png"));
		primaryStage.setTitle("Measurement Visualization");		
		primaryStage.setScene(scene);
		primaryStage.show();
		
		//Event handlers starts here 
		
		createBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				sc.getData().clear();
				createNewMeasurement(e);
				xAxis.setLabel(currentMeasurement.getxLabel());                
			    yAxis.setLabel(currentMeasurement.getyLabel());
			    sc.setTitle(currentMeasurement.getGraphTitle());
			    clearControls();
			}
		});

		measurementidCmb.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				selectMeasurement(e);
			}
		});
		
		addBtn.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	if (Integer.parseInt(xValueField.getText()) < 140 && Integer.parseInt(yValueField.getText()) < 140)
		    		drawGraph(e);
		    	else {
		    		Alert alert = new Alert(AlertType.ERROR, "X and Y values max 140!");
	            	alert.showAndWait();
		    	}	
		    }
		});
		
		removeBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				removeMeasurement(e);
			}
		});
		
		saveAs.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				 File file = fileChooser.showSaveDialog(primaryStage);
                 if (file != null) {
                    saveOrdersToFile(file);
                 }
			}
		});
		
		open.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				 File file = fileChooser.showOpenDialog(primaryStage);
                 if (file != null) {
                    readOrdersFromFile(file);
                 }
			}
		});
		
		exit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Platform.exit();
			}
		});

	}
	
	//Creates new measurement and adds it to collection
	private void createNewMeasurement(ActionEvent e) {
		int measurementID = Integer.parseInt(idField.getText());
		String graphTitle = titleField.getText();
		String xLabel = xAxisField.getText();
		String yLabel = yAxisField.getText();
		currentMeasurement = new Measurement(measurementID, graphTitle, xLabel, yLabel);
		measurements.add(currentMeasurement);
		measurementidCmb.getItems().add(measurementID);
		measurementidCmb.setPromptText("" + currentMeasurement.getMeasurementID()); //Display current measurement ID at combo box
	}
	
	//Draws new graph when adding more values
	private void drawGraph(ActionEvent e) {
		currentMeasurement.insertValues(Integer.parseInt(xValueField.getText()), Integer.parseInt(yValueField.getText()));
        if (sc.getData() == null) 
            sc.setData(
                FXCollections.<XYChart.Series<Number,
                    Number>>observableArrayList());
        ScatterChart.Series<Number, Number> series = 
            new ScatterChart.Series<Number, Number>();
        series.setName("Value "+(sc.getData().size()+1));
        int count = currentMeasurement.getCount()-1;
        series.getData().add(
            new ScatterChart.Data<Number, 
                Number>(currentMeasurement.getxValue(count), currentMeasurement.getyValue(count)));
        sc.getData().add(series);
        //System.out.println(currentMeasurement.getCount()); //For testing
	}
	
	//Draws new graph when selected from drop-down
	private void showGraph(ActionEvent e, int valuePoint) {
        if (sc.getData() == null) 
            sc.setData(
                FXCollections.<XYChart.Series<Number,
                    Number>>observableArrayList());
        ScatterChart.Series<Number, Number> series = 
            new ScatterChart.Series<Number, Number>();
        series.setName("Value "+(sc.getData().size()+1));
        series.getData().add(
            new ScatterChart.Data<Number, 
                Number>(currentMeasurement.getxValue(valuePoint), currentMeasurement.getyValue(valuePoint)));
        sc.getData().add(series);
        //System.out.println(currentMeasurement.getCount()); //For testing
	}
	
	//Shows selected measurement
	private void selectMeasurement(ActionEvent e) {
		if (measurementidCmb.getValue() != null) {
			int index = measurementidCmb.getSelectionModel().getSelectedIndex();
			currentMeasurement = measurements.get(index);
			xAxis.setLabel(currentMeasurement.getxLabel());                
		    yAxis.setLabel(currentMeasurement.getyLabel());
		    sc.setTitle(currentMeasurement.getGraphTitle());
		    sc.getData().clear();
		    int numbersMax = currentMeasurement.getCount();
		    for(int i=0; i < numbersMax; i++) {
		    	showGraph(e, i);
		    }
		}
	}
	
	//Removes selected measurement from collection
	private void removeMeasurement(ActionEvent e) {
		int index = measurementidCmb.getSelectionModel().getSelectedIndex();
		currentMeasurement = measurements.get(index);
		measurements.remove(index);
		measurementidCmb.getItems().remove(index);
		if (measurementidCmb.getValue() != null) {
			currentMeasurement = measurements.get(index-1);
		}
	}	
	
	//Writes measurements to file 
	private void saveOrdersToFile(File file) {
		System.out.println(file.getAbsolutePath());
		try (ObjectOutputStream file_out
	             = new ObjectOutputStream(new FileOutputStream(file))){
	            file_out.writeObject(measurements);
	        }
	        catch(Exception e) {
	            Alert alert = new Alert(AlertType.ERROR, "Problems at saving file.");
	            alert.showAndWait();
	        }
	}
	
	//Reads measurements from file
	@SuppressWarnings("unchecked")
	private void readOrdersFromFile(File file) {
		try (ObjectInputStream file_in 
	            = new ObjectInputStream(new FileInputStream(file))){
	            measurements = (ArrayList<Measurement>)file_in.readObject();
	            measurementidCmb.getItems().clear();
	            for(Measurement o: measurements) {
	            	measurementidCmb.getItems().add(o.getMeasurementID()); 
	            }
	            clearControls();
	            Alert information = new Alert(AlertType.INFORMATION, "File loaded successfully!");
	            information.showAndWait();
	        }
	        catch(Exception e) {
	        	Alert alert = new Alert(AlertType.ERROR, "Problems at opening file.");
	            alert.showAndWait();
	        }
	}
	
	//Clears GUI controls
	private void clearControls() {
		idField.setText(null);
		titleField.setText(null);
		xAxisField.setText(null);
		yAxisField.setText(null);
		xValueField.setText(null);
		yValueField.setText(null);
		textArea.setText(null);
		measurementidCmb.getSelectionModel().clearSelection();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
