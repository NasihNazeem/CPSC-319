import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * 
 * 
 * 
 * Assignment No: 2
 * Author: Nasih Nazeem
 * Date: Jan 28, 2022
 */
class LinkedList{

    private Node head;
    private Node sorted;

    private static class Node{
        String data;
        Node next;

        Node(String data){
            this.data = data;
            next = null;
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


     // function to sort a singly linked list using insertion sort
    public void insertionSort(LinkedList test)
     {
         // Initialize sorted linked list
         sorted = null;
         Node headref = test.head;
         Node current = headref;
         // Traverse the given linked list and insert every
         // node to sorted
         while (current != null)
         {
             // Store next for next iteration
             Node next = current.next;
             // insert current in sorted linked list
             sortedInsert(current);
             
             // Update current
             current = next;
         }
         // Update head_ref to point to sorted linked list
         test.head = sorted;
     }
  
     /*
      * function to insert a new_node in a list. Note that
      * this function expects a pointer to head_ref as this
      * can modify the head of the input linked list
      * (similar to push())
      */
     void sortedInsert(Node newnode)
     {
         /* Special case for the head end */
         if (sorted == null || sorted.data.compareToIgnoreCase(newnode.data) >= 0)
         {
             newnode.next = sorted;
             sorted = newnode;
         }
         else
         {
            Node current = sorted;
             /* Locate the node before the point of insertion */
             while (current.next != null && current.data.compareToIgnoreCase(newnode.data) < 0)
             {
                 current = current.next;
             }
             newnode.next = current.next;
             current.next = newnode;
         }
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

/**
 * Class created to be known as the reference to all LinkedLists.
 */
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


    /**
     * Was not used due to the mistakes in the Quick Sort Implementation.
     * @param test
     * @param low
     * @param high
     */
    public void quickSort(ArrayList test, int low, int high){
        if(low < high)
        {
            int pi = partition(test, low, high);

            quickSort(test, low, pi-1);
            quickSort(test, pi+1, high);
        }
    }

    /**
     * Was not used due to the mistakes in the Quick Sort.
     * @param test
     * @param low
     * @param high
     * @return
     */
    public int partition(ArrayList test, int low, int high){

        LinkedList pivot = test.get(high);
        int i = low;

        for(int j = low + 1; j < high; j++){
            if((test.get(j).get(j).compareTo(pivot.get(j)) <= 0))
            {
                i++;
                LinkedList temp = test.get(i);
                test.push(test.get(j), i);
                test.push(temp, j);

            }
        }

        LinkedList temp = test.get(i + 1);
        test.push(test.get(high), i+1);
        test.push(temp, high);

        return i + 1;
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
    
    private String input;                       // input file
    private String output;                      // output file
    ArrayList list = new ArrayList();           // ArrayList was the reference to all the LinkedLists
    LinkedList raw = new LinkedList();          // Used as the initial RAW strings that were taken in from the input file.
    LinkedList temp = new LinkedList();         // Used during the sorting algorithms, checking of anagrams
    LinkedList verified = new LinkedList();     // Used to verify anagrams and to permanently place them onto the ArrayList.

    /**
     * Method's main function is to sort alphabetically in ascending order.
     * @param str
     * @return
     */
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

        return str;
    }

    /**
     * Swapping of chars for the letters sorting in alphabetically ascending order.
     * @param i
     * @param j
     * @param charArray
     */
    private static void swapChars(int i, int j, char[] charArray) {
        char temp = charArray[i];
        charArray[i] = charArray[j];
        charArray[j] = temp;
    }

    /**
     * Checks for Anagrams in the Raw LinkedList, removes verified anagram words and adds that to the ArrayList of LinkedLists.
     */
    public void checkAnagram(){

        String str;
        String str1;

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

                createList(temp);
            }
            
            verifyComplete();

            temp.deleteList();         
           
        }

    }

    /**
     * Removes words that has already been accounted for.
     * 
     */
    public void verifyComplete(){

        for(int i = 0; i < temp.size(); i++){
            if(!verified.contains(temp.get(i)))
                verified.add(temp.get(i));
        }

    }

    /**
     * Used to create the ArrayList once sorting alphabetically and finding the anagrams
     * @param newIndex
     */
    public void createList(LinkedList newIndex){
        LinkedList newTemp = new LinkedList();

        for(int i = 0; i < newIndex.size(); i++){
            newTemp.add(newIndex.get(i));
        }
        list.push(newTemp);
        
    }
    
    /**
     * Reads the input file and adds it to a RAW LinkedList
     * @param inputFile
     */
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
    /**
     * Insertion Sort for each LinkedList in the ArrayList
     */
    public void sortWords(){

        for(int i = 0; i < list.size(); i++){

            raw.insertionSort(list.get(i));

        }

        list.print();

    }

    /**
     * Quick Sort Implementation was not working, skipped this part of the assignment.
     */
    public void sortLists(){
        list.quickSort(list, 0, list.size());
    }

    /**
     * Outputs the ArrayList to a text file. Feel free to edit the address of the
     * @param file
     * @throws IOException
     */
    public void writeToFile(String file) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        
        for(int i = 0; i < list.size(); i++){
            for(int j = 0; j < list.get(i).size(); j++){
                String content = list.get(i).get(j);

                
                writer.write(content + " ");
                
                
            }
            writer.write("\n");
            writer.flush();
        }
        writer.close();
    }

    public static void main(String[] args) throws IOException {
        Assign2 a2 = new Assign2();

        a2.input = args[0];
        a2.output = args[1];
        
        a2.readFile(a2.input);
        a2.checkAnagram();
        a2.sortWords();
        //a2.sortLists();

        a2.writeToFile(a2.output);

    }



    
}