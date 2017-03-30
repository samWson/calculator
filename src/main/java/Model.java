/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pc2project;

/**
 *
 * @author 91025973
 */
public class Model {
        
    private final int BASE = 10;    // the base value that will be multiplied to the power of a specified number of decimal places, used to assist will calculating rounding accuracy
    private final double PI = Math.PI;  // 3.141592653589793
    private final double E = Math.E;    // 2.718281828459045
    
    private String answer = "";         // used for storing the final answer to be returned to the ViewController
    private String operator = "";       // used for storing the operator to be used in caculations
    
    private double operand1 = 0.0;      // the first operand for calculations, some calculations only use this operand
    private double operand2 = 0.0;      // the second operand for calculations
    
    private boolean decimal = false;    // sets state when a floating point number is used i.e. a decimal has been entered in ViewController
    private boolean operatorState = false;    // sets state when an operator is used
    private boolean degrees = false;    // used for setting the advanced calculator state to degrees
    private boolean radians = false;    // used for setting the advanced calculator state to radians
    private boolean sin = false;        // represented by 1 in the setTrigFunction() switch statement
    private boolean cos = false;        // represented by 2 in the setTrigFunction() switch statement
    private boolean tan = false;        // represented by 3 in the setTrigFunction() switch statement
    private boolean asin = false;       // represented by 4 in the setTrigFunction() switch statement
    private boolean acos = false;       // represented by 5 in the setTrigFunction() switch statement
    private boolean atan = false;       // represented by 6 in the setTrigFunction() switch statement
    
    // ------------------Setter and Getter Methods------------------------------
    
    // used by the +, -, *, /, x^n button event handlers to set the operator variable
    public void setOperator(String operator) {
        this.operator = operator;
        operatorState = true;
    }    
    
    // sets the decimal state, called when ViewController.btnDecimal is used
    public void setDecimal() {
        decimal = true;
    }
    
    // sets the operand1 or operand2 variables when an operator button is pressed, storing the operand and allowing a second operand to be entered
    public void setOperand(String operand) {
        if (operand1 == 0.0) {
            operand1 = Double.parseDouble(operand);
        } else {
            operand2 = Double.parseDouble(operand);
        }            
    }
    
    // gets the first operand for the ViewController
    public String getOperand1() {
        return String.valueOf(operand1);
    }
    
    // gets the second operand for the ViewController
    public String getOperand2() {
        return String.valueOf(operand2);
    }
    
    // gets the operator state for the ViewController
    public boolean getOperatorState() {
        return operatorState;
    }
    
    // allows the ViewController to reset operand1, operand2, operator, operatorState and decimal back to their default empty and false states
    public void clearAll() {
        operand1 = 0.0;
        operand2 = 0.0;
        operator = "";
        operatorState = false;
        decimal = false;
    }
    
    // this method sets the advanced calculator degrees state to true or false
    public void setDegrees(boolean state) {
        if(state) {
            degrees = true;
        } else {
            degrees = false;
        }
    }
    
    // this method sets the advanced calculator radians state to true or false
    public void setRadians(boolean state) {
        if(state) {
            radians = true;
        } else {
            radians = false;
        }
    }   
    
    // used to set all trigonometric function variables to false
    public void resetTrigFunctions() {
        sin = false;
        cos = false;
        tan = false;
        asin = false;
        acos = false;
        atan = false;
    }
    
    // used to set the trigonometric function state
    public void setTrigFunction(int function) {
        switch(function) {
            case 1:
                sin = true;
                break;
            case 2:
                cos = true;
                break;
            case 3:
                tan = true;
                break;
            case 4:
                asin = true;
                break;
            case 5:
                acos = true;
                break;
            case 6:
                atan = true;
                break;
        }
    }
    
    // allows the ViewController to make method calls with E as an argument
    public String getE() {
        return String.valueOf(E);
    }
    
    // allows the ViewController to make method calls with PI as an argument
    public String getPI() {
        return String.valueOf(PI);
    }
    
    // -------------Menu Methods------------------------------------------------
    // this method is called by other calculation methods to round to the correct number of decimal places
    public String rounding(String numberStr, int decimal) {
        double number = Double.parseDouble(numberStr);
        
        if(decimal == 0) {
            number = Math.round(number);
            return String.valueOf(number);
        } else {
            String accuracy = powerOf(String.valueOf(BASE), String.valueOf(decimal));     // 10 will be multiplied by decimal, accuracy will then be used to multiply number before rounding 
            double roundedValue = number * Double.parseDouble(accuracy);                // sets the existing decimal place to the number to be rounded e.g 123.4567 * 10^3 = 123456.7
            roundedValue = Math.round(roundedValue);                                    // rounds at the decimal point e.g. 123456.7 rounds to 123457
            roundedValue = roundedValue / Double.parseDouble(accuracy);                 // changes the decimal back to it's original position e.g. 123457 / 10^3 = 123.457
            return String.valueOf(roundedValue);
        }        
    }
    
