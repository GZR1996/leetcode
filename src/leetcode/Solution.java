package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author 甘梓润
 */
public class Solution {
	
	/**
	 * LeetCode 35 
	 * @param nums
	 * @param target
	 * @return
	 */
	public static int insertSearch(int[] nums, int target) {		
		int left = 0, right = nums.length - 1, mid = 0;
		while (left <= right) {
			mid = (left + right) / 2;
			if (target == nums[mid]) {
				return mid;
			} else if (target > nums[mid]) {
				left = mid + 1;
			} else if (target < nums[mid]) {
				right = mid - 1;
			}
		}
		
		if (target > nums[mid]) {
			return mid + 1;
		}
		else {
			return mid;
		}
	}
	
	/**
	 * LeetCode 36
	 * @param board
	 * @return
	 */
	public static boolean isValidSudoku(char[][] board) {
		for (int row = 0; row < board.length; ++row) {
			boolean[] rows = new boolean[9];
			boolean[] cols = new boolean[9];
			boolean[] cube = new boolean[9];
			
			for (int col = 0; col < board.length; col++) {
				int crow = 3 * (row / 3) + (col / 3);
		        int ccol = 3 * (row % 3) + (col % 3);
		        
		        if (!isValid(rows, board[row][col]) 
		        		|| !isValid(cols, board[col][row])
		        		|| !isValid(cube, board[crow][ccol])) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * LeetCode 36
	 * @param valids
	 * @param target
	 * @return
	 */
	private static boolean isValid(boolean[] valids, char target) {
		if (target == '.') {
			return true;
		}
		
		int num = Character.getNumericValue(target) - 1;
		if (valids[num]) {
			return false;
		} 
		valids[num] = true;
		return true;
	}
	
	/**
	 * LeetCode 39
	 * @param candidates
	 * @param target
	 * @return
	 */
	public static List<List<Integer>> combinationSum(int[] candidates, int target) {
		List<List<Integer>> res = new ArrayList<>();
		List<Integer> list = new ArrayList<>();
		Arrays.sort(candidates);
		combination(res, list, candidates, target, 0);
		return res;
	}
	
	/**
	 * LeetCode 39
	 * @param res
	 * @param list
	 * @param candidates
	 * @param target
	 * @param index
	 */
	private static void combination(List<List<Integer>> res, List<Integer> list, int[] candidates, int target, int index) {	
		if (target == 0) {
			res.add(new ArrayList<>(list));
			return;
		} else if (target > 0){
			for (int i = index; i < candidates.length && target >= candidates[i]; ++i) {
				list.add(candidates[i]);
				combination(res, list, candidates, target-candidates[i], i);
				list.remove(list.size()-1);
			}
		} 
	}
	
	/**
	 * LeetCode 40
	 * @param candidates
	 * @param target
	 * @return
	 */
	public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
		List<List<Integer>> res = new ArrayList<>();
		List<Integer> list = new ArrayList<>();
		Arrays.sort(candidates);
		combination2(res, list, candidates, target, 0);
		return res;
	}

	/**
	 * LeetCode 40
	 * @param res
	 * @param list
	 * @param candidates
	 * @param target
	 * @param index
	 */
	private static void combination2(List<List<Integer>> res, List<Integer> list, int[] candidates, int target, int index) {	
		if (target == 0) {
			res.add(new ArrayList<>(list));
			return;
		} else if (target > 0){
			for (int i = index; i < candidates.length; ++i) {
				// 规避重复
				if (i > index && candidates[i] == candidates[i-1]) {
					continue;
				}
				list.add(candidates[i]);
				combination2(res, list, candidates, target-candidates[i], i+1);
				list.remove(list.size()-1);
			}
		} 
	}
}
