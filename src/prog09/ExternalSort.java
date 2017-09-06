package prog09;
import java.io.*;
import java.util.*;



public class ExternalSort {
  static Scanner openIn (File file) {
    try {
      return new Scanner(file);
    } catch (Exception e) {
      System.out.println(e);
    }
    return null;
  }
  
  static PrintWriter openOut (File file) {
    try {
      return new PrintWriter(file);
    } catch (Exception e) {
      System.out.println(e);
    }
    return null;
  }

  // Read this to make sure you understand file I/O.
  static void copy (String from, String to) {
    File inFile = new File(from);
    Scanner in = openIn(inFile);
    File outFile = new File(to);
    PrintWriter out = openOut(outFile);

    while (in.hasNextLine()) {
      String line = in.nextLine();
      out.println(line);
    }
    in.close();
    out.close();
  }

  // Merge the first n lines of in0 with the first n lines of in1 and
  // write the output to out.  in0 or in1 may have less than n lines.
  static void merge (int n, Scanner in0, Scanner in1, PrintWriter out) {
    // EXERCISE
	  Integer a = null;
	  Integer b = null;
	  int lineCounter0 = 0;
	  int lineCounter1 = 0;
	  
	  if(in0.hasNextLine() && lineCounter0 < n) {
		  a = Integer.parseInt(in0.nextLine());
		  lineCounter0++;
	  }
	  if(in1.hasNextLine() && lineCounter1 < n) {
		  b = Integer.parseInt(in1.nextLine());
		  lineCounter1++;
	  }
	  
	  while(a != null || b != null) {
		  if(a != null && b != null) {
			 if (a < b) {
				 out.println(a);
				 a = null;
			 }
			 else {
				 out.println(b);
				 b = null;
			 }
		  }
		  else {
			  if (a == null) {
				  out.println(b);
				  b = null;
			  }
			  else {
				  out.println(a);
				  a = null;
			  }
		  }
		  if(a == null && in0.hasNextLine() && lineCounter0 < n) {
			  a = Integer.parseInt(in0.nextLine());
			  lineCounter0++;
		  }
		  if(b == null && in1.hasNextLine() && lineCounter1 < n) {
			  b = Integer.parseInt(in1.nextLine());
			  lineCounter1++;
		  }
	  }
	  
	  
	  
  }

  static void sort (String from, String to) {
    File inFile = new File(from);
    File outFile = new File(to);
    
    File[] tmpFile0 = { new File("tmp00"), new File("tmp01") };
    File[] tmpFile1 = { new File("tmp10"), new File("tmp11") };
    File[][] tmpFiles = { tmpFile0, tmpFile1 };
    
    int size = 0;

    int i = 0;
    int j = 0;
    
    // Split the input into two temporary file.
    {
      Scanner in = openIn(inFile);
      PrintWriter[] outTmp = { openOut(tmpFiles[i][0]), 
                               openOut(tmpFiles[i][1]) };
      while (in.hasNextLine()) {
        String line = in.nextLine();
        outTmp[j].println(line);
        j = 1 - j;
      size++;
      }
      in.close();
      outTmp[0].close();
      outTmp[1].close();
    }

    // Merge groups of 1, 2, 4, 8, ...
    int n = 1;
    while (2 * n < size) {
      Scanner[] inTmp = { openIn(tmpFiles[i][0]), 
                          openIn(tmpFiles[i][1]) };
      PrintWriter[] outTmp = { openOut(tmpFiles[1-i][0]), 
                               openOut(tmpFiles[1-i][1]) };
      while (inTmp[0].hasNextLine() || inTmp[1].hasNextLine()) {
        merge(n, inTmp[0], inTmp[1], outTmp[j]);
        j = 1 - j;
      }
      inTmp[0].close();
      inTmp[1].close();
      outTmp[0].close();
      outTmp[1].close();
      
      i = 1 - i;
      n *= 2;
    }

    // Merge the two (sorted) temporary files.
    {
      Scanner[] inTmp = { openIn(tmpFiles[i][0]), 
                          openIn(tmpFiles[i][1]) };
      PrintWriter out = openOut(outFile);
      merge(n, inTmp[0], inTmp[1], out);
      inTmp[0].close();
      inTmp[1].close();
      out.close();
    }
  }
  
  public static void main (String[] args) {
    sort("numbers.txt", "sorted.txt");
  }
}
