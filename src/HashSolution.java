import java.util.*;

public class HashSolution {

    // 字母异位词 单独添加一个记录表，记录26个单词的数量，以此判断是否为异位
    public boolean isAnagram(String s, String t) {
        int[] record = new int[26];
        for(int i = 0; i < s.length(); i++) {
            record[s.charAt(i)-'a']++;
        }
        for(int i = 0; i < t.length(); i++) {
            record[t.charAt(i)-'a']--;
        }
        for(int count :record){
            if(count!=0){
                return false;
            }
        }
        return true;
    }

    // 查找多个字符串中常用的字符，即都存在的字符，先以第一个字符串为参照，每次比对出最小的存在数目，根据最后的
    // hash数据获取到存在的常用字符串
    public List<String> commonChars(String[] A){
        List<String> ans = new ArrayList<>();
        int[] hash = new int[26];
        for(int i=0;i<A[0].length();i++){
            hash[A[0].charAt(i)-'a']++;
        }
        for(int i=1;i<A.length;i++){
            int[] hashOther = new int[26];
            for(int j=0;j<A[i].length();j++){
                hashOther[A[i].charAt(j)-'a']++;
            }
            for(int j=0;j<26;j++){
                hash[j] = Math.min(hashOther[j], hash[j]);
            }
        }
        for(int i=0;i<26;i++){
            while(hash[i]!=0){
                char c = (char) (i+'a');
                ans.add(String.valueOf(c));
                hash[i]--;
            }
        }
        return ans;
    }

    // 两个数组的交集
    public int[] intersection(int[] nums1, int[] nums2) {
        if(nums1==null|| nums1.length==0||nums2==null|| nums2.length==0){
            return new int[0];
        }
        Set<Integer> set1 = new HashSet<>();
        Set<Integer> ansSet = new HashSet<>();
        for(int i:nums1){
            set1.add(i);

        }
        for(int i:nums2){
            if(set1.contains(i)){
                ansSet.add(i);
            }
        }
        return ansSet.stream().mapToInt(Integer::intValue).toArray();
    }

    // 两个数组相交，使用hash函数
    public int[] intersection2(int[] nums1, int[] nums2) {
        int[] hash = new int[1002];
        int[] hash2 = new int[1002];
        for(int i=0;i<nums1.length;i++){
            hash[nums1[i]]++;
        }
        for(int i=0;i<nums2.length;i++){
            hash2[nums2[i]]++;
        }
        List<Integer> list1 = new ArrayList<>();
        for(int i=0;i<1002;i++){
            if(hash[i]>0 && hash2[i]>0){
                list1.add(i);
            }
        }
        int[] ans = new int[list1.size()];
        for(int i=0;i<list1.size();i++){
            ans[i] = list1.get(i);
        }
        return ans;
    }

    // 快乐数，正整数的各位分别平方求和，最后结果为1的话即为快乐数
    // 计算各位平方和，判断结果是否重复，如果重复则永远不会等于1
    public boolean isHappy(int n){
        Set<Integer> record = new HashSet<>();
        while(n!=1&&!record.contains(n)){

            record.add(n);
            n = getNumber(n);
        }
        return n == 1;
    }
    public int getNumber(int n){
        int ans = 0;
        while(n>0){
            int temp = n%10;
            ans += temp*temp;
            n=n/10;
        }
        return ans;
    }

