package graphs.freshcode;

import java.util.*;

public class VertexIDList extends TreeMap<Integer, Double> {
	// CHANGED: maps vertex id to weight (for transition prob)

	/*
	 * List of vertices, to be used predominantly to represent
	 * the neighbours of a vertex
	 * 
	 * As an extension of TreeMap, inherits its methods, such
	 * as size()
	 */
	
	private Iterator<Map.Entry<Integer, Double>> it; 
	// CHANGED: maps vertex id to weight (for transition prob)
	
	private Double weightTotal = 0.0; // total of weights for edges
	// ADDED: for weights
	
	public VertexIDList() {
		// constructor
		super();
	}
	
	public Integer top() {
		// returns first element of list, treating as a queue
		return this.firstKey();
	}
	
	public void pop() {
		// CHANGED to change weight
		// deletes first element of list, treating as a queue
		weightTotal -= this.get(firstKey());
		this.remove(this.firstKey());
	}
	
	public void push(Integer n) {
		// inserts first element of list, treating as a queue
		this.put(n, 0.0); // CHANGED: set to default 0 weight
	}
	
	public void push(Integer n, Double w) {
		// inserts first element of list, treating as a queue, weight w
		// ADDED
		weightTotal += w;
		this.put(n, w);
	}
	
	public Double getWeightTotal() {
		return weightTotal;
	}
	
	public void print() {
		it = this.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, Double> pairs = it.next(); // CHANGED
			System.out.print(" " + pairs.getKey() + "(" + pairs.getValue() + ")"); // CHANGED
		}		
		System.out.println();
	}
	
	public Iterator<Integer> iterator() {
		// returns an iterator to use to iterate over the list
		return this.keySet().iterator();
	}
	
	public Integer nthElement(Integer n) {
		// ADDED
		ArrayList<Integer> a = new ArrayList<Integer>(navigableKeySet());
		return a.get(n);
	}

	public static void main(String[] args) {
		VertexIDList v = new VertexIDList();
		
		v.push(2);
		v.push(5);
		v.push(3);
		v.print();
		v.pop();
		System.out.println(v.top());
		v.print();
		
		Iterator<Integer> vIt = v.iterator();
		
		System.out.println("testing iterator ...");
		while (vIt.hasNext()) {
			System.out.println(vIt.next());
		}
		v.print();
	}
}
