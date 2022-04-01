import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Scanner;

/**
 * graphtrav
 */
public class GraphTrav {

    private int vertices;
    private String path;
    private String[] node_pairs = new String[2];
    private String matrix;
    private String query;
    private int [] start_nodes;
    private int [] end_nodes;
    private String DFSout;
    private String BFSout;
    private String [] rowContents;
    private LinkedList<Integer> [] adjList;
    private int col;
    private int row;
    private int qrow;
    private PrintWriter output2;
    private static GraphTrav graph;

    GraphTrav(){

    }

    @SuppressWarnings("unchecked") GraphTrav(int v){
        vertices = v;
        adjList = new LinkedList[v];
        for (int i = 0; i < v; ++i) {
            adjList[i] = new LinkedList<Integer>();
        }

    }

    

    public void addEdges(int v, int v2){
        adjList[v].add(v2);
    }

    public void DFSVisit(int v, boolean visited[], int e) throws IOException{
        visited[v] = true;
        path += v + ", ";

        // Iterator<Integer> i = adjList[v].iterator();
        // while(i.hasNext()){
        //     int n = i.next();
            
        //     if(!visited[n]){

        //         if(n == e){
        //             path += n;
        //             //System.out.println("I WANT TO LEAVE");
        //             break;
        //         }
        //         //System.out.println("I AM STUCK");

        //         DFSVisit(n, visited, e);
        //     }
        // }

        for(Integer k : adjList[v]){
            if(!visited[k]){                
                System.out.println("I AM STUCK");
                DFSVisit(k, visited, e);

                if(k==e){
                    path += k;
                    System.out.println("I WANT TO LEAVE");
                    return;
                    
                }
            }
        }
    }

    public void DFS(int v, int e) throws IOException{
        boolean visited[] = new boolean[vertices];
        graph.path = "";

        DFSVisit(v, visited, e);
    }

    static class LinkedList<T> implements Iterable<T>{
        Node<T> head, tail;

        public void add(T data){
            Node<T> node = new Node<>(data, null);
            if(head == null) {
                tail = head = node;
            } else {
                tail.setNext(node);
                tail = node;
            }

        }

        public Node<T> getHead(){
            return head;       
        }

        public Node<T> getTail(){
            return tail;
        }

        public Iterator<T> iterator(){
            return new ListIterator<T>(this);
        }

        public void printList(LinkedList<T> list)
        {
            Node<T> currNode = list.head;
        
            System.out.print("LinkedList: ");
    
        // Traverse through the LinkedList
            while (currNode != null) {
                // Print the data at current node
                System.out.print(currNode.data + " ");
        
                // Go to next node
                currNode = currNode.next;
            }
        }

        static class Node<T> {
            T data;
            Node<T> next;

            Node(T data, Node<T> next){
                this.data = data;
                this.next = next;
            }

            public void setData(T data){
                this.data = data;
            }

            public void setNext(Node<T> next){
                this.next = next;
            }

            public T getData(){
                return data;
            }

            public Node<T> getNext(){
                return next;
            }
        }

        class ListIterator<T> implements Iterator<T>{
            Node<T> current;

            public ListIterator(LinkedList<T> list){
                current = list.getHead();
            }

            public boolean hasNext(){
                return current != null;
            }

            public T next() {
                T data = current.getData();
                current = current.getNext();
                return data;
            }

            public void remove(){
                throw new UnsupportedOperationException();
            }
        }

    }

    public void readEdges(String content, int key){
        String[] str = new String[content.length()];
        str = content.split("\t");
        for(int i = 0; i < str.length; i++){
            if(str[i].equals("1")){
                addEdges(key, i);
            }
        }
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

    public static int getFileColumnsNumber(String filename) {
        File file = new File(filename);
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    
        int number = 0;
        if (scanner.hasNextLine()) {
            number = scanner.nextLine().split("\t").length;
        }
        scanner.close();
        return number;
    }

    public void readInput(String filename){
        File file = new File(filename);
        Scanner scanner;

        try{
            scanner = new Scanner(file);
            for(int i = 0; i < graph.rowContents.length; i++){
                graph.rowContents[i] = scanner.useDelimiter("\t").nextLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }        
    }

    public void readQuery(String filename){
        File file = new File(filename);
        Scanner scanner;
        int i = 0;
        graph.start_nodes = new int[graph.qrow];
        graph.end_nodes = new int[graph.qrow];
        try{
            scanner = new Scanner(file);
            while(scanner.hasNext()){
                
                graph.node_pairs = scanner.nextLine().split("\t");
                graph.start_nodes[i] = Integer.parseInt(graph.node_pairs[0]);
                graph.end_nodes[i] = Integer.parseInt(graph.node_pairs[1]);
                i++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } 
    }



    public void writetoDFS(String result) throws IOException {
        output2 = new PrintWriter(new BufferedWriter(new FileWriter(DFSout,true)));
        output2.println(result);
        System.out.println("I reached here.");
        System.out.println(result);
        output2.flush();
    }

    public static void main(String[] args) throws IOException {
        int col = getFileColumnsNumber(args[0]);
        graph = new GraphTrav(col);
        
        graph.matrix = args[0];
        graph.query = args[1];
        graph.DFSout = args[2];

        graph.row = countLineNumberReader(graph.matrix);
        graph.qrow = countLineNumberReader(graph.query);

        
        
        graph.rowContents = new String [graph.row];
        graph.readInput(graph.matrix);
        graph.readQuery(graph.query);

        for(int i = 0; i < graph.row; i++){
            graph.readEdges(graph.rowContents[i], i);
        }


        // for (int i = 0; i < graph.adjList.length; i++) {
        //     graph.adjList[i].printList(graph.adjList[i]);
        //     System.out.println();
        // }
        //System.out.println(graph.start_nodes.length);
        for(int i = 0; i < graph.start_nodes.length; i++){
            graph.DFS(graph.start_nodes[i], graph.end_nodes[i]);
            graph.writetoDFS(graph.path);
            graph.path = "";
            //System.out.println(graph.start_nodes[i] + " " + graph.end_nodes[i]);
            
        }

    }


    
}