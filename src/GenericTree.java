import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class GenericTree {

    private class Node {
        int data;
        ArrayList<Node> children;

        public Node(int data) {
            this.data = data;
            children = new ArrayList<>();
        }
    }

    private Node root;
    private int size;

    public GenericTree() {
        Scanner scan = new Scanner(System.in);
        this.root = this.takeInput(scan, null, 0);
    }

    // function to make a child for a node
    private Node takeInput(Scanner scan, Node parent, int ithChild) {
        if (parent == null) { // if no parent, then it is the root node
            System.out.println("Enter the data for root node ");
        } else { // else ask for child data for the parent
            System.out.println("Enter the data for " + ithChild + " child of " + parent.data);
        }

        // get data to be stored in the child
        int childData = scan.nextInt();
        System.out.println("Enter the number of children for " + childData);
        // to make this whole subtree, we need number of children of the child (grandchildren)
        int numGrandChildren = scan.nextInt();

        // make a new child node with the data
        Node child = new Node(childData);
        this.size++;

        // for all grandchildren, make new nodes and attach to this child
        for (int i = 0; i < numGrandChildren; i++) {  // for each grandchild
            Node grandChild = this.takeInput(scan, child, i); // call this method
            child.children.add(grandChild); // then add the grandchild to the ArrayList of children.
        }

        return child;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return (this.size() == 0);
    }

    public void display() {
        this.display(this.root);
        System.out.println("*********************");
    }

    private void display(Node node) {
        System.out.print(node.data + " => "); // print data for root node
        for (int i = 0; i < node.children.size(); i++) {
            System.out.print(node.children.get(i).data + ", "); //print data for its children
        }
        System.out.println("END");

        // print data for all grandchildren
        for (int i = 0; i < node.children.size(); i++) {
            this.display(node.children.get(i));
        }
    }

    public int size2() {
        return this.size2(this.root);
    }

    private int size2(Node node) {
        int rv = 0;
        for (int i = 0; i < node.children.size(); i++) {
            int childSize = this.size2(node.children.get(i));
        }

        //increase 1 for the node itself
        rv += 1;

        return rv;
    }

    public int max() {
        return this.max(this.root);
    }

    // method to find the largest data
    private int max(Node node) {
        int max = node.data; // initially assume root is the largest

        for (int i = 0; i < node.children.size(); i++) {
            int childMax = this.max(node.children.get(i)); // call this function for all children of node
            if (childMax > max) {
                max = childMax;
            }
        }

        return max;
    }

    public int height() {
        return this.height(this.root);
    }

    private int height(Node node) {
        int maxChildheight = -1;

        for (int i = 0; i < node.children.size(); i++) {
            int childHeight = this.height(node.children.get(i)); // find the height of each child
            if (childHeight > maxChildheight) {
                maxChildheight = childHeight;
            }
        }

        // add maxChildHeight coming down the recursion stack
        maxChildheight += 1;

        return maxChildheight;
    }

    public boolean find(int data) {
        return this.find(this.root, data);
    }

    private boolean find(Node node, int data) {
        if (node.data == data) { // if found at root node, return true
            return true;
        }
        for (int i = 0; i < node.children.size(); i++) { // look for it in all children
            boolean foundInChild = this.find(node.children.get(i), data); // call find for each child
            if (foundInChild){
                return true;
            }
        }

        return false; // if not found, return false
    }

    private Integer justLarger(int data) { // to find the value just larger than data
        Node jl = this.justLarger(this.root, data);

        if (jl != null) {
            return jl.data;
        } else {
            return null;
        }
    }

    private Node justLarger(Node node, int data) {
        Node rv = null;

        if (node.data > data) { // if larger than data, assign node to rv
            rv = node;
        }
        for (int i = 0; i < node.children.size(); i++) { // run a loop on all children
            Node cjl = this.justLarger(node.children.get(i), data); // call function on all children nodes
            if (cjl == null) { // if childnode data isnt larger, continue
                continue;
            } else { // if it is larger than given data
                if (rv == null) { // and if rv is still null
                    rv = cjl; // then rv gets child node
                } else { // if rv is not null, and has a node
                    if (cjl.data < rv.data) { // then compare and keep the smaller one
                        rv = cjl;             // because we need the justLarger data
                    }
                }
            }
        }

        return rv; // return the node
    }

    public int kthSmallest(int k) {
        int rv = Integer.MIN_VALUE; // set it to the min possible integer

        int counter = 0;
        while (counter < k) {
            rv = justLarger(this.root, rv).data; // with every iteration, rv gets one larger value
            counter++;
        }

        return rv;
    }

    public void mirror() {
        this.mirror(this.root);
    }

    private void mirror(Node node) {
        int left = 0, right = node.children.size() - 1;
        while(left <= right) { // while all nodes are traversed, swap left and right
            Node temp = node.children.get(left);
            node.children.set(left, node.children.get(right));
            node.children.set(right, temp);

            left++;
            right--;
        }
        // run the same mirror function for all children
        for (int i = 0; i < node.children.size(); i++) {
            this.mirror(node.children.get(i));
        }
    }

    public void printAtLevel(int level) {
        this.printAtLevel(this.root, level);
    }

    private void printAtLevel(Node node, int level) { //
        if(level == 0) { // floor hit!
            System.out.println(node.data);
            return;
        }

        for (int i = 0; i < node.children.size(); i++) {
            this.printAtLevel(node.children.get(i), level - 1); // recursive call to all children, at 1 level lower
        }
    }

    public void preOrderTraversal() {
        this.preOrderTraversal(this.root);
    }

    private void preOrderTraversal(Node node) {
        System.out.print(node.data + ", "); // print before recursive call
        for (int i = 0; i < node.children.size(); i++) {
            this.preOrderTraversal(node.children.get(i));
        }
    }

    public void postOrderTraversal() {
        this.postOrderTraversal(this.root);
    }

    private void postOrderTraversal(Node node) {
        for (int i = 0; i < node.children.size(); i++) {
            this.postOrderTraversal(node.children.get(i));
        }
        System.out.print(node.data + ", "); // print after recursive call
    }

    // BFS  - Level Order Traversal in a tree
    // Step 1 - Remove from tree
    // Step 2 - Print
    // Step 3 - Enqueue all its children
    public void levelOrderTraversal() { // iterative
        LinkedList<Node> queue = new LinkedList<>(); // using only addLast and removeFirst
        queue.addLast(this.root);
        while (!queue.isEmpty()) {
            Node rem = queue.removeFirst(); // Step 1
            System.out.print(rem.data + ", "); // Step 2

            for (Node child : rem.children) { // Step 3
                queue.addLast(child); // Step 3
            }
        }
        System.out.println(".");
    }

}
