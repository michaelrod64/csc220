package prog03;
import prog02.UserInterface;
import prog02.GUI;


//estimated time is far too low. Be sure to ask about that
/**
 *
 * @author vjm
 */
public class Main {
  /** Use this variable to store the result of each call to fib. */
  public static double fibn;

  /** Determine the time in microseconds it takes to calculate the
      n'th Fibonacci number.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @return the time for the call to fib(n) in microseconds
  */
  public static double time (Fib fib, int n) {
    // Get the current time in nanoseconds.
    long start = System.nanoTime();

    // Calculate the n'th Fibonacci number.
    // Store the result in fibn.
    double fibn = fib.fib(n);
    


    // Get the current time in nanoseconds.  Using long, like start.
    long end = System.nanoTime();
    // Uncomment the following for a quick test.
     //System.out.println("start " + start + " end " + end);
  
    // Return the difference between the end time and the
    // start time divided by 1000.0 to convert to microseconds.
    return (end - start) / 1000.0;
  }

  /** Determine the average time in microseconds it takes to calculate
      the n'th Fibonacci number.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @param ncalls the number of calls to average over
      @return the average time per call
  */
  public static double averageTime (Fib fib, int n, long ncalls) {
    double totalTime = 0;
    
    // Add up the total call time for ncalls calls to time (above).
    // Use long instead of int in your declaration of the counter variable.
    
    for( long i = 0; i < ncalls; i++){
    	totalTime += time(fib, n);
    }
    


    // Return the average time.
    return (double) (totalTime / ncalls);
  }

  /** Determine the time in microseconds it takes to to calculate
      the n'th Fibonacci number ACCURATE TO THREE SIGNIFICANT FIGURES.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @return the time it it takes to compute the n'th Fibonacci number
  */
  public static double accurateTime (Fib fib, int n) {
    // Get the time using the time method above.
	double time = time(fib, n);

    // Since it is not very accurate, it might be zero.  If so set it to 0.1
	if(time == 0) {
		time = 0.1;
	}


    // Calculate the number of calls to average over that will give
    // three figures of accuracy.  You will need to "cast" it to int
    // by putting   (int)   in front of the formula.
	int ncalls = (int)((1000 * 1000) / (time * time));

    // If ncalls is 0, increase it to 1.
	if(ncalls < 1) {
		ncalls = 1;
	}


    // Get the accurate time using averageTime.
	return averageTime(fib, n, ncalls);
  }

  static void labExperiments () {
    // Create (Exponential time) Fib object and test it.
    Fib efib = new ExponentialFib();
    System.out.println(efib);
    for (int i = 0; i < 11; i++)
      System.out.println(i + " " + efib.fib(i));
    
    // Determine running time for n1 = 20 and print it out.
    int n1 = 20;
    double time1 = averageTime(efib, n1, 1000);
    System.out.println("n1 " + n1 + " time1 " + time1);
    
    int ncalls = (int)((1000* 1000) / (time1 * time1));
    		if (ncalls == 0) {
    			ncalls = 1;
    		}
    System.out.println("ncalls " + ncalls);
    double accurateTime1 = accurateTime(efib, n1);
    System.out.println("n1 " + n1 + " accurate time " + accurateTime1);
    
    // Calculate constant:  time = constant times O(n).
    double c = time1 / efib.o(n1);
    System.out.println("c " + c);
    
    // Estimate running time for n2=30.
    int n2 = 30;
    double time2est = c * efib.o(n2);
    System.out.println("n2 " + n2 + " estimated time " + time2est);
    
    int ncalls2 = (int)((1000 * 1000) / (time2est * time2est));
    if (ncalls2 == 0) {
    	ncalls2 = 1;
    }
    System.out.println("ncalls " + ncalls2);

    
    // Calculate actual running time for n2=30.
    double time2 = averageTime(efib, n2, 100);
    System.out.println("n2 " + n2 + " actual time " + time2);
    
    double accurateTime2 = accurateTime(efib, n2);
    System.out.println("n2 " + n2 + " accurate time " + accurateTime2);
    
    //Estimate running time for n3=100.
    int n3 = 100;
    double time3est = c * efib.o(n3);
    System.out.println("n3 " + n3 + " estimated time " + time3est);
    System.out.println("Which is " + time3est / (3.1536E13) + " years.");
    
  }

