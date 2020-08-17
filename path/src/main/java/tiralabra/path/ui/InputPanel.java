package tiralabra.path.ui;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tiralabra.path.logic.PathService;
import tiralabra.path.logic.exceptions.InvalidScenarioException;
import tiralabra.path.logic.exceptions.MissingUserInputException;
import tiralabra.path.logic.exceptions.NoPathFoundException;

/**
 * Nodes related to submitting user input and executing the algorithm
 * @author Tatu
 */
public class InputPanel {
    
    private final PathService pathService;
    private final Stage stage;
    
    public InputPanel(Stage stage) {
        this.pathService = new PathService();
        this.stage = stage;
    }
    
    public VBox getInputPanel() {
        FlowPane userInput = new FlowPane();
        userInput.setAlignment(Pos.CENTER);
        userInput.setHgap(15);
        
        Button mapSelection = mapChooser(stage);
        HBox inputCoordinates = inputCoordinates();
        HBox algoSelection = algorithmSelection();
        
        userInput.getChildren().addAll(mapSelection, inputCoordinates, algoSelection);
        
        VBox inputPanel = new VBox();
        inputPanel.setSpacing(20);
        inputPanel.setAlignment(Pos.CENTER);
        
        Button executeProgram = programExecution();
        
        inputPanel.getChildren().addAll(userInput, executeProgram);
        
        return inputPanel;
    }
    
    private Button mapChooser(Stage stage) {
        Button mapFileSelection = new Button("Choose a map");
        mapFileSelection.setOnAction(
            (event) -> {
                FileChooser mapChooser = new FileChooser();
                mapChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Map Files", "*.map"));
                pathService.setMapFile(mapChooser.showOpenDialog(stage));
        });
        
        return mapFileSelection;
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
    
    private Button programExecution() {
        Button progExecution = new Button("Run algorithm");
        progExecution.setOnAction(
            (event) -> {
                try {
                    pathService.executeProgram();
                } catch(InvalidScenarioException | MissingUserInputException e) {
                    System.out.println(e.getMessage());
                    System.out.println("tapahtui virhe");
                } catch (NoPathFoundException ex) {
                    Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                }
        });
        return progExecution;
    }
}