    // -------------Basic Calculation Methods-----------------------------------
    // this method calls the correct calculation method based on the operator argument
    public String calculate(String operand1, String operand2) throws ArithmeticException {
        if(operator.equals("/") && operand2.equals("0.0")) {
            throw new ArithmeticException();
        }
        
        if (operator.equals("+")) {
            answer = addition(operand1, operand2);
        } else if (operator.equals("-")) {
            answer = subtraction(operand1, operand2);
        } else if (operator.equals("*")) {
            answer = multiplication(operand1, operand2);
        } else if (operator.equals("/")) {
            answer = division(operand1, operand2);            
        } else if (operator.equals("^")) {
            answer = powerOf(operand1, operand2);
        }
        return answer;
    }
    
    // this overloaded method is for when a calculation is atempted with only one operand, e.g. "3", "=" should result in "3"
    public String calculate(String operand1) {
        answer = operand1;
        return answer;
    }
    
    // adds operand1 and operand2
    private String addition(String operand1, String operand2) {
        double value = Double.parseDouble(operand1) + Double.parseDouble(operand2);
        return String.valueOf(value);        
    }
    
    // subtracts operand1 and operand2
    private String subtraction(String operand1, String operand2) {
        double value = Double.parseDouble(operand1) - Double.parseDouble(operand2);
        return String.valueOf(value);
    }
    
    // multiplies operand1 and operand2
    private String multiplication(String operand1, String operand2) {
        double value = Double.parseDouble(operand1) * Double.parseDouble(operand2);
        return String.valueOf(value);
    }
    
    // divides operand1 and operand2
    private String division(String operand1, String operand2) {
        double value = Double.parseDouble(operand1) / Double.parseDouble(operand2);
        return String.valueOf(value);
    }
    
    // calculates the fraction of 1 over the operand
    public String oneOverX(String operand) throws ArithmeticException {
        if(operand.equals("0.0")) {
            throw new ArithmeticException();
        }
        
        double value = Double.parseDouble(operand);
        double fraction = 1 / value;
        return String.valueOf(fraction);
    }
    
    // calculates operand1 to the power of operand2
    private String powerOf(String operand1, String operand2) {
        double value = Math.pow(Double.parseDouble(operand1), Double.parseDouble(operand2));
        return String.valueOf(value);
    }
    
    // inverts the positive or negative state of a supplied operand
    public String plusOrMinus(String operand) {
        double value = Double.parseDouble(operand);
        value *= -1;
        return String.valueOf(value);        
    }      
    
    // calculates the factorial of a given operand
    public String factorial(String operand) throws ArithmeticException, NumberFormatException {        
           
        double round = Math.round(Double.parseDouble(operand)); // rounds operand off at the decimal point allowing conversion to int and comparision in the if statement
        int value = (int)round;
        
        if((value < 0 || value > 20) || decimal == true) { // if operand is not a positive integer between 0 and 20 inlcusive an exception is thrown
            throw new ArithmeticException();
        }
                
        if(value == 0) {
            return String.valueOf("1");
        } else {
            double factorial = value;
            while (value > 1) {
                factorial *= value - 1;
                value--;
            }
        return String.valueOf(factorial);
        }        
    }
    
    // -----------------Advanced Calculation Methods----------------------------
    
    // will calculate angles based on which trigonometric function is currently in its true state i.e. sin, cos, tan etc. and weather the degrees or radians state is set
    public String trigCalculation(String operand) {
        double number = Double.parseDouble(operand);
        
        // sin calculations
        if(sin == true) {
            if(degrees == true) {
                number = Math.toRadians(number);
                answer = String.valueOf(Math.sin(number));
            } else if (radians == true) {
                answer = String.valueOf(Math.sin(number));
            }
        }
            
        // cos calculations
        if(cos == true) {
            if(degrees == true) {
                number = Math.toRadians(number);
                answer = String.valueOf(Math.cos(number));
            } else if(radians == true) {
                answer = String.valueOf(Math.cos(number));
            }
        }
        
        // tan calculations
        if(tan == true) {
            if(degrees == true) {
                number = Math.toRadians(number);
                answer = String.valueOf(Math.tan(number));
            } else if(radians == true) {
                answer = String.valueOf(Math.tan(number));
            }                    
        }
        
        // asin calculations
        if(asin == true) {
            if(degrees == true) {
                number = Math.asin(number);
                answer = String.valueOf(Math.toDegrees(number));
            } else if(radians == true) {
                answer = String.valueOf(Math.asin(number));
            }
        }
        
        // acos calculations
        if(acos == true) {
            if(degrees == true) {
                number = Math.acos(number);
                answer = String.valueOf(Math.toDegrees(number));
            } else if(radians == true) {
                answer = String.valueOf(Math.acos(number));
            }
        }
        
        // atan caclulations
        if(atan == true) {
            if(degrees == true) {
                number = Math.atan(number);
                answer = String.valueOf(Math.toDegrees(number));
            } else if(radians == true) {
                answer = String.valueOf(Math.atan(number));
            }
        }
        
        return answer;
    }
}
