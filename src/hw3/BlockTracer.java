package hw3;
/**
 * This class prompts the user to enter a file name and then scans the file The output is all the variable name.
 *
 * @author Shengnan You
 *  e-mail: shengnan.you@stonybrook.edu
 *  SBU ID:112361646
 *  HW #3
 *  CSE 214, Rec 04
 */
import java.io.*;
import java.util.Scanner;

public class BlockTracer {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter a file name: ");
        String fileName = input.nextLine();
        File file = new File(fileName);
        Stack stack = new Stack();
        Stack tempStack = new Stack();
        Boolean flag = true;
        try {
            Scanner stdin = new Scanner(file);
            do {
                String data = stdin.nextLine();
                data = data.trim();
                //handle the situation blank space
                if(data.length()==0)
                    continue;
                // handle situation "{"
                if (data.charAt(0) == '{') {
                    Block block = new Block();
                    stack.push(block);
                }
                // handle situation "}"
                if(data.charAt(0) == '}'){
                    stack.pop();
                }

                // handle situation of lines containing the word "int "
                if ( data.length()>=5 && data.contains("int ") && (data.contains(",")||data.indexOf(";")==5||data.contains("="))) {
                    if((data.contains(",")||data.indexOf(";")==5||data.contains("="))) {
                        String f = data.substring(data.indexOf("int "), data.indexOf(";"));
                        String temp = f.substring(4);
                        String[] token = temp.split(",");
                        for (int k = 0; k < token.length; k++) {
                            token[k] = token[k].trim();
                            if (token[k].contains("=")) {
                                String x = token[k];
                                String name = x.substring(0, x.indexOf("=") - 1);
                                int value = 0;
                                if (isNumeric(x.substring(x.indexOf("=") + 2))) {
                                    value = Integer.parseInt(x.substring(x.indexOf("=") + 2));
                                    Variable var = new Variable(name, value);
                                    stack.peek().addVariable(var);
                                } else {
                                    int operator1;
                                    int operator2;
                                    String[] o = x.substring(x.indexOf("=") + 2).split(" ");
                                    if (isNumeric(o[0]))
                                        operator1 = Integer.parseInt(o[0]);
                                    else
                                        operator1 = stack.peek().findValue(o[0]);
                                    if (isNumeric(o[2]))
                                        operator2 = Integer.parseInt(o[2]);
                                    else
                                        operator2 = stack.peek().findValue(o[2]);

                                    value = operator1 + operator2;
                                    Variable var = new Variable(name, value);
                                    stack.peek().addVariable(var);
                                }
                            }
                            else
                                stack.peek().addUnknownVariable(token[k]);
                        }
                    }
                    // handle situation "print local in the same line of int declaration"
                    if(data.contains("LOCAL")){
                        System.out.printf("%-20s%-20s","Variable Name", "Initial Value");
                        System.out.println();
                        Variable[] x = stack.peek().getData();
                        if(stack.peek().getSize()!=0)
                            for (int i = 0; i < stack.peek().getSize(); i++) {
                                System.out.println(x[i].toString());
                            }
                        else
                            System.out.println("No local variables to print.");
                    }

                    // handle situation "print variable name in the same line of int declaration"
                    else if (data.contains("/*$print")) {
                        data = data.trim();
                        String varName = data.substring(data.indexOf("t") + 2, data.lastIndexOf("*"));
                        Variable[] x = stack.peek().getData();
                        System.out.printf("%-20s%-20s", "Variable Name", "Initial Value");
                        System.out.println();
                        boolean found = false;
                        while(!stack.isEmpty() ) {
                            if(found)
                                break;
                            Variable[] y = stack.peek().getData();
                            for (int i = 0; i < stack.peek().getSize(); i++) {
                                if(y[i].getName().equals(varName)){
                                    System.out.println(y[i].toString());
                                    found = true;
                                }
                            }
                            Block tempBlock = stack.pop();
                            tempStack.push(tempBlock);
                        }
                        if(!found)
                            System.out.println("Variable not found: "+varName);
                        while(!tempStack.isEmpty()){
                            Block block = tempStack.pop();
                            stack.push(block);
                        }
                    }
                    System.out.println();
                }
                //  handle the situation "print all local variable name "
                if(data.contains("LOCAL") && !data.contains("=")){
                    System.out.printf("%-20s%-20s","Variable Name", "Initial Value");
                    System.out.println();
                    Variable[] x = stack.peek().getData();
                    if(stack.peek().getSize()!=0)
                        for (int i = 0; i < stack.peek().getSize(); i++) {
                            System.out.println(x[i].toString());
                        }
                    else
                        System.out.println("No local variables to print.");
                    System.out.println();
                }

                // handle the situation "print variable name  "
                else if (data.contains("/*$print") && !data.contains("=")) {
                    data = data.trim();
                    String varName = data.substring(data.indexOf("t") + 2, data.lastIndexOf("*"));
                    Variable[] x = stack.peek().getData();
                    System.out.printf("%-20s%-20s", "Variable Name", "Initial Value");
                    System.out.println();
                    boolean found = false;
                    while(!stack.isEmpty() ) {
                        if(found)
                            break;
                        Variable[] y = stack.peek().getData();
                        for (int i = 0; i < stack.peek().getSize(); i++) {
                            if(y[i].getName().equals(varName)){
                                System.out.println(y[i].toString());
                                found = true;
                            }
                        }
                        Block tempBlock = stack.pop();
                        tempStack.push(tempBlock);
                    }
                    if(!found)
                        System.out.println("Variable not found: "+varName);
                    while(!tempStack.isEmpty()){
                        Block block = tempStack.pop();
                        stack.push(block);
                    }
                    System.out.println();
                }

                // handle the situation "if"
                if (data.contains("if")) {
                    flag = true;
                    int operand1 = 0;
                    int operand2 = 0;
                    String a = data.substring(data.indexOf("(") + 1, data.indexOf(" "));
                    String b = data.substring(data.lastIndexOf(" ") + 1, data.lastIndexOf(")"));
                    if (!isNumeric(a))
                        operand1 = stack.peek().findValue(a);
                    else
                        operand1 = Integer.parseInt(a);
                    if (!isNumeric(b))
                        operand2 = stack.peek().findValue(b);
                    else
                        operand2 = stack.peek().findValue(b);
                    String c = data.substring(data.indexOf(" ") + 1, data.lastIndexOf(" "));
                    if (c.equals("<=") && !(operand1 <= operand2))
                        flag = false;
                    if (c.equals(">=") && !(operand1 >= operand2))
                        flag = false;
                    if (c.equals("<") && !(operand1 < operand2))
                        flag = false;
                    if (c.equals(">") && !(operand1 > operand2))
                        flag = false;
                    if (c.equals("==") && !(operand1 == operand2))
                        flag = false;
                    if (flag) {
                        boolean end = false;
                        do {
                            data = stdin.nextLine();
                            data = data.trim();
                            //handle the situation blank space
                            if (data.length() == 0)
                                continue;
                            //handle the situation "{"
                            if (data.charAt(0) == '}')
                                end = true;
                            //handle the situation "}"
                            if (data.charAt(0) == '{') {
                                Block block = new Block();
                                stack.push(block);
                            }

                            // handle the situation of lines containing "int " in if
                            if ( data.length()>=5 && data.contains("int ") && (data.contains(",")||data.indexOf(";")==5||data.contains("="))) {
                                if((data.contains(",")||data.indexOf(";")==5||data.contains("="))) {
                                    String f = data.substring(data.indexOf("int "), data.indexOf(";"));
                                    String temp = f.substring(4);
                                    String[] token = temp.split(",");
                                    for (int k = 0; k < token.length; k++) {
                                        token[k] = token[k].trim();
                                        if (token[k].contains("=")) {
                                            String name = token[k].substring(0, token[k].indexOf("=") - 1);
                                            if (token[k].contains("+")) {
                                                String x = token[k];
                                                int value = 0;
                                                int operator1;
                                                int operator2;
                                                String[] o = x.substring(x.indexOf("=") + 2).split(" ");
                                                if (isNumeric(o[0]))
                                                    operator1 = Integer.parseInt(o[0]);
                                                else
                                                    operator1 = stack.peek().findValue(o[0]);
                                                if (isNumeric(o[2]))
                                                    operator2 = Integer.parseInt(o[2]);
                                                else
                                                    operator2 = stack.peek().findValue(o[2]);

                                                value = operator1 + operator2;
                                                Variable var = new Variable(name, value);
                                                stack.peek().addVariable(var);
                                            } else {
                                                int value;
                                                String z = token[k].substring(token[k].indexOf("=") + 2);
                                                if (isNumeric(z))
                                                    value = Integer.parseInt(z);
                                                else
                                                    value = stack.peek().findValue(z);
                                                Variable var = new Variable(name, value);
                                                stack.peek().addVariable(var);
                                            }
                                        } else {
                                            stack.peek().addUnknownVariable(token[k]);
                                        }
                                    }
                                }
                                //  handle the situation "print local names the same line as int declaration in if "
                                if(data.contains("LOCAL")){
                                    System.out.printf("%-20s%-20s","Variable Name", "Initial Value");
                                    System.out.println();
                                    Variable[] x = stack.peek().getData();
                                    if(stack.peek().getSize()!=0)
                                        for (int i = 0; i < stack.peek().getSize(); i++) {
                                            System.out.println(x[i].toString());
                                        }
                                    else
                                        System.out.println("No local variables to print.");
                                    System.out.println();
                                }

                                //  handle the situation "print variable name the same line as int declaration in if "
                                if (data.contains("/*$print") && !data.contains("LOCAL")) {
                                    data = data.trim();
                                    String varName = data.substring(data.indexOf("t") + 2, data.lastIndexOf("*"));
                                    Variable[] x = stack.peek().getData();
                                    System.out.printf("%-20s%-20s", "Variable Name", "Initial Value");
                                    System.out.println();
                                    boolean found = false;
                                    while(!stack.isEmpty() ) {
                                        if(found)
                                            break;
                                        Variable[] y = stack.peek().getData();
                                        for (int i = 0; i < stack.peek().getSize(); i++) {
                                            if(y[i].getName().equals(varName)){
                                                System.out.println(y[i].toString());
                                                found = true;
                                            }
                                        }
                                        Block temp = stack.pop();
                                        tempStack.push(temp);
                                    }
                                    if(!found)
                                        System.out.println("Variable not found: "+varName);
                                    while(!tempStack.isEmpty()){
                                        Block block = tempStack.pop();
                                        stack.push(block);
                                    }
                                    System.out.println();
                                }
                            }
                            //  handle the situation "print local names in if  "
                            if(data.contains("LOCAL") && !data.contains("=")){
                                System.out.printf("%-20s%-20s","Variable Name", "Initial Value");
                                System.out.println();
                                Variable[] x = stack.peek().getData();
                                if(stack.peek().getSize()!=0)
                                    for (int i = 0; i < stack.peek().getSize(); i++) {
                                        System.out.println(x[i].toString());
                                    }
                                else
                                    System.out.println("No local variables to print.");
                                System.out.println();
                            }

                            //  //  handle the situation "print local names in if  "
                            else if (data.contains("/*$print") && !data.contains("=")) {
                                data = data.trim();
                                String varName = data.substring(data.indexOf("t") + 2, data.lastIndexOf("*"));
                                Variable[] x = stack.peek().getData();
                                System.out.printf("%-20s%-20s", "Variable Name", "Initial Value");
                                System.out.println();
                                boolean found = false;
                                while(!stack.isEmpty() ) {
                                    if(found)
                                        break;
                                    Variable[] y = stack.peek().getData();
                                    for (int i = 0; i < stack.peek().getSize(); i++) {
                                        if(y[i].getName().equals(varName)){
                                            System.out.println(y[i].toString());
                                            found = true;
                                        }
                                    }
                                    Block tempBlock = stack.pop();
                                    tempStack.push(tempBlock);
                                }
                                if(!found)
                                    System.out.println("Variable not found: "+varName);
                                while(!tempStack.isEmpty()){
                                    Block block = tempStack.pop();
                                    stack.push(block);
                                }
                                System.out.println();
                            }
//
                        } while (!end);
                    }
                }
                            // handle the situation of "while"
                            if (data.contains("while")) {
                                Boolean whileFlag;
                                whileFlag = true;
                                String a = data.substring(data.indexOf("(") + 1, data.indexOf(" "));
                                String b = data.substring(data.lastIndexOf(" ") + 1, data.lastIndexOf(")"));
                                String c = data.substring(data.indexOf(" ") + 1, data.lastIndexOf(" "));
                                int operand1,operand2;
                                if (!isNumeric(a))
                                     operand1 = stack.peek().findValue(a);
                                else
                                     operand1 = Integer.parseInt(a);
                                if (!isNumeric(b))
                                     operand2 = stack.peek().findValue(b);
                                else
                                     operand2 = stack.peek().findValue(b);
                                if (whileFlag){
                                    if (c.equals("<=") && !(operand1 <= operand2))
                                        whileFlag = false;
                                    if (c.equals(">=") && !(operand1 >= operand2))
                                        whileFlag = false;
                                    if (c.equals("<") && !(operand1 < operand2))
                                        whileFlag = false;
                                    if (c.equals(">") && !(operand1 > operand2))
                                        whileFlag = false;
                                    if (c.equals("==") && !(operand1 == operand2))
                                        whileFlag = false;
                                    if (whileFlag) {
                                        data = stdin.nextLine();
                                        data = data.trim();
                                        // handle the situation of "blank space"
                                        if (data.length() == 0)
                                            continue;
                                        // handle the situation of "{"
                                        else if (data.charAt(0) == '{') {
                                            Block block = new Block();
                                            stack.push(block);
                                        }
                                        // handle the situation of "}"
                                        else if(data.charAt(0) == '}'){
                                            stack.pop();
                                        }
                                        // handle the situation of lines containing "int "
                                        if ( data.length()>=5 && data.contains("int ") && (data.contains(",")||data.indexOf(";")==5||data.contains("="))) {
                                            if((data.contains(",")||data.indexOf(";")==5||data.contains("="))) {
                                                String f = data.substring(data.indexOf("int "), data.indexOf(";"));
                                                String temp = f.substring(4);
                                                String[] token = temp.split(",");
                                                for (int k = 0; k < token.length; k++) {
                                                    token[k] = token[k].trim();
                                                    if (token[k].contains("=")) {
                                                        String name = token[k].substring(0, token[k].indexOf("=") - 1);
                                                        if (token[k].contains("+")) {
                                                            String x = token[k];
                                                            int value = 0;
                                                            int op1;
                                                            int op2;
                                                            String[] o = x.substring(x.indexOf("=") + 2).split(" ");
                                                            if (isNumeric(o[0]))
                                                                op1 = Integer.parseInt(o[0]);
                                                            else
                                                                op1 = stack.peek().findValue(o[0]);
                                                            if (isNumeric(o[2]))
                                                                op2 = Integer.parseInt(o[2]);
                                                            else
                                                                op2 = stack.peek().findValue(o[2]);

                                                            value = op1 + op2;
                                                            Variable var = new Variable(name, value);
                                                            stack.peek().addVariable(var);
                                                        } else {
                                                            int value;
                                                            String z = token[k].substring(token[k].indexOf("=") + 2);
                                                            if (isNumeric(z))
                                                                value = Integer.parseInt(z);
                                                            else
                                                                value = stack.peek().findValue(z);
                                                            Variable var = new Variable(name, value);
                                                            stack.peek().addVariable(var);
                                                        }
                                                    } else {
                                                        stack.peek().addUnknownVariable(token[k]);
                                                    }
                                                }
                                            }
                                            //  handle the situation "print local variables the same line as int declaration in while "
                                            if(data.contains("LOCAL")){
                                                System.out.printf("%-20s%-20s","Variable Name", "Initial Value");
                                                System.out.println();
                                                Variable[] x = stack.peek().getData();
                                                if(stack.peek().getSize()!=0)
                                                    for (int i = 0; i < stack.peek().getSize(); i++) {
                                                        System.out.println(x[i].toString());
                                                    }
                                                else
                                                    System.out.println("No local variables to print.");
                                            }

                                            //  handle the situation "print variable names the same line as int declaration in while "
                                            if (data.contains("/*$print") && !data.contains("LOCAL") ) {
                                                data = data.trim();
                                                String varName = data.substring(data.indexOf("t") + 2, data.lastIndexOf("*"));
                                                Variable[] x = stack.peek().getData();
                                                System.out.printf("%-20s%-20s", "Variable Name", "Initial Value");
                                                System.out.println();
                                                boolean found = false;
                                                while(!stack.isEmpty() ) {
                                                    if(found)
                                                        break;
                                                    Variable[] y = stack.peek().getData();
                                                    for (int i = 0; i < stack.peek().getSize(); i++) {
                                                        if(y[i].getName().equals(varName)){
                                                            System.out.println(y[i].toString());
                                                            found = true;
                                                        }
                                                    }
                                                    Block temp = stack.pop();
                                                    tempStack.push(temp);
                                                }
                                                if(!found)
                                                    System.out.println("Variable not found: "+varName);
                                                while(!tempStack.isEmpty()){
                                                    Block block = tempStack.pop();
                                                    stack.push(block);
                                                }
                                                System.out.println();
                                            }

                                                    }
                                         // handle the situation "print local variables in while "
                                        if(data.contains("LOCAL") && !data.contains("=")){
                                            System.out.printf("%-20s%-20s","Variable Name", "Initial Value");
                                            System.out.println();
                                            Variable[] x = stack.peek().getData();
                                            if(stack.peek().getSize()!=0)
                                                for (int i = 0; i < stack.peek().getSize(); i++) {
                                                    System.out.println(x[i].toString());
                                                }
                                            else
                                                System.out.println("No local variables to print.");
                                            System.out.println();
                                        }

                                         //  handle the situation "print variable names in while "
                                        if (data.contains("/*$print") && !data.contains("LOCAL") && !data.contains("=")) {
                                            data = data.trim();
                                            String varName = data.substring(data.indexOf("t") + 2, data.lastIndexOf("*"));
                                            Variable[] x = stack.peek().getData();
                                            System.out.printf("%-20s%-20s", "Variable Name", "Initial Value");
                                            System.out.println();
                                            boolean found = false;
                                            while(!stack.isEmpty() ) {
                                                if(found)
                                                    break;
                                                Variable[] y = stack.peek().getData();
                                                for (int i = 0; i < stack.peek().getSize(); i++) {
                                                    if(y[i].getName().equals(varName)){
                                                        System.out.println(y[i].toString());
                                                        found = true;
                                                    }
                                                }
                                                Block temp = stack.pop();
                                                tempStack.push(temp);
                                            }
                                            if(!found)
                                                System.out.println("Variable not found: "+varName);
                                            while(!tempStack.isEmpty()) {
                                                Block block = tempStack.pop();
                                                stack.push(block);
                                                System.out.println();
                                            }
                                        }
                                    }
                                }
                            }
                    } while (stdin.hasNext());

            } catch (java.io.FileNotFoundException ex) {
                    System.out.println("The file does not exist.");
             }
        }

    /**
     * This is a method used to test whether a String is numeric
     * @param str
     * the String that being tested
     * @return
     * true if it is numeric
     * false if it is not numeric
     */
    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException ex) {
            return false;
        } catch (NullPointerException ex) {
            return false;
        }
        return true;
    }
}


