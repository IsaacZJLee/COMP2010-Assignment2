package graphs.freshcode;

public class Edge {
	
	/*
	 * class Edge consists only of a pair of integers, to represent
	 * edges in a graph with vertices labelled with integers, plus a double
	 * for the weight of the edge; there are
	 * corresponding get and set methods for those
	*/
	 
	private Integer first;
	private Integer second;
	private Double weight;

	public Edge(Integer f, Integer s) {
		first = f;
		second = s;
		weight = 0.0;
	}

	public Edge(Integer f, Integer s, Double w) {
		first = f;
		second = s;
		weight = w;
	}
	
	public Integer getFirst() {
		return first;
	}

	public Integer getSecond() {
		return second;
	}

	public void setFirst(int f) {
		first = f;
	}
	
	public void setSecond(int s) {
		second = s;
	}
	
	public Double getWeight() {
		return weight;
	}
	
	public void setWeight (Double w) {
		weight = w;
	}
}
