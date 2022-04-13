import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Arrays;
import java.util.Collections;


/**
 * Assign5
 */
public class Hashtables {

    
    private static Hashtables table;
    private String input, output;
    private int maxSize, currentSize;
    private String [] keys;
    private String [] vals;
    private Integer [] reads;
    private int tempReads;
    private long loadFactor;
    private long avg_reads;
    private long hash_eff;
    private long longestRead;
    

    
    public Hashtables(int cap){
        currentSize = 0;
        maxSize = cap;
        keys = new String[maxSize];
        vals = new String[maxSize];

    }

    /**
     * PROMISES: Clear the Hashtable
     * REQUIRES: Nothing required.
     * 
     */
    public void clear(){
        currentSize = 0;
        keys = new String[maxSize];
        vals = new String[maxSize];
    }

    /**
     * PROMISES: Return the size of the hashtable
     * REQUIRES: Nohting required.
     */
    public int getSize(){ return currentSize;}

    /**
     * PROMISES: Returns a true/false stating whether the hashtable is full or not
     * REQUIRES: Nothing Reqiured.
     */
    public boolean isFull(){return currentSize == maxSize;}

    /**
     * PROMISES: Returns a true/false stating whether the hashtable is empty or not
     * REQUIRES: Nothing required.
     */
    public boolean isEmpty(){return getSize() == 0;}

    /**
     * PROMISES: Checks table to see if it contains a certain word.
     * REQUIRES: Requires the key to the word.
     * @param key
     */
    public boolean contains(String key){return getValue(key) != null;}

    /**
     * PROMISES: Has function that returns the modulus of the hashCode 
     *           with respect to the maximum table size.
     * REQUIRES: Key
     * @param key
     */
    public int hash(String key) {return key.hashCode() % maxSize;}

    /**
     * PROMISES: Inserts a value and key into the hashtable
     * REQUIRES: Key, Value
     * @param key
     * @param value
     */
    public void put(String key, String value){
        for (int i = index(hashCode(Integer.parseInt(key))); ; i++) {
            if (i == maxSize) {
                i = 0;
            }
            if (keys[i] == null) {
                keys[i] = key;
            }
            if (keys[i] == key) {
                vals[i] = value;
                return;
            }
        }
    }

    /**
     * PROMISES: Returns the value/word in the hashtable
     * REQUIRES: Key data 
     * @param key
     */
    public String getValue(String key){
        int i = hash(key);
        while(keys[i] != null){
            if(keys[i] == key)
                return vals[i];
            i = (i+1)%maxSize;
        
        }

        return null;
    }

    /**
     * PROMISES: Returns the key, acts as a reverse search. This also returns the amount of words read before finding the key.
     * REQUIRES: Key, read count
     * @param key
     * @param reads
     */
    public String getKey(String value, int reads){
        //System.out.println("I reached here.");
        for(int i = 0; i < maxSize; i++){
            reads = i + 1;
            if(value.equals(vals[i])){
                tempReads = reads;
                return keys[i];
            }
        }

        return null;
    }

    /**
     * PROMISES: Searches for a word within the hashtable
     * REQUIRES: Nothing required.
     * @throws IOException
     */
    public void search() throws IOException{
        String result;

        BufferedReader in = new BufferedReader(new FileReader(table.input));
        String str;
        int i = 0;
        table.reads = new Integer[maxSize];
        Arrays.fill(table.reads, 0);
        //System.out.println(table.reads.length);
        while((str = in.readLine()) != null){
            result = table.getKey(str, table.reads[i]);
            if(table.getValue(result).equals(str)){
                table.reads[i] = table.tempReads;
            }
            i++;    
            
        }

    }

    /**
     * PROMISES: Index of array
     * REQUIRES: hash value
     * @param hash
     */
    private int index(int hash) {
        return Math.abs(hash) % maxSize;
    }

    /**
     * PROMISES: Manipulation of initial value to create a unique key.
     * REQUIRES: Key data 
     * @param h
     */
    private int hashCode(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    /**
     * PROMISES: Prints the Hashtable
     * REQUIRES: Nothing is required.
     */
    public void printHashTable()
    {
        System.out.println("\nHash Table: ");
        for (int i = 0; i < maxSize; i++)
            if (keys[i] != null)
                System.out.println(keys[i] + " " + vals[i]);
        System.out.println();
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

    /**
     * PROMISES: Reads input file and creates hashtable
     * REQUIRES: Input File
     * @param inputFileName
     * @throws IOException
     */
    public void readFile(String inputFileName) throws IOException{
        BufferedReader in = new BufferedReader(new FileReader(inputFileName));
        String str;
        int key =  0, i = 0;
        while((str = in.readLine()) != null){
            key = i + index(hashCode(1));
            table.put(Integer.toString(key),str);
            i++;
        }
    }
    
    /**
     * PROMISES: Writes statistics to output file
     * REQUIRES: Output file
     * @param file
     * @throws IOException
     */
    public void writeToFile(String file) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        
        writer.write("Load Factor:" + Long.toString(table.loadFactor) + "\n");
        writer.write("Average Reads:" + Long.toString(table.avg_reads) + "\n");
        writer.write("Hashing Efficiency:" + Long.toString(table.hash_eff) + "\n");
        writer.write("Longest Read:" + Long.toString(table.longestRead) + "\n");

        writer.write("\n");
        writer.flush();
        writer.close();
    }

    /**
     * PROMISES: Calculations for the load factor, average reads, hashing efficiency,
     *           and longest read.
     * REQUIRES: Nothing is required.
     * @throws  IOException
     */
    public void Calculations() throws IOException{
        table.loadFactor = vals.length/maxSize;

        for(int i = 0; i < table.reads.length;i++){
            table.avg_reads += table.reads[i];
        }
        table.avg_reads = table.avg_reads/table.reads.length;

        table.hash_eff = table.loadFactor/table.avg_reads;

        table.longestRead = Collections.max(Arrays.asList(table.reads));


        table.writeToFile(table.output);


    }
    public static void main(String[] args) throws IOException {
        String inFile;
        String outFile;
        int size;
        inFile = args[0];
        outFile = args[1];

        size = countLineNumberReader(inFile);
        
        
        table = new Hashtables(size);
        table.input = inFile;
        table.output = outFile;
        table.readFile(table.input);


        //table.printHashTable();
        table.search();
        table.Calculations();
        

        
    }
}