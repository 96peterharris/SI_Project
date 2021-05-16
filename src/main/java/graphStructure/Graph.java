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

    public LinkedHashSet<Vertex> getVertices()
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
        this.vertices.add(vertex);
        return dot;
    }

    public void addVertexRand()
    {
        String label = "v" + (vertices.size() + 1);
        StackPane dot = getDot("green", label);
        Vertex vertex = new Vertex(label, dot);
        layoutX = (randInt(20, 780));
        layoutY = (randInt(20, 500));
        dot.setLayoutX(layoutX + dot.getTranslateX());
        dot.setLayoutY(layoutY + dot.getTranslateY());
        dot.toFront();
        this.vertices.add(vertex);
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

    public void clearGraph()
    {
        this.vertices.clear();
        this.edges.clear();
    }

    private int randInt(int min, int max)
    {
        return ((int) (Math.random() * (max - min))) + min;
    }

    public void randGraph()
    {
        int numOfVertices = randInt(2, 15);
        int maxNumOfEdge = (numOfVertices * (numOfVertices - 1)) / 2;
        int numOfEdges = randInt(numOfVertices - 1, maxNumOfEdge);

        for(int i = 0; i < numOfVertices; i++)
        {
            addVertexRand();
        }

        for (int i = 0; i < numOfEdges; i++)
        {
            Vertex first = (Vertex) this.vertices.toArray()[randInt(0, this.vertices.size())];
            Vertex second = (Vertex) this.vertices.toArray()[randInt(0, this.vertices.size())];

            if (!findEdgeBool(first.getLabel(), second.getLabel()))
            {
                addEdge(first.getLabel(), second.getLabel());
            }
            else
            {
                while(true)
                {
                    first = (Vertex) this.vertices.toArray()[randInt(0, this.vertices.size())];
                    second = (Vertex) this.vertices.toArray()[randInt(0, this.vertices.size())];

                    if(!findEdgeBool(first.getLabel(), second.getLabel()))
                    {
                        break;
                    }
                }

                addEdge(first.getLabel(), second.getLabel());
            }
        }
    }
}
