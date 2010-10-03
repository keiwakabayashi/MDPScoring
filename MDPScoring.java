
public class MDPScoring {
	private int nodeNum;
	private boolean[][] topology;
	private double[] tfidf;
	private double beta;
	private double upsilon;
	private double[] V;
	private double[][] W;
	static public MDPScoring createInstance(boolean[][] topology, double[] tfidf, double beta, double upsilon){
		return new MDPScoring(topology, tfidf, beta, upsilon);
	}
	public MDPScoring(boolean[][] topology, double[] tfidf, double beta, double upsilon){
		this.topology = topology;
		this.tfidf = tfidf;
		this.beta = beta;
		this.upsilon = upsilon;
		nodeNum = tfidf.length;
		V = new double[nodeNum];
		W = new double[nodeNum][nodeNum];
		for(int i=0; i<nodeNum; i++){
			V[i] = tfidf[i];
		}
	}
	public void update(int iteration){
		for(int it=0; it<iteration; it++){
			//update T
			double T = 0.0;
			for(int i=0; i<nodeNum; i++){
				T += V[i];
			}
			//update W
			for(int i=0; i<nodeNum; i++){
				for(int a=0; a<nodeNum; a++){
					W[i][a] = tfidf[i] + beta*(1.0-upsilon)*V[a] + beta*upsilon*T/nodeNum;
				}
			}
			//update V
			for(int i=0; i<nodeNum; i++){
				V[i] = W[i][maxW(i)];
			}
		}
	}
	private int maxW(int i){
		int maxW = 0;
		double maxWValue = 0.0;
		for(int a=0; a<nodeNum; a++){
			if( topology[i][a] == true ){
				if( W[i][a] > maxWValue ){
					maxWValue = W[i][a];
					maxW = a;
				}
			}
		}
		return maxW;
	}
	public int[] getMoveTo(){
		int[] moveTo = new int[nodeNum];
		for(int i=0; i<nodeNum; i++){
			moveTo[i] = maxW(i);
		}
		return moveTo;
	}
	public double[] score1(){
		return V;
	}
	public double[] score2(){
		int[] optMove = getMoveTo();
		double[][] P = new double[nodeNum][nodeNum];
		for(int i=0; i<nodeNum; i++){
			for(int j=0; j<nodeNum; j++){
				if( optMove[i] == j ){
					P[i][j] = upsilon/nodeNum + (1-upsilon);
				}
				else{
					P[i][j] = upsilon/nodeNum;
				}
			}
		}
		PageRank pr = PageRank.createInstance(P);
		return pr.calcPageRank(0.00001, 100000);
	}
}
