package tiralabra.path.ui;

import java.io.File;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.PriorityQueue;
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
import javafx.stage.DirectoryChooser;
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
        
        Button executeProgram = new Button("Run algorithm");
        
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
                    // executeProgram();, ehkä erillinen metodi kutsulle jossa otetaan kiinni poikkeukset
                } catch(Exception e) {
                    // popup window joka kertoo mikä input puuttuu
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
        field.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
        field.setPrefWidth(50);
        
        field.setOnMouseReleased((event) -> {
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
                pathService.setAlgorithm(testi.getId());
             } 
        });
        
        return algoBox;
    }
    
    public static void main(String[] args) {
        
        //launch(ui.class);

        
        PrioQueue testQueue = new PrioQueue(10);

        testQueue.add(new Grid(0, 0, 3, 0));
        testQueue.add(new Grid(5, 5, 1, 1));
        testQueue.add(new Grid(0, 0, 100, 0));
        testQueue.add(new Grid(0, 0, 5, 5));
        testQueue.add(new Grid(0, 0, 5, 0));
        
        
        
        /*
        char[][] testMatrix = {{'.','@','@','.',},
                               {'@','@','.','.'},
                               {'.','.','.','.'},
                               {'.','.','.','@'}};
        
        char[][] testMatrix2 = {{'.','.','.'},
                                {'.','.','.'},
                                {'.','.','.'},
                               };
        
        
        FileGridMapReader mapReader = new FileGridMapReader();
        
        
        GridMap test1Map = new GridMap(testMatrix);
        GridMap test2Map = new GridMap(testMatrix2);
        
        GridMap map = mapReader.getGridMap("src/battleground.map");
        */
        
        // sama kuin ylläoleva mutta eri mapissa
        /*
        Algorithm bfs = new BreadthFirstSearch(map, new Scenario(86, 59, 426, 414));
        bfs.runAlgorithm();
        System.out.println("runtime bfs " + bfs.getRunTime());
        
        Algorithm dijkstra2 = new Dijkstra(map, new Scenario(86, 59, 426, 414));
        dijkstra2.runAlgorithm();
        System.out.println("runtime dijkstra " + dijkstra2.getRunTime());
        System.out.println("dijkstra length " + dijkstra2.getPathLength());
        
        Algorithm aStar = new AStar(map, new Scenario(86, 59, 426, 414));
        aStar.runAlgorithm();
        System.out.println("runtime aStar " + aStar.getRunTime());
        System.out.println("aStar length " + aStar.getPathLength());
        
        Algorithm jps2 = new JumpPointSearch(map, new Scenario(86, 59, 426, 414));
        jps2.runAlgorithm();
        System.out.println("runtime jps " + jps2.getRunTime());
        System.out.println("jump points: " + jps2.getJumpPoints());
        System.out.println("jps length: " + jps2.getPathLength());
        */
        
        /*
        Algorithm aStar = new AStar(map, new Scenario(86, 59, 426, 414));
        aStar.runAlgorithm();
        System.out.println("runtime aStar " + aStar.getRunTime());
        //aStar.printPath();
        ArrayDeque<Integer> asd = new ArrayDeque<>();
        int testi = (int) (6.0/7.0*9);
        System.out.println(testi);
        
        int[] testi2 = new int[3];
        System.out.println(testi2[2]);
        
        ArrayDeque<Integer> testi3 = new ArrayDeque<>();
        System.out.println(testi3.pollFirst());
        
        
        char[][] jpsTest = {{'.','.'},
                            {'.','.'},
                            {'@','.'},
                            {'.','.'},
                            {'.','.'}};
       
        GridMap jpsMap = new GridMap(jpsTest);
        Algorithm jps = new JumpPointSearch(jpsMap, new Scenario(4,0,0,0));
        */
        
        /*
0	battleground.map	512	512	146	393	148	394	2.41421356 OK 2.4142137
0	battleground.map	512	512	312	252	312	250	2.00000000 OK 2.0
0	battleground.map	512	512	221	102	221	102	0.00000000 EI PÄÄSE LÄPI VALIDATIONISTA
0	battleground.map	512	512	352	279	353	282	3.41421356 OK 3.4142137
0	battleground.map	512	512	298	65	300	63	2.82842712 OK 2.828427
0	battleground.map	512	512	256	181	257	184	3.41421356 OK 3.4142137
0	battleground.map	512	512	373	366	372	365	1.41421356 OK 1.4142135
0	battleground.map	512	512	104	126	106	128	2.82842712 OK 2.828427
0	battleground.map	512	512	379	448	381	448	2.00000000 OK 2.0
        */
        /*
        Algorithm jps = new JumpPointSearch(map, new Scenario(448, 379, 448, 381));
        jps.runAlgorithm();
        System.out.println(jps.getPathLength());
        */
    }
}

        