import java.util.*;

public class StackQueue {

    class MyQueue{
        Stack<Integer> stackIn;
        Stack<Integer> stackOut;

        public MyQueue(){
            stackIn = new Stack<>();
            stackOut = new Stack<>();
        }

        public void push(int x){
            stackIn.push(x);
        }
        public int pop(){
            dumpsStackIn();
            return stackOut.pop();

        }
        public int peek(){
            dumpsStackIn();
            return stackOut.peek();
        }

        public boolean isEmpty(){
            return stackIn.isEmpty() && stackOut.isEmpty();
        }

        public void dumpsStackIn(){
            if(stackIn.isEmpty()){
                return;
            }
            while(!stackIn.isEmpty()){
                stackOut.push(stackIn.pop());
            }
        }
    }

    class MyStack{
        Queue<Integer> queue1;
        Queue<Integer> queue2;
        public MyStack(){
            queue1 = new LinkedList<>();
            queue2 = new LinkedList<>();
        }
        public void push(int x){
            queue2.offer(x);
            while(!queue1.isEmpty()){
                queue2.offer(queue1.poll());
            }
            Queue<Integer>  temp = queue1;
            queue1 = queue2;
            queue2 = temp;

        }
        public int pop(){
            return queue1.poll();
        }
        public int top(){
            return queue1.peek();
        }

        public boolean isEmpty(){
            return queue1.isEmpty() ;
        }
    }

    // 有效括号，通过栈的方式，实现匹配
    // 栈空时，但是未遍历完，则非有效括号
    // 遍历完后，看栈是否为空
    // 栈顶的元素没有找到合适的匹配
    public boolean isValid(String s){
        Deque<Character> stack = new LinkedList<>();
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if(c=='('){
                stack.push(')');
            }else if(c=='{'){
                stack.push('}');
            }else if(c=='['){
                stack.push(']');
            }else if(stack.isEmpty() || stack.peek() != c){
                return false;
            }else{
                stack.pop();
            }
        }
        return stack.isEmpty();
    }

    // 删除字符串中所有相邻的重复项
    public String deleteDuplicates(String s){
        ArrayDeque<Character> stack = new ArrayDeque<>();
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if(stack.isEmpty() || stack.peek() !=c){
                stack.push(c);
            }else{
                stack.pop();
            }
        }
        String str = "";
        while(!stack.isEmpty()){
            str = stack.pop()+str;
        }
        return str;
    }

    // 逆波兰表达式的计算，只要有运算符弹出，就将数据计算并弹入栈
    // deque 栈： push pop
    public int evalRPN(String[] tokens){
        Deque<Integer> stack = new LinkedList<>();
        for(String token: tokens){
            if(token.equals("+")){
                stack.push(stack.pop()+stack.pop());
            }else if(token.equals("-")){
                stack.push(-stack.pop()+stack.pop());
            }else if(token.equals("*")){
                stack.push(stack.pop()*stack.pop());
            }else if(token.equals("/")){
                int tmp1 = stack.pop();
                int tmp2 = stack.pop();
                stack.push(tmp2/tmp1);
            }else{
                stack.push(Integer.parseInt(token));
            }
        }
        return stack.pop();
    }

    // 滑动窗口最大值
    // 自制单调队列，出队时比对队头是否相等，相等则出队；入队时比对队列尾部是否小于该值，小于的出队
    // 创建滑动窗口，每次移动去掉队头，增加队尾，取出队首为当前滑动窗口最大值
    // deque 队列 poll add
    class MyQueue1{
        Deque<Integer> queue = new LinkedList<>();
        void poll(int val){
            if(!queue.isEmpty() && val == queue.peek()){
                queue.poll();
            }
        }
        void add(int val){
            while(!queue.isEmpty() && val > queue.getLast()){
                queue.removeLast();
            }
            queue.add(val);
        }
        int peek(){
            return queue.peek();
        }
    }

    // 1 设置返回数组，数组长度为n-k+1
    // 2 遍历第一个滑动窗口，创建单调数组，add 如果最后一个小于待入队数目，移除最后一个
    // 3 弹出前一个滑动窗口的第一个值，弹入一个新的值，让窗口滑动，弹出时比对队头是否为要弹出的值，如果是，则弹出。
    // 4 每次滑动窗口都要在返回数组中增加一个最大值
    public int[] slidingWindowMax(int[] nums,int k){
        if (nums.length<=1){
            return nums;
        }
        int resLen = nums.length - k +1;
        int[] res = new int[resLen];
        int num = 0;
        MyQueue1 myQueue1 = new MyQueue1();
        int[] slidingWindow = new int[k];
        for(int i=0;i<k;i++){
            myQueue1.add(nums[i]);
        }

        res[num++] = myQueue1.peek();
        for(int i = k;i<nums.length;i++){
            myQueue1.poll(nums[i-k]);
            myQueue1.add(nums[i]);
            res[num++] = myQueue1.peek();
        }
        return res;
    }

    // 前k个高频元素
    // 1 记录每个数值出现的频次，使用map记录
    // 2 使用PriorityQueue构建小顶堆，小顶堆数量为k个
    // 3 当顶堆的数量等于k之后，将频次大于堆顶的数据推出，替换为稍大的频次
    // 4 遍历完成后，记录数组从后往前记录弹出的小顶堆，因为最先弹出的是频次最小的。
    public int[] topKFrequent(int[] nums,int k){
        Map<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<nums.length;i++){
            map.put(nums[i],map.getOrDefault(nums[i],0)+1);
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>((pair1,pair2) -> pair1[1]-pair2[1]);
        for(Map.Entry<Integer,Integer> entry:map.entrySet()){
            if(pq.size()<k){
                pq.add(new int[]{entry.getKey(),entry.getValue()});
            }else{
                if(entry.getValue() > pq.peek()[1]){
                    pq.poll();
                    pq.add(new int[]{entry.getKey(),entry.getValue()});
                }
            }
        }
        int[] res = new int[k];
        for(int i=k-1;i>=0;i--){
            res[i]=pq.poll()[0];
        }
        return res;
    }

}
