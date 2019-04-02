package leetcode;

public class Solution {
	
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
	
	public static boolean isValidSudoku(char[][] board) {
		for (int row = 0; row < 9; ++row) {
			boolean[] rows = new boolean[9];
			boolean[] cols = new boolean[9];
			boolean[] cube = new boolean[9];
			
			for (int col = 0; col < 9; col++) {
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
}
