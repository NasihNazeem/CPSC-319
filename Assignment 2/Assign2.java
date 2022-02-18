import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

/**
 * 
 * 
 * 
 * Assignment No: 1
 * Author: Nasih Nazeem
 * Date: Jan 28, 2022
 */
class LinkedList{

    private Node head;

    private static class Node{
        String data;
        Node next;

        Node(String data){
            this.data = data;
            next = null;
        }

        public String getData() {
            return data;
        }
    
        public void setData(String data) {
            this.data = data;
        }
    
        public Node getNextNode() {
            return next;
        }
    
        public void setNextNode(Node nextNode) {
            this.next = nextNode;
        }
        
    }


    public boolean contains(String str){
        Node temp = head;

        while(temp != null){
            
            if(str.equals(temp.data))
                return true;

            temp = temp.next;


        }

        return false;
    }
    public void deleteList(){
        head = null;
    }
    //---------NODE INSERTION---------//

    public void add(String data){
        
        Node freshNode = new Node(data);
        if(head == null){
            head = freshNode;
            return;
        } 
        
        Node temp = head;

        while(temp.next != null){
            temp = temp.next;
        }

        temp.next = freshNode;
    }

    //---------PRINT LIST---------//

    public void printList(){
        Node temp = head;

        while(temp != null){
            System.out.println(temp.data);  // Print data of current node
            temp = temp.next;               // Move to next node
        }

    }

    public String get(int i) {
        int n = indexOf(head); // count-1 actually
        Node current = head;
        while (n > i) {
            --n;
            current = current.next;
        }
        return current.data;
    }
    
    private int indexOf(Node link) {
        if (link == null) {
            return -1;
        }
        return 1 + indexOf(link.next);
    }

    public int size () {
        int size = 1;
        Node Current = head;
        while(Current.next != null)
        {
          Current = Current.next;
          size++;     
        }
        return size;
      }

    
}


 class ArrayList {
    
    private LinkedList arr[];
    private int capacity;
    private int current;

    public ArrayList()
    {
        arr = new LinkedList[1];
        capacity = 1;
        current = 0;
    }

    public void push(LinkedList data)
    {
        if(current == capacity)
        {
            LinkedList temp[] = new LinkedList[2*capacity];

            for(int i = 0; i < capacity; i++)
                temp[i] = arr[i];
            
            capacity *= 2;
            arr = temp;
        }

        arr[current] = data;
        current++;
    }

    void push(LinkedList data, int index)
    {
        if(index == capacity)
            push(data);
        else
            arr[index] = data;
    }

    LinkedList get(int index)
    {
        if(index < current)
            return arr[index];
        
        return null;

    }

    void pop()
    {
        current--;
    }

    int size()
    {
        return current;
    }

    int getCapacity()
    {
        return capacity;
    }

    void print()
    {
        for(int i = 0; i < current; i++){
            arr[i].printList();
            System.out.println();
        }
        System.out.println();
    }
}




public class Assign2 {
    
    private String input;
    private String output;
    ArrayList list = new ArrayList();
    LinkedList raw = new LinkedList();
    LinkedList temp = new LinkedList();
    LinkedList verified = new LinkedList();

    public String sortAlpha(String str){

        char charArray[] = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            for (int j = i + 1; j < charArray.length; j++) {
                if (Character.toLowerCase(charArray[j]) < Character.toLowerCase(charArray[i])) {
                    swapChars(i, j, charArray);
                }
            }
        }

        str = String.valueOf(charArray);
        //System.out.println(str);
        return str;
    }

    private static void swapChars(int i, int j, char[] charArray) {
        char temp = charArray[i];
        charArray[i] = charArray[j];
        charArray[j] = temp;
    }

    public void checkAnagram(){

        String str;
        String str1;
        //raw.printList();
        boolean status = false;

        for(int i = 0; i < raw.size(); i++){
            temp.add(raw.get(i));
  
            
            if(!verified.contains(temp.get(i))){
                
                for(int j = i + 1; j < raw.size(); j++) {
                    
                    if(raw.get(i).length() == raw.get(j).length()){

                        str = raw.get(i);
                        str1 = raw.get(j);

                        str = sortAlpha(str);
                        str1 = sortAlpha(str1);

                        status = str.equals(str1);

                        if(status){
                            temp.add(raw.get(j));
                            
                        }
                    }
                }
                //verified.printList();
                createList(temp);
            }
            
            verifyComplete();

            temp.deleteList();         
           
        }

        list.print();

    }

    public void verifyComplete(){

        for(int i = 0; i < temp.size(); i++){
            if(!verified.contains(temp.get(i)))
                verified.add(temp.get(i));
        }

    }
    public void createList(LinkedList newIndex){
        LinkedList newTemp = new LinkedList();

        for(int i = 0; i < newIndex.size(); i++){
            newTemp.add(newIndex.get(i));
        }
        //newTemp.printList();
        list.push(newTemp);
        
    }
    
    public void readFile(String inputFile){
        try {
                
    
                
            File myObj = new File(input);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                raw.add(myReader.nextLine());
            } 


            myReader.close();
            } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            }
    }
    
    public static void main(String[] args) {
        Assign2 a2 = new Assign2();

        a2.input = args[0];
        a2.output = args[1];
        
        a2.readFile(a2.input);
        a2.checkAnagram();


    }



    
}