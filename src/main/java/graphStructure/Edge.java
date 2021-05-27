package graphStructure;

import javafx.scene.layout.StackPane;
import org.chocosolver.solver.variables.IntVar;

import java.util.Objects;

public class Edge
{
    private Vertex firstVertex;
    private Vertex secondVertex;
    private StackPane colorLabel;

    private long id;
    private boolean isColor;

    private IntVar solverVar;

    public Edge(Vertex firstVertex, Vertex secondVertex)
    {
        this.firstVertex = firstVertex;
        this.secondVertex = secondVertex;
        this.isColor  = false;
    }

    public Edge(Vertex firstVertex, Vertex secondVertex, StackPane colorLabel)
    {
        this.firstVertex = firstVertex;
        this.secondVertex = secondVertex;
        this.colorLabel = colorLabel;
        this.isColor  = false;
    }

    public Edge(Vertex firstVertex, Vertex secondVertex, StackPane colorLabel, boolean isColor)
    {
        this.firstVertex = firstVertex;
        this.secondVertex = secondVertex;
        this.colorLabel = colorLabel;
        this.isColor  = isColor;
        this.colorLabel.setVisible(isColor);
    }

    public Edge(Vertex firstVertex, Vertex secondVertex, long id, StackPane colorLabel, boolean isColor)
    {
        this.firstVertex = firstVertex;
        this.secondVertex = secondVertex;
        this.colorLabel = colorLabel;
        this.isColor  = isColor;
        this.colorLabel.setVisible(isColor);
        this.id = id;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public boolean isColor()
    {
        return isColor;
    }

    public void setColor(boolean color)
    {
        isColor = color;
    }
    public IntVar getSolverVar()
    {
        return solverVar;
    }

    public void setSolverVar(IntVar solverVar)
    {
        this.solverVar = solverVar;
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

    public boolean getIsColor()
    {
        return isColor;
    }

    public void setIsColor(boolean color)
    {
        isColor = color;
    }

    public void showColorLabel(boolean flag)
    {
        this.isColor = flag;
        this.colorLabel.setVisible(flag);
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