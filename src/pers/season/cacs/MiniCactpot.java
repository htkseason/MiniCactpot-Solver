package pers.season.cacs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MiniCactpot {

	// rows, cols
	private int[] grids;
	private int size;
	private int totalPoses;
	private HashMap<Integer, Integer>[] rowCounter;
	private HashMap<Integer, Integer>[] colCounter;
	private HashMap<Integer, Integer> ltCounter; // left top
	private HashMap<Integer, Integer> rtCounter; // right top

	public MiniCactpot(int size) {
		grids = new int[size * size];
		this.size = size;
		rowCounter = new HashMap[size];
		colCounter = new HashMap[size];
		for (int i = 0; i < rowCounter.length; i++)
			rowCounter[i] = new HashMap<Integer, Integer>();
		for (int i = 0; i < colCounter.length; i++)
			colCounter[i] = new HashMap<Integer, Integer>();
		ltCounter = new HashMap<Integer, Integer>();
		rtCounter = new HashMap<Integer, Integer>();
	}

	public void put(int row, int col, int val) {
		grids[row * size + col] = val;
	}

	public Expectation calcResult() {
		totalPoses = 0;
		for (int i = 0; i < rowCounter.length; i++)
			rowCounter[i].clear();
		for (int i = 0; i < colCounter.length; i++)
			colCounter[i].clear();
		ltCounter.clear();
		rtCounter.clear();
		LinkedList<Integer> nums = new LinkedList<Integer>();
		for (int i = 1; i <= size * size; i++) {
			boolean contain = false;
			for (int t = 0; t < grids.length; t++) {
				if (grids[t] == i)
					contain = true;
			}
			if (!contain)
				nums.addLast(i);
		}
		recurser(nums, grids.clone());
		System.out.println(totalPoses + " possibilities simulated.");
		return new Expectation(totalPoses, rowCounter, colCounter, ltCounter, rtCounter);
	}

	private void recurser(LinkedList<Integer> nums, int[] grids) {
		if (nums.isEmpty()) {
			charge(grids);
			// print(grids);
			totalPoses++;
			return;
		}
		for (int r = 0; r < nums.size(); r++) {
			int val = nums.pollFirst();
			int index = -1;
			for (int i = 0; i < grids.length; i++)
				if (grids[i] == 0) {
					index = i;
					break;
				}
			if (index == -1) {
				nums.addFirst(val);
				charge(grids);
				// print(grids);
				totalPoses++;
				return;
			}
			grids[index] = val;
			recurser(nums, grids);
			nums.addLast(val);
			grids[index] = 0;
		}
	}

	private void charge(int[] grids) {
		for (int i = 0; i < size; i++) {
			int sum = 0;
			for (int x = 0; x < size; x++)
				sum += grids[i * size + x];
			addResultToMap(rowCounter[i], sum);
		}

		for (int i = 0; i < size; i++) {
			int sum = 0;
			for (int y = 0; y < size; y++)
				sum += grids[y * size + i];
			addResultToMap(colCounter[i], sum);
		}

		int ltsum = 0, rtsum = 0;
		for (int i = 0; i < size; i++) {
			ltsum += grids[i * size + i];
			rtsum += grids[i * size + size - 1 - i];
		}
		addResultToMap(ltCounter, ltsum);
		addResultToMap(rtCounter, rtsum);
	}

	public void print(int[] grids) {
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++)
				System.out.print(grids[y * size + x] + "\t");
			System.out.println();
		}
		System.out.println();
	}

	private void addResultToMap(HashMap<Integer, Integer> map, Integer val) {
		if (map.get(val) != null)
			map.put(val, map.get(val) + 1);
		else
			map.put(val, 1);
	}

}
