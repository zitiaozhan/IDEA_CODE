package sort.practice;

public class Sort515{
	static void insertSort(int[] nums){
		int i,j,x;
		for(i = 0;i < nums.length-1;i ++){
			x = nums[i+1];
			j = i;
			while(j > -1 && x < nums[j]){
				nums[j+1] = nums[j--];
			}
			nums[j+1] = x;
		}
	}
	
	static void selectSort(int[] nums){
		int i,j,x,temp;
		for(i = 0;i < nums.length-1;i ++){
			x = i;
			for(j = i+1;j < nums.length;j++){
				x = nums[x] > nums[j]? j : x;
			}
			if(x != i){
				temp = nums[i];
				nums[i] = nums[x];
				nums[x] = temp;
			}
		}
	}
	
	static void quickSort(int[] nums,int low,int high){
		if(low<high){
			int i=low,j=high,x=nums[i];
			while(i<j){
				while(i<j&&x<=nums[j]) {
					j--;
				}
				if(i<j){
					nums[i++]=nums[j];
				}
				while(i<j&&x>nums[i]){
					i++;
				}
				if(i<j){
					nums[j--]=nums[i];
				}
			}
			nums[i]=x;
			quickSort(nums,low,i-1);
			quickSort(nums,i+1,high);
		}
	}
	
	public static void main(String[] args){
		int[] nums = {
			22,15,6,43,35,2,84,99,17,3,0,74,11
		};
		//insertSort(nums);
		//selectSort(nums);
		quickSort(nums,0,nums.length-1);
		for(int num : nums){
			System.out.print(num+"    ");
		}
	}
}