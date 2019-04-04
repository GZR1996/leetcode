package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
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
	
	/**
	 * LeetCode 41 Hard
	 * The key here is to use swapping to keep constant space and also make use of the length of the array, 
	 * which means there can be at most n positive integers. 
	 * So each time we encounter an valid integer, find its correct position and swap. Otherwise we continue.
	 * 这里是利用数组长度来估算最小正整数的位置
	 * 假定有一个n个数的数组，
	 * 1. 最小正整数最大值为n，例如：n=7， nums={1,3,2,4,5,7,6}，return 7;
	 * 2. 当出现一个nums[i]<=0，最小正整数需要递减1，例如：n=7， nums={1,3,2,4,5,-7,6} return 6;
	 * 3. 当正整数出现间隙时，最小正整数的值会是间隙中最小值
	 * 综合1，2，3，得出解法为统计i滑动的距离来得出最小正整数的值，i初始值为0，分为两步，
	 * 1. 调整数组使得数组正整数num[i] = i+1
	 * 2. 统计num[i] = i+1的个数，得出最小正整数的值
	 * 		由于最小正整数最大值为nums.length，可以假定满足这一假定时，有两种情况，
	 * 		1. i < num.length --> i++ 数组的遍历
	 * 		2. nums[i]=i+1 --> i++, 跳过间隙
	 * @param nums
	 * @return
	 */
	public static int firstMissingPositive(int[] nums) {
        int i = 0;
        while(i < nums.length){
			/**
			 *	不交换情况：	nums[i] = i+1, 调整完成
			 *				nums[i] <= 0, 不为正整数
			 *			    nums[i] > nums.length 超出界限, 例如n=7， nums={1,3,2,4,5,100,6}, 参照假定1，100对最小正整数不影响
			 */
            if(nums[i] == i+1 || nums[i] <= 0 || nums[i] > nums.length) {
            	i++;
            }
            else if(nums[nums[i]-1] != nums[i]) {
            	swap(nums, i, nums[i]-1);
            }
            else {
            	i++;
            }
        }
        i = 0;
        while (i < nums.length && nums[i] == i+1) {
        	i++;
		}
        return i+1;
    }
    
	
    private static void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
