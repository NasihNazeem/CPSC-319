import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * BinarySearchTree
 */
public class BinarySearchTree {

    private static BinarySearchTree tree = new BinarySearchTree();
    private char operation;
    private String id;
    private String name;
    private String dept;
    private String prgm;
    private String year;
    public Node root;
    private String dpthFile;
    private String brdthFile;
    private PrintWriter output1;
    private PrintWriter output2;

    static class Node {
        public String data;
        public Node left;
        public Node right;


        public Node(String data){
            this.data = data;
            this.left = null;
            this.right = null;
        }


    }


    public BinarySearchTree(){
        this.root = null;
    }

    public void insert(String newData){
        this.root = insert(root, newData);
    }

    public Node insert(Node root, String newData) {
        if( root == null ) {
            root = new Node(newData);
            return root;
            
        }
        else if(root.data.compareTo(newData) >= 0 || root.data.equals(newData)){

            root.left = insert(root.left, newData);
        } else {
            root.right = insert(root.right, newData);
            
        }

        return root;
    }

    public void deleteNode(Node node){
        deleteNode(this.root, node);
    }

    private Node deleteNode(Node root2, Node node) {
        if(root == null){
            System.out.println("There are no nodes to delete.");
            return null;
        } else if (node.data.compareTo(root.data) < 0){
            root.left = deleteNode(root.left,node);
        } else if (node.data.compareTo(root.data) > 0){
            root.right = deleteNode(root.right, node);
        } else if (root.data.equals(node.data)){
            
            if(root.left != null && root.right != null) {
                String lmax = findMaxData(root.left);
                root.data = lmax;
                root.left = deleteNode(root.left, new Node(lmax));
                return root;
            } else if (root.left != null) {
                return root.left;
            } else if (root.right != null) {
                return root.right;
            } else
                return null;
        }
        return root;
    }

    public String findMaxData(Node root) {
        if(root.right != null){
            return findMaxData(root.right);
        } else {
            return root.data;
        }
    }
    

    /**
     * These two functions are meant for Depth-First In-Order Traversal.
     * @throws IOException
     * 
     */
    public void inOrder() throws IOException {
        inOrder(root);
    }

    public void inOrder(Node node) throws IOException {
        if(node != null) {
            inOrder(node.left);
            //System.out.println(node.data);
            writetoDpthFile(node.data);
            inOrder(node.right);
        }
    }

    private void writetoDpthFile(String data) throws IOException {
        output1 = new PrintWriter (new BufferedWriter(new FileWriter(dpthFile, true)));
        output1.println(data);
        output1.flush();

    }

    private void writetoBrdthFile(String data) throws IOException {
        output2 = new PrintWriter (new BufferedWriter(new FileWriter(brdthFile, true)));
        output2.println(data);
        output2.flush();

    }

    private void readOperation() {
        switch(operation){
            case 'I':
                insert(name);
                break;
            case 'D':
                break;
            default:
                break;
        }
    }

    public void breadthFirst() throws IOException {
        int h = treeHeight(root);
        for (int i = 0; i < h; i++) {
            breadthFirstTraversal(root, i);
        }
    }

    public void breadthFirstTraversal(Node node, int level) throws IOException {
        if (node == null) {
            return;
        }

        if (level == 0) {
            writetoBrdthFile(node.data);
        } else {
            breadthFirstTraversal(node.left, level - 1);
            breadthFirstTraversal(node.right, level - 1);
        }
    }

    public int treeHeight(Node root) {
        if (root == null) {
            return 0;
        }
        int leftHeight = treeHeight(root.left);
        int rightHeight = treeHeight(root.right);
        return Math.max(leftHeight, rightHeight) + 1;
    }

    public static void main(String[] args) throws Exception {
        File file = new File(args[0]);
        BufferedReader br = new BufferedReader(new FileReader(file));
        tree.dpthFile = args[1];
        tree.brdthFile = args[2];

        String st;
        

        while((st = br.readLine()) != null){

        tree.operation = st.charAt(0);
        tree.id = st.substring(1,7);
        tree.name = st.substring(8,32).replaceAll("\\s+", "");
        tree.dept = st.substring(33, 37);
        tree.prgm = st.substring(37, 40);
        tree.year = st.substring(41);


        tree.readOperation();
        
        // System.out.println(tree.operation);
        // System.out.println(tree.id);
        // System.out.println(tree.name);
        // System.out.println(tree.dept);
        // System.out.println(tree.prgm);
        // System.out.println(tree.year);
        
        
        }

        //DEPTH FIRST
        tree.inOrder(tree.root);
        
        //BREADTH FIRST 
        tree.breadthFirst();
        
        tree.output1.close();
        tree.output2.close();
        br.close();

        

        //System.out.println(names);
    }

    
    
}

