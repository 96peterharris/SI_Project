package graphStructure;

import javafx.scene.layout.StackPane;

import java.util.Objects;

public class Edge
{
    private Vertex firstVertex;
    private Vertex secondVertex;
    private StackPane colorLabel;

    public Edge(Vertex firstVertex, Vertex secondVertex)
    {
        this.firstVertex = firstVertex;
        this.secondVertex = secondVertex;
    }

    public Edge(Vertex firstVertex, Vertex secondVertex, StackPane colorLabel)
    {
        this.firstVertex = firstVertex;
        this.secondVertex = secondVertex;
        this.colorLabel = colorLabel;
    }

    public Vertex getFirstVertex()
    {
        return this.firstVertex;
    }

    public void setFirstVertex(Vertex firstVertex)
    {
        this.firstVertex = firstVertex;
    }

    public Vertex getSecondVertex()
    {
        return this.secondVertex;
    }

    public void setSecondVertex(Vertex secondVertex)
    {
        this.secondVertex = secondVertex;
    }

    public StackPane getColorLabel()
    {
        return this.colorLabel;
    }

    public void setColorLabel(StackPane colorLabel)
    {
        this.colorLabel = colorLabel;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Objects.equals(firstVertex, edge.firstVertex) &&
                Objects.equals(secondVertex, edge.secondVertex);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(firstVertex, secondVertex);
    }

    @Override
    public String toString()
    {
        return "Edge{" +
                "firstVertex=" + firstVertex +
                ", secondVertex=" + secondVertex +
                '}';
    }
}
