package graphStructure;

import javafx.scene.layout.StackPane;

import java.util.*;

import static guiUtilities.DrawingFunctions.*;

public class Graph
{
    private LinkedHashSet<Vertex> vertices;
    private LinkedHashSet<Edge> edges;

    public Graph()
    {
        this.vertices = new LinkedHashSet<>();
        this.edges = new LinkedHashSet<>();
    }

    public HashSet<Vertex> getVertices()
    {
        return vertices;
    }

    public void setVertices(LinkedHashSet<Vertex> vertices)
    {
        this.vertices = vertices;
    }

    public LinkedHashSet<Edge> getEdges()
    {
        return edges;
    }

    public void setEdges(LinkedHashSet<Edge> edges)
    {
        this.edges = edges;
    }

    public void addEdge(String firstLabel, String secondLabel)
    {
       if(findVertexBool(firstLabel) && findVertexBool(secondLabel) && !findEdgeBool(firstLabel, secondLabel))
       {
           Edge edge = new Edge(findVertex(firstLabel), findVertex(secondLabel), new StackPane());
           this.edges.add(edge);
       }
    }

    public boolean findEdgeBool(String firstLabel, String secondLabel)
    {
        return this.edges.stream()
                .anyMatch(e -> (e.getFirstVertex().getLabel().equals(firstLabel) && e.getSecondVertex().getLabel().equals(secondLabel))
                || (e.getFirstVertex().getLabel().equals(secondLabel) && e.getSecondVertex().getLabel().equals(firstLabel)));
    }

    public void deleteEdge(String firstLabel, String secondLabel)
    {
        Iterator<Edge> i = this.edges.iterator();

        while (i.hasNext())
        {
            if((i.next().getFirstVertex().getLabel().equals(firstLabel) && i.next().getSecondVertex().getLabel().equals(secondLabel)
            || (i.next().getSecondVertex().getLabel().equals(secondLabel) && i.next().getFirstVertex().getLabel().equals(firstLabel))))
            {
                i.remove();
                break;
            }
        }
    }

    public StackPane addVertex()
    {
        String label = "v" + (vertices.size() + 1);
        StackPane dot = getDot("green", label);
        Vertex vertex = new Vertex(label, dot);
        System.out.println(vertex.getId());
        System.out.println(vertex.getLabel());
        this.vertices.add(vertex);
        return dot;
    }

    private Vertex findVertex(String label)
    {
        return this.vertices.stream().filter(v -> v.getLabel().equals(label)).findAny().get();
    }

    public boolean findVertexBool(String label)
    {

        return this.vertices.stream().anyMatch(v -> v.getLabel().equals(label));
    }

    public void deleteVertex(String label)
    {
        Iterator<Vertex> i = this.vertices.iterator();

        while (i.hasNext())
        {
            if(i.next().getLabel().equals(label))
            {
                i.remove();
                break;
            }
        }
    }

    public Edge getLastEdge()
    {
        System.out.println(this.edges.size());
        return (Edge) this.edges.toArray()[this.edges.size() - 1];
    }
}
