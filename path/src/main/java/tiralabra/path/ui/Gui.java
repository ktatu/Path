package tiralabra.path.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tiralabra.path.logic.InputData;

/**
 * Graphical user interface for running algorithms and observing results
 * @author Tatu
 */
public class Gui extends Application {
    
    private InputData dataCollector;
    private InputPanel inputPanel;
    private AlgorithmPanel resPanel;
    
    @Override
    public void start(Stage stage) {
        
        this.dataCollector = new InputData();
        this.inputPanel = new InputPanel(stage, dataCollector);
        this.resPanel = new AlgorithmPanel(dataCollector);
        
        BorderPane uiPane = new BorderPane();
        
        uiPane.setTop(inputPanel.getInputPanel());
        uiPane.setCenter(resPanel.getAlgorithmPanel());
        
        uiPane.setPrefSize(850, 500);
        
        stage.setTitle("Path");
        stage.setScene(new Scene(uiPane));
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(Gui.class);
    }
}