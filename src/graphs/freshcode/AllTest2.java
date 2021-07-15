package graphs.freshcode;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

import org.junit.Test;

public class AllTest2 {

	String PATH = "C:/Users/Isaac Lee/Desktop/Macquarie University/Session 3/COMP2010 - Algorithms and Data Structures/Assignment 2/assignment-sample-graphs/";
	  // change this path to your own directory
	Integer seed = 1;
	Double tol = 0.01;  // tolerance for assertEquals with doubles
	
	@Test
	public void testNewVertexWeight() {
		Vertex v1 = new Vertex(3, seed);
		assertEquals(Double.valueOf(0.0), v1.getWeight(), tol);
	}

	@Test
	public void testNewVertexPseudoRandGen() {
		Vertex v1 = new Vertex(3, seed);
		Double d = v1.getPseudoRandomDouble();
		assertEquals(Double.valueOf(0.731), d, tol);
	}

	@Test
	public void testNewVertexVisits() {
		Vertex v1 = new Vertex(3, seed);
		assertEquals(Integer.valueOf(0), v1.getVisits());
	}
	
	@Test
	public void testOneRandomLink() {
		GraphApplic g = new GraphApplic(seed);
		try {
			g.readWeightedFromFileWSeedAndSetDirected(PATH + "tiny-weight.txt");
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		Integer v = g.getVertex(0).getPseudoRandomLink();
		assertEquals(Integer.valueOf(1), v);
	}

	@Test
	public void testSeveralRandomLinksOrder() {
		GraphApplic g = new GraphApplic(seed);
		try {
			g.readWeightedFromFileWSeedAndSetDirected(PATH + "tiny-weight.txt");
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}

		Integer[] counts = {0, 0, 0, 0, 0};
		Integer v;
		for (int i = 0; i < 1000; i++) {
			v = g.getVertex(1).getPseudoRandomLink();
			counts[v] += 1;
		}
		assertTrue(counts[4] < counts[2]);
		assertTrue(counts[4] < counts[3]);
	}

	@Test
	public void testSeveralRandomLinksVals() {
		GraphApplic g = new GraphApplic(seed);
		try {
			g.readWeightedFromFileWSeedAndSetDirected(PATH + "tiny-weight.txt");
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}

		Integer[] counts = {0, 0, 0, 0, 0};
		Integer v;
		for (int i = 0; i < 1000; i++) {
			v = g.getVertex(1).getPseudoRandomLink();
			counts[v] += 1;
		}
		for (int i = 0; i < counts.length; i++) {
			System.out.println(counts[i]);			
		}
		Integer[] expecteds = {0, 0, 424, 383, 193};
		assertArrayEquals(expecteds, counts);
	}
	
	@Test
	public void testSurfNoJump() {
		GraphApplic g = new GraphApplic(seed);
		try {
			g.readWeightedFromFileWSeedAndSetDirected(PATH + "tiny-weight.txt");
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}

		g.surfNoJump(g.getFirstVertexID(), 1000);
		int[] expecteds = {272, 272, 152, 253, 51};
		for (int i = 0; i < expecteds.length; i++) {
			assertEquals(Integer.valueOf(expecteds[i]), g.getVertex(i).getVisits());
		}
	}

	@Test
	public void testSurfWithJump() {
		GraphApplic g = new GraphApplic(seed);
		try {
			g.readWeightedFromFileWSeedAndSetDirected(PATH + "tiny-weight.txt");
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}

		g.surfWithJump(g.getFirstVertexID(), 10000, 0.9);
		int[] upperLimits = {2793, 2722, 1554, 2545, 764};
		int[] lowerLimits = {2676, 2594, 1360, 2376, 586};
		for (int i = 0; i < upperLimits.length; i++) {
			assertTrue(g.getVertex(i).getVisits() < upperLimits[i]);
			assertTrue(g.getVertex(i).getVisits() > lowerLimits[i]);
		}
	}

	@Test
	public void testSurfWithJumpUniform() {
		GraphApplic g = new GraphApplic(seed);
		try {
			g.readWeightedFromFileWSeedAndSetDirected(PATH + "tiny-weight.txt");
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}

		g.surfWithJump(g.getFirstVertexID(), 10000, 0.0);
		for (int i = 0; i < 5; i++) {
			assertTrue(g.getVertex(i).getVisits() < 2200);
			assertTrue(g.getVertex(i).getVisits() > 1800);
		}

	}

	@Test
	public void testTopNTiny() {
		GraphApplic g = new GraphApplic(seed);
		try {
			g.readWeightedFromFileWSeedAndSetDirected(PATH + "tiny-weight.txt");
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}

		g.surfWithJump(g.getFirstVertexID(), 10000, 0.9);
		int N = 3;
		ArrayList<Integer> visits = g.topN(N);
		Set<Integer> visitsSet = new HashSet<Integer>(visits);
		Integer[] valuesArray = {0, 1, 3};
		Set<Integer> valuesSet = new HashSet<Integer>(Arrays.asList(valuesArray));
		assertTrue(valuesSet.containsAll(visitsSet));
		assertTrue(visitsSet.containsAll(valuesSet));
	}

	@Test
	public void testTopNMedium() {
		GraphApplic g = new GraphApplic(seed);
		try {
			g.readWeightedFromFileWSeedAndSetDirected(PATH + "medium-weight.txt");
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}

		g.surfWithJump(g.getFirstVertexID(), 500000, 0.9);
		int N = 10;
		ArrayList<Integer> visits = g.topN(N);
		Set<Integer> visitsSet = new HashSet<Integer>(visits);
		Integer[] valuesArray = {6, 22, 13, 23, 9, 28, 30, 21, 37}; // only use top 9
		Set<Integer> valuesSet = new HashSet<Integer>(Arrays.asList(valuesArray));
		assertTrue(visitsSet.containsAll(valuesSet));
	}

	@Test
	public void testSetVertexWeights() {
		GraphApplic g = new GraphApplic(seed);
		try {
			g.readWeightedFromFileWSeedAndSetDirected(PATH + "tiny-weight.txt");
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		Integer N = 20000;
		g.surfWithJump(g.getFirstVertexID(), N, 0.9);
		g.setVertexWeights();
		for (int i = 0; i < 5; i++)
			assertEquals((double) g.getVertex(i).getVisits() / N, g.getVertex(i).getWeight(), tol);
	}

	@Test
	public void testConvergenceWeights() {
		GraphApplic g = new GraphApplic(seed);
		try {
			g.readWeightedFromFileWSeedAndSetDirected(PATH + "tiny-weight.txt");
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		g.convergenceWeights(3, 0.9);
		Double[] expecteds = {0.274, 0.265, 0.146, 0.247, 0.068};
		for (int i = 0; i < 5; i++)
			assertEquals(expecteds[i], g.getVertex(i).getWeight(), tol);
	}

	@Test
	public void testAverageHittingTimeTiny() {
		GraphApplic g = new GraphApplic(seed);
		try {
			g.readWeightedFromFileWSeedAndSetDirected(PATH + "tiny-weight.txt");
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		Integer v = g.getFirstVertexID();
		Double avgHit = g.averageHittingTime(v, 10000, 1000000, 0.9);
		g.setVertexWeights();
		//System.out.println("avgHit: " + avgHit);
		assertTrue(avgHit < 4);
		assertTrue(avgHit > 3.5);
		assertEquals(1 / g.getVertex(v).getWeight(), avgHit, tol);
	}

	@Test
	public void testAverageHittingTimeMedium() {
		GraphApplic g = new GraphApplic(seed);
		try {
			g.readWeightedFromFileWSeedAndSetDirected(PATH + "medium-weight.txt");
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		Integer v = g.getFirstVertexID();
		Double avgHit = g.averageHittingTime(v, 10000, 1000000, 0.9);
		g.setVertexWeights();
		assertTrue(avgHit < 500);
		assertTrue(avgHit > 400);
		assertEquals(1 / g.getVertex(v).getWeight(), avgHit, tol);
	}

	@Test
	public void testAverageHittingTimeTinyMaxed() {
		GraphApplic g = new GraphApplic(seed);
		try {
			g.readWeightedFromFileWSeedAndSetDirected(PATH + "tiny-weight.txt");
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		Integer v = g.getFirstVertexID();
		Double avgHit = g.averageHittingTime(v, 10000, 1, 1.0);
		g.setVertexWeights();
		System.out.println("avgHit: " + avgHit);
		assertEquals(Double.valueOf(0.0), avgHit, tol);
	}

	@Test
	public void testAverageCoverTime() {
		GraphApplic g = new GraphApplic(seed);
		try {
			g.readWeightedFromFileWSeedAndSetDirected(PATH + "tiny-weight.txt");
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		Double avgCover = g.averageCoverTime(10000, 10000, 0.9);
		System.out.println("avgCover: " + avgCover);
		assertTrue(avgCover < 16.5);
		assertTrue(avgCover > 12.5);
	}

	// NEW GRAPH
	
	@Test
	public void testOneRandomLinkNew() {
		GraphApplic g = new GraphApplic(seed);
		try {
			g.readWeightedFromFileWSeedAndSetDirected(PATH + "new.txt");
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		Integer v = g.getVertex(0).getPseudoRandomLink();
		Integer[] answersArray = {1, 2};
		Set<Integer> answersSet = new HashSet<Integer>(Arrays.asList(answersArray));
		assertTrue(answersSet.contains(v));
	}

	@Test
	public void testSurfWithJumpRandNewStartOne() {
		GraphApplic g = new GraphApplic(seed);
		try {
			g.readWeightedFromFileWSeedAndSetDirected(PATH + "new-start1.txt");
			//g.print();
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}

		g.surfWithJump(g.getFirstVertexID(), 10000, 1.0);
		g.print();
		int[] upperLimits = {5000, 4100, 1100};
		int[] lowerLimits = {5000, 3900, 900};
		int totalVisits = 0;
		for (int i = 0; i < upperLimits.length; i++) {
			assertTrue(g.getVertex(i+1).getVisits() <= upperLimits[i]);
			assertTrue(g.getVertex(i+1).getVisits() >= lowerLimits[i]);
			totalVisits += g.getVertex(i+1).getVisits();
		}
		assertEquals(totalVisits, 10000);
	}
	
	@Test
	public void testSurfWithJumpRandNew() {
		GraphApplic g = new GraphApplic(seed);
		try {
			g.readWeightedFromFileWSeedAndSetDirected(PATH + "new.txt");
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}

		g.surfWithJump(g.getFirstVertexID(), 10000, 1.0);
		g.print();
		int[] upperLimits = {5000, 4100, 1100};
		int[] lowerLimits = {5000, 3900, 900};
		int totalVisits = 0;
		for (int i = 0; i < upperLimits.length; i++) {
			assertTrue(g.getVertex(i).getVisits() <= upperLimits[i]);
			assertTrue(g.getVertex(i).getVisits() >= lowerLimits[i]);
			totalVisits += g.getVertex(i).getVisits();
		}
		assertEquals(totalVisits, 10000);
	}

	@Test
	public void testSurfWithJumpMixedNew() {
		GraphApplic g = new GraphApplic(seed);
		try {
			g.readWeightedFromFileWSeedAndSetDirected(PATH + "new.txt");
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}

		g.surfWithJump(g.getFirstVertexID(), 10000, 0.5);
		int[] upperLimits = {4500, 3500, 2200};
		int[] lowerLimits = {4350, 3350, 2050};
		for (int i = 0; i < upperLimits.length; i++) {
			assertTrue(g.getVertex(i).getVisits() < upperLimits[i]);
			assertTrue(g.getVertex(i).getVisits() > lowerLimits[i]);
		}
	}

	@Test
	public void testTopNNew() {
		GraphApplic g = new GraphApplic(seed);
		try {
			g.readWeightedFromFileWSeedAndSetDirected(PATH + "new.txt");
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}

		g.surfWithJump(g.getFirstVertexID(), 10000, 0.9);
		int N = 1;
		ArrayList<Integer> visits = g.topN(N);
		Set<Integer> visitsSet = new HashSet<Integer>(visits);
		Integer[] valuesArray = {0};
		Set<Integer> valuesSet = new HashSet<Integer>(Arrays.asList(valuesArray));
		assertTrue(valuesSet.containsAll(visitsSet));
		assertTrue(visitsSet.containsAll(valuesSet));
	}



	@Test
	public void testConvergenceWeightsNew() {
		GraphApplic g = new GraphApplic(seed);
		try {
			g.readWeightedFromFileWSeedAndSetDirected(PATH + "new.txt");
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		g.convergenceWeights(3, 0.9);
		Double[] weights = {0.491, 0.387, 0.122};
		for (int i = 0; i < weights.length; i++)
			assertEquals(g.getVertex(i).getWeight(), weights[i], 0.01);
	}

	@Test
	public void testAverageHittingTimeNew() {
		GraphApplic g = new GraphApplic(seed);
		try {
			g.readWeightedFromFileWSeedAndSetDirected(PATH + "new.txt");
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		Integer v = g.getFirstVertexID();
		Double avgHit = g.averageHittingTime(v, 10000, 10000, 0.9);
		g.setVertexWeights();
		System.out.println("avgHit: " + avgHit);
		assertEquals(avgHit, 2.0, 0.1);
	}

	@Test
	public void testAverageCoverTimeNew() {
		GraphApplic g = new GraphApplic(seed);
		try {
			g.readWeightedFromFileWSeedAndSetDirected(PATH + "new.txt");
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		Double avgCover = g.averageCoverTime(10000, 10000, 0.9);
		System.out.println("avgCover: " + avgCover);
		assertTrue(avgCover < 8.5);
		assertTrue(avgCover > 5.5);
	}
	
	@Test
	public void testBoostVertex() {
		GraphApplic[] g = new GraphApplic[5];
		for (int i = 0; i < 5; i++) {
			g[i] = new GraphApplic(seed);
			try {
				g[i].readWeightedFromFileWSeedAndSetDirected(PATH + "new-four-vertex.txt");
			}
			catch (IOException e) {
				System.out.println("in exception: " + e);
			}
		}
		Integer countThree = 0; // soln should be 3 (occasionally 1)
		for (int i = 0; i < 5; i++) {
			System.out.println(i);
			Integer v = g[i].boostVertex(2, 3); // for new-four-vertex graph
			System.out.println("boosting vertex 2: " + v);
			if (v == 3)
				countThree += 1;
		}
		assertTrue(countThree >= 4);
	}
}