package prog04;

public class SortedDLLPD extends DLLBasedPD {
	
	public String addOrChangeEntry (String name, String number) {
	    modified = true;
	    DLLEntry entry = find(name);
	    
	    if(entry == null && tail == null) {
	    	entry = new DLLEntry(name, number);
	    	tail = head = entry;
	    }
	    else if(entry == null) {
	    	entry = new DLLEntry(name, number);
	    	tail.setNext(entry);
	    	entry.setPrevious(tail);
	    	tail = entry;
	    }
	    else if (entry.getName().equals(name)) {
	      String oldNumber = entry.getNumber();
	      entry.setNumber(number);
	      return oldNumber;
	    } else {
	      DLLEntry next = entry;
	      DLLEntry previous = entry.getPrevious();
	      entry = new DLLEntry(name, number);
	      if (previous == null) {
	    	  head = entry;
	      }
	      else {
	    	  previous.setNext(entry);
	      } 
	      next.setPrevious(entry);
	      entry.setNext(next);
	      entry.setPrevious(previous);
	      
	    }
	    return null;
	  }
	    
	  /** Find an entry in the directory.
	      @param name The name to be found
	      @return The entry with the same name or null if it is not there.
	  */
	  protected DLLEntry find (String name) {
	    // EXERCISE
		  DLLEntry entry = head;
		  
		  while(entry != null && entry.getName().compareToIgnoreCase(name) < 0) {	
			  entry = entry.getNext();
		  }
		  return entry;
		  
	  }
	  
	  /** Remove an entry from the directory.
	      @param name The name of the person to be removed
	      @return The current number. If not in directory, null is
	      returned
	  */
	  public String removeEntry (String name) {
	    // Call find to find the entry to remove.
	    DLLEntry entry = find(name);
	    // If it is not there, forget it!
	    if ((entry == null) || !(entry.getName().equals(name))) {
	      return null;
	    }
	    // Get the next entry and the previous entry.
	    // EXERCISE
	    DLLEntry previous = entry.getPrevious();
	    DLLEntry next = entry.getNext();
	    
	    // Two cases:  previous is null (entry is head) or not
	    // EXERCISE
	    
	    
	    if (previous == null){
	    	head = next;
	    }
	    else {
	    	previous.setNext(next);
	    }
	    // Two cases:  next is null (entry is tail) or not
	    // EXERCISE
	    if (next == null) {
	    	tail = previous;
	    }
	    else {
	    	next.setPrevious(previous);
	    }
	    
	    

	    modified = true;
	    return entry.getNumber();
	  }
	
	
	
	
	
	
	
	
	
	
	
	

}
