import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DoublePointer {

    // 移除元素，并且返回移除元素后数组的长度，采用双指针法，利用快慢指针
    public int removeElement(int[] nums,int val){
        int slowIndex = 0;
        for(int fastIndex=0;fastIndex<nums.length;fastIndex++){
            if(nums[fastIndex]!=val){
                nums[slowIndex] = nums[fastIndex];
                slowIndex++;
            }
        }
        return slowIndex;
    }

    // 反转字符串
    public void reverseString(char[] s){
        int l = 0;
        int r = s.length-1;
        while(l<r){
            char temp = s[l];
            s[l] = s[r];
            s[r] = temp;
            l++;
            r--;
        }
    }

    // 将字符串中的数字更改为number，利用双指针法，新扩充一个数组，并且从后往前替换。
    public char[] changeToNumber(char[] s){
        int nums = 0;
        for(int i=0;i<s.length;i++){
            if(Character.isDigit(s[i])){
                nums++;
            }
        }
        int newLen = s.length+nums*5;
        char[] newS = new char[newLen];
        int oldLen = s.length;
        for(int i=oldLen-1,j=newLen-1;i<j;i--,j--){
            if(!Character.isDigit(s[i])){
                newS[j] = s[i];
            }else{
                newS[j] = 'r';
                newS[j-1] = 'e';
                newS[j-2] = 'b';
                newS[j-3] = 'm';
                newS[j-4] = 'u';
                newS[j-5] = 'n';
                j=j-5;
            }
        }
        return newS;
    }

    // 翻转字符串里的单词
    public String reverseWords(String s){
        // 清除空格
        StringBuilder sb = removeSpace(s);
        // 倒转字符串
        reverseWords(sb,0,sb.length()-1);
        // 倒转每个单词
        reverseSingleWords(sb);
        return sb.toString();
    }

    public StringBuilder removeSpace(String s){
        int start = 0;
        int end = s.length()-1;
        while(s.charAt(start)==' '){ start++;}
        while(s.charAt(end)==' '){ end--;}

        StringBuilder sb = new StringBuilder();
        for(int i=start; i<end; i++){
            if(s.charAt(i)!=' ' || sb.charAt(sb.length()-1)!=' '){
                sb.append(s.charAt(i));
            }
        }
        return sb;
    }

    public void reverseWords(StringBuilder sb, int start, int end){
        while(start<end){
            char temp = sb.charAt(start);
            sb.setCharAt(start,sb.charAt(end));
            sb.setCharAt(end,temp);
            start++;
            end--;
        }
    }

    public void reverseSingleWords(StringBuilder sb){
        int start = 0;
        for(int i=0;i< sb.length();i++){
            if(sb.charAt(i)==' '){
                int end = i-1;
                reverseWords(sb,start,end);
                start=i+1;
            }
        }
    }

    // 反转链表，一个指针指向head，一个指向空指针，然后将指针倒置
    public ListNode reverseList(ListNode head){
        ListNode prev = null;
        ListNode cur = head;
        ListNode temp = null;
        while(cur!=null){
            temp = cur;
            cur.next = prev;
            prev = cur;
            cur = temp;
        }
        return prev;
    }

    // 删除链表的倒数第n个节点，双指针方法，
    // 一个指针挪动n+1次，之后两个指针一起移动，那么当第一个指针到达尾部时，
    // 慢指针指向的就是倒数第n+个节点，之后进行删除动作
    public ListNode removeNthFromEnd(ListNode head,int n){
        ListNode dummyHead = new ListNode(-1);
        dummyHead.next = head;
        ListNode fastIndex = dummyHead;
        ListNode slowIndex = dummyHead;

        for(int i=0;i<=n;i++){
            fastIndex = fastIndex.next;
        }

        while(fastIndex!=null){
            fastIndex = fastIndex.next;
            slowIndex = slowIndex.next;
        }

        if(slowIndex!=null){
            slowIndex.next = slowIndex.next.next;
        }
        return dummyHead.next;
    }

    // 链表的交点
    //方法一：移动长链表到和短链表相同的长度，然后比对链表的指针，相等则为交点
    public ListNode getIntersectionNode(ListNode head1,ListNode head2){
        int len1=0,len2=0;
        ListNode cur1 = head1;
        ListNode cur2 = head2;
        while(cur1!=null){
            len1++;
            cur1 = cur1.next;
        }
        while(cur2!=null){
            len2++;
            cur2 = cur2.next;
        }
        cur1 = head1;
        cur2 = head2;
        // 始终让len1最长
        if(len2>len1){
            int tempLen = len2;
            len2 = len1;
            len1 = tempLen;
            ListNode tempNode = cur1;
            cur1 = cur2;
            cur2 = tempNode;
        }
        int gap = len1-len2;
        while(gap>0){
            cur1 = cur1.next;
            gap--;
        }
        while(cur1!=null){
            if(cur1 == cur2){

                return cur1;
            }
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return null;
    }

    //方法二：让两个节点一起移动，如果存在交点，则会在路程中相遇 m+n-k
    public ListNode getIntersectionNode2(ListNode head1,ListNode head2){
        ListNode p1 = head1,p2 = head2;
        while(p1!=p2){
            if(p1==null){
                p1 = head2;
            }else{
                p1 = p1.next;
            }
            if(p2==null){p2=head1;}
            else{
                p2 = p2.next;
            }
        }
        return p1;
    }

    // 环形链表，判断是否有环，利用快慢指针，一个指针移动两步，一个节点移动一步
    // 有环之后将一个节点指向头节点，一个节点指向交点，两个节点移动，当两个节点相等的时候就表示为交点
    public ListNode detectCycle(ListNode head){
        ListNode fastIndex = head;
        ListNode slowIndex = head;
        while(fastIndex!=null && fastIndex.next!=null){
            fastIndex = fastIndex.next.next;
            slowIndex = slowIndex.next;
            if(fastIndex==slowIndex){
                ListNode headIndex = head;
                ListNode interIndex = fastIndex;
                while(interIndex!=headIndex){
                    interIndex = interIndex.next;
                    headIndex = headIndex.next;
                }
                return interIndex;
            }
        }
        return null;
    }

    // 三数之和 排除重复的话，计算上一个数据是否相等，确定第一个数据进行遍历
    // 第二个数据和第三个数据不停的比对，确保数据加起来等于0
    public List<List<Integer>> threeSum(int[] nums){
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);

        for(int i=0;i<nums.length;i++){
            if(nums[i]>0){
                break;
            }
            if(i>0 && nums[i]==nums[i-1]){
                continue;
            }
            int left = i+1;
            int right = nums.length-1;
            while(left<right){
                int sum = nums[i]+nums[left]+nums[right];
                if (sum > 0) {
                    right--;
                }else if(sum<0){
                    left++;
                }else{
                    res.add(Arrays.asList(nums[i],nums[left],nums[right]));
                    while(left<right&& nums[left]==nums[left+1]) left++;
                    while(left<right&& nums[right]==nums[right-1]) right--;
                    right--;
                    left++;
                }
            }
        }
        return res;
    }

    // 四数之和，不重复且等于target
    public List<List<Integer>> fourSum(int[] nums,int target){
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        for(int i=0;i<nums.length;i++){
            //剪枝
            if(nums[i] >target && nums[i]>=0){
                break;
            }
            if(i>0 && nums[i]== nums[i-1] ){
                continue;
            }
            for(int j=i+1;j<nums.length;j++){
                //剪枝
                if(nums[i]+nums[j]>target && nums[i]+nums[j]>=0){
                    break;
                }
                if(j>i+1 &&  nums[j] == nums[j-1]){
                    continue;
                }
                int left = j+1;
                int right = nums.length-1;
                while(left<right){
                    int sum = nums[i]+nums[j]+nums[left]+nums[right];
                    if(sum > target){
                        right--;
                    }else if(sum < target){
                        left++;
                    }else{
                        res.add(Arrays.asList(nums[i],nums[j],nums[left],nums[right]));
                        while(left<right && nums[left]==nums[left+1]) left++;
                        while(left<right && nums[right] == nums[right-1]) right--;
                        left++;
                        right--;
                    }
                }
            }
        }
        return res;
    }



}
