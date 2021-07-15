package graphs.freshcode;

import java.io.IOException;
import java.util.*;
import java.lang.Math;

/*
 * CHANGELOG
 * 
 * 08/05: 
 * - removed incorrect import statements from Vertex.java
 * - in GraphApplic.java main(), added example to show access to edge weight
 * 
 */

public class GraphApplic extends Graph {

	public GraphApplic(long s) {
		super(s);
	}

	// PASS LEVEL
	
	public Integer surfNoJump(Integer v, Integer n) {
		// PRE: v is vertex to start surf; n >= 0
		// POST: surfs the graph randomly for n moves, 
		//       choosing adjacent vertex according to distribution of edge weights
		//       modifies # visits in vertices
		//       returns last visited vertex at end of surfing

		// TODO
		Integer curID = v;
		
		for(Integer i = 0; i < n; i++) {
			this.getVertex(curID).setVisits(this.getVertex(curID).getVisits()+1);
			curID = this.getVertex(curID).getPseudoRandomLink();
		}
		
		return curID;
	}
	
	public Integer surfWithJump(Integer v, Integer n, Double jumpThreshold) {
		// PRE: v is vertex to start surf; n >= 0; 0 <= jumpThreshold <= 1.0
		// POST: surfs the graph randomly for n moves, 
		//       choosing adjacent vertex according to distribution of edge weights if random number is below jumpThreshold, 
		//       choosing any vertex uniformly randomly otherwise;
		//       modifies # visits in vertices
		//       returns last visited vertex at end of surfing

		// TODO
		Integer curID = v;
		
		for(Integer i = 0; i < n; i++) {
			Integer size = this.numVertices();
			Double random = p.genPseudoRandDouble();
			Double random2 = p.genPseudoRandDouble()*size;
			this.getVertex(curID).setVisits(this.getVertex(curID).getVisits()+1);
			
			if(random < jumpThreshold && this.getVertex(curID).adjs.size() > 0) {
				curID = this.getVertex(curID).getPseudoRandomLink();
			}
			
			else {
				curID = random2.intValue();
			}
		}
		
		return curID;
	}
	
	public ArrayList<Integer> topN(Integer N) {
		// PRE: none
		// POST: returns N vertices with highest number of visits, in order;
		//       returns all vertices if <N in the graph;
		//       returns vertices ranked 1,..,N,N+1,..,N+k if these k have the
		//         same number of visits as vertex N
		
		// TODO
		ArrayList<Integer> highN = new ArrayList<Integer>();
		Integer size = this.numVertices();
		
		for(Integer i = 0; i < N; i++) {
			Integer top = 0;
			Integer max = 0;
			
			for(Integer j = 0; j < size; j++) {
				if(this.getVertex(j).getVisits() > max) {
					max = this.getVertex(j).getVisits();
					top = j;
				}
			}
			
			highN.add(top);
			this.getVertex(top).setVisits(0);
		}
		
		return highN;
	}
	
	// CREDIT LEVEL
	
	public void setVertexWeights () {
		// PRE: -
		// POST: set weights of each vertex v to be v.visits divided by
		//         the total of visits for all vertices

		// TODO
		Integer visittotal = 0;
		Integer size = this.numVertices();
		
		for(Integer i = 0; i < size; i++) {
			visittotal += this.getVertex(i).getVisits(); 
		}
		
		for(Integer j = 0; j < size; j++) {
			this.getVertex(j).setWeight((double)(this.getVertex(j).getVisits())/visittotal);
		}
	}
	
	public void convergenceWeights(Integer dp, Double jumpThreshold) {
		// PRE: dp >= 0 representing number of decimal places
		//		0 <= jumpThreshold <= 1.0
		// POST: web is surfed until all weights are constant to dp decimal places,
		//         for at least one iteration
		
		// TODO
		
		ArrayList<Double> totalpreWeight = new ArrayList<Double>();
		Integer size = this.numVertices();
		Integer con = 0;
		Integer curID = surfWithJump(this.getFirstVertexID(), 1000, jumpThreshold);
		Double curWeight = 0.0;
		Double preWeight = 0.0;
		
		for(Integer i = 0; i < size; i++) {
			totalpreWeight.add(i, this.getVertex(i).getWeight());
		}
		
		while (con == 0) {
			setVertexWeights();
			con = 1;
			
			for(Integer j = 0; j < size; j++) {
				curWeight = this.getVertex(j).getWeight()*Math.pow(10, dp);
				preWeight = totalpreWeight.get(j)*Math.pow(10, dp);
				
				if(curWeight.intValue() != preWeight.intValue()) {
					con = 0;
				}
			}
			
			curID = surfWithJump(curID, 1000, jumpThreshold);
			
			for(Integer k = 0;  k < size; k++) {
				totalpreWeight.set(k, this.getVertex(k).getWeight());
			}
		}
	}

	// DISTINCTION LEVEL