    // 两数之和 使用map统计已经计算过，但未匹配的数据，保存数值和对应的索引
    public int[] towSum(int[] nums,int target){
        int[] ans = new int[2];
        if(nums==null||nums.length==0){
            return ans;
        }
        Map<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<nums.length;i++){
            int tmp = target - nums[i];
            if(map.containsKey(tmp)){
                ans[0] = i;
                ans[1] = map.get(tmp);
                break;
            }
            map.put(nums[i],i);
        }
        return ans;
    }

    // 四数之和 二，先计算两个数组的和以及出现的次数，再遍历另外两个的和的负数是否在map中出现，记录次数

    public int fourSumCount(int[] A, int[] B, int[] C, int[] D){
        int ans = 0;
        Map<Integer,Integer> map = new HashMap<>();
        for(int i : A){
            for(int j: B){
                map.put(i+j,map.getOrDefault(i+j,0)+1);
            }
        }
        for(int i:C){
            for(int j:D){
                ans += map.getOrDefault(0-i-j,0);
            }
        }
        return ans;
    }

    // 赎金信 表示从杂志中挑取数字在金信中拼接，杂志中的每个字仅能使用一次
    public boolean canConstruct(String randomNote, String magazine){
        if(randomNote.length() > magazine.length()){
            return false;
        }
        int[] chars = new int[26];

        for(char c : magazine.toCharArray()){
            chars[c-'a']++;
        }
        for(char c : randomNote.toCharArray()){
            chars[c-'a']--;
        }
        for(int i=0;i<26;i++){
            if(chars[i] <0){
                return false;
            }
        }
        return true;
    }

    // 三数之和 要求三个数相加，结果为0，且三个数不能重复
    // 哈希法 将第三个数字存放在哈希表中，在计算完两数之和之后，取反就可以得到第三个数
    public List<List<Integer>> threeSum(int[] nums){
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        for(int i=0;i<nums.length;i++){
            if(nums[i]>0){
                return result;
            }
            if(i>0 && nums[i] ==nums[i-1]){
                continue;
            }

            Set<Integer> set = new HashSet<>();
            for(int j=i+1;j<nums.length;j++){
                if(j>i+2 && nums[j]==nums[j-1] && nums[j-2]==nums[j-1]){
                    continue;
                }
                int target = 0-nums[i]-nums[j];
                if(set.contains(target)){
                    result.add(Arrays.asList(nums[i],nums[j],target));
                    set.remove(target);
                }else{
                    set.add(nums[i]);
                }

            }
        }
        return result;
    }

    // 三数之和，双指针法，固定i指针，设置左右指针，当三数相加大于0，则右指针++，小于0时，则左指针右移
    public List<List<Integer>> threeSum2(int[] nums){
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        for(int i=0;i<nums.length;i++){
            if(nums[i]>0){
                return result;
            }
            if(i>0 && nums[i]==nums[i-1]){
                continue;
            }
            int left = i+1;
            int right = nums.length-1;
            while(left<right){
                int sum = nums[i]+nums[left]+nums[right];
                if(sum>0){
                    right--;
                }else if(sum<0){
                    left++;
                }else {
                    result.add(Arrays.asList(nums[i],nums[left],nums[right]));
                    while(left<right && nums[left]==nums[left+1]) left++;
                    while(left<right && nums[right]==nums[right-1]) right--;
                    right--;
                    left++;
                }
            }
        }
        return result;
    }

    // 四数之和，四个数相加之和等于target，且四个数不能重复
    public List<List<Integer>> fourSum(int[] nums, int target) {

        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        for(int i=0;i<nums.length;i++){
            if(nums[i]>target&&nums[i]>0){
                break;
            }
            if(i>0 && nums[i]==nums[i-1]){
                continue;
            }
            for(int j=i+1;j<nums.length;j++){
                if(nums[j]+nums[i]>target&&nums[i]+nums[j]>0){
                    break;
                }
                if(j>i+2 && nums[j]==nums[j-1]){
                    continue;
                }
                int left = j+1;
                int right = nums.length-1;
                while(left<right){
                    long sum = (long)nums[i]+nums[j]+nums[left]+nums[right];
                    if(sum>target){
                        right--;
                    } else if(sum<target){
                        left++;
                    }else{
                        result.add(Arrays.asList(nums[i],nums[j],nums[left],nums[right]));
                        while(left<right && nums[left]==nums[left+1]) left++;
                        while(left<right && nums[right]==nums[right-1]) right--;
                        left++;
                        right--;
                    }
                }
            }
        }
        return result;
    }




}
