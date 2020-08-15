package tiralabra.path.ui;

import javafx.application.Application;
import javafx.stage.Stage;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.converter.NumberStringConverter;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import tiralabra.path.algorithms.AStar;
import tiralabra.path.algorithms.Algorithm;
import tiralabra.path.algorithms.BreadthFirstSearch;
import tiralabra.path.algorithms.Dijkstra;
import tiralabra.path.algorithms.JumpPointSearch;
import tiralabra.path.data.FileGridMapReader;
import tiralabra.path.data.FileScenarioReader;
import tiralabra.path.datastructures.PrioQueue;
import tiralabra.path.logic.Grid;
import tiralabra.path.logic.GridMap;
import tiralabra.path.logic.PathService;
import tiralabra.path.logic.Scenario;
import tiralabra.path.logic.ScenarioValidation;
import tiralabra.path.logic.exceptions.InvalidScenarioException;
import tiralabra.path.logic.exceptions.MissingUserInputException;

/**
 * Graphical user interface for running algorithms and observing results
 * @author Tatu
 */
public class ui extends Application {
    
    private final PathService pathService = new PathService();
    
    @Override
    public void start(Stage stage) throws Exception {
        
        BorderPane uiPane = new BorderPane();
        
        FlowPane userInput = new FlowPane();
        userInput.setAlignment(Pos.CENTER);
        userInput.setHgap(15);
        
        Button mapSelection = mapChooser(stage);
        HBox inputCoordinates = inputCoordinates();
        HBox algoSelection = algorithmSelection();
        
        userInput.getChildren().addAll(mapSelection, inputCoordinates, algoSelection);
        
        VBox topPanel = new VBox();
        topPanel.setSpacing(20);
        topPanel.setAlignment(Pos.CENTER);
        
        Button executeProgram = programExecution();
        
        topPanel.getChildren().addAll(userInput, executeProgram);
        
        uiPane.setTop(topPanel);
        uiPane.setPrefSize(750, 500);
        
        stage.setTitle("Path");
        stage.setScene(new Scene(uiPane));
        stage.show();
    }
    
    private Button mapChooser(Stage stage) {
        Button mapFileSelection = new Button("Choose a map");
        mapFileSelection.setOnAction(
            (event) -> {
                FileChooser mapChooser = new FileChooser();
                mapChooser.getExtensionFilters().add(new ExtensionFilter("Map Files", "*.map"));
                pathService.setMapFile(mapChooser.showOpenDialog(stage));
        });
        
        return mapFileSelection;
    }
    
    private Button programExecution() {
        Button progExecution = new Button("Run algorithm");
        progExecution.setOnAction(
            (event) -> {
                try {
                    //pathService.dijkstraTest();
                    pathService.executeProgram();
                } catch(InvalidScenarioException | MissingUserInputException e) {
                    System.out.println(e.getMessage());
                    System.out.println("tapahtui virhe");
                }
        });
        
        return progExecution;
    }
    
    private HBox inputCoordinates() {
        HBox coordPane = new HBox();
        coordPane.setSpacing(5);
        
        Label coordStartLabel = new Label("Start coordinates:");
        Label coordGoalLabel = new Label("Goal coordinates:");
        TextField startX = createCoordinateField("startX", "X");
        TextField startY = createCoordinateField("startY", "Y");
        TextField goalX = createCoordinateField("goalX", "X");
        TextField goalY = createCoordinateField("goalY", "Y");
        
        coordPane.getChildren().addAll(coordStartLabel, startX, startY, coordGoalLabel, goalX, goalY);
        
        return coordPane;
    }
    
    private TextField createCoordinateField(String id, String promptText) {
        TextField field = new TextField();
        
        field.setPromptText(promptText);
        field.setId(id);
        //field.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
        field.setPrefWidth(50);
        
        field.setOnKeyTyped((event) -> {
            pathService.setCoordinate(id, Integer.valueOf(field.getText()));
        });
        
        return field;
    }
    
    private HBox algorithmSelection() {
        HBox algoBox = new HBox();
        algoBox.setSpacing(15);
        ToggleGroup algos = new ToggleGroup();
        
        RadioButton bfs = new RadioButton("BFS");
        bfs.setToggleGroup(algos);
        bfs.setId("bfs");
        bfs.setSelected(true);
        pathService.setAlgorithmId("bfs");
        
        RadioButton dijkstra = new RadioButton("Dijkstra");
        dijkstra.setId("dijkstra");
        dijkstra.setToggleGroup(algos);
        
        RadioButton aStar = new RadioButton("A*");
        aStar.setId("aStar");
        aStar.setToggleGroup(algos);
        
        algoBox.getChildren().addAll(bfs, dijkstra, aStar);
        
        algos.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle prevToggle, Toggle newToggle) {
                RadioButton testi = (RadioButton)newToggle.getToggleGroup().getSelectedToggle();
                pathService.setAlgorithmId(testi.getId());
             } 
        });
        
        return algoBox;
    }
    
    public static void main(String[] args) {
        launch(ui.class);
    }
}

        

/*
21.897959183
*/