public class DoubleLinkedList {

    class LinkedListNode{
        int val;
        LinkedListNode pre,next;
        LinkedListNode(int val){
            this.val = val;
        }
    }

    int size;
    // 虚拟头节点和尾节点
    LinkedListNode head,tail;
    public DoubleLinkedList(){
        this.size = 0;
        this.head = new LinkedListNode(0);
        this.tail = new LinkedListNode(0);
        this.head.next = this.tail;
        this.tail.pre = this.head;
    }

    public int get(int index){
        if(index<0||index>=size){
            return -1;
        }
        LinkedListNode cur = head;
        if(index >= size/2){
            cur = tail;
            for(int i=0;i<size-index;i++){
                cur = cur.pre;
            }
        }else{
            for(int i=0;i<=index;i++){
                cur = cur.next;
            }
        }
        return cur.val;
    }

    public void add(int index,int val){
        if(index<0 || index>size){
            return;
        }
        LinkedListNode cur = head;
        LinkedListNode newNode = new LinkedListNode(val);
        if(index >= size/2){
            for(int i=0;i<size-index-1;i++){
                cur = cur.pre;
            }
            newNode.next = cur;
            newNode.pre = cur.pre;
            cur.pre.next = newNode;
            cur.pre = newNode;
            size++;
        }else{
            for(int i=0;i<index;i++){
                cur = cur.next;
            }
            newNode.pre = cur;
            newNode.next = cur.next;
            cur.next.pre = newNode;
            cur.next = newNode;
            size++;
        }

    }

    public void remove(int index){
        if(index<0||index>=size){
            return;
        }
        LinkedListNode cur = head;
        if(index >= size/2){
            for(int i=0;i<size-index;i++){
                cur = cur.pre;
            }

        }else{
            for(int i=0;i<=index;i++){
                cur = cur.next;
            }

        }
        cur.pre.next = cur.next;
        cur.next.pre = cur.pre;
        size--;
    }
}
