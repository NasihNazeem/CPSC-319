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
 * BinarySearchTree
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

    public static String minimumElement(Node root) {
        String mins = root.data;
        while(root.left != null){
            mins = root.left.data;
            root = root.left;
        }

        return mins;
    }

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

    private void readOperation() {
        switch(student.operation){
            case 'I':
                insert(student.name);
                break;
            case 'D':
                deleteNode(student.name);
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
        
        try {
            tree.br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }

        tree.dpthFile = args[1];
        tree.brdthFile = args[2];

        

        String st;

        int lines = countLineNumberReader(args[0]), i = 0;
        String [] content = new String[lines];
        students = new Student[lines];

        try {
            while((st = tree.br.readLine()) != null){
            content[i] = st;
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
        
        tree.output1.close();
        tree.output2.close();
        tree.br.close();

        


    }

    
    
}

