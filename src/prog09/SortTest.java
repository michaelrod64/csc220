package prog09;
import java.util.Random;

public class SortTest<E extends Comparable<E>> {
  public void test (Sorter<E> sorter, E[] array) {
    E[] copy = array.clone();
    sorter.sort(copy);
    System.out.println(sorter);
    for (int i = 0; i < copy.length; i++)
      System.out.print(copy[i] + " ");
    System.out.println();
  }
  
  public static void main (String[] args) {
    Integer[] array = { 3, 1, 4, 1, 5, 9, 2, 6 };
    Random random = new Random(0);
    if (args.length > 0) {
      // Print out command line argument if there is one.
      System.out.println("args[0] = " + args[0]);

      // Create a random object to call random.nextInt() on.
      

      // Make array.length equal to args[0] and fill it with random
      // integers:

    }
    
    Sorter<Integer> insertion = new InsertionSort<Integer>();
    Sorter<Integer> heap = new HeapSort<Integer>();
    Sorter<Integer> quick = new QuickSort<Integer>();
    Sorter<Integer> merge = new MergeSort<Integer>();
    

    SortTest<Integer> tester = new SortTest<Integer>();
    tester.test(insertion, array);
     tester.test(heap, array);
     tester.test(quick, array);
     tester.test(merge, array);
     int n;
     if(args.length > 0) {
    	 n = Integer.parseInt(args[0]);
     }
     else {
    	 n = 10; //default value
     }
     
     while(n < 1000000) {
    	 Integer[] data = new Integer[n];
    	 for(int i = 0; i < n; i++) {
    		 data[i] = random.nextInt();
    	 }
    	 Integer[] duplicate = data.clone();
    	 
    	 double start = Math.pow(10, -6) * System.nanoTime();
    	 insertion.sort(duplicate);
    	 double end = Math.pow(10, -6) * System.nanoTime();
    	 double time = end - start;
    	 System.out.println("InsertionSort took " + time + "ms to sort at size = " + n );
    	 if (time > 4000) {
    		 System.out.println("The next size will take too long, so I am going to skip it");
    		 break;
    	 }
    	 n = 2 * n;
     }
     
     if(args.length > 0) {
    	 n = Integer.parseInt(args[0]);
     }
     else {
    	 n = 10; //default value
     }
     
     
     
     while(n < 100000000) {
    	 Integer[] data = new Integer[n];
    	 for(int i = 0; i < n; i++) {
    		 data[i] = random.nextInt();
    	 }
    	 Integer[] duplicate = data.clone();
    	 
    	 double start = Math.pow(10, -6) * System.nanoTime();
    	 heap.sort(duplicate);
    	 double end = Math.pow(10, -6) * System.nanoTime();
    	 double time = end - start;
    	 System.out.println("HeapSort took " + time + "ms to sort at size = " + n );
    	 if (time > 4000) {
    		 System.out.println("The next size will take too long, so I am going to skip it");
    		 break;
    	 }
    	 n = 2 * n;
     }
     
     
     if(args.length > 0) {
    	 n = Integer.parseInt(args[0]);
     }
     else {
    	 n = 10; //default value
     }
     
     while(n < 100000000) {
    	 Integer[] data = new Integer[n];
    	 for(int i = 0; i < n; i++) {
    		 data[i] = random.nextInt();
    	 }
    	 Integer[] duplicate = data.clone();
    	 
    	 double start = Math.pow(10, -6) * System.nanoTime();
    	 quick.sort(duplicate);
    	 double end = Math.pow(10, -6) * System.nanoTime();
    	 double time = end - start;
    	 System.out.println("QuickSort took " + time + "ms to sort at size = " + n );
    	 if (time > 4000) {
    		 System.out.println("The next size will take too long, so I am going to skip it");
    		 break;
    	 }
    	 n = 2 * n;
     }
     
     if(args.length > 0) {
    	 n = Integer.parseInt(args[0]);
     }
     else {
    	 n = 10; //default value
     }
     
     
     while(n < 100000000) {
    	 Integer[] data = new Integer[n];
    	 for(int i = 0; i < n; i++) {
    		 data[i] = random.nextInt();
    	 }
    	 Integer[] duplicate = data.clone();
    	 
    	 double start = Math.pow(10, -6) * System.nanoTime();
    	 merge.sort(duplicate);
    	 double end = Math.pow(10, -6) * System.nanoTime();
    	 double time = end - start;
    	 System.out.println("MergeSort took " + time + "ms to sort at size = " + n );
    	 if (time > 4000) {
    		 System.out.println("The next size will take too long, so I am going to skip it");
    		 break;
    	 }
    	 n = 2 * n;
     }
     
    
 
     
     
     
     
     
     
     
     
  }
}

