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
    private long color;

    private IntVar solverVar;

    public Edge(Vertex firstVertex, Vertex secondVertex)
    {
        this.firstVertex = firstVertex;
        this.secondVertex = secondVertex;
        this.color = 0;
    }

    public Edge(Vertex firstVertex, Vertex secondVertex, StackPane colorLabel, long id)
    {
        this.firstVertex = firstVertex;
        this.secondVertex = secondVertex;
        this.colorLabel = colorLabel;
        this.color = 0;
        this.id = id;
    }

    public Edge(Vertex firstVertex, Vertex secondVertex, StackPane colorLabel, long color, long id)
    {
        this.firstVertex = firstVertex;
        this.secondVertex = secondVertex;
        this.colorLabel = colorLabel;
        this.color = color;
        if(this.color != 0)
        {
            this.colorLabel.setVisible(false);
        }
        else
        {
            this.colorLabel.setVisible(true);
        }
        this.id = id;
    }

    public Edge(Vertex firstVertex, Vertex secondVertex, long id, StackPane colorLabel, long color)
    {
        this.firstVertex = firstVertex;
        this.secondVertex = secondVertex;
        this.id = id;
        this.colorLabel = colorLabel;
        this.color  = color;
        if(color != 0)
        {
            this.colorLabel.setVisible(false);
        }
        else
        {
            this.colorLabel.setVisible(true);
        }
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
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

    public long getColor()
    {
        return color;
    }

    public void setColor(long color)
    {
        this.color = color;
    }

    public long getColorFromVar()
    {
        if(solverVar != null)
        {
            return solverVar.getValue();
        }
        else
        {
            return color;
        }
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
                "firstVertex=" + firstVertex.getLabel() +
                ", secondVertex=" + secondVertex.getLabel() +
                ", id=" + id +
                ", color=" + color +
                '}';
    }
}