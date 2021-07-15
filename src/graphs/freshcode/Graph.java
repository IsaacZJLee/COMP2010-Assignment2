package graphs.freshcode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graph {

	/*
	 * Represents a graph using an adjacency list.
	 */
	
	private TreeMap<Integer, Vertex> m;
	// Graph implemented using a TreeMap: key is vertex id label,
	// value is the vertex object, which contains the adjacency list
	// for that vertex
	private Integer numEdges;
	private Iterator<Map.Entry<Integer, Vertex>> gIt;
	protected PseudoRand p;
	private long seed;
	
	public Graph(long s) {
		m = new TreeMap<Integer, Vertex>();
		numEdges = 0;
		seed = s;
		p = new PseudoRand(s);
	}
	
	public Integer numVertices() {
		return m.size();
	}
	
	public Integer numEdges() {
		return numEdges;
	}
	
	public Vertex getVertex(Integer n) {
		// Used for accessing vertex to e.g. add neighbours
		return m.get(n);
	}
	
	public NavigableSet<Integer> getVertexSet() {
		// Returns ordered set of all vertices in the graph
		return m.navigableKeySet();
	}

	public void print() {
		gIt = m.entrySet().iterator();
		
		System.out.println("Number of nodes is " + m.size());
		System.out.println("Number of edges is " + numEdges);

		while (gIt.hasNext()) {
			Map.Entry<Integer, Vertex> pairs = gIt.next();
			pairs.getValue().print();
		}		
		System.out.println();
	}

	public void setUnmarked() {
		// Sets all vertices to be unmarked e.g. after traversal
		gIt = m.entrySet().iterator();
		while (gIt.hasNext()) {
			Map.Entry<Integer, Vertex> pairs = gIt.next();
			pairs.getValue().setUnmarked();
			pairs.getValue().setNum(0);
		}				
	}
	
	
	public Integer getFirstVertexID() {
		// Returns first vertex ID in TreeMap ordering
		// e.g. for starting a traversal
		return m.firstKey();
	}
	
	public boolean containsVertex(Integer n) {
		// Checks if n is a vertex in the graph
		return m.containsKey(n);
	}

	
	
	public void readWeightedFromFileWSeedAndSetDirected(String fInName) throws IOException {
		// ADDED+
		// CHANGED to add weight, pseudo-random seed
		// Reads graph from a file:
		//    first line, # vertices N
		//    next N lines, one pair of integers per line, vertex ID and pseudorandom seed
		//    following, one pair of integers per line with double weight
		
		Scanner fIn = new Scanner(
							 new FileReader(fInName));
		Integer N, x, y, s;
		Double w;
		
		N = fIn.nextInt();
		for (int i = 0; i < N; i++) {
			x = fIn.nextInt();
			s = fIn.nextInt();
			m.put(x, new Vertex(x, s));
		}
		while (fIn.hasNext()) {
			x = fIn.nextInt();
			y = fIn.nextInt();
			w = fIn.nextDouble();
			m.get(x).addAdj(y,w);
			numEdges++;
		}
		fIn.close();
		
	}

	public static void main(String[] args) {
		Graph g = new Graph(1);

		try {
			g.readWeightedFromFileWSeedAndSetDirected("C:/Users/Isaac Lee/Desktop/Macquarie University/Session 3/COMP2010 - Algorithms and Data Structures/Assignment 2/assignment-sample-graphs/tiny-weight.txt");
				// change this path to wherever you store your data files
			g.print();
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		
	}
}
