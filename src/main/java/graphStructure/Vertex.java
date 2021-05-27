package graphStructure;

import javafx.scene.layout.StackPane;
import org.chocosolver.solver.variables.IntVar;

import java.util.Objects;

public class Vertex
{
    private String name;
    private long id;
    private String label;
    private StackPane dot;

    private IntVar solverVar;

    public Vertex(){}

    public Vertex(String name, long id, StackPane dot)
    {
        this.name = name;
        this.id = id;
        this.dot = dot;
        this.label = this.name + this.id;
    }
    public IntVar getSolverVar()
    {
        return solverVar;
    }

    public void setSolverVar(IntVar solverVar)
    {
        this.solverVar = solverVar;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public StackPane getDot()
    {
        return dot;
    }

    public void setDot(StackPane dot)
    {
        this.dot = dot;
    }

    public String getLabel()
    {
        return this.label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return id == vertex.id &&
                Objects.equals(name, vertex.name) &&
                Objects.equals(dot, vertex.dot);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, id, dot);
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", label='" + label + '\'' +
                ", dot=" + dot +
                '}';
    }
}