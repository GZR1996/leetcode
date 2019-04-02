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
	
}
