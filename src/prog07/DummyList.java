package prog07;
import java.util.*;

public class DummyList <K extends Comparable<K>, V> 
  extends AbstractMap<K, V> {

  protected static class Node <K extends Comparable<K>, V>
    implements Map.Entry<K, V> {

    K key;
    V value;
    Node<K, V> next;
    
    Node (K key, V value, Node next) {
      this.key = key;
      this.value = value;
      this.next = next;
    }
    
    public K getKey () { return key; }
    public V getValue () { return value; }
    public V setValue (V newValue) {
      V oldValue = value;
      value = newValue;
      return oldValue;
    }
  }
  
  int size = 0;
  protected Node<K, V> dummy = new Node<K, V>(null, null, null);
  
  protected class Iter implements Iterator<Map.Entry<K, V>> {
    Node<K, V> previous = dummy;
    
    public boolean hasNext () { 
      // STEP 5:  Replace the following line with the correct answer.
      return previous.next != null;
    	//return false;
    }
    
    public Map.Entry<K, V> next () {
    	previous = previous.next;
    	return previous;
      // STEP 5: Move previous forward and replace the following line
      // with the correct answer.
      //return null;
    }
    
    public void remove () {
      throw new UnsupportedOperationException();
    }
  }
  
  public boolean containsKey (Object keyAsObject) {
    K key = (K) keyAsObject;
    return containsKey(key, findPrevious(key, dummy));
  }
  //should be protected
  protected boolean containsKey (K key, Node<K, V> previous) {
    return previous.next != null && key.equals(previous.next.key);
  }
  
  protected Node<K, V> findPrevious (K key, Node start) {
	//if(start != null)
	//System.out.print(start.key + " ");
    Node<K, V> node = (Node<K, V>) start;
    	
    while (node.next != null && node.next.key.compareTo(key) < 0){ 
    	node = node.next;
    }
    // STEP 6:  Move node forward until it is previous to key.

    return node;
  }
  
  public V get (Object keyAsObject) {
    K key = (K) keyAsObject;
    return get(key, findPrevious(key, dummy));
  }
  
  protected V get (K key, Node<K, V> previous) {
	  if (previous.next != null && previous.next.key.equals(key)) {
		  return previous.next.getValue();
	  }
    // STEP 7:  Implement protected get method.

    return null;
  }
  
  public V remove (Object keyAsObject) {
	  K key = (K) keyAsObject;
	  return remove(key, findPrevious(key, dummy));
  }
  
  protected V remove (K key, Node<K, V> previous) {
	  if (previous.next != null && previous.next.key.equals(key)) {
		  size--;
		  V value = previous.next.getValue();
		  previous.next = previous.next.next;
		  return value;
	  }
	  return null;
  }

  // STEP 8:  Implement remove methods.

  
  public V put(K key, V value) {
	  return put(key, value, findPrevious(key, dummy));
  }
  
  protected V put (K key, V value, Node<K, V> previous) {
	  if (previous.next != null && previous.next.key.equals(key)) {
		  V oldValue = previous.next.getValue();
		  previous.next.setValue(value);
		  return oldValue;
	  }
	  Node<K, V> newNode = new Node(key, value, previous.next);
	  previous.next = newNode;
	  size++;
	  return null;
  }
  // STEP 9:  Implement put methods.

  public boolean isEmpty () { return size == 0; }
  
  protected class Setter extends AbstractSet<Map.Entry<K, V>> {
    public Iterator<Map.Entry<K, V>> iterator () {
      return new Iter();
    }
    
    public int size () { return size; }
  }
  
  public Set<Map.Entry<K, V>> entrySet () { return new Setter(); }
  
  protected void makeTestList () {
    for (int i = 24; i > 0; i -= 2) {
      dummy.next = new Node("" + (char)('A' + i), i, dummy.next);
      size++;
    }
  }

  public static void main (String[] args) {
    DummyList<String, Integer> map = new DummyList<String, Integer>();
    map.makeTestList();
    System.out.println(map);
    System.out.println("containsKey(A) = " + map.containsKey("A"));
    System.out.println("containsKey(C) = " + map.containsKey("C"));
    System.out.println("containsKey(L) = " + map.containsKey("L"));
    System.out.println("containsKey(M) = " + map.containsKey("M"));
    System.out.println("containsKey(Y) = " + map.containsKey("Y"));
    System.out.println("containsKey(Z) = " + map.containsKey("Z"));

    System.out.println("get(A) = " + map.get("A"));
    System.out.println("get(C) = " + map.get("C"));
    System.out.println("get(L) = " + map.get("L"));
    System.out.println("get(M) = " + map.get("M"));
    System.out.println("get(Y) = " + map.get("Y"));
    System.out.println("get(Z) = " + map.get("Z"));

    System.out.println("remove(A) = " + map.remove("A"));
    System.out.println("remove(C) = " + map.remove("C"));
    System.out.println("remove(L) = " + map.remove("L"));
    System.out.println("remove(M) = " + map.remove("M"));
    System.out.println("remove(Y) = " + map.remove("Y"));
    System.out.println("remove(Z) = " + map.remove("Z"));

    System.out.println(map);

    System.out.println("put(A,7) = " + map.put("A", 7));
    System.out.println("put(A,9) = " + map.put("A", 9));
    System.out.println("put(M,17) = " + map.put("M",17));
    System.out.println("put(M,19) = " + map.put("M",19));
    System.out.println("put(Z,3) = " + map.put("Z",3));
    System.out.println("put(Z,20) = " + map.put("Z",20));

    System.out.println(map);
  }
}
