package guiContent;

import graphSolver.Solver;
import graphStructure.Graph;
import guiUtilities.Export;
import guiUtilities.Import;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
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
    private Button solveSBtn;

    @FXML
    private Button solveHBtn;

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

    private Graph graph = new Graph();

    public Controller(){ }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        edgeWarnLabel.setVisible(false);
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
            clearGraph();

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
                        0);
                this.graph.getLastEdge().getFirstVertex().getDot().toFront();
                this.graph.getLastEdge().getSecondVertex().getDot().toFront();
            }
        }
        if (actionEvent.getSource() == clearBtn)
        {
            clearGraph();
        }
        if (actionEvent.getSource() == randBtn)
        {
            clearGraph();

            graph.randGraph();

            drawGraph(graphPane, graph.getVertices(), graph.getEdges());
        }
        if (actionEvent.getSource() == solveSBtn)
        {
            graphPane.getChildren().clear();
            Solver solver = new Solver();
            solver.solve(graph, false);
            drawGraph(graphPane, graph.getVertices(), graph.getEdges());
        }
        if (actionEvent.getSource() == solveHBtn)
        {
            graphPane.getChildren().clear();
            Solver solver = new Solver();
            solver.solve(graph, true);
            drawGraph(graphPane, graph.getVertices(), graph.getEdges());
        }
    }

    public void clearGraph()
    {
        graph.clearGraph();
        graphPane.getChildren().clear();
    }
}