class InsertionSort<E extends Comparable<E>>
  implements Sorter<E> {
  public void sort (E[] array) {
    for (int n = 0; n < array.length; n++) {
      E data = array[n];
      int i = n;
      
      while((i > 0) && (array[i-1].compareTo(data) > 0)) {
    	  array[i] = array[i-1];
    	  i--;
    	  
      }
      // while array[i-1] > data move array[i-1] to array[i] and
      // decrement i
      array[i] = data;
    }
  }
}

class HeapSort<E extends Comparable<E>>
  implements Sorter<E> {
  
  private E[] array;
  
  private void swap (int i, int j) {
    E data = array[i];
    array[i] = array[j];
    array[j] = data;
  }
  
  public void sort (E[] array) {
    this.array = array;
    
    for (int i = parent(array.length - 1); i >= 0; i--)
      swapDown(i, array.length - 1);
    
    for (int n = array.length - 1; n >= 0; n--) {
      swap(0, n);
      swapDown(0, n - 1);
    }
  }
  
  public void swapDown (int root, int end) {
    // Calculate the left child of root.
	int leftChild = left(root);
	int rightChild;
	int biggerChild;
  
    // while the left child is still in the array
	while(leftChild < end) {
	//  calculate the right child
		rightChild = right(root);
		//   if the right child is in the array and 
	    //      it is bigger than than the left child
	    //     bigger child is right child
		if (rightChild < end && array[rightChild].compareTo(array[leftChild]) > 0) {
			biggerChild = rightChild;
		}
		//   else
	    //     bigger child is left child
		else {
			biggerChild = leftChild;
		}
		
		//   if the root is not less than the bigger child
	    //     return
		if(array[root].compareTo(array[biggerChild]) >= 0) {
			return;
		}
		//   swap the root with the bigger child
		swap(root, biggerChild);
		
		//   update root and calculate left child
		root = biggerChild;
		leftChild = left(root);
	}

    
    
    
    
   
  }
  
  private int left (int i) { return 2 * i + 1; }
  private int right (int i) { return 2 * i + 2; }
  private int parent (int i) { return (i - 1) / 2; }
}

class QuickSort<E extends Comparable<E>>
  implements Sorter<E> {
  
  private E[] array;
  private void swap (int i, int j) {
    E data = array[i];
    array[i] = array[j];
    array[j] = data;
  }
  
  public void sort (E[] array) {
    this.array = array;
    sort(0, array.length-1);
  }
  
  private void sort(int left, int right) {
    if (left >= right)
      return;
    
    swap(left, (left + right) / 2);
    
    E pivot = array[left];
    int a = left + 1;
    int b = right;
    while (a <= b) {
    	boolean swap = true;
    	if(array[a].compareTo(pivot) <= 0) {
    		a++;
    		swap = false;
    	}
    	if (array[b].compareTo(pivot) > 0) {
    		b--;
    		swap = false;
    	}
    	if(swap) {
    		swap(a, b);
    	}
    	
      // Move a forward if array[a] <= pivot
      // Move b backward if array[b] > pivot
      // Otherwise swap array[a] and array[b]
    }
    
    swap(left, b);
    
    sort(left, b-1);
    sort(b+1, right);
  }
}

class MergeSort<E extends Comparable<E>>
  implements Sorter<E> {
  
  private E[] array, array2;
  
  public void sort (E[] array) {
    this.array = array;
    array2 = array.clone();
    sort(0, array.length-1);
  }
  
  private void sort(int left, int right) {
    if (left >= right)
      return;
    
    int middle = (left + right) / 2;
    sort(left, middle);
    sort(middle+1, right);
    
    int i = left;
    int a = left;
    int b = middle+1;
    while (a <= middle || b <= right) {
    	// If both a <= middle and b <= right
    	if (a <= middle && b <= right) {
    		// copy the smaller of array[a] or array[b] to array2[i]
    		if (array[a].compareTo(array[b]) < 0) {
    			array2[i++] = array[a++];
    			
    		}
    		else {
    			array2[i++] = array[b++];
    		}
    	}
    	else {
    		if (a <= middle) {
    			array2[i++] = array[a++];
    		}
    		else {
    			array2[i++] = array[b++];
    		}
    		
    	}
      
      
      // Otherwise just copy the remaining elements to array2
    }
    System.arraycopy(array2, left, array, left, right - left + 1);
    
  }
}
