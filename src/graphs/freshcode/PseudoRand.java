package graphs.freshcode;

import java.util.Random;

public class PseudoRand {

	Random rnd;
	
	public PseudoRand(long seedval) {
		rnd = new Random(seedval);
	}
	
	public double genPseudoRandDouble() {
		return rnd.nextDouble();
	}
	
	public static void main(String[] args) {
		
		PseudoRand p = new PseudoRand(1);
		
		for (int i = 0; i < 5; i++)
			System.out.println(p.genPseudoRandDouble());
	
	}
}
