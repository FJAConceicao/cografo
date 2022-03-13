import java.util.HashMap;

public class Vertex {
    protected Integer id;
    protected HashMap<Integer, Vertex> neighborhood;

    /**
     * Construtor do vértice
     * @param id identificador do vértice
     */
    public Vertex (int id) {
        this.id = id;
        this.neighborhood = new HashMap<Integer,Vertex>();
    }

    /**
     * Imprime dados do vértice
     */
    public void printVertex() {
        System.out.print("\n" + id + " -->");
        for(Vertex v : this.neighborhood.values())
            System.out.print(" " + v.getId()); //v.id
    }

    public Integer getId() {
        return this.id;
    }
}