  private static UserInterface ui = new GUI();

  static void hwExperiments (Fib fib) {
    double c = -1;  // -1 indicates that no experiments have been run yet.
    while(true) {
    
    
      // Ask the user for an integer n.
      // Return if the user cancels.
      // Deal with bad inputs:  not positive, not an integer.
    	boolean incorrectInput = true;
    	Integer n = 1;
    	while(incorrectInput) {
    		String testVar = ui.getInfo("Enter n");
			// Ask the user before doing another experiment.  Otherwise return.
    		if (testVar == null) {
    			return;
    		}
    		try {
    			
    			n = Integer.parseInt(testVar);
    			
    			if(n <= 0) {
        			ui.sendMessage(n + " is not positive. Try again.");
        		}
    			else {
        			incorrectInput = false;
        		}
    			
    		} catch(NumberFormatException e) {
    			ui.sendMessage("java.lang.NumberFormatException: For input string: \"" + testVar + "\" Try again.");
    		}
    	
    		
    		
    	} 
    	double totalTime;
    	double estimatedTime = 0;
    	
    	// If this not the first experiment, estimate the running time for
        // that n using the value of the constant from the last time.
    	if (c != -1) {
    		
    		//estimated time is far too low. Be sure to ask about that
    		estimatedTime = c * fib.o(n);
    		// Display the estimate.
    		ui.sendMessage("Estimated time for fib(" + n + ") is " + 
    		estimatedTime + " ms.");
    	}
    	// ADD LATER: If it is greater than an hour, ask the user for
        // confirmation before running the experiment.
        // If not, the "continue;" statement will skip the experiment.
    	
    	//3600000000 microseconds = 1 hour
    	if(estimatedTime >= 3600000000.0) {
    		ui.sendMessage("Estimated time is more than an hour\n"
    				+ "I am going to ask if you want to do it.");
    		String[] commands = {
    				"yes", "no"
    		};
    		int d = ui.getCommand(commands);
    		switch(d) {
    		   	case 0:
    		   	// Now, calculate the (accurate) running time for that n.
    		    // Calculate the constant c.
    		   		totalTime = accurateTime(fib, n);
    		   		c =  totalTime / fib.o(n);
    		        // Display fib(n) and the actual running time.
    		   		ui.sendMessage("fib(" + n + ")=" + fib.fib(n) + " in " + totalTime + " ms.");
    		    		break;
    		    	case 1:
    		    		break;
    		}
    	}
    	else{ 
    		// Now, calculate the (accurate) running time for that n.
		    // Calculate the constant c.
    	totalTime = accurateTime(fib, n);
		c = totalTime / fib.o(n);
		// Display fib(n) and the actual running time.
   		ui.sendMessage("fib(" + n + ")=" + fib.fib(n) + " in " + totalTime + " ms.");
    	}
    	}
    }		
      
      
     
      
      
      
      
      
     
    

  static void hwExperiments () {
    // In a loop, ask the user which implementation of Fib or exit,
    // and call hwExperiments (above) with a new Fib of that type.
	  
	String[] commands = {
		"ExponentialFib",
		"LinearFib",
		"LogFib",
		"ConstantFib",
	"EXIT"};
	    	
	while(true) {
	    Fib fib;
	    int c = ui.getCommand(commands);
	    switch(c) {
	    	case 0:
	    		fib = new ExponentialFib();
	    		hwExperiments(fib);
	    		break;
	    	case 1:
	    		fib = new LinearFib();
	    		hwExperiments(fib);
	    		break;
	    	case 2:
	    		fib = new LogFib();
	    		hwExperiments(fib);
	    		break;
	    	case 3:
	    		fib = new ConstantFib();
	    		hwExperiments(fib);
	    		break;
	    	case 4:
	    		return;	
	    }
	}
  }
	    	
	    	
	    	
  

  /**
   * @param args the command line arguments
   */
  public static void main (String[] args) {
    labExperiments();
    hwExperiments();
  }
}
