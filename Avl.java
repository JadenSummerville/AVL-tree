import javax.management.RuntimeErrorException;
import java.util.Scanner;
/**
 * An Avl is a Mutable AVL tree.
*/
class Avl{
    private Node node;
    private final boolean DEBUG = true;
    // CheckRep methods
    private void checkRep(){
        if(node == null || !DEBUG){
            return;
        }
        if(testInOrder(node) == null) {
            throw new RuntimeErrorException(null);
        }
        if(!testHeight(node)){
            throw new RuntimeErrorException(null);
        }
        if(!testBalanced(node)){
            throw new RuntimeErrorException(null);
        }
    }
    /**
     * See if the node 'root' forms a binary search tree.
     * Iff it does return an integer array of length 2 containing the max (at [1]) and min
     * (at [0]) key values, otherwise return null;
     * 
     * @param root root node of tree to check (can be subtree).
     * @requires 'root' != null and 'root' forms a tree.
     * @return null if it is not in order, an int array of the max and min key values of length 2 with the max being at [1]
     * and the min being at [0]. Return null if the tree is not balanced
     * 
     * O(n)
    */
    private static Integer[] testInOrder(Node root){
        Integer[] goal = new Integer[2];
        if(root.left == null && root.right == null){
            goal[0] = root.key;
            goal[1] = root.key;
            return goal;
        }if(root.left == null){
            Integer[] right = testInOrder(root.right);
            if(right == null){
                return null;
            }
            if(root.key >= right[0]){
                return null;
            }
            right[0] = root.key;
            return right;
        }if(root.right == null){
            Integer[] left = testInOrder(root.left);
            if(left == null){
                return null;
            }
            if(root.key <= left[1]){
                return null;
            }
            left[1] = root.key;
            return left;
        }
        Integer[] left = testInOrder(root.left);
        Integer[] right = testInOrder(root.right);
        if(left == null || right == null){
            return null;
        }
        if(left[1] >= root.key || right[0] <= root.key){
            return null;
        }
        goal[0] = left[0];
        goal[1] = right[1];
        return goal;
    }
    /**
     * Return true iff a tree is balanced.
     * 
     * @param root root node of tree to check (can be subtree).
     * @requires 'root' != null and 'root' forms a tree and heights are correct.
     * @return true iff the tree is balanced (the difference between sub nodes is at most 1 for all nodes).
     * 
     * O(n)
    */
    private static boolean testBalanced(Node root){
        int heightLeft = -1;
        int heightRight = -1;
        if(root.left != null){
            if(!testBalanced(root.left)){
                return false;
            }
            heightLeft = root.left.height;
        }
        if(root.right != null){
            if(!testBalanced(root.right)){
                return false;
            }
            heightRight = root.right.height;
        }
        return Math.abs(heightRight - heightLeft) < 2;
    }
    /**
     * Return true iff heights are correct.
     * 
     * @param root root node of tree to check (can be subtree).
     * @requires 'root' != null and 'root' forms a tree.
     * @return true iff 'root' forms a tree of correct heights.
     * 
     * O(n)
    */
    private static boolean testHeight(Node root){
        int heightLeft = -1;
        int heightRight = -1;
        if(root.left != null){
            if(!testHeight(root.left)){
                return false;
            }
            heightLeft = root.left.height;
        }
        if(root.right != null){
            if(!testHeight(root.right)){
                return false;
            }
            heightRight = root.right.height;
        }
        if(heightRight >= root.height){
            return false;
        }
        if(heightLeft >= root.height){
            return false;
        }
        return heightLeft + 1 == root.height || heightRight + 1 == root.height;
    }
    // Main method
    public static void main(String[] args){
        System.out.println("Welcome to my AVL tree!\nThis is a standard AVL tree. Try it out!");
        Scanner scanner = new Scanner(System.in);
        String value;
        Avl avl = new Avl();
        while(true){
            System.out.println("Given a key a value is retrieved.");
            System.out.println("Enter 'insert' to insert a value. Do not enter values or keys yet.");
            System.out.println("Enter 'find' to find a value given a key. Do not enter the key yet.");
            System.out.println("Enter 'clear' to clear all values and keys from the AVL trees.");
            System.out.println("Enter 'STOP' to terminate the program.");
            switch(scanner.nextLine().toLowerCase()){
                case "insert":
                    System.out.println("Enter the value now.");
                    value = scanner.nextLine();
                    System.out.println("Enter the key now.");
                    avl.insert(value, scanner.nextLine());
                    break;
                case "find":
                    System.out.println("Enter the key now.");
                    value = (String)avl.get(scanner.nextLine());
                    if(value == null){
                        System.out.println("Key not entered.");
                        break;
                    }
                    System.out.println(value);
                    break;
                case "clear":
                System.out.println();
                System.out.println("AVL cleared!");
                    avl.clear();
                    break;
                case "quit":
                case "stop":
                    return;
                default:
                System.out.println("Command not recognized.");
                    break;
            }
            System.out.println();
        }
    }
    // Avl interface
    /**
     * Insert 'value' with key 'key' into the AVL tree. If it already exists, replace the old value with 'value'.
     * 
     * @param value value to insert or replace.
     * @param key key to 'value'.
    */
    public void insert(Object value, Object key){
        checkRep();
        Node node = new Node(value, key);
        if(this.node == null){
            this.node = node;
            checkRep();
            return;
        }
        this.node.insert(node);
        checkRep();
    }
    public void display(){
        if(node == null){
            System.out.println("Avl is empty.");
            return;
        }
        for(int i = 0; i != node.height+1; i++){
            node.display(i);
            System.out.println();
        }
    }
    /**
     * Delete entry with key 'key'.
     * 
     * @param key delete node with key 'key'.
     * 
     * O(log(n))
    */
    public void delete(Object key){
        checkRep();
        insert(null, key);
        checkRep();
    }
    /**
     * Get value of entry with key 'key'. If it does not exist return null.
     * 
     * @param key key of entry
     * @return value of entry 'key'. If it does not exist return null.
     * 
     * O(log(n))
    */
    public Object get(Object key){
        checkRep();
        if(node == null){
            return null;
        }
        Object goal = node.find(valueOf(key));
        checkRep();
        return goal;
    }
    /**
     * Return true iff this contains entry with key 'key'.
     * 
     * @param key key of entry.
     * @return true if an entry with key 'key' exists.
     * 
     * O(log(n))
    */
    public boolean contains(Object key){
        checkRep();
        Object val = get(key);
        checkRep();
        return val != null;
    }
    public void clear(){
        checkRep();
        node = null;
        checkRep();
    }
    /**
     * Mutable. Forms a Sub-AVL tree.
    */
    private class Node{
        int key;
        Object value;
        int height;
        Node left;
        Node right;
        /**
         * Create node with value 'value' and key 'key'. Is a single node AVL tree.
         * 
         * @param value value of node.
         * @param key key of node.
        */
        public Node(Object value, Object key){
            this.value = value;
            this.key = valueOf(key);
            left = null;
            right = null;
            height = 0;
        }
        /**
         * Insert node 'node' into the AVL tree or replace node if duplicate key exists.
         * 
         * @param node node to insert or replace.
         * @requires node 'node' is not null, has no children, and forms a valid AVL sub-tree.
        */
        public void insert(Node node){
            if(key == node.key){
                value = node.value;
                return;
            }if(node.key < key){
                if(left == null){
                    left = node;
                    updateHeight();
                    balanceFix();
                    return;
                }
                left.insert(node);
                updateHeight();
                balanceFix();
                return;
            }
            if(right == null){
                    right = node;
                    updateHeight();
                    balanceFix();
                    return;
                }
                right.insert(node);
                updateHeight();
                balanceFix();
                return;
        }
        /**
         * Return value of node in AVL sub-tree with key 'key'. If none exists return null.
         * 
         * @param key key of node to find value of.
         * @return value of node with key 'key' or null if node is not present.
        */
        public Object find(int key){
            if(this.key == key){
                return value;
            }if(this.key > key){
                if(left == null){
                    return null;
                }
                return left.find(key);
            }
            if(right == null){
                return null;
            }
            return right.find(key);
        }
        /**
         * Updates the height of this.
        */
        private void updateHeight(){
            if(left == null && right == null){
                height = 0;
                return;
            }if(right == null){
                height = left.height + 1;
                return;
            }if(left == null){
                height = right.height + 1;
                return;
            }
            height = Math.max(left.height, right.height)+1;
        }
        /***/
        private void balanceFix(){
            int move = findProblemNode(this);
            if(move == 0){
                return;
            }
            Node subTree_1;
            Node subTree_2;
            Node subTree_3;
            Node subTree_4;

            Node node_1;
            Node node_2;
            Node node_3;
            if(move == 2){
                move += 2*(findLongerPath(right))+1;
            }else{
                move += 2*(findLongerPath(left))+1;
            }
            switch(move){
                case 4: // left left
                    subTree_1 = left.left.left;
                    subTree_2 = left.left.right;
                    subTree_3 = left.right;
                    subTree_4 = right;

                    node_1 = new Node(left.left.value, left.left.key);
                    node_2 = new Node(left.value, left.key);
                    node_3 = new Node(value, key);
                    break;
                case 5: // right left
                    subTree_1 = left;
                    subTree_2 = right.left.left;
                    subTree_3 = right.left.right;
                    subTree_4 = right.right;

                    node_1 = new Node(value, key);
                    node_2 = new Node(right.left.value, right.left.key);
                    node_3 = new Node(right.value, right.key);
                    break;
                case 6: // left right
                    subTree_1 = left.left;
                    subTree_2 = left.right.left;
                    subTree_3 = left.right.right;
                    subTree_4 = right;

                    node_1 = new Node(left.value, left.key);
                    node_2 = new Node(left.right.value, left.right.key);
                    node_3 = new Node(value, key);
                    break;
                case 7: // right right
                    subTree_1 = left;
                    subTree_2 = right.left;
                    subTree_3 = right.right.left;
                    subTree_4 = right.right.right;

                    node_1 = new Node(value, key);
                    node_2 = new Node(right.value, right.key);
                    node_3 = new Node(right.right.value, right.right.key);
                    break;
                default:
                    throw new IllegalAccessError();
            }
            // Rotate
            value = node_2.value;
            key = node_2.key;
            left = node_1;
            right = node_3;

            // Add trees
            left.left = subTree_1;
            left.right = subTree_2;
            right.left = subTree_3;
            right.right = subTree_4;

            // Update heights
            left.updateHeight();
            right.updateHeight();
            updateHeight();
        }
        public void display(int place){
            if(place == 0){
                System.out.print(key+":"+value+" ");
                return;
            }
            if(left != null){
                left.display(place-1);
            }
            if(right != null){
                right.display(place-1);
            }
        }
    }
    /**
     * Find the problem node of 'node'. If 'node' has no problem node return 0.
     * If the problem node is on the left return 1. If the problem node is on the tight return 2.
     * 
     * @param node node to find height imbalance in.
     * @requires 'node' != null and 'node' is a valid BS tree and heights are correct.
     * @return 0 if node is balanced. 1 if there is a left imbalance. 2 if there is a right imbalance.
    */
    public static int findProblemNode(Node node){
        int move = 0;
            if(node.right == null && node.left == null){
                return move;
            }
            if(node.right == null){
                if(node.left.height == 0){
                    return move;
                }
                move = 1;
            }else if(node.left == null){
                if(node.right.height == 0){
                    return move;
                }
                move = 2;
            }else{
                int difference = node.right.height - node.left.height;
                if(Math.abs(difference) < 2){
                    return move;
                }
                if(difference > 0){
                    move = 2;
                }else{
                    move = 1;
                }
            }
            return move;
    }
    public static int findLongerPath(Node node){
        if(node.left == null){
            return 2;
        }if(node.right == null){
            return 1;
        }if(node.right.height - node.left.height > 0){
            return 2;
        }
        return 1;
    }
        /**
         * Return hash code of 'obj'. Return 0 if 'obj' == null.
         * 
         * @param obj object to find hash code of.
         * @return hash code of 'obj' or 0 if 'obj' == null.
         * 
         * O(1)
        */
        private static int valueOf(Object obj){
            if(obj == null){
                return 0;
            }
            return obj.hashCode();
        }
     
}