public class ListNode{
    int val;
    ListNode next;

    ListNode(){

    }
    ListNode(int val){this.val = val;}
    ListNode(int val,ListNode next){this.val=val;this.next=next;}

    // 移除链表中的指定元素，设置虚拟头节点，让头节点与其余节点的处理逻辑一致
    ListNode removeElements(ListNode head,int val){
        ListNode dummy = new ListNode();
        dummy.next = head;

        ListNode cur = dummy.next;
        while(cur!=null){
            if(cur.val == val){
                cur.next = cur.next.next;
            }else{
                cur = cur.next;
            }
        }
        return dummy.next;
    }


}