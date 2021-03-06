import java.io.BufferedReader;
import java.io.FileReader;

public class FileController {
    private String fileName;

    /**
     * Construtor do controlador de arquivos
     * @param fileName nome do arquivo de entrada com os dados do grafo
     */
    FileController(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Lê os dados do grafo do arquivo e atribui à instância
     * @param graph instância do grafo
     */
    public void readGraph(Graph graph) {
        String row;
        String[] data;

        try {
            FileReader fileReader = new FileReader(this.fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            Integer vertexId, neighborVertexId;

            while ((row = bufferedReader.readLine()) != null) {

                row = row.replaceAll("\\s+", " ").trim(); // remove espaços em branco

                try {
                    data = row.split(" ");
                    vertexId = Integer.parseInt(row.split(" = ")[0]);

                    if(!graph.vertexSet.containsKey(vertexId))
                        graph.addVertex(vertexId);

                    for(int i = 2; i < data.length; i++) {

                        neighborVertexId = Integer.parseInt(data[i]);

                        if(graph.vertexSet.containsKey(vertexId)) {
                            Vertex neighborVertex = new Vertex(neighborVertexId);
                            graph.vertexSet.get(vertexId).neighborhood.put(neighborVertex.id, neighborVertex); //addNeighbor(neighborVertex);
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("As linhas de entrada devem estar no padrão <vertice> = <vizinho1> <vizinho2> ... Por exemplo: 1 = 2 3 4");
                    bufferedReader.close();
                    System.exit(1);
                }
            }
            bufferedReader.close();
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}