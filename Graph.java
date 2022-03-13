import java.util.*;

public class Graph
{
    protected HashMap<Integer, Vertex> vertexSet; // conjunto de vértices do grafo

    public Graph()
    {
        this.vertexSet = new HashMap<>();
    }

    /**
     * Imprime dados do grafo
     */
    public void printGraph()
    {
        System.out.println("Id do vértice --> Vizinhos");
        for(Vertex v : vertexSet.values())
            v.printVertex();
    }

    /**
     * Adiciona um novo vértice ao grafo
     * @param id identificador do vértice
     */
    public void addVertex(int id)
    {
        if (id > 0 && !this.vertexSet.containsKey(id))
        {
            Vertex v = new Vertex(id);
            vertexSet.put(v.id, v);
        }
        else
        {
            System.out.println("Id " + id + " inválido ou já utilizado!");
        }
    }

    private void addVextex(Vertex v) {
        if(!this.vertexSet.containsValue(v)) {
            this.vertexSet.put(v.id, v);
        }
    }

    private boolean hasEdge(Integer v1, Integer v2) {
        // Verifiacar se ambos possuem-se como vértices vizinhos, caso positivo então existe aresta
        boolean v1TemV2 = this.vertexSet.get(v1).neighborhood.containsKey(v2);
        boolean v2TemV1 = this.vertexSet.get(v2).neighborhood.containsKey(v1);

        return v1TemV2 && v2TemV1;
    }

    /**
     * Verifica se o grafo é não-direcionado
     */
    public boolean isUndirected()
    {
        // Iterando todos os vértices dois a dois
        for (Vertex v1 : vertexSet.values()) {
            for (Vertex v2 : vertexSet.values()) {
                // Se os vértices foram os mesmos, ignora
                if (v1.id == v2.id) {
                    continue;
                }

                if (v1.neighborhood.containsKey(v2.id) != v2.neighborhood.containsKey(v1.id)) {
                    System.out.println("O grafo em questão é direcionado.");
                    return true;
                }

            }
        }
        return false;
    }

    private void combinaArray(int[] array, int arrayAux[], int inicio,
                              int fim, int index, int quantElemCombinar, List<List<Integer>> listaSaida)
    {
        if (index == quantElemCombinar)
        {

            List<Integer> listDataInteger = new ArrayList<>();
            Collections.addAll(listDataInteger, Arrays.stream(arrayAux).boxed().toArray(Integer[]::new));
            listaSaida.add(listDataInteger);

            return;
        }
        for (int i=inicio; i<=fim && fim-i+1 >= quantElemCombinar-index; i++)
        {
            arrayAux[index] = array[i];
            combinaArray(array, arrayAux, i+1, fim, index+1, quantElemCombinar, listaSaida);
        }
    }

    private List<List<Integer>> combinaArrayNumANumElementos(int[] array, int quantElemCombinar)
    {
        List<List<Integer>> combinations = new ArrayList<>();

        int data[] = new int[quantElemCombinar];
        int size_array = array.length;
        combinaArray(array, data, 0, size_array-1, 0, quantElemCombinar, combinations);

        return combinations;
    }

    private int[] listIntegerToIntArray(List<Integer> list) {
        int[] ret = new int[list.size()];
        for(int i = 0; i < ret.length; i++)
            ret[i] = list.get(i);
        return ret;
    }

    public List<List<Integer>> generatePermutations(List<Integer> list) {
        if (list.isEmpty()) {
            List<List<Integer>> listResult = new ArrayList<>();
            listResult.add(new ArrayList<>());
            return listResult;
        }
        Integer firstElement = list.remove(0);
        List<List<Integer>> returnValue = new ArrayList<>();
        List<List<Integer>> permutations = generatePermutations(list);
        for (List<Integer> smallerPermutated : permutations) {
            for (Integer index = 0; index <= smallerPermutated.size(); index++) {
                List<Integer> temp = new ArrayList<>(smallerPermutated);
                temp.add(index, firstElement);
                returnValue.add(temp);
            }
        }
        return returnValue;
    }

    public Graph getSubgraphFromSetVertexIntegers(List<Integer> setVertexIntegers)
    {
        Graph subgraph = new Graph();
        for(Integer vertexValue : setVertexIntegers)
        {
            Vertex v = this.vertexSet.get(vertexValue);
            subgraph.addVextex(v);
        }
        return subgraph;
    }

    //Verifica se o grafo da classe não possui caminho P4
    public boolean isCograph()
    {
        Collection<Integer> listVertex = this.vertexSet.keySet();
        List<Integer> listVertexGraph = new ArrayList<Integer>(listVertex);

        // Transformar listaVerticesGraph em um array simples.
        int[] listVertexGraphArrayInt = this.listIntegerToIntArray(listVertexGraph);

        // Encontrando todas as combinações com 4 vértices
        List<List<Integer>> listCombinationsOf4Vertex = this.combinaArrayNumANumElementos(listVertexGraphArrayInt, 4);

        for(List<Integer> combination : listCombinationsOf4Vertex) {

            List<List<Integer>> permutations_ = generatePermutations(combination);

            for(List<Integer> possibleP4Subgraph : permutations_) {

                boolean hasEdgeInVertexZeroUm = this.hasEdge(possibleP4Subgraph.get(0), possibleP4Subgraph.get(1));
                boolean hasEdgeInVertexUmDois = this.hasEdge(possibleP4Subgraph.get(1), possibleP4Subgraph.get(2));
                boolean hasEdgeInVertexDoisTres = this.hasEdge(possibleP4Subgraph.get(2), possibleP4Subgraph.get(3));

                if (hasEdgeInVertexZeroUm && hasEdgeInVertexUmDois && hasEdgeInVertexDoisTres) {

                    boolean hasEdgeInVertexZeroDois = this.hasEdge(possibleP4Subgraph.get(0), possibleP4Subgraph.get(2));
                    boolean hasEdgeInVertexZeroTres = this.hasEdge(possibleP4Subgraph.get(0), possibleP4Subgraph.get(3));
                    boolean hasEdgeInVertexUmTres = this.hasEdge(possibleP4Subgraph.get(1), possibleP4Subgraph.get(3));

                    if(hasEdgeInVertexZeroDois || hasEdgeInVertexZeroTres || hasEdgeInVertexUmTres)
                    {
                        break;
                    }
                    else
                    {
                        System.out.println("O grafo dado como entrada não é cografo. \nSegue abaixo o subgrafo que comprova isso: \n");
                        getSubgraphFromSetVertexIntegers(possibleP4Subgraph).printGraph();
                        return false;
                    }
                }
            }
        }

        System.out.println("O grafo dado como entrada é um cografo");
        return true;
    }
}