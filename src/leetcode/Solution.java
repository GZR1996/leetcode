package leetcode;

import java.util.*;

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
	 * 
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
	 * 
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
	
	/**
	 * LeetCode 43
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static String multiply(String num1, String num2) {
		if (num1.equals("0") || num2.equals("0")) {
			return "0";
		}
		
		char zero = '0';
		int carry = 0;
		int num = 0;
		char[] res = new char[num1.length()+num2.length()];
		for (int i = 0; i < res.length; i++) {
			res[i] = '0';
		}
		
		for (int i = num2.length()-1, k = 1; i >= 0; i--, k++) {
			int index = res.length - k;
			int n2 = num2.charAt(i) - zero;
			for (int j = num1.length()-1; j >= 0; j--, index--) {
				int n1 = num1.charAt(j) - zero;
				n1 *= n2;
				num = num + n1 % 10 + carry;
				res[index] += (char) (num % 10);
				
				if (res[index] > '9') {
					res[index] = (char) (res[index] - ':' + '0');
					carry = 1 + n1 / 10 + num / 10;
				} else {
					carry = n1 / 10 + num / 10;
				}
				
				num = 0;
			}
			res[index] += (char)carry;
			carry = 0;
		}
		
		String result = new String(res); 
		if (res[0] == '0') {
			return result.substring(1);
		}
		else {
			return result;
		}
	}

	/**
	 * LeetCode 46
	 * @param nums
	 * @return
	 */
	public static List<List<Integer>> permute(int[] nums) {
		List<List<Integer>> res = new ArrayList<>();
		putElement(res, new ArrayList<>(), nums);
		return res;
	}

	/**
	 * LeetCode 46
	 * @param list
	 * @param element
	 * @param nums
	 */
	private static void putElement(List<List<Integer>> list, List<Integer> element, int[] nums) {
		if (element.size() == nums.length) {
			list.add(new ArrayList<>(element));
		} else {
			for (int i = 0; i < nums.length; i++) {
				if (element.contains(nums[i])) {
					continue;
				}
				element.add(nums[i]);
				putElement(list, element, nums);
				element.remove(element.size()-1);
			}
		}
	}

	/**
	 * LeetCode 47
	 * @param nums
	 * @return
	 */
	public static List<List<Integer>> permuteUnique(int[] nums) {
		List<List<Integer>> res = new ArrayList<>();
		// 要排序，才能配合i-1和i确定重复值
		Arrays.sort(nums);
		putElementUnique(res, new ArrayList<>(), nums, new boolean[nums.length]);
		return res;
	}

	/**
	 * LeetCode 47
	 * @param list
	 * @param element
	 * @param nums
	 * @param isUsed
	 */
	private static void putElementUnique(List<List<Integer>> list, List<Integer> element, int[] nums, boolean[] isUsed) {
		if (element.size() == nums.length) {
			list.add(new ArrayList<>(element));
		} else {
			for (int i = 0; i < nums.length; i++) {
				if(isUsed[i] || i > 0 && nums[i] == nums[i-1] && !isUsed[i - 1]) {
					continue;
				}
				element.add(nums[i]);
				isUsed[i] = true;
				putElementUnique(list, element, nums, isUsed);
				element.remove(element.size()-1);
				isUsed[i] = false;
			}
		}
	}
	
	/**
	 * LeetCode 53 Easy
	 * this problem was discussed by Jon Bentley (Sep. 1984 Vol. 27 No. 9 Communications of the ACM P885)
	 * the paragraph below was copied from his paper (with a little modifications) algorithm that operates on arrays: 
	 * it starts at the left end (element A[1]) and scans through to the right end (element A[n]), keeping track of the maximum sum subvector seen so far. 
	 * The maximum is initially A[0]. 
	 * Suppose we've solved the problem for A[1 .. i - 1]; how can we extend that to A[1 .. i]? 
	 * The maximum sum in the first I elements is either the maximum sum in the first i - 1 elements (which we'll call MaxSoFar), 
	 * or it is that of a subvector that ends in position i (which we'll call MaxEndingHere).
	 * MaxEndingHere is either A[i] plus the previous MaxEndingHere, or just A[i], whichever is larger.
	 * 
	 * 动态规划
	 * 递推公式为：  sum[i] = max(sum[i-1]+num[i], num[i])	// 比较：当前[i-1]最大值+num[i], num[i]
	 * 			f(x) = max(history, sum[i])				// 比较：历史最大值, 当前[i]最大值
	 * @param nums
	 * @return
	 */
	public static int maxSubArray1(int[] nums) {
	    int maxSoFar = nums[0], maxEndingHere = nums[0];
	    for (int i = 1; i < nums.length; ++i){
	    	maxEndingHere = Math.max(maxEndingHere+nums[i], nums[i]);
	    	maxSoFar = Math.max(maxSoFar, maxEndingHere);	
	    }
	    return maxSoFar;
	}
	
	/**
	 * LeetCode 53 Easy
	 *   动态规划
	 * 递推公式为：	maxSubArray(A, i) = maxSubArray(A, i - 1) > 0 ? maxSubArray(A, i - 1) : 0 + A[i]; 
	 * @param nums
	 */
	public int maxSubArray(int[] nums) {
        int n = nums.length;
        //dp[i] means the maximum subarray ending with A[i];
        int[] dp = new int[n];
        dp[0] = nums[0];
        int max = dp[0];
        
        for(int i = 1; i < n; i++){
            dp[i] = nums[i] + (dp[i - 1] > 0 ? dp[i - 1] : 0);
            max = Math.max(max, dp[i]);
        }
        return max;
	}
	
	/**
	 * LeetCode 54
	 * @param matrix
	 * @return
	 */
	public static List<Integer> spiralOrder(int[][] matrix) {
		List<Integer> list = new ArrayList<>();
		if (matrix.length == 0) {
			return list;
		}
		
		int i = 0, j = 0;
		int minI = 1, minJ = 0;
		int maxI = matrix.length, maxJ = matrix[0].length;
		int maxSize = maxI * maxJ;
		
		while (list.size() != maxSize) {
			while (list.size() != maxSize && j < maxJ) {
				list.add(matrix[i][j++]);
			}
			i ++; j --; maxJ --;
			while (list.size() != maxSize && i < maxI) {
				list.add(matrix[i++][j]);
			}
			i --; j --; maxI --;
			while (list.size() != maxSize && j >= minJ) {
				list.add(matrix[i][j--]);
			}
			i --; j ++; minJ ++;
			while (list.size() != maxSize && i >= minI) {
				list.add(matrix[i--][j]);
			}
			i ++; j ++; minI ++;
		}
		return list;
	}
	
	/**
	 * LeetCode 55 middle
	 * Backtracking
	 * This is the inefficient solution where we try every single jump pattern that takes us from the first position to the last. 
	 * We start from the first position and jump to every index that is reachable. 
	 * We repeat the process until last index is reached. When stuck, backtrack.
	 * @param position
	 * @param nums
	 * @return
	 */
	public static boolean canJumpFromPosition(int position, int[] nums) {
        if (position == nums.length - 1) {
            return true;
        }

        int furthestJump = Math.min(position + nums[position], nums.length - 1);
        // old, which is inefficient
        // for (int nextPosition = position + 1; nextPosition <= furthestJump; nextPosition++) {
        // new, this is better, 
        // a，跳远的位置是接近furthestJump值而不是1
        for (int nextPosition = furthestJump; nextPosition > position; nextPosition--) {
            if (canJumpFromPosition(nextPosition, nums)) {
                return true;
            }
        }

        return false;
    }

    public static boolean canJump(int[] nums) {
        return canJumpFromPosition(0, nums);
    }	
	
    /**
     * LeetCode 55 middle
     * Dynamic Programming Top-down
     * Top-down Dynamic Programming can be thought of as optimized backtracking. 
     * It relies on the observation that once we determine that a certain index is good / bad, this result will never change. 
     * This means that we can store the result and not need to recompute it every time.
     * Therefore, for each position in the array, we remember whether the index is good or bad. 
     * Let's call this array memo and let its values be either one of: GOOD, BAD, UNKNOWN. 
     * This technique is called memoization[3].
     * An example of a memoization table for input array nums = [2, 4, 2, 1, 0, 2, 0] can be seen in the diagram below. 
     * We write G for a GOOD position and B for a BAD one. 
     * We can see that we cannot start from indices 2, 3 or 4 and eventually reach last index (6), 
     * but we can do that from indices 0, 1, 5 and (trivially) 6.
     * Index	0	1	2	3	4	5	6
     * nums		2	4	2	1	0	2	0
     * memo		G	G	B	B	B	G	G
     * 1.Initially, all elements of the memo table are UNKNOWN, except for the last one, which is (trivially) GOOD (it can reach itself)
     * 		1.Modify the backtracking algorithm such that the recursive step first checks if the index is known (GOOD / BAD)
     * 		2.If it is known then return True / False
     * 2.Otherwise perform the backtracking steps as before
     * 3.Once we determine the value of the current index, we store it in the memo table
     */
    enum Index {
        GOOD, BAD, UNKNOWN
    }
    
    private static Index[] memo;

    public static boolean canJumpFromPosition2(int position, int[] nums) {
        if (memo[position] != Index.UNKNOWN) {
            return memo[position] == Index.GOOD ? true : false;
        }

        int furthestJump = Math.min(position + nums[position], nums.length - 1);
        for (int nextPosition = position + 1; nextPosition <= furthestJump; nextPosition++) {
            if (canJumpFromPosition2(nextPosition, nums)) {
                memo[position] = Index.GOOD;
                return true;
            }
        }

        memo[position] = Index.BAD;
        return false;
    }

    public static boolean canJump2(int[] nums) {
        memo = new Index[nums.length];
        for (int i = 0; i < memo.length; i++) {
            memo[i] = Index.UNKNOWN;
        }
        memo[memo.length - 1] = Index.GOOD;
        return canJumpFromPosition2(0, nums);
    }

	public static int[][] merge(int[][] intervals) {
    	int count = 0;
    	Arrays.sort(intervals, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o1[0] - o2[0];
			}
		});

    	for (int i = 1; i < intervals.length; i++) {
    		int[] i1 = intervals[i-1];
    		int[] i2 = intervals[i];

    		if (i1[1] > i2[0]) {
				count += 1;
    			i1[1] = i2[1];
    			i2[0] = Integer.MAX_VALUE;
    			i2[1] = Integer.MAX_VALUE;
    			i += 1;
			}
    	}

    	int[][] res = new int[intervals.length-count][2];
    	for (int i = 0, j = 0; i < intervals.length; i++) {
    		if (intervals[i][0] == Integer.MAX_VALUE || intervals[i][1] == Integer.MAX_VALUE) {
    			continue;
			}
    		res[j][0] = intervals[i][0];
    		res[j][1] = intervals[i][1];
    		j += 1;
		}
		return res;
	}
    
    private static void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
