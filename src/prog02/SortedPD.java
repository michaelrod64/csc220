package prog02;

import java.io.*;

/**
 *
 * @author vjm
 */
public class SortedPD extends ArrayBasedPD {
	
	 public String addOrChangeEntry (String name, String number) {
		    int index = find(name);
		    modified = true;
		    if (index < size && theDirectory[index].getName().equals(name)) {
		      String oldNumber = theDirectory[index].getNumber();
		      theDirectory[index].setNumber(number);
		      return oldNumber;
		    } else {
		      if(size >= theDirectory.length) {
		    	  reallocate();
		      }
		      for(int i = size; i > index; i--) {
		    	  theDirectory[i] = theDirectory[i - 1];
		      }
		      theDirectory[index] = new DirectoryEntry(name, number);
		      size++;
		      return null;
		    }
		  }

		  /** Find an entry in the directory.
		      @param name The name to be found
		      @return the index of the entry with that name or, if it is not
		      there, where it should be added
		  */
		  protected int find(String name) {
			  int first = 0;
			  int last = size;
			  
			  while (last != first){
				  int middle = (last + first) / 2;
				  if(theDirectory[middle].getName().compareTo(name) == 0) {
					  first = middle;
					  last = middle;
				  }
				  else if (theDirectory[middle].getName().compareTo(name) > 0) {
					  last = middle;
				  }
				  else {
					  first = middle + 1;
				  }
			  }
			  return last;
		  }
		  
		  public String removeEntry (String name) {
			    
			    int index = find(name);
			    if (index < size && theDirectory[index].getName().equals(name)) {
			      
			    	DirectoryEntry entry = theDirectory[index];
			    	for (int i = index; i < size -1; i++ ) {
			    		theDirectory[i] = theDirectory[i + 1];
			    	}
			        size--;
			      modified = true;
			      return entry.getNumber();
			    }
			    else
			      return null;
			  }
		  
		  
		  
		 
	
		
}
