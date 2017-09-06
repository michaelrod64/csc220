package prog08;
import java.util.*;

public class Heap <E> extends AbstractQueue<E> {
  private List<E> theData = new ArrayList<E>();

  public Heap () {}
  
  /** An optional reference to a Comparator object. */
  Comparator < E > comparator = null; //ADD your comparator possibly

  /** Creates a heap-based priority queue with that orders its
       elements according to the specified comparator.
       @param comp The comparator used to order this priority queue
   */
  public Heap (Comparator < E > comp) {
    comparator = comp;
  }
  public int getLeftChild(int i) {
	  i = (2 * i) + 1;
	  return i;
  }
  public int getRightChild(int i) {
	  i = (2 * i) + 2;
	  return i;
  }
  public int getParent(int j) {
	  j = (j - 1) / 2;
	  return j;
  }
  public int size () { return theData.size(); }

  /** Compare the items with index i and index j in theData using
      either a Comparator object's compare method or their natural
      ordering using method compareTo.
      @param i index of first item in theData
      @param j index of second item in theData
      @return Negative int if first less than second,
              0 if first equals second,
              positive int if first > second
      @throws ClassCastException if items are not Comparable
   */
  private int compare (int i, int j) {
    if (comparator == null)
      return ((Comparable<E>) theData.get(i)).compareTo(theData.get(j));
    else
      return comparator.compare(theData.get(i), theData.get(j));
  }

  /** Swap the items with index i and index j in theData.
      @param i index of first item in theData
      @param j index of second item in theData
   */
  private void swap (int i, int j) {
    E temp = theData.get(i);
    theData.set(i, theData.get(j));
    theData.set(j, temp);
  }

  /** Insert an item into the priority queue.
      pre:  theData is in heap order.
      post: The item is in the priority queue and
            theData is in heap order.
      @param item The item to be inserted
      @throws NullPointerException if the item to be inserted is null.
   */
  public boolean offer (E item) {
    if (item == null)
      throw new NullPointerException();
    
    theData.add(item);

    int child = size() - 1;
    int parent = (child - 1) / 2;

    while (child > 0 && compare(child, parent) < 0) {
      swap(parent, child);
      child = parent;
      parent = (child - 1) / 2;
    }

    return true;
  }

  /** Remove an item from the priority queue
      pre: The ArrayList theData is in heap order.
      post: Removed smallest item, theData is in heap order.
      @return The item with the smallest priority value or null if empty.
   */
  public E poll() {
    // IMPLEMENT
    // Return null if queue is empty.
	if(size() == 0)
    return null;
	// Save the top of the heap.
	E top = theData.get(0);
    // If only one item then remove it and you are done.
	if(size() == 1) {
		return theData.remove(0);
	}
	/* Remove the last item from the ArrayList and place it into
    the first position. */
	else {
		
		E last = theData.remove(size() - 1);
		theData.set(0, last);	
	}
	int parent = 0;
	int leftChild = getLeftChild(parent); 
    int rightChild = getRightChild(parent);
    while ((rightChild < size() && compare(rightChild, parent) < 0) || (leftChild < size() 
    		&& (compare(leftChild, parent) < 0 ))) {
      if (rightChild < size() && compare(leftChild, rightChild) > 0  ) {
    	  swap(parent, rightChild);
    	  parent = rightChild;
      }
      else {
    	  swap(parent, leftChild);
    	  parent = leftChild;
      }
      leftChild = getLeftChild(parent);
      rightChild = getRightChild(parent);
      
    }
    
    return top;
    /* Use swaps to move that item into the correct position. */

    /* See the lab notes for a hint on this loop. */

  }

  /** Remove a specified item from the priority queue.
      If it appears more than once, only one is removed.
      @param o The item to be removed.
      @returns true if item remove, false if it was not there.
  */
  public boolean remove (Object o) {
    // Get the index of the object to be removed.
    int index = theData.indexOf(o);
    // If it is not there, done.
    if (index == -1)
      return false;

    // If it is the last item, just remove it.
    if (index == theData.size() - 1) {
      theData.remove(index);
      return true;
    }
    
    
    theData.set(index, theData.remove(theData.size() - 1));
    
    int child;
    child = index;
    int parent = (child - 1) / 2;
    
    //offer while loop
    while (child > 0 && compare(child, parent) < 0) {
        swap(parent, child);
        child = parent;
        parent = (child - 1) / 2;
      }
    
    parent = index; //update parent and child variables
    int leftChild = (2 * parent + 1);
    int rightChild = (2 * parent + 2);
    
    
    //poll while loop
    while ((rightChild < size() && compare(rightChild, parent) < 0) || (leftChild < size() 
    		&& (compare(leftChild, parent) < 0 ))) {
      if (rightChild < size() && compare(leftChild, rightChild) > 0  ) {
    	  swap(parent, rightChild);
    	  parent = rightChild;
      }
      else {
    	  swap(parent, leftChild);
    	  parent = leftChild;
      }
      leftChild = getLeftChild(parent);
      rightChild = getRightChild(parent);
      
    }
    
    
    //offer and poll while loops
    //make sure to have right parent and child for each while loop
    
    // Copy the item at size-1 to index and then remove the item at size-1.

    // If the item at index is better than its parent, swap it upward
    // as in offer.  Otherwise, swap it downward as in poll().

    return true;
  }

  public String toString () {
    return toString(0, 0);
  }
  
  private String toString (int root, int indent) {
    if (root >= size())
      return "";
    String ret = toString(2 * root + 2, indent + 2);
    for (int i = 0; i < indent; i++)
      ret = ret + "  ";
    ret = ret + theData.get(root) + "\n";
    ret = ret + toString(2 * root + 1, indent + 2);
    return ret;
  }

  public static void main (String[] args) {
    int[] data = { 3, 1, 4, 1, 5, 9, 2, 6 };
    Heap<Integer> queue = new Heap<Integer>();
    
    for (int i = 0; i < data.length; i++) {
      queue.offer(data[i]);
      System.out.println(queue);
      System.out.println("----------------");
      System.out.println();
    }
    for (int i = 0; i < data.length; i++) {
      queue.poll();
      System.out.println(queue);
      System.out.println("----------------");
      System.out.println();
    }
  }
@Override
public E peek() {
	if(theData.get(0) != null)
		return theData.get(0);
	// TODO Auto-generated method stub
	return null;
}
@Override
public Iterator<E> iterator() {
	return theData.iterator();
	// TODO Auto-generated method stub
}
}

