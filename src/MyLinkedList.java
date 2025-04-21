public class MyLinkedList {

    // 定义listnode
    class ListNode{
        int val;
        ListNode next;
        ListNode(int x){
            this.val = x;
        }
    }
    // 链表长度
    private int size;
    //头节点
    private ListNode dummy;

    MyLinkedList(){
        dummy = new ListNode(0);
        size = 0;
    }

    // index获取的其实是index+1，因为节点是从虚拟节点开始
    public int get(int index){
        if(index<0 || index>=size){
            return -1;
        }
        ListNode cur = dummy;
        for(int i=0; i<=index; i++){
            cur = cur.next;
        }
        return cur.val;
    }

    // 添加头节点，即在虚拟头节点之后插入节点
    public void addHead(int val){
        ListNode newNode = new ListNode(val);
        newNode.next = dummy.next;
        dummy.next = newNode;
        size++;
    }

    // 遍历链表到尾部，在尾部添加节点，并增加size
    public void addTail(int val){
        ListNode newNode = new ListNode(val);
        ListNode cur = dummy;
        while(cur.next!=null){
            cur = cur.next;
        }
        cur.next = newNode;
        size++;
    }

    public void addAtIndex(int index, int val){
        if(index<0 || index>=size){
            return;
        }
        ListNode newNode = new ListNode(val);
        ListNode cur = dummy;
        for(int i=0; i<index; i++){
            cur = cur.next;
        }
        newNode.next = cur.next;
        cur.next = newNode;
        size++;
    }

    public void remove(int index){
        if(index<0 || index>=size){
            return;
        }
        ListNode cur = dummy;
        for(int i=0; i<index; i++){
            cur = cur.next;
        }
        cur.next = cur.next.next;
        size--;
    }


    // 反转链表 双指针法
    public ListNode reverseList(ListNode head){
        ListNode prev = null;
        ListNode cur = head;
        ListNode temp = null;

        while(cur!=null){
            temp = cur.next;
            cur.next = prev;
            prev = cur;
            cur = temp;
        }
        return prev;
    }

    // 反转链表 递归法
    public ListNode reverseList2(ListNode head){
        return reverse(null,head);
    }
    ListNode reverse(ListNode prev,ListNode cur){
        if(cur==null){ return prev;}
        ListNode temp = cur.next;
        cur.next = prev;
        // 此处和双指针法的第二次赋值类似
        return reverse(cur,temp);
    }

    // 两两交换链表节点 指针法
    public ListNode swapPairs(ListNode head){
        ListNode dummyHead = new ListNode(-1);
        dummyHead.next = head;
        ListNode cur= dummyHead;
        ListNode firstNode;
        ListNode secondNode;
        ListNode temp;

        while(cur.next!=null && cur.next.next!=null){
            temp = cur.next.next.next;
            firstNode = cur.next;
            secondNode = cur.next.next;
            cur.next = secondNode;
            secondNode.next = firstNode;
            firstNode.next = temp;
            cur = firstNode;
        }
        return dummyHead.next;

    }

    // 两两交换链表节点 递归法
    public ListNode swapPairs2(ListNode head){
        if(head==null||head.next==null){
            return head;
        }
        ListNode next = head.next;
        ListNode newNode = swapPairs(next.next);
        next.next = head;
        head.next = newNode;

        return next;
    }


    // 删除链表倒数第n个节点 使用双指针法，将两个指针的距离变为n+1，则最后的指针指向倒数第n+1个方便删除
    public ListNode removeNthFromEnd(ListNode head,int n){
        // 设置虚拟节点
        ListNode dummyHead = new ListNode(-1);
        dummyHead.next = head;
        ListNode firstIndex = dummyHead;
        ListNode secondIndex = dummyHead;

        // 第一个指针移动n+1次
        for(int i=0;i<=n;i++){
            firstIndex = firstIndex.next;
        }

        // 第一个节点移动到空节点
        while(firstIndex!=null){

            secondIndex = secondIndex.next;
            firstIndex = firstIndex.next;
        }
        // 要删除的节点不为null
        if(secondIndex.next!=null){
            secondIndex.next = secondIndex.next.next;
        }

        return dummyHead.next;
    }

    // 链表的相交
    // 方法1 比较两个链表的长度，将长链表的指针移动到与短链表的指针平齐（即剩下的一样多），然后便利链表，找出相交点
    public ListNode getIntersectionNode(ListNode headA,ListNode headB){
        int lenA=0,lenB=0;
        ListNode curA = headA;
        ListNode curB = headB;
        // 链表长度
        while(curA!=null){
            lenA++;
            curA = curA.next;
        }
        while(curB!=null){
            lenB++;
            curB = curB.next;
        }
        curA = headA;
        curB = headB;
        // 假设A表最长
        if(lenB>lenA){
            ListNode temp = curA;
            int tmpLen = lenA;
            lenA = lenB;
            curA = curB;
            lenB = tmpLen;
            curB = temp;
        }

        // 偏移多余的长度
        int n = lenA-lenB;
        for(int i=0;i<n;i++){
            curA = curA.next;
        }
        // 比对长度
        while(curA!=null){
            if(curA==curB){
                return curA;
            }
            curA = curA.next;
            curB = curB.next;
        }

        return null;


    }
    // 方法2 让两个链表合并，走一样的路程，当两链表相遇时，则为交点，若没相遇，则均走完全程，无交点
    public ListNode getIntersectionNode2(ListNode headA,ListNode headB){
        ListNode p1 = headA;
        ListNode p2 = headB;
        while(p1!=p2){
            if(p1==null) p1=headB;
            else p1=p1.next;
            if(p2==null) p2=headA;
            else p2=p2.next;
        }
        return p1;
    }

    // 环形链表，确定链表是否有环，并且找到环形入口
    public ListNode detectCycle(ListNode head){
        ListNode fastIndex = head;
        ListNode slowIndex = head;
        // 定义快慢指针，慢指针步长为1，快指针步长为2
        while(fastIndex!=null && fastIndex.next!=null){
            fastIndex=fastIndex.next.next;
            slowIndex=slowIndex.next;
            // 快慢指针相遇的话，表明有环
            if(fastIndex==slowIndex){
                ListNode index2 = fastIndex;
                ListNode index1 = head;
                // 一个指针指向表头，一个指针指向相遇点，当两个指针相等的时候，表明是环的入口
                while(index1!=index2){
                    index1=index1.next;
                    index2=index2.next;
                }
                return index1;
            }
        }
        return null;
    }

}
