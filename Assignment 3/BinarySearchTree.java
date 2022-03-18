import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;


/**
 * Assignment No: 3
 * Author: Nasih Nazeem
 * Date: March 18, 2022
 */
public class BinarySearchTree {

    private static BinarySearchTree tree = new BinarySearchTree();
    private static Student [] students;
    private static Student student;
    public Node root;
    
    private String dpthFile;
    private String brdthFile;
    private PrintWriter output1;
    private PrintWriter output2;
    private BufferedReader br;

    /**
     * Created a Student class to hold record of ID, Department, 
     * Program and Year so it is easily accessible when writing 
     * to output text files.
     */
    static class Student {
        private char operation;
        private String id;
        private String name;
        private String dept;
        private String prgm;
        private String year;

        public Student(String content){
            this.operation = content.charAt(0);
            this.id = content.substring(1,7);
            this.name = content.substring(8,32).replaceAll("\\s+", "");
            this.dept = content.substring(33, 37);
            this.prgm = content.substring(37, 40);
            this.year = content.substring(41);
        }
        
    }

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

    /**
     * 
     * PROMISES: These next two functions allow for the insertion 
     *           of a node that holds the last name of a student.
     * REQUIRES: Student Last Name
     * @param newData
     */
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

    /**
     * PROMISES: Finds the minimum element to replace the deleted node.
     * REQUIRES: Root Node
     * @param root
     * @return mins
     */
    public static String minimumElement(Node root) {
        String mins = root.data;
        while(root.left != null){
            mins = root.left.data;
            root = root.left;
        }

        return mins;
    }

    /**
     * PROMISES: Removes the requested node from the binary 
     *           search tree, as well as balancing the tree again.
     * REQUIRES: Key data for respective node
     * @param newData
     */
    public void deleteNode(String newData){
        this.root = deleteNode(root, newData);
    }

    public static Node deleteNode(Node root, String value) {
        if (root == null)
            return null;
        if (root.data.compareTo(value) > 0) {
            root.left = deleteNode(root.left, value);
        } else if (root.data.compareTo(value) < 0) {
            root.right = deleteNode(root.right, value);
 
        } else {
            if(root.left == null){
                return root.right;
            } else if (root.right == null)
                return root.left;

            root.data = minimumElement(root.right);

            root.right = deleteNode(root.right, root.data);
        }
        return root;
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

    /**
     * PROMISES: Writes to the first output file, which traverses 
     *           through the search tree in Depth-First In-Order.
     * REQUIRES: Data to write to file
     * @param data
     * @throws IOException
     */
    private void writetoDpthFile(String data) throws IOException {
        try{
            String id = "",dept = "",prgm = "",year = "";
    
            for(int i = 0; i < students.length;i++){
                if(data.equals(students[i].name)) {
                    id = students[i].id;
                    dept = students[i].dept;
                    prgm = students[i].prgm;
                    year = students[i].year;
                }
            }
            
            output1 = new PrintWriter (new BufferedWriter(new FileWriter(dpthFile, true)));
            output1.println(String.format("%-7s \t %-25s \t %-4s \t %-4s \t %-1s", id, data, dept, prgm, year));
            output1.flush();
            }
            catch (IOException e){
                System.out.println("Something went wrong.");
            }
        
        

    }

    /**
     * PROMISES: Writes to the first output file, which traverses through
     *           the search tree in Breadth-First.
     * REQUIRES: Data to write to file
     * @param data
     * @throws IOException
     */
    private void writetoBrdthFile(String data) throws IOException {
        try{
        String id = "",dept = "",prgm = "",year = "";

        for(int i = 0; i < students.length;i++){
            if(data.equals(students[i].name)) {
                id = students[i].id;
                dept = students[i].dept;
                prgm = students[i].prgm;
                year = students[i].year;
            }
        }
        
        output2 = new PrintWriter (new BufferedWriter(new FileWriter(brdthFile, true)));
        output2.println(String.format("%-7s \t %-25s \t %-4s \t %-4s \t %-1s", id, data, dept, prgm, year));
        output2.flush();
        }
        catch (IOException e){
            System.out.println("Something went wrong.");
        }
    }

    /**
     * PROMISES: Reads the first char of each new line on the input file,
     *           to figure out whether the requested operation is an insertion
     *           or a deletion
     * REQUIRES: Nothing requried.
     */
    private void readOperation() {
        switch(student.operation){
            case 'I':
                insert(student.name);
                break;
            case 'D':
                deleteNode(student.name);
                break;
            default:
                System.out.println("No operation code found.");
                break;
        }
    }

    /**
     * PROMISES: Traversal in Breadth-First mode. Finds current height of tree
     *           and visits every node from top to bottom of the tree.
     * REQUIRES: Nothing required.
     * @throws IOException
     */
    public void breadthFirst() throws IOException {
        int h = treeHeight(root);
        for (int i = 0; i < h; i++) {
            breadthFirstTraversal(root, i);
        }
    }

    /**
     * PROMISES: Recursive function that visits all nodes by level of the tree 
     *           from top-to-bottom.
     * REQUIRES: Current node, current level
     * @param node
     * @param level
     * @throws IOException
     */
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

    /**
     * PROMISES: Figures out the height of the tree by 
     *           referencing the root node.
     * REQUIRES: Root node required.
     * @param root
     * @return
     */
    public int treeHeight(Node root) {
        if (root == null) {
            return 0;
        }
        int leftHeight = treeHeight(root.left);
        int rightHeight = treeHeight(root.right);
        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * PROMISES: Returns the amount of lines in the file being read.
     * REQUIRES: File name required.
     * @param fileName
     * @return
     */
    public static int countLineNumberReader(String fileName) {

        File file = new File(fileName);
  
        int lines = 0;
  
        try (LineNumberReader lnr = new LineNumberReader(new FileReader(file))) {
  
            while (lnr.readLine() != null) ;
  
            lines = lnr.getLineNumber();
  
        } catch (IOException e) {
            e.printStackTrace();
        }
  
        return lines;
  
    }

    public static void main(String[] args) throws IOException {

        File file = new File(args[0]);
        tree.dpthFile = args[1];
        tree.brdthFile = args[2];
        String st;


        try {
            tree.br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }

        

        int lines = countLineNumberReader(args[0]), i = 0;
        students = new Student[lines];

        // POPULATING STUDENT ARRAY
        try {
            while((st = tree.br.readLine()) != null){
            student = new Student(st);
            students[i] = student;


            tree.readOperation();
            i++;
            }
        } catch (IOException e) {
            System.out.println("Something went wrong.");
            e.printStackTrace();
        }

        //DEPTH FIRST
        tree.inOrder(tree.root);
        
        //BREADTH FIRST 
        tree.breadthFirst();
        
        // CLOSE ALL BUFFEREDREADER AND FILEWRITER OBJECTS
        tree.output1.close();
        tree.output2.close();
        tree.br.close();

        


    }

    
    
}

