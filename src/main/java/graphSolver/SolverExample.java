package graphSolver;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import graphStructure.Edge;
import graphStructure.Graph;
import graphStructure.Vertex;
import javafx.scene.layout.StackPane;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class SolverExample {

    public static void main(String[] args) {
        // The model is the main component of Choco Solver
        Model model = new Model("Graph Antymagic Labeling with Choco Solver");

        // Creating new graph
        Graph g = new Graph();

        LinkedHashSet<Vertex> vertices_list = new LinkedHashSet<>();

        for (int i = 0; i < 4; i++)
        {
            vertices_list.add(new Vertex("v", i, new StackPane()));
        }

        g.setVertices(vertices_list);

        g.addEdge("v0", "v1", 0);
        g.addEdge("v1", "v2", 1);
        g.addEdge("v2", "v3", 2);
        g.addEdge("v3", "v0", 3);
        g.addEdge("v0", "v2", 4);
        g.addEdge("v3", "v1", 5);

        //Solving
        // Get all edges which are connected with selected vertex and parse them into IntVar[]
        for (Vertex v : g.getVertices())
        {
            v.setSolverVar(model.intVar(v.getLabel(), 0, g.getEdges().size() * 2));
        }

       // g.getEdges().forEach(edge -> {edge.setSolverVar(model.intVar(String.valueOf(edge.getId()), 1, g.getEdges().size()));});

        for(Edge e : g.getEdges())
        {
            e.setSolverVar(model.intVar(String.valueOf(e.getId()), 1, g.getEdges().size()));
        }

        IntVar[] vertex_var_array = new IntVar[g.getVertices().size()];

        int i = 0;
        for(Vertex v : g.getVertices())
        {
            vertex_var_array[i++] = v.getSolverVar();
        }

        model.allDifferent(vertex_var_array).post();

        IntVar[] edge_var_array = new IntVar[g.getEdges().size()];

        i = 0;
        for(Edge e : g.getEdges())
        {
            edge_var_array[i++] = e.getSolverVar();
        }

//        model.allDifferent(edge_var_array).post();

        for(Edge e : g.getEdges())
        {
            //model.arithm(e.getFirstVertex().getSolverVar(), ">", e.getSecondVertex().getSolverVar()).post();
            //model.arithm(e.getFirstVertex().getSolverVar(), "-", e.getSecondVertex().getSolverVar(), ">", 0).post();
//            model.arithm(e.getFirstVertex().getSolverVar(), "-", e.getSecondVertex().getSolverVar(), "=", e.getSolverVar()).post();
//            e.getFirstVertex().getSolverVar().dist(e.getSecondVertex().getSolverVar());
            model.arithm(e.getFirstVertex().getSolverVar().dist(e.getSecondVertex().getSolverVar()).intVar(), "=", e.getSolverVar()).post();
        }

        model.getSolver().solve();


//        // Get IntVar[] of vertices
//        IntVar[] vertex_var_array = new IntVar[g.getVertices().size()];
//        for (int i = 0; i < vertex_var_array.length; i++) {
//            vertex_var_array[i] = g.getVertices().get(i).getSolverVar();
//        }
//
//        model.allDifferent(vertex_var_array).post(); // Makes vertex sum unique
//
//        model.getSolver().solve(); // Repeating this operation gives next solutions (if they exist)

        // Print solution
        for (Vertex v : g.getVertices()) {
            System.out.println("Vertex: " + v.getSolverVar());
        }

        System.out.print("\n");

        for (Edge e : g.getEdges()) {
            System.out.println("Edge: " + e.getSolverVar());
        }

    }
}