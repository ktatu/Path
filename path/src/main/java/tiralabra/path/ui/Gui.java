package tiralabra.path.ui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tiralabra.path.logic.AlgorithmService;
import tiralabra.path.logic.InputData;
import tiralabra.path.logic.exceptions.InvalidScenarioException;
import tiralabra.path.logic.exceptions.MissingUserInputException;
import tiralabra.path.logic.exceptions.NoPathFoundException;

/**
 * Graphical user interface for running algorithms and observing results
 * @author Tatu
 */
public class Gui extends Application {
    
    private InputData inputData;
    private AlgorithmService algoService;
    private InputPanel inputPanel;
    
    // global for adjusting size based on images
    private Stage stage;
    
    //private PerformanceTest perf = new PerformanceTest();
    
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        
        //stage.setScene(new Scene(perf.performanceChart()));
        
        
        this.algoService = new AlgorithmService();
        this.inputData = new InputData();
        this.inputPanel = new InputPanel(stage, inputData);
        
        VBox mainScreen = new VBox();
        
        mainScreen.setAlignment(Pos.TOP_CENTER);
        mainScreen.setPrefSize(900, 500);
        mainScreen.setSpacing(50);
        
        BorderPane resNodes = new BorderPane();
        Button executeProg = progExecution(resNodes);
        
        mainScreen.getChildren().addAll(inputPanel.getInputPanel(), executeProg, resNodes);
        
        stage.setTitle("Path");
        stage.setScene(new Scene(mainScreen));
        
        stage.show();
    }
    
    private Button progExecution(BorderPane resNodes) {
        Button executeProgram = new Button("Run algorithm");
        executeProgram.setOnAction(
            (event) -> {
                try {
                    inputData.dataVerification();
                    algoService = new AlgorithmService();
                    algoService.executeAlgorithm(inputData.getAlgoId(), inputData.getMap(), inputData.getScen(), inputData.getSaveImage());  
                    resNodes.setCenter(algoRes());
                } catch (InvalidScenarioException | MissingUserInputException | NoPathFoundException e) {
                    exceptionPopup(e.getMessage());
                } catch (IndexOutOfBoundsException | NumberFormatException e) {
                    exceptionPopup("Selected map file is incorrectly formatted");
                }
        });
        
        return executeProgram;
    }
    
    private VBox algoRes() {
        VBox algoBox = new VBox();
        algoBox.setAlignment(Pos.CENTER);
        algoBox.setSpacing(30);
        algoBox.getChildren().addAll(algoImageView(), resultInfo());
        return algoBox;
    }

    private void exceptionPopup(String message) {
        Stage popup = new Stage();
        popup.alwaysOnTopProperty();
        popup.setMinHeight(100);
        popup.setMinWidth(350);
        
        BorderPane popupPane = new BorderPane();
        
        Button ok = new Button("OK");
        ok.setOnAction((event) -> {
            popup.close();
        });
        
        Label exceptionMsg = new Label(message);
        exceptionMsg.setAlignment(Pos.CENTER);
        
        popupPane.setTop(exceptionMsg);
        popupPane.setCenter(ok);
        
        popup.setTitle("Exception occurred");
        popup.setScene(new Scene(popupPane));
        popup.show();
    }
    
    private ImageView algoImageView() {
        adjustGuiSize();
        return new ImageView(algoService.getAlgoImage());
    }
    
    private void adjustGuiSize() {
        int mapWidth = algoService.algo.gridMap.getMapHeight();
        int mapHeight = algoService.algo.gridMap.getMapWidth();
        
        if (mapWidth > 900) {
            stage.setMinWidth(mapWidth + 100);
        }
        stage.setMinHeight(mapHeight + 400);
    }
    
    private Label resultInfo() {
        return new Label(algoService.getResultInfo());
    }
    
    public static void main(String[] args) {
        launch(Gui.class);
    }
}