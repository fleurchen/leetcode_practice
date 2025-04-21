import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }

    int search(int[] nums,int target){
        int length = nums.length;
        int left=0,right=length-1;
        while(left<=right){
            int middle=left+(right-left)/2;
            if(nums[middle]>target){
                right=middle-1;
            }else if(nums[middle]<target){
                left=middle+1;
            }else {
                return middle;
            }
        }
        return -1;
    }

    // 快慢指针
    int removeElement(int[] nums,int target){
        int length = nums.length;
        int fastIndex=0,slowIndex=0;
        while(fastIndex<length){
            if(nums[fastIndex]!=target){
                nums[slowIndex] = nums[fastIndex];
                slowIndex++;
            }
            fastIndex++;
        }
        return slowIndex;
    }

    // 双指针 两个指针在两端，不一样的就相加
    int removeElement2(int[] nums,int target){
        int length = nums.length;
        int left=0,right=length-1;
        while(right>=0 && nums[right] == target) right--;
        while(left<=right){
            if(nums[left]==target){
                nums[left]=nums[right];
                right--;
            }
            left++;
            while(right>=0 &&nums[right]==target) right--;
        }
        return left;
    }

    int[] sortedSquares(int[] nums){
        int l=0;
        int r=nums.length-1;
        int[] result=new int[nums.length];
        int index=nums.length-1;
        while(l<=r){
            if(nums[r]*nums[r] > nums[l] *nums[l]){
                result[index--] = nums[r]*nums[r];
                r--;
            }else{
                result[index--] = nums[l]*nums[l];
                l++;
            }
        }
        return result;
    }

    // 长度最小的子数组
    int minSubArrayLen(int[] nums,int s){
        int left=0,right=0,subLen=Integer.MAX_VALUE;
        int sum=0;
        for(;right<nums.length;right++){
            sum+=nums[right];
            if(sum>=s){
                subLen=Math.min(right-left+1,subLen);
                sum=sum-nums[left++];
            }
        }
        return subLen==Integer.MAX_VALUE?0:subLen;
    }

    int getDurationSum(){
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int[] rec = new int[n];
        int[] p = new int[n];

        int preSum = 0;
        for(int i=0;i<n;i++){
            rec[i] = scanner.nextInt();
            preSum+=rec[i];
            p[i]=preSum;
        }

        while(scanner.hasNext()){
            int a = scanner.nextInt();
            int b = scanner.nextInt();

            if(a ==0){
                return p[b];
            }else{
                return p[b]- p[a-1];
            }
        }
        scanner.close();
        return -1;
    }

    int getMinDuration(){
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[][] rec = new int[n][m];

        int sum = 0;
        for(int i =0;i<n;i++){
            for(int j=0;j<m;j++){
                rec[i][j] = scanner.nextInt();
                sum+=rec[i][j];
            }
        }

        // 纵向
        int[] horizontal = new int[n];
        for(int i =0;i<n;i++){
            for(int j=0;j<m;j++){
                horizontal[i]+=rec[i][j];
            }
        }

        //横向
        int[] vertical = new int[m];
        for(int j=0;j<m;j++){
            for(int i=0;i<n;i++){
                vertical[j]+=rec[i][j];
            }
        }

        //横向分割
        int result = Integer.MAX_VALUE;
        int horizontalSum=0;
        for(int i =0;i<n;i++){
            horizontalSum+=horizontal[i];
            result = Math.min(result,(sum-horizontalSum)-horizontalSum);
        }

        int vertialSum=0;
        for(int i =0;i<m;i++){
            vertialSum+=vertical[i];
            result = Math.min(result,(sum-vertialSum)-vertialSum);
        }
        return  result;

    }

    // 遵循左闭右开的原则 不停的螺旋循环
    int[][] generateMatrix(int n){
        int[][] matrix = new int[n][n];
        int startX=0,startY=0;
        int offset=1;
        int loop = 1;
        int count=1;
        int i,j;

        while(loop<=n/2){
            for( j=0;j<n-offset;j++) {
                matrix[startX][j] = count++;
            }
            for(i=0;i<n-offset;i++) {
                    matrix[i][j] = count++;
            }
            for(;j>startY;j--){
                matrix[i][j] = count++;
            }
            for(;i<startX;i--){
                matrix[i][j] = count++;
            }
            startX++;
            startY++;
            offset +=1;
        }

        if(n%2!=0){
            matrix[startX][startY] = count;
        }
        return matrix;
    }

    int[] findDuplicate(int[] a,int[] b){
        Set<Integer> set = new HashSet<>();
        for(int m : a){
            set.add(m);
        }

        List<Integer> result = new ArrayList<>();
        for(int n : b){
            if(set.contains(n)){
                result.add(n);
            }
        }
        return result.stream().mapToInt(i->i).toArray();
    }

    int  getCandy(int[] nums){
        if(nums == null){
            return 0;
        }
        if(nums.length == 1){
            return nums[0];
        }

        int[] dup = new int[nums.length];
        dup[0] = nums[0];
        dup[1] = Math.max(nums[0],nums[1]);
        for(int i=2;i<nums.length;i++){
            dup[i] = Math.max(nums[i]+dup[i-2],dup[i-1]);
        }
        return  dup[nums.length-1];
    }
}