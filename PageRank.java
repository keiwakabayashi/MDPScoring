import java.util.*;
public class PageRank {
	int nodeNum;
	double[][] P;
	static public PageRank createInstance(double[][] P){
		return new PageRank(P);
	}
	static public PageRank createInstance(boolean[][] topology, double upsilon){
		return new PageRank(topology, upsilon);
	}
	public PageRank(double[][] P){
		this.P = P;
		nodeNum = P.length;
	}
	public PageRank(boolean[][] topology, double upsilon){
		nodeNum = topology.length;
		P = new double[nodeNum][nodeNum];
		for(int i=0; i<nodeNum; i++){
			int linkNum = 0;
			for(int j=0; j<nodeNum; j++){
				if( topology[i][j] ){
					linkNum++;
				}
			}
			for(int j=0; j<nodeNum; j++){
				if( topology[i][j] ){
					P[i][j] = upsilon/nodeNum + (1.0-upsilon)/linkNum;
				}
				else{
					P[i][j] = upsilon/nodeNum;
				}
			}
		}
	}
	public double[] calcPageRank(double threshold, int maxIteration){
		/*
		 * Iteration of to multiply by P itself and returns diagonal elements
		 */
		double[][] Pn = new double[nodeNum][nodeNum];
		for(int i=0; i<Pn.length; i++){
			Pn[i] = Arrays.copyOf(P[i], P[i].length);
		}
		for(int it=0; it<maxIteration; it++){
			double change = 0.0;
			double[][] product = new double[nodeNum][nodeNum];
			for(int i=0; i<nodeNum; i++){
				for(int j=0; j<nodeNum; j++){
					for(int k=0; k<nodeNum; k++){
						product[i][j] += Pn[i][k] * Pn[k][j];
					}
					change += Math.pow(product[i][j] - Pn[i][j], 2);
				}
			}
			Pn = product;
			if( change <= threshold ){
				break;
			}
		}
		double[] v = new double[nodeNum];
		for(int i=0; i<nodeNum; i++){
			v[i] = Pn[i][i];
		}
		return v;
	}
}
