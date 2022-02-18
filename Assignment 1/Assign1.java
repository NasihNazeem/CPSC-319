import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * In this assignment we explore the implementation and comaprison of sorting algorithms. We used some websites to remind ourselves how certain 
 * sorting methods work. Those can be found below:
 * 
 * 1) https://www.geeksforgeeks.org/quick-sort/
 * 2) https://www.baeldung.com/java-merge-sort
 * 3) https://www.geeksforgeeks.org/selection-sort/
 * 4) https://www.geeksforgeeks.org/insertion-sort/
 * 
 * Assignment No: 1
 * Author: Nasih Nazeem
 * Date: Jan 28, 2022
 */
public class Assign1 {

    Random random = new Random();       // Object created to fill array with random integers
    private String order;               // order inputted from user (ascending, descending, random)
    private int size;                   // user inputted size of the array
    private String algo;                // user inputted sort algorithm (selection, insertion, merge, quick)
    private String filename;            // user inputted file name that the sorted array is written to
    private int arr[];                  // array generated with random values input
    private long startTime;             // Start time of beginning of sorting method (in nanoseconds)
    private long elapsedTime;           // Elapssed time of the sorting method (in nanoseconds) 
    
    public static void main(String[] args) {

        Assign1 a1 = new Assign1();     // Object created to interact with static main
        
        try{
            a1.order = args[0];
            a1.size = Integer.parseInt(args[1]);
            a1.algo = args[2];
            a1.filename = args[3];

            if(a1.size < 0)
                throw new IllegalArgumentException();
        } catch( IllegalArgumentException er) {
            System.out.println("Size must be a non-negative. Aborting...");
            System.exit(0);
        }

       
        a1.orderFill();
        a1.chooseAlgorithm();           // match the algorithm within a switch statement, to then call upon an appropriate sorting function
        a1.createFile();
        a1.printTime();
               
    }


    /**
     * PROMISES: Depending on the order that was user-inputted, this method selects a case and filling method that either fills an empty array in random, 
     *           ascending or descending order.
     * REQUIRES: N/A.
     */
    public void orderFill(){
        switch(order){
            case "ascending":
                fillAscending();
                break;
            case "descending":
                fillDescending();
                break;
            case "random":
                fillRandom();
                break;
            default:
                System.err.println("Please re-run the program and choose an appropriate order. (Random | Ascending | Descending)");
                System.exit(0);
                break;
        }
    }

    /**
     * PROMISES: Fills an empty in ascending order. This is done by filling randomly at first, and then sorting it into ascending order.
     * REQUIRES: N/A.
     */
    public void fillAscending(){
        arr = new int [size];
        for(int i = 1; i <= arr.length; i++){
            arr[i-1] = i;
        }        
    }

    /**
     * PROMISES: Fills an empty array in descending order. This is done by filling randomly at first, and then converting the empty array 
     * to use the Collections methods to sort, and reverse the order.
     * REQUIRES: N/A.
     */
    public void fillDescending() {
        arr = new int [size];
        for(int i = arr.length; i > 0; i--){
            arr[i-1] = i;
        } 
    }


    /**
     * PROMISES: Filling an empty array that is arbitrarily sized with random integers..These integers are within the 0 to 100 boundary.
     * REQUIRES: N/A.
     */
    public void fillRandom() {
        arr = new Random().ints(0,Integer.MAX_VALUE).limit(size).toArray();
    }

    /**
     * PROMISES: Method call to print the elapsed time for a selected sorting algorithm.
     * REQUIRES: N/A.
     */
    public void printTime(){
        System.out.println("The elapsed time of the " + order + " sorting algorithm is: " + elapsedTime + " nanoseconds.");
    }

    /**
     * PROMISES: Creates a file named by the user. This also checks as to whether this file already exists or not, as well as checking for any incorrect inputs.
     * REQUIRES: N/A.
     */
    public void createFile(){
        try{

            File f = new File(filename);
            if(f.createNewFile()){
                System.out.println("File created:" + f.getName());
                fileWrite();
            } else
                System.out.println("File already exists.");

        } catch (IOException e) {
            System.out.println("An error has occured. Aborting...");
            System.exit(0);
        }
    }

