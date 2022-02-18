

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
        int data;
        Node next;

        Node(int data){
            this.data = data;
            next = null;
        }

    }


    //---------NODE INSERTION---------//
    
    
    public void add(int data){
        
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

}


public class Assign2 {

    public static void main(String[] args) {
        LinkedList list = new LinkedList();

        list.add(12);
        list.add(22);

        list.printList();
    }
}