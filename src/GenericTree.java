import java.util.ArrayList;
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


}