    /**
     * PROMISES: Writes to the file created once the array has been sorted.
     * REQUIRES: N/A.
     */
    public void fileWrite(){
        try{

            FileWriter writer = new FileWriter(filename);

            for(int i = 0; i < arr.length; i++){
                writer.write(String.valueOf(arr[i]) + "\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("An error has occured.");
            e.printStackTrace();

        }
    }


    /**
     * PROMISES: Choosing an algorithm that matches the algorithm the user requested.
     * REQUIRES: N/A.
     */
    public void chooseAlgorithm() {

        switch(algo){
            case "selection":

                startTime = System.nanoTime();
                selectionSort();
                elapsedTime = System.nanoTime() - startTime;
                break;
            case "insertion":
                startTime = System.nanoTime();
                insertionSort();
                elapsedTime = System.nanoTime() - startTime;
                break;

            case "merge":
                startTime = System.nanoTime();
                mergeSort();
                elapsedTime = System.nanoTime() - startTime;
                break;

            case "quick":
                startTime = System.nanoTime();
                quickSort(arr, 0, arr.length - 1);
                elapsedTime = System.nanoTime() - startTime;
                break;

            default:
                System.err.println("Please re-run the program and choose an appropriate algorithm. (Selection | Insertion | Merge | Quick)");
                System.exit(0);

        }
    }


    /**
     * PROMISES: The swapping of the values in two indices.
     * REQUIRES: The array, the first index, and the second.
     * @param array Main Array
     * @param left  First value being swapped
     * @param right Second vlaue being swapped
     */
    public  void swap(int [] array, int left, int right){

        int temp = array[left];
        array[left] = array[right];
        array[right] = temp;

    }

    

    /**
     * PROMISES: Looks into the order requested, and sorts the array based upon that order.
     * REQUIRES: N/A.
     */
    public void selectionSort(){

        int min;                                            
        for(int i = 0; i < arr.length - 1; i++){            
            min = i;                                        

            for(int j = i + 1; j < arr.length; j++) {       
                if(arr[j] < arr[min]) {                     
                    min = j;
                }
            }

            swap(arr, min, i);
        }
    }
    

    /**
     * PROMISES: Looks into the order requested, and sorts the array based upon that order.
     * REQUIRES: N/A.
     */
    public void insertionSort(){

        for(int i = 1; i < arr.length; ++i){        
            int key = arr[i];                       
            int j = i-1;                            

            while( j >= 0 && arr[j] > key) {         
                arr[j+1] = arr[j];                  
                j = j - 1;                          
            }
            arr[j+1] = key;                         
        }
    }

    /**
     * PROMISES: Looks into the order requested, and sorts the array based upon that order. Calls upon a sorting function.
     * REQUIRES: N/A.
     */
    public void mergeSort(){
        sort(arr,arr.length);
    }

    /**
     * PROMISES: Splits the array into two separate sub-arrays. Sorts both sub-arrays before merging with one another.
     * REQUIRES: main array with random values, length of main array.
     */
    public void sort(int arr[], int length){
        if(length < 2)                          // log n
            return;

        int  mid = length/2;                    // 1
        int [] l = new int[mid];                // 1
        int [] r = new int[length - mid];       // 1

        for(int i = 0; i < mid; i++){           // n + 1
            l[i] = arr[i];                      // 1
        }
        for(int i = mid; i < length; i++){      // n + 1
            r[i - mid] = arr[i];                // 1
        }

        sort(l, mid);                           // log n
        sort(r, length-mid);                    // log n

        
        ascendMerge(arr, l, r, mid, length - mid);
        
        
    }

    /**
     * PROMISES: Once mergeSort has completed the sorting of the two sub-arrays, this function merges both arrays into an ascending order.
     * REQUIRES: Main array, left sub-array, right sub-array, last index of left array, last index of right array.
     */
    public void ascendMerge(int arr[], int [] l, int [] r, int left, int right){

        int i = 0, j = 0, k = 0;
        while(i < left && j < right) {
            if(l[i] <= r[j]) {
                arr[k++] = l[i++];
            }
            else {
                arr[k++] = r[j++];
            }
        }

        while(i < left) {
            arr[k++] = l[i++];
        }
        while(j < right) {
            arr[k++] = r[j++];
        }
        
    }


    /**
     * PROMISES: Uses certain values in the array as a pivot/reference to compare the other elements in the array.
     * REQUIRES: Main array, first index, last index.
     */
    public void quickSort(int [] arr, int low, int high) {
        if (low < high)
        {
            /* pi is partitioning index, arr[pi] is 
              now at right place */
            int pi = ascendPartition(arr, low, high);
  
            // Recursively sort elements before
            // partition and after partition
            quickSort(arr, low, pi-1);
            quickSort(arr, pi+1, high);
        }
    }


    public int ascendPartition(int [] arr, int low, int high) {
        int pivot = arr[high]; 
        int i = (low-1); // index of smaller element
        for (int j=low; j<high; j++)
        {
            // If current element is smaller than or
            // equal to pivot
            if (arr[j] < pivot)
            {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return (i + 1);
    }

    
}
    /**
     * PROMISES: Arranges the array using the pivot to sort in an ascending order.
     * REQUIRES: Main array, first index, last index.
     */