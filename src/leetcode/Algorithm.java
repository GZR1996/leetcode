package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 甘梓润
 */
public class Algorithm {

	public static void main(String[] args) {
		int[][] nums = {{1,3},{2,6},{8,10},{15,18}};
		List<Interval> list = new ArrayList<>();
		list.add(new Interval(1, 3));
		list.add(new Interval(2, 6));
		list.add(new Interval(8, 10));
		list.add(new Interval(15, 18));
		int[][] res = Solution.merge(nums);
		for (int i = 0; i < res.length; i++) {
			for (int j = 0; j < res[j].length; j++) {
				System.out.print(res[i][j] + " ");
			}
			System.out.println();
		}
	}
}
