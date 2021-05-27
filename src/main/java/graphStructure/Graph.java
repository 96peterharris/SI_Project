package graphStructure;

import javafx.scene.layout.StackPane;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
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
           Edge edge = new Edge(findVertex(firstLabel), findVertex(secondLabel), new StackPane(), edges.size());
           this.edges.add(edge);
       }
    }

    private void addEdge(String firstLabel, String secondLabel, long color)
    {
        if(findVertexBool(firstLabel) && findVertexBool(secondLabel) && !findEdgeBool(firstLabel, secondLabel))
        {
            Edge edge = new Edge(findVertex(firstLabel), findVertex(secondLabel), new StackPane(), color, edges.size());
            this.edges.add(edge);
        }
    }

    public void addEdgeExample(String firstLabel, String secondLabel, long id)
    {
        if(findVertexBool(firstLabel) && findVertexBool(secondLabel) && !findEdgeBool(firstLabel, secondLabel))
        {
            Edge edge = new Edge(findVertex(firstLabel), findVertex(secondLabel), id, new StackPane(), 0);
            this.edges.add(edge);
        }
    }

    public boolean findEdgeBool(String firstLabel, String secondLabel)
    {
        if(firstLabel.equals(secondLabel))
        {
            return true;
        }
        {
            return this.edges.stream()
                    .anyMatch(e -> ((e.getFirstVertex().getLabel().equals(firstLabel) && e.getSecondVertex().getLabel().equals(secondLabel))
                            || (e.getFirstVertex().getLabel().equals(secondLabel) && e.getSecondVertex().getLabel().equals(firstLabel))));
        }
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
        String name = "v";
        long id = vertices.size() + 1;
        String label = name + id;
        StackPane dot = getDot("green", label);
        Vertex vertex = new Vertex(name, id, dot);
        this.vertices.add(vertex);
        return dot;
    }

    public void addVertexRand()
    {
        String label = "v" + (vertices.size() + 1);
        StackPane dot = getDot("green", label);
        Vertex vertex = new Vertex("v", vertices.size() + 1, dot);
        layoutX = (randInt(20, 780));
        layoutY = (randInt(20, 500));
        dot.setLayoutX(layoutX + dot.getTranslateX());
        dot.setLayoutY(layoutY + dot.getTranslateY());
        dot.toFront();
        this.vertices.add(vertex);
    }

    private void addVertexRand(long id, String name, String label)
    {
        StackPane dot = getDot("green", label);
        Vertex vertex = new Vertex(name, id, dot);
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
        return (Edge) this.edges.toArray()[this.edges.size() - 1];
    }

    public void clearGraph()
    {
        this.vertices.clear();
        this.edges.clear();
    }

    private int randInt(int min, int max)
    {
//        return ((int) (Math.random() * (max - min))) + min;
        if(min >= max)
        {
            return ThreadLocalRandom.current().nextInt(min);

        }
        else
        {
            return ThreadLocalRandom.current().nextInt(min, max);
        }
    }

    public void randGraph()
    {
        int numOfVertices = randInt(2, 8);
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

    private ArrayList<HashMap<String, String>> verticesToArrOfMap()
    {
        ArrayList<HashMap<String, String>> result = new ArrayList<>();

        for(Vertex v : this.vertices)
        {
            result.add(new HashMap<String, String>(){{
                put("id", String.valueOf(v.getId()));
                put("name", v.getName());
                put("label", v.getLabel());
            }});
        }

        return result;
    }

    private ArrayList<HashMap<String, String>> edgesToArrOfMap()
    {
        ArrayList<HashMap<String, String>> result = new ArrayList<>();

        for(Edge e : this.edges)
        {
            result.add(new HashMap<String, String>(){{
                put("firstV", e.getFirstVertex().getLabel());
                put("secondV", e.getSecondVertex().getLabel());
                put("color", String.valueOf(e.getColorFromVar()));
            }});
        }

        return result;
    }

    public HashMap<String, ArrayList<HashMap<String, String>>>  graphToMap()
    {
       return new HashMap<String, ArrayList<HashMap<String, String>>>(){{
           put("vertices", verticesToArrOfMap());
           put("edges", edgesToArrOfMap());
       }};
    }

    public void graphFromMap(HashMap<String, ArrayList<HashMap<String, String>>> graphMap)
    {
        if(graphMap != null)
        {
            this.vertices.clear();
            this.edges.clear();

            ArrayList<HashMap<String, String>> verticesArr = graphMap.get("vertices");
            ArrayList<HashMap<String, String>> edgesArr = graphMap.get("edges");

            if(verticesArr != null)
            {
                for(HashMap<String, String> m : verticesArr)
                {
                    addVertexRand(Long.valueOf(m.get("id")), m.get("name"), m.get("label"));
                }
            }

            if(edgesArr != null)
            {
                for (HashMap<String, String> m : edgesArr)
                {
                    addEdge(m.get("firstV"), m.get("secondV"), Long.valueOf(m.get("color")));
                }
            }

        }
    }

    public List<Edge> getConnectedEdges(String vertexLabel)
    {
        List<Edge> listOfEdges = new ArrayList<>();
        this.edges.forEach(edge -> {if(edge.getFirstVertex().getLabel().equals(vertexLabel)
        || edge.getSecondVertex().getLabel().equals(vertexLabel)){listOfEdges.add(edge);}});
        return  listOfEdges;
    }
}