import java.util.*;

public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(){}
    TreeNode(int val){
        this.val = val;
    }
    TreeNode(int val,TreeNode left,TreeNode right){
        this.val = val;
        this.right = right;
        this.left = left;
    }

    // 前序遍历递归法
    List<Integer> preOrderTraversal(TreeNode root){
        List<Integer> resultList = new ArrayList<>();
        preOrder(root,resultList);
        return resultList;
    }

    void preOrder(TreeNode root,List<Integer> list){
        if(root == null) return;
        list.add(root.val);
        preOrder(root.left, list);
        preOrder(root.right,list);
    }

    // 前序遍历迭代法 中左右  入栈顺序：中右左
    List<Integer> preOrder1(TreeNode root){
        List<Integer> list = new ArrayList<>();
        if(root == null){
            return list;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while(!stack.isEmpty()){
            TreeNode cur = stack.pop();
            list.add(cur.val);
            if(cur.right!=null){
                stack.push(cur.right);
            }
            if(cur.left!=null){
                stack.push(cur.left);
            }
        }
        return list;
    }

    // 前序遍历空指针法，这个迭代方法可以给后序和中序使用，但是注意调整入栈顺序，是和遍历顺序反着的
    // 前序遍历： 右左中
    List<Integer> preOrder3(TreeNode node){
        if(node ==null){
            return new ArrayList<>();
        }
        Stack<TreeNode> stack = new Stack<>();
        List<Integer> list = new ArrayList<>();
        stack.push(node);
        while(!stack.isEmpty()){
            TreeNode cur = stack.peek();
            stack.pop();
            if(cur !=null){
                if(cur.right!=null) stack.push(cur.right);
                if(cur.left!=null) stack.push(cur.left);
                stack.push(cur);
                // 空节点标记，下次如果遍历到null，就表示这个节点下面的节点可以出来了。
                stack.push(null);
            }else{
                stack.pop();
                cur = stack.peek();
                stack.pop();
                list.add(cur.val);
            }
        }
        return list;
    }

    // 中序遍历法
    List<Integer> inOrderTraversal(TreeNode root){
        List<Integer> resultList = new ArrayList<>();
        inOrder(root,resultList);
        return resultList;
    }

    void inOrder(TreeNode root,List<Integer> list){
        if(root == null) return;
        inOrder(root.left,list);
        list.add(root.val);
        inOrder(root.right,list);
    }

    // 中序迭代：左中右，入栈顺序为左右，直到当前指针为空，则出栈并且右指针入栈
    List<Integer> inorder2(TreeNode node){
        if(node == null){
            return new ArrayList<>();
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = node;
        List<Integer> list = new ArrayList<>();

        while(cur != null || !stack.isEmpty()){
            if(cur !=  null){
                stack.push(cur);
                cur = cur.left;
            }else{
                cur = stack.pop();
                list.add(cur.val);
                cur = cur.right;
            }
        }
        return list;
    }


    // 后序遍历法
    List<Integer> postOrderTraversal(TreeNode root){
        List<Integer> resultList = new ArrayList<>();
        postOrder(root,resultList);
        return resultList;
    }

    void postOrder(TreeNode root,List<Integer> list){
        if(root == null) return;
        postOrder(root.left,list);
        postOrder(root.right,list);
        list.add(root.val);
    }

    // 后序遍历：左右中 利用前序遍历中左右调整为中右左，之后反序即可
    List<Integer> postOrder2(TreeNode node){
        if(node == null){
            return new ArrayList<>();
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = node;
        List<Integer> list = new ArrayList<>();
        stack.push(cur);
        if(!stack.isEmpty()){
            cur = stack.pop();
            list.add(cur.val);
            if(cur.left!=null){
                stack.push(cur.left);
            }
            if(cur.right!=null){
                stack.push(cur.right);
            }
        }
        Collections.reverse(list);
        return list;
     }

     // 层序遍历，利用队列，一层队列弹出一次，并将左右队列入队，进行下一层遍历
    List<List<Integer>> resList = new ArrayList<List<Integer>>();
    public List<List<Integer>> levelOrder(TreeNode root){
        levelOrderFunc(root);
        //levelOrderRe(root,0);
        return resList;
    }

    void levelOrderFunc(TreeNode root){
        if(root == null) return;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            int len = queue.size();
            List<Integer> item = new ArrayList<>();

            while(len>0){
                TreeNode node = queue.poll();
                item.add(node.val);
                if(node.left!=null) queue.offer(node.left);
                if(node.right!=null) queue.offer(node.right);
                len--;
            }
            resList.add(item);
        }

    }

    // 递归方式进行层序遍历
    void levelOrderRe(TreeNode root,int deep){
        if(root == null) return;
        deep++;

        if(resList.size() < deep){
            List<Integer> itemList = new ArrayList<>();
            resList.add(itemList);
        }
        resList.get(deep-1).add(root.val);

        levelOrderRe(root.left,deep);
        levelOrderRe(root.right,deep);
    }

    // 翻转二叉树
    // 利用前序遍历翻转
    void swap(TreeNode node){
        TreeNode temp = node.left;
        node.left = node.right;
        node.right = temp;
    }

    // 递归方式实现反转，注意递归三要素
    // 1 方法参数
    // 2 退出机制
    // 3 单个节点的操作逻辑
    void inverseTree(TreeNode root){
        if(root == null) return;
        swap(root);
        inverseTree(root.left);
        inverseTree(root.right);
     }

     // 基于前序遍历：栈操作为中右左，先出栈交换位置后入栈右左节点
     void inverseTree2(TreeNode root){
        if(root == null){
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while(!stack.isEmpty()){
            TreeNode cur = stack.peek();
            stack.pop();
            swap(cur);
            if(cur.right!=null) stack.push(cur.right);
            if(cur.left!=null) stack.push(cur.left);
        }


     }
}
