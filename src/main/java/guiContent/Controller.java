package guiContent;

import graphStructure.Graph;
import guiUtilities.Export;
import guiUtilities.Import;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    private  Button  clearBtn;

    @FXML
    private TextField importTF;

    @FXML
    private TextField exportTF;

    @FXML
    private TextField firstVTF;

    @FXML
    private TextField secondVTF;

    @FXML
    private Label edgeWarnLabel;

    Graph graph = new Graph();

    public Controller(){ }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        edgeWarnLabel.setVisible(false);
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
            exportJson.exportMap(exportBtn, graph.graphToMap());
            exportTF.setText(exportJson.getPath());
        }
        if (actionEvent.getSource() == importBtn)
        {
            Import importJson = new Import();
            HashMap<String, ArrayList<HashMap<String, String>>> m = importJson.importJson(importBtn);
            if(m != null)
            {
                graph.graphFromMap(m);
                drawGraph(graphPane, graph.getVertices(), graph.getEdges());
                importTF.setText(importJson.getPath());
            }
        }
        if (actionEvent.getSource() == addVertexBtn)
        {
            drawVertex(graphPane, graph.addVertex());
        }
        if (actionEvent.getSource() == addEdgeBtn)
        {
            if(firstVTF.getText().equals("") || secondVTF.getText().equals(""))
            {
                edgeWarnLabel.setVisible(false);

                if(firstVTF.getText().equals(""))
                {
                    firstVTF.setText("Fill this field");
                }

                if(secondVTF.getText().equals(""))
                {
                    secondVTF.setText("Fill this field");
                }
            }
            else if(!graph.findVertexBool(firstVTF.getText()) || !graph.findVertexBool(secondVTF.getText()))
            {
                edgeWarnLabel.setVisible(false);

                if(!graph.findVertexBool(firstVTF.getText()))
                {
                    firstVTF.setText("Not exist!");
                }
                if(!graph.findVertexBool(secondVTF.getText()))
                {
                    secondVTF.setText("Not exist!");
                }
            }
            else if(graph.findEdgeBool(firstVTF.getText(), secondVTF.getText()))
            {
                edgeWarnLabel.setVisible(true);
            }
            else
            {
                edgeWarnLabel.setVisible(false);
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
        if (actionEvent.getSource() == clearBtn)
        {
            graph.clearGraph();
            graphPane.getChildren().clear();
        }
        if (actionEvent.getSource() == randBtn)
        {
            graph.clearGraph();
            graphPane.getChildren().clear();
            graph.randGraph();
            drawGraph(graphPane, graph.getVertices(), graph.getEdges());
        }
    }
}
