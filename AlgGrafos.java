import java.io.*;
import java.util.ArrayList;

public class AlgGrafos {

    public static void main(String[] args) {
        ArrayList<String> filenames = new ArrayList<String>();
        filenames.add("cograph1.txt");
        filenames.add("cograph2.txt");
        filenames.add("cograph-is-not1.txt");
        filenames.add("cograph-is-not2.txt");
        filenames.add("digraph.txt");

        for (String filename : filenames) {
            String file = System.getProperty("user.dir") + java.io.File.separator + "myfiles" + java.io.File.separator + filename;

            FileController fileController = new FileController(file);
            System.out.println("\nVerificando o grafo no arquivo: " + filename);

            // Cria um grafo, lê os vértices e arestas do arquivo e valida se o arquivo está no formato especificado
            Graph graph = new Graph();
            fileController.readGraph(graph);

            // Verifica os requisitos do grafo de entrada. Deve ser não direcionado
            if (!graph.isUndirected()) {
                // Verifica se é cografo
                graph.isCograph();
            }
        }
    }
}