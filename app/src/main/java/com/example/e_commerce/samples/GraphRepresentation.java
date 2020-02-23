package com.example.e_commerce.samples;

import java.util.LinkedList;

public class GraphRepresentation {

    static class Graph{
        int numberOfVertax;
        LinkedList<Integer> adjListArray[];

        public Graph(int numberOfVertax) {
            this.numberOfVertax = numberOfVertax;
            adjListArray = new LinkedList[numberOfVertax];
            for (int i = 0; i < numberOfVertax; i++){
                adjListArray[i] = new LinkedList<>();
            }
        }
    }

    static void addEdge(Graph graph, int src, int dsc) {
        graph.adjListArray[src].add(dsc);
        graph.adjListArray[dsc].add(src);
    }

    static void printGraph(Graph graph) {
        for (int i = 0; i < graph.numberOfVertax; i++){
            System.out.println("Adj list of vartex "+ i);
            System.out.print("Head ");
            for (Integer v : graph.adjListArray[i]) {
                System.out.print("->" + v);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Graph graph = new Graph(5);

        addEdge(graph, 0, 1);
        addEdge(graph, 0, 4);
        addEdge(graph, 1, 2);
        addEdge(graph, 1, 3);
        addEdge(graph, 1, 4);
        addEdge(graph, 2, 3);
        addEdge(graph, 3, 4);

        printGraph(graph);
    }

}