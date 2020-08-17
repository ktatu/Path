package tiralabra.path.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tiralabra.path.logic.AlgorithmService;
import tiralabra.path.logic.InputData;
import tiralabra.path.logic.exceptions.InvalidScenarioException;
import tiralabra.path.logic.exceptions.MissingUserInputException;
import tiralabra.path.logic.exceptions.NoPathFoundException;

/**
 * Nodes related to executing an algorithm and displaying results
 * @author Tatu
 */
public class AlgorithmPanel {
    
    private AlgorithmService algoService;
    private final InputData inputData;
    
    public AlgorithmPanel(InputData data) {
        this.inputData = data;
    }
    
    /*
    public VBox getResultPanel() {
        VBox resultPanel = new VBox();
        resultPanel.setAlignment(Pos.CENTER);
        resultPanel.setSpacing(30);
        
        Button executeProgram = new Button("Run algorithm");
        executeProgram.setOnAction(
            (event) -> {
                try {
                    inputData.dataVerification();
                    algoService = new AlgorithmService();
                    algoService.executeAlgorithm(inputData.getAlgoId(), inputData.getMap(), inputData.getScen(), inputData.getSaveImage());
                    
                    resultPanel.getChildren().addAll(executeProgram, algoImageView(), resultInfo());
                } catch (InvalidScenarioException | MissingUserInputException | NoPathFoundException e) {
                    System.out.println(e);
                    exceptionPopup(e.getMessage());
                } catch (IndexOutOfBoundsException | NumberFormatException e) {
                    exceptionPopup("Selected map file is incorrectly formatted");
                }
        });
        
        ImageView imgView = algoImageView();
        Label resInfo = resultInfo();
        
        resultPanel.getChildren().addAll(executeProgram);
        
        return resultPanel;
    }
    */
    
    public BorderPane getAlgorithmPanel() {
        BorderPane algoPanel = new BorderPane();
        
        Button executeProgram = new Button("Run algorithm");
        executeProgram.setAlignment(Pos.CENTER);
        executeProgram.setOnAction(
            (event) -> {
                try {
                    inputData.dataVerification();
                    algoService = new AlgorithmService();
                    algoService.executeAlgorithm(inputData.getAlgoId(), inputData.getMap(), inputData.getScen(), inputData.getSaveImage());
                    
                    algoPanel.setCenter(algoBox());
                } catch (InvalidScenarioException | MissingUserInputException | NoPathFoundException e) {
                    System.out.println(e);
                    exceptionPopup(e.getMessage());
                } catch (IndexOutOfBoundsException | NumberFormatException e) {
                    exceptionPopup("Selected map file is incorrectly formatted");
                }
        });
        algoPanel.setTop(executeProgram);
        
        return algoPanel;
    }
    
    private VBox algoBox() {
        VBox algoBox = new VBox();
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
        return new ImageView(algoService.getAlgoImage());
    }
    
    private Label resultInfo() {
        return new Label(algoService.getResultInfo());
    }
}
