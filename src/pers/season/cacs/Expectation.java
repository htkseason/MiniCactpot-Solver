package pers.season.cacs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

public class Expectation {

	public class BenefitPossibility {
		public int benefit;
		public double possibility;

		public BenefitPossibility(int benefit, double possibility) {
			this.benefit = benefit;
			this.possibility = possibility;
		}

		@Override
		public String toString() {
			return benefit + ",   " + String.format("%.2f", possibility * 100) +"%";
		}

	}

	public List<BenefitPossibility>[] rowResult;
	public List<BenefitPossibility>[] colResult;
	public List<BenefitPossibility> ltResult; // left top
	public List<BenefitPossibility> rtResult; // right top
	public final int totalPoses;

	public Expectation(int totalPoses, HashMap<Integer, Integer>[] rowCounter, HashMap<Integer, Integer>[] colCounter,
			HashMap<Integer, Integer> ltCounter, HashMap<Integer, Integer> rtCounter) {
		this.totalPoses = totalPoses;
		rowResult = new List[rowCounter.length];
		colResult = new List[colCounter.length];
		for (int i = 0; i < rowResult.length; i++) {
			rowResult[i] = new ArrayList<BenefitPossibility>();
			addUp(rowCounter[i], rowResult[i]);
		}
		for (int i = 0; i < colResult.length; i++) {
			colResult[i] = new ArrayList<BenefitPossibility>();
			addUp(colCounter[i], colResult[i]);
		}
		ltResult = new ArrayList<BenefitPossibility>();
		addUp(ltCounter, ltResult);
		rtResult = new ArrayList<BenefitPossibility>();
		addUp(rtCounter, rtResult);
	}

	private void addUp(HashMap<Integer, Integer> map, List<BenefitPossibility> list) {
		for (Entry<Integer, Integer> entry : map.entrySet()) {
			if (entry.getKey() > Rule.scores.length || entry.getKey() < 0)
				continue;
			int benefit = Rule.scores[entry.getKey()];
			double possibility = (double) entry.getValue() / totalPoses;
			list.add(new BenefitPossibility(benefit, possibility));
		}
		Collections.sort(list, new Comparator<BenefitPossibility>() {
			@Override
			public int compare(BenefitPossibility o1, BenefitPossibility o2) {
				return Double.compare(o2.possibility, o1.possibility);
			}
		});
	}

}
