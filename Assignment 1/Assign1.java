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
    
    public static void main(String[] args) throws IOException {

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
    public void fillAscending(){
        fillRandom();
        Arrays.sort(arr);
        
    }

    public void fillDescending(){
        
        fillRandom();
        List<Integer> list = Arrays.stream(arr).boxed().collect(Collectors.toList());
        Collections.sort(list);
        Collections.reverse(list);
        arr = list.stream().mapToInt(i->i).toArray();
    }

    public static void reverse(int[] array)
    {
        // Length of the array
        int n = array.length;
  
        // Swaping the first half elements with last half
        // elements
        for (int i = 0; i < n / 2; i++) {
  
            // Storing the first half elements temporarily
            int temp = array[i];
  
            // Assigning the first half to the last half
            array[i] = array[n - i - 1];
  
            // Assigning the last half to the first half
            array[n - i - 1] = temp;
        }
    }

    /**
     * PROMISES: Filling an empty array that is arbitrarily sized with random integers..These integers are within the 0 to 100 boundary.
     * REQUIRES: N/A.
     */
    public void fillRandom() {
        arr = new Random().ints().limit(size).toArray();
    }


    public void printTime(){
        System.out.println("The elapsed time of the " + order + " sorting algorithm is: " + elapsedTime + " nanoseconds.");
    }

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
                quickSort();
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
        if(length < 2)
            return;

        int  mid = length/2;
        int [] l = new int[mid];
        int [] r = new int[length - mid];

        for(int i = 0; i < mid; i++){
            l[i] = arr[i];
        }
        for(int i = mid; i < length; i++){
            r[i - mid] = arr[i];
        }

        sort(l, mid);
        sort(r, length-mid);

        
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
     * PROMISES: Uses a pivotSort function that pivots on certain values in the array to sort as reference.
     * REQUIRES: N/A.
     */
    public void quickSort(){
        pivotSort(arr, 0, arr.length - 1);   
    }

    /**
     * PROMISES: Uses certain values in the array as a pivot/reference to compare the other elements in the array.
     * REQUIRES: Main array, first index, last index.
     */
    public void pivotSort(int [] arr, int low, int high) {
        int pi;
        if(low < high){
            pi = ascendPartition(arr,low,high);

            if(pi - low <= high - (pi+1))
            {
                pivotSort(arr, low, pi-1);

            }else{
                pivotSort(arr, pi +1 , high);
            }
            
            
            
        }
    }

    /**
     * PROMISES: Arranges the array using the pivot to sort in an ascending order.
     * REQUIRES: Main array, first index, last index.
     */
    public int ascendPartition(int [] arr, int low, int high) {
        
        int pivot = arr[high];

        int i = (low - 1);

        for(int j = low; j <= high - 1; j++) {

            if(arr[j] < pivot)
            {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i+1, high);
        return (i+1);
    }

    
}