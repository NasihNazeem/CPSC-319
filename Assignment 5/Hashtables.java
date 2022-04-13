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

    public void clear(){
        currentSize = 0;
        keys = new String[maxSize];
        vals = new String[maxSize];
    }

    public int getSize(){ return currentSize;}

    public boolean isFull(){return currentSize == maxSize;}

    public boolean isEmpty(){return getSize() == 0;}

    public boolean contains(String key){return getValue(key) != null;}

    public int hash(String key) {return key.hashCode() % maxSize;}

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

    public String getValue(String key){
        int i = hash(key);
        while(keys[i] != null){
            if(keys[i] == key)
                return vals[i];
            i = (i+1)%maxSize;
        
        }

        return null;
    }

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

    private int index(int hash) {
        return Math.abs(hash) % maxSize;
    }

    private int hashCode(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

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