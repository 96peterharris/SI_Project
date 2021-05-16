package guiContent;

import graphStructure.Graph;
import guiUtilities.Export;
import guiUtilities.Import;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import static guiUtilities.DrawingFunctions.*;


public class Controller implements Initializable
{
    @FXML
    private Pane graphPane;

    @FXML
    private Button importBtn;

    @FXML
    private Button exportBtn;

    @FXML
    private Button randBtn;

    @FXML
    private Button addVertexBtn;

    @FXML
    private Button solveBtn;

    @FXML
    private  Button  addEdgeBtn;

    @FXML
    private TextField importTF;

    @FXML
    private TextField exportTF;

    @FXML
    private TextField firstVTF;

    @FXML
    private TextField secondVTF;

    Graph graph = new Graph();

    public Controller(){ }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
//        StackPane dotA = getDot("green", "A");
//        StackPane dotB = getDot("red", "B");
//        StackPane dotC = getDot("yellow", "C");
//        StackPane dotD = getDot("pink", "D");
//        StackPane dotE = getDot("silver", "E");
//
//        buildSingleDirectionalLine(dotA, dotB, graphPane, false, false, false); // A <--> B
//        buildSingleDirectionalLine(dotB, dotC, graphPane, false, false, false); // B <--> C
//        buildSingleDirectionalLine(dotC, dotD, graphPane, false, false, false); // C --> D
//
//        // D <===> E
//        buildBiDirectionalLine(true, dotD, dotE, graphPane);
//        buildBiDirectionalLine(false, dotD, dotE, graphPane);
//
//        graphPane.getChildren().addAll(dotA, dotB, dotC, dotD, dotE);
    }

    public void handleClicks(ActionEvent actionEvent) throws IOException
    {
        if (actionEvent.getSource() == exportBtn)
        {
            Export exportJson = new Export();
            exportJson.exportMap(exportBtn, new HashMap<>()); //ZASLEPKOWE DANE DO POPRAWY POTEM
            exportTF.setText(exportJson.getPath());
        }
        if (actionEvent.getSource() == importBtn)
        {
            Import importJson = new Import();
            importJson.importJson(importBtn); //ZASLEPKOWE DANE DO POPRAWY POTEM
            importTF.setText(importJson.getPath());
        }
        if (actionEvent.getSource() == addVertexBtn)
        {
            graphPane.getChildren().add(graph.addVertex());
        }
        if (actionEvent.getSource() == addEdgeBtn)
        {
            if(firstVTF.getText() == "")
            {
                firstVTF.setText("Fill this field");
            }
            else if(secondVTF.getText() == "")
            {
                secondVTF.setText("Fill this field");
            }
            else if(!graph.findVertexBool(firstVTF.getText()))
            {
                firstVTF.setText("Not exist!");
            }
            else if(!graph.findVertexBool(secondVTF.getText()))
            {
                secondVTF.setText("Not exist!");
            }
            else if(graph.findEdgeBool(firstVTF.getText(), secondVTF.getText()))
            {

            }
            else
            {
                this.graph.addEdge(firstVTF.getText(), secondVTF.getText());
                buildSingleDirectionalLine(this.graph.getLastEdge().getFirstVertex().getDot(),
                        this.graph.getLastEdge().getSecondVertex().getDot(),
                        graphPane,
                        this.graph.getLastEdge().getColorLabel(),
                        false,
                        false,
                        false);
                this.graph.getLastEdge().getFirstVertex().getDot().toFront();
                this.graph.getLastEdge().getSecondVertex().getDot().toFront();
            }
        }
    }
}
