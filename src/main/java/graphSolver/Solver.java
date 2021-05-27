package graphSolver;

import graphStructure.Edge;
import graphStructure.Graph;
import graphStructure.Vertex;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.ParallelPortfolio;
import org.chocosolver.solver.variables.IntVar;

import java.util.ArrayList;
import java.util.List;

public class Solver
{
    private Graph graph;

    public Solver(){}

    public Graph getGraph()
    {
        return graph;
    }

    public void setGraph(Graph graph)
    {
        this.graph = graph;
    }

    public void solve(Graph g, boolean typOfSolve)
    {
        Model model = new Model("Labeling graph with sigma labeling method.");

        for(Vertex v : g.getVertices())
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

        /** True - hard solving, False - sof solving*/
        if(typOfSolve)
        {
            model.allDifferent(edge_var_array).post();
        }

//        model.allDifferent(edge_var_array).post();

        for(Vertex v : g.getVertices())
        {
            List<IntVar> l = new ArrayList<>();

            for(Edge e : g.getConnectedEdges(v.getLabel()))
            {
                l.add(e.getSolverVar());
            }

            IntVar[] a = new IntVar[l.size()];
            model.allDifferent(l.toArray(a)).post();
        }

        for(Edge e : g.getEdges())
        {
            model.arithm(e.getFirstVertex().getSolverVar().dist(e.getSecondVertex().getSolverVar()).intVar(), "=", e.getSolverVar()).post();
        }

        model.getSolver().solve();

        // Print solution
        for (Vertex v : g.getVertices())
        {
            System.out.println("Vertex: " + v.getSolverVar());
        }

        System.out.print("\n");

        for (Edge e : g.getEdges())
        {
            System.out.println("Edge: " + e.getSolverVar());
        }
    }
}