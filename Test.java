
public class Test {
	static public void main(String[] args){
		boolean[][] topology = {{false, true, false, true, false, true},
								{false, false, false, true, false, true},
								{false, true, false, false, true, false},
								{false, false, true, false, false, false},
								{false, false, false, false, false, true},
								{true, false, false, true, false, false}};
		double[] tfidf = {8.4, 3.9, 7.8, 7.9, 9.1, 1.9};
		PageRank pr = PageRank.createInstance(topology, 0.15);
		double[] pagerank = pr.calcPageRank(0.00001, 10000);
		MDPScoring mdps = MDPScoring.createInstance(topology, tfidf, 0.95, 0.15);
		mdps.update(1000);
		int[] moveTo = mdps.getMoveTo();
		double[] score1 = mdps.score1();
		double[] score2 = mdps.score2();
		System.out.println("Page\ttf-idf\tPageRank\tmove to\tscore1\tscore2");
		for(int i=0; i<tfidf.length; i++){
			System.out.print((char)('A'+i)+"\t");
			System.out.print(tfidf[i]+"\t");
			System.out.print(pagerank[i]+"\t");
			System.out.print((char)('A'+moveTo[i])+"\t");
			System.out.print(score1[i]+"\t");
			System.out.print(score2[i]+"\n");
		}
	}
}
