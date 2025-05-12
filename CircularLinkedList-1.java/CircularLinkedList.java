import java.sql.SQLOutput;
import java.util.Iterator;

public class CircularLinkedList<E> implements Iterable<E> {


    // Your variables
    Node<E> head;
    Node<E> tail;
    int size;  // BE SURE TO KEEP TRACK OF THE SIZE


    // implement this constructor
    public CircularLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    // I highly recommend using this helper method
    // Return Node<E> found at the specified index(Node)
    // be sure to handle out of bounds cases (Done)
    private Node<E> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        Node<E> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    // attach a node to the end of the list
    public boolean add(E item) {
        this.add(size, item);
        return false;
    }


    // Cases to handle
    // out of bounds(Done)
    // adding to empty list(Done)
    // adding to front(Done)
    // adding to "end"(Done)
    // adding anywhere else(Done)
    // REMEMBER TO INCREMENT THE SIZE(Done)
    public void add(int index, E item) {
        if (index < 0 || index > size) { //Out of Bounds
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        Node<E> node = new Node<>(item);
        if (size == 0) { //Adding empty list
            head = node;
            tail = node;
        } else if (index == 0) { //Adding to front
            node.next = head;
            head = node;
            tail.next = head;
        } else if (index == size ) {//adding to the end of the list
            tail.next = node;
            tail = node;
            tail.next = head;
        } else { //anywhere else
            Node<E> before = getNode(index - 1);
            node.next = before.next;
            before.next = node;
        }
        size++;//increment size


    }
    // remove must handle the following cases
    // out of bounds(Done)
    // removing the only thing in the list(Done)
    // removing the first thing in the list (need to adjust the last thing in the list to point to the beginning)(Done)
    // removing the last thing(Done)
    // removing any other node(Done)
    // REMEMBER TO DECREMENT THE SIZE(Done)
    public E remove(int index) {
        if (index < 0 || index >= size) { // Out of bounds check
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        E toReturn = null;
        if(size == 1) {//removing the only element
            toReturn = head.item;
            head = null;
            tail = null;
        }
        else  if (index == 0) {//removing the head element
            toReturn = head.item;//store
            head = head.next;
            tail.next = head;
        } else if (index == size -1) {//removing the last element
            Node<E> before = getNode(index - 1);
            toReturn = before.next.item;
            before.next = head;
            tail = before;
        }else{ //remove anywhere
            Node<E> before = getNode(index - 1);
            toReturn = before.next.item;
            before.next = before.next.next;
        }
        size--;
        return toReturn;

    }


    // Turns your list into a string
    // Useful for debugging
    public String toString() {
        if (size == 0) {
            return "Empty List";
        }
        StringBuilder result = new StringBuilder();
        Node<E> current = head;
        int count = 0; // Prevent infinite loops
        while (count < size) {
            result.append(current.item);
            if (count < size - 1) {
                result.append(" >>> ");
            }
            current = current.next;
            count++;
        }
        result.append(" >>>");
        return result.toString();
    }


    public Iterator<E> iterator() {
        return new ListIterator<E>();
    }

    // provided code for different assignment
    // you should not have to change this
    // change at your own risk!
    // this class is not static because it needs the class it's inside of to survive!
    private class ListIterator<E> implements Iterator<E>{

        Node<E> nextItem;
        Node<E> prev;
        int index;

        @SuppressWarnings("unchecked")
        //Creates a new iterator that starts at the head of the list
        public ListIterator(){
            nextItem = (Node<E>) head;
            index = 0;
        }

        // returns true if there is a next node
        // this is always should return true if the list has something in it
        public boolean hasNext() {
            // TODO Auto-generated method stub
            return size != 0;
        }

        // advances the iterator to the next item
        // handles wrapping around back to the head automatically for you
        public E next() {
            // TODO Auto-generated method stub
            prev =  nextItem;
            nextItem = nextItem.next;
            index =  (index + 1) % size;
            return prev.item;

        }

        // removed the last node was visted by the .next() call
        // for example if we had just created a iterator
        // the following calls would remove the item at index 1 (the second person in the ring)
        // next() next() remove()
        public void remove() {
            int target;
            if(nextItem == head) {
                target = size - 1;
            } else{
                target = index - 1;
                index--;
            }
            CircularLinkedList.this.remove(target); //calls the above class
        }

    }

    // It's easiest if you keep it a singly linked list
    // SO DON'T CHANGE IT UNLESS YOU WANT TO MAKE IT HARDER
    private static class Node<E>{
        E item;
        Node<E> next;

        public Node(E item) {
            this.item = item;
        }

    }
    //n is the number of people and k is the count that you are using to determine who is "killed"
    //Cases
    // Out of bounds
    public static int Solution(int n, int k) {
        if (n <= 0 || k <= 0) { //Out of bounds check
            throw new IllegalArgumentException("n and k must be positive integers");
        }

        CircularLinkedList<Integer> list = new CircularLinkedList<>();

        for (int i = 1; i <= n; i++) {//Increment a number to every spot for each person
            list.add(i);
        }
        Iterator<Integer> iter = list.iterator();
        while (list.size > 1) {
            for (int i = 0; i < k; i++) {
                iter.next();  // Move k steps forward
            }
            System.out.println( list.toString());
            iter.remove(); // Remove the kth  person
        }
        return list.getNode(0).item;
    }

    public static void main(String[] args) {
        int n = 13; // Number of people
        int k = 2; // Count
        int survivor = Solution(n, k);
        System.out.println(survivor);
    }
}

