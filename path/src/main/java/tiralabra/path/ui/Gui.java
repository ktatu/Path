package tiralabra.path.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Graphical user interface for running algorithms and observing results
 * @author Tatu
 */
public class Gui extends Application {
    
    private InputPanel inputPanel;
    
    @Override
    public void start(Stage stage) throws Exception {
        
        BorderPane uiPane = new BorderPane();
        this.inputPanel = new InputPanel(stage);
        
        uiPane.setTop(inputPanel.getInputPanel());
        uiPane.setPrefSize(750, 500);
        
        stage.setTitle("Path");
        stage.setScene(new Scene(uiPane));
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(Gui.class);
    }
}