	public Integer surfWithJumpUntilHit(Integer v, Integer n, Double jumpThreshold) {
		// PRE: v is vertex to start surf; n >= 0; 0 <= jumpThreshold <= 1.0
		// POST: surfs the graph randomly until visit v for second time (maximum n moves), 
		//       choosing adjacent vertex according to distribution of edge weights if random number is below jumpThreshold, 
		//       choosing any vertex uniformly randomly otherwise;
		//       modifies # visits in vertices
		//       returns number of vertices visited

		// TODO
		Integer curID = v;
		Integer visittotal = 0;
		
		for(Integer i = 0; i < n; i++) {
			curID = surfWithJump(curID, 1, jumpThreshold);
			visittotal++;
			
			if(curID == v) {
				return visittotal;
			}
		}
		
		return 0;
	}

	public Double averageHittingTime(Integer v, Integer n, Integer maxHits, Double jumpThreshold) {
		// PRE: n >= 1 is number of iterations for averaging; maxHits >= 0; 0 <= jumpThreshold <= 1.0
		// POST: returns average number of vertices visited until hitting, across n iterations,
		//         (not including those which stopped because they reached maxHits)
		//       returns 0 if all iterations reached maxVisits
		
		// TODO
		Integer visitNum = 0;
		Integer size = this.numVertices();
		Double visitTotal = 0.0;
		Double total = 0.0;
		
		for (Integer i = 0; i < n; i++) {
			Integer curID = this.getVertex(this.getFirstVertexID()).getPseudoRandomDouble().intValue()*size;
			visitNum = surfWithJumpUntilHit(curID, maxHits, jumpThreshold);
			
			if (visitNum == 0) {
				total--;
			}
			
			else {
			visitTotal += visitNum;
			total++;
			}
		}
		
		if (!(total == 0)) {
			return visitTotal/total;
		}
		
		return 0.0;
	}

	public Integer surfWithJumpUntilCover(Integer v, Integer n, Double jumpThreshold) {
		// PRE: v is vertex to start surf; n >= 0; 0 <= jumpThreshold <= 1.0
		// POST: surfs the graph randomly until all vertices visited (with maximum n vertices visited), 
		//       choosing adjacent vertex according to distribution of edge weights if random number is below jumpThreshold, 
		//       choosing any vertex uniformly randomly otherwise;
		//       modifies # visits in vertices
		//       returns number of vertices visited
		
		// TODO
		Integer curID = v;
		Integer visitNum = 0;
		Integer Nvertices = 1;
		Integer size = this.numVertices();
		
		ArrayList<Integer> vertices = new ArrayList<Integer>();
		vertices.add(curID);
		
		for(Integer i = 0; i < n; i++) {
			curID = surfWithJump(curID, 1, jumpThreshold);
			
			if(!vertices.contains(curID)) {
				Nvertices++;
				vertices.add(curID);
			}
			
			visitNum++;
			
			if(Nvertices == size) {
				return visitNum;
			}
		}
		
		return 0;
	}
	
	public Double averageCoverTime(Integer n, Integer maxVisits, Double jumpThreshold) {
		// PRE: n >= 1 is number of iterations for averaging; maxVisits >= 0; 0 <= jumpThreshold <= 1.0
		// POST: returns average number of vertices visited until cover, across n iterations,
		//         (not including those which stopped because they reached maxVisits)
		//         randomly selecting start vertex each iteration
		//       returns 0 if all iterations reached maxVisits
		
		// TODO
		Integer visitNum = 0;
		Integer size = this.numVertices();
		Double visitTotal = 0.0;
		Double totali = 0.0;
		
		for (Integer i = 0; i < n; i++) {
			Integer curID = this.getVertex(this.getFirstVertexID()).getPseudoRandomDouble().intValue()*size;
			visitNum = surfWithJumpUntilCover(curID, maxVisits, jumpThreshold);
			
			if (visitNum == 0) {
				totali--;
			}
			
			else {
			visitTotal += visitNum;
			totali++;
			}
		}
		
		return visitTotal/totali;
	}

	public Integer boostVertex(Integer v, Integer dp) {
		// PRE: v is a vertex in the graph
		// POST: returns a vertex v2 (!= v) such that when edge (v,v2,1.0) is added to the graph,
		//         the weight of v is larger than when edge (v,v3,1.0) is added to the graph
		//         (for any v3), when the graph is surfed to convergence
		//       if there is no such vertex v2 (i.e. v is already connected to all other vertices),
		//         return v
		//       edges are only added if they do not already exist in the graph
		
		// TODO
		Double weight = 0.0;
		Integer index = 0;
		Integer size = this.numVertices();
		
		for(Integer i = 0; i < size; i++) {
			if(!(v == i || getVertex(v).getAdjs().containsKey(i))) {
				getVertex(v).getAdjs().put(i, 1.0);
				convergenceWeights(dp, 0.9);
				
				if(weight < getVertex(v).getWeight()) {
					weight = getVertex(v).weight;
					index = i;
				}
				
				getVertex(v).getAdjs().remove(i);
			}
		}
		
		return index;
	}
	
	
	public static void main(String[] args) {
		GraphApplic g = new GraphApplic(1);

		try {
			g.readWeightedFromFileWSeedAndSetDirected("C:/Users/Isaac Lee/Desktop/Macquarie University/Session 3/COMP2010 - Algorithms and Data Structures/Assignment 2/assignment-sample-graphs/tiny-weight.txt");
			  // change this path to wherever you store your data files
			g.print();
			System.out.print("weight of edge (1,2): ");
			System.out.println(g.getVertex(1).getAdjs().get(2));			
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}

	}
}
