package prog11;

import java.util.*;

public class ChainedHashTable<K, V> extends AbstractMap<K, V> {
  protected static class Node<K, V> implements Map.Entry<K, V> {
	K key;
	V value;
	Node<K,V> next;

        public K getKey () { return key; }
        public V getValue () { return value; }
        public V setValue (V value) { return this.value = value; }

	Node (K key, V value, Node<K,V> next) {
	    this.key = key;
	    this.value = value;
	    this.next = next;
	}
    }

    private final static int DEFAULT_CAPACITY = 5;
    private Node<K,V>[] table = new Node[DEFAULT_CAPACITY];
    private int size;

    private int hashIndex (Object key) {
	int index = key.hashCode() % table.length;
	if (index < 0)
	    index += table.length;
	return index;
    }

    private Node<K,V> find (Object key) {
	int index = hashIndex(key);
	for (Node<K,V> node = table[index]; node != null; node = node.next)
	    if (key.equals(node.key))
		return node;
	return null;
    }

    public boolean containsKey (Object key) {
	return find(key) != null;
    }

    public V get (Object key) {
	Node<K,V> node = find(key);
	if (node == null)
	    return null;
	return node.value;
    }

    public V put (K key, V value) {
	Node<K,V> node = find(key);
	if (node != null) {
	    V old = node.value;
	    node.value = value;
	    return old;
	}
	if (size == table.length)
	    rehash(2 * table.length);
	int index = hashIndex(key);
	table[index] = new Node<K,V>(key, value, table[index]);
	size++;
	return null;
    }
	
    public V remove (Object key) {
    	
	// Get the index for the key.
    	int index = hashIndex(key);
	// Deal with the case that the linked list at that index is empty.
    	if(table[index] == null) {
    		return null;
    	}
	// Deal with the case that the key belongs to the first
	// element in that list.
    	if(table[index].key.equals(key)) {
    		V old = table[index].value;
    		table[index] = table[index].next;
    		size--;
    		return old;
    	}
	// Deal with the case that the key belongs to some other
	// element in that list.
    	for(Node<K, V> node = table[index]; node != null; node = node.next) {
    		if(node.key.equals(key)) {
    			V old = node.value;
    			node = node.next;
    			return old;
    		}
    	}
    	return null;
	// Return null otherwise.
    }

    private void rehash (int newCapacity) {
    	Node<K, V> oldTable[] = table;
    	table = new Node[newCapacity];
    	Node<K, V> entry;
    	for(int i = 0; i < oldTable.length; i++) {
    		entry = oldTable[i];
    		while(entry != null) {
    			put(entry.key, entry.value);
    			entry = entry.next;
    		}
    	}
      // IMPLEMENT

    }

    private Iterator<Map.Entry<K, V>> nodeIterator () {
      return new NodeIterator();
    }

    private class NodeIterator implements Iterator<Map.Entry<K, V>> {
      // EXERCISE
    	int index;
    	Node<K, V> node = null;
    	public NodeIterator() {
    		index = 0;
    		node = null;
    		while(index < table.length && table[index] == null) {
    			index++;
    		}
    		node = table[index];
    		
    	}
    



      public boolean hasNext () {
        
        return (node != null);
      }

      public Map.Entry<K, V> next () {
        
    	Node<K, V> oldNode = node;
    	
    	if (node.next == null) {
    		index++;
    		while(index < table.length && table[index] == null) {
    			index++;
    		}
			node = node.next;
		
		
			if(index < table.length) {
			node = table[index];
			}
			else {
			node = null;
			}
    	}
    	else {
    		node = node.next;
    	}
    	
    	
    	return oldNode;
      }

      public void remove () {}
    }

    public Set<Map.Entry<K,V>> entrySet() { return new NodeSet(); }

    private class NodeSet extends AbstractSet<Map.Entry<K, V>> {
      public int size() { return size; }

      public Iterator<Map.Entry<K, V>> iterator () {
        return nodeIterator();
      }

      public void remove () {}
    }

    public String toString () {
	String ret = "-------------------------------\n";
	for (int i = 0; i < table.length; i++) {
	    ret = ret + i + ":";
	    for (Node<K,V> node = table[i]; node != null; node = node.next)
		ret = ret + " " + node.key + " " + node.value;
	    ret = ret + "\n";
	}
	return ret;
    }

    public static void main (String[] args) {
	ChainedHashTable<String, Integer> table =
	    new ChainedHashTable<String, Integer>();

	table.put("Brad", 46);
	System.out.println(table);
	table.put("Hal", 10);
	System.out.println(table);
	table.put("Kyle", 6);
	System.out.println(table);
	table.put("Lisa", 43);
	System.out.println(table);
	table.put("Lynne", 43);
	System.out.println(table);
	table.put("Victor", 46);
	System.out.println(table);
	table.put("Zoe", 6);
	System.out.println(table);
	table.put("Zoran", 76);
	System.out.println(table);

        for (String key : table.keySet())
          System.out.print(key + " ");
        System.out.println();

	table.remove("Zoe");
	System.out.println(table);
	table.remove("Kyle");
	System.out.println(table);
	table.remove("Brad");
	System.out.println(table);
	table.remove("Zoran");
	System.out.println(table);
	table.remove("Lisa");
	System.out.println(table);
    }
}
			     
