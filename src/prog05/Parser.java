package prog05;

import java.util.Stack;
import java.util.Scanner;
import prog02.UserInterface;
import prog02.GUI;

public class Parser {
  static final String OPERATORS = "()+-*/u^"; //ADD a u(negative sign) with precedence 4
  static final int[] PRECEDENCE = { -1, -1, 1, 1, 2, 2, 3, 4 };
  boolean lastNum = false;
  String input = null;
  int index = 0;
  Stack<Character> operatorStack = new Stack<Character>();
  Stack<Double> numberStack = new Stack<Double>();
  static UserInterface ui = new GUI();

  boolean atEndOfInput () {
    while (index < input.length() &&
           Character.isWhitespace(input.charAt(index)))
      index++;
    return index == input.length();
  }

  boolean isNumber () {
    return Character.isDigit(input.charAt(index));
  }

  double getNumber () {
    int index2 = index;
    while (index2 < input.length() &&
           (Character.isDigit(input.charAt(index2)) ||
            input.charAt(index2) == '.'))
      index2++;
    double d = 0;
    try {
      d = Double.parseDouble(input.substring(index, index2));
    } catch (Exception e) {
      System.out.println(e);
    }
    index = index2;
    return d;
  }

  char getOperator () {
    char op = input.charAt(index++);
    if (OPERATORS.indexOf(op) == -1)
      System.out.println("Operator " + op + " not recognized.");
    return op;
  }

  String numberStackToString () {
	  // EXERCISE
	    // Put every element of numberStack into helperStack
	    // You will need to use a loop.  What kind?
	    // What condition? When can you stop moving elements out of numberStack?
	    // What method do you use to take an element out of numberStack?
	    // What method do you use to put that element into helperStack.

	    // Now put them back, but also add each one to s:
	    // s = s + " " + number;
	  
	  
	  
    String s = "numberStack: ";
    Stack<Double> helperStack = new Stack<Double>();
    while(!numberStack.empty()){
    	helperStack.push(numberStack.pop());
    }
    while(!helperStack.empty()) {
    	s = s + " " + helperStack.peek();
    	numberStack.push(helperStack.pop());
    }
    
    

    return s;
  }

  String operatorStackToString () {
	// EXERCISE    
    String s = "operatorStack: ";
    Stack<Character> helperStack = new Stack<Character>();
    while(!operatorStack.empty()) {
    	helperStack.push(operatorStack.pop());
    }
    while(!helperStack.empty()){
    	s = s + " " + helperStack.peek();
    	operatorStack.push(helperStack.pop());
    }
    return s;
    

  }

  void displayStacks () {
    ui.sendMessage(numberStackToString() + "\n" +
                   operatorStackToString());
  }

  double evaluate (String expr) {
    input = expr;
    index = 0;
    while (!operatorStack.empty()) operatorStack.pop();
    while (!numberStack.empty()) numberStack.pop();
    lastNum = false;
    
    while (!atEndOfInput()) {
      if (isNumber()) {
        numberStack.push(getNumber());
        displayStacks();
        lastNum = true;
      }
      else {
    	  
    	char tempOperator = getOperator();
    	processOperator(tempOperator);
    	lastNum = (tempOperator == ')');
    	
        displayStacks();
        
      }
    }
    while(!operatorStack.empty()) {
    	evaluateOperator();
    	displayStacks();
    }
    
    return numberStack.pop();

   
  }

  int precedence (char op) {
    return PRECEDENCE[OPERATORS.indexOf(op)];
  }
  
  double evaluateOperator(double a, char op, double b) {
	  
	  
	  switch(op) {
	  case '+': return a + b;
	  case '-': return a - b;
	  case '*': return a * b;
	  case '/': if (b!= 0) {
		  return a / b;
	  			}
	  			else {
	  				throw new IllegalArgumentException("Argument 'divisor' is 0");
	  			}
	  case '^': return Math.pow(a, b);
	  }
	  System.out.println("operator is not known.");
	  return 0;
  }
  
  void evaluateOperator() {
	  char op = operatorStack.pop();
	  if (op != 'u') {
	  double second = numberStack.pop();
	  double first = numberStack.pop();
	  
	  numberStack.push(evaluateOperator(first, op, second));
	  }
	  else {
		  numberStack.push(-numberStack.pop());
		  
	  }
	  /*double result;
	  
	  switch(op) {
	  case '+': result = first + second;
	  	break;
	  case '-': result = first - second;
	  	break;
	  case '*': result = first * second;
	  	break;
	  case '/': if (second!= 0) {
		  result = first / second;
	  			}
	  			else {
	  				throw new IllegalArgumentException("Argument 'divisor' is 0");
	  			}
	  	break;
	  default : result = Math.pow(first, second);
	  }
	  
	  numberStack.push(result);
	  displayStacks();
	  return;
	  */
  }
  
  void processOperator(char op) {
	  	
	  if (op == '(') {
	  		operatorStack.push(op);
	  	}
	  	else if (op == ')') {
	  		while (!operatorStack.empty() && operatorStack.peek() != '(') {
	  			evaluateOperator();
	  		}
	  		operatorStack.pop();
	  	}
	  	else if(op == '-' && !lastNum) {
	  		op = 'u';
	  		operatorStack.push(op);
	  	}
	  	else {
		  while (!operatorStack.empty() && precedence(op) <= precedence(operatorStack.peek())) {
			  
			  evaluateOperator();
		  }
		 
		  operatorStack.push(op);
	  	}
	  
  }
  
  public static void main (String[] args) {
    Parser parser = new Parser();

    while (true) {
      String line = ui.getInfo("Enter arithmetic expression or cancel.");
      if (line == null)
        return;

     try {
        double result = parser.evaluate(line);
        ui.sendMessage(line + " = " + result);
      } catch (Exception e) {
        System.out.println(e);
      }
    }
  }
}
