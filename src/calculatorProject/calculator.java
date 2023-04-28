package calculatorProject;

import java.text.DecimalFormat;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class calculator extends Application {
	/**
	 *  display for the memory
	 */
	private Text memDisplay = new Text("Memory = 0");
	/**
	 *  display textfield
	 */
	private TextField tfDisplay;    
	/**
	 * 30 buttons
	 */
	private Button[] btns;          
	/**
	 * Labels of 30 buttons
	 */
	private String[] btnLabels = {  
			"7", "8", "9", "+",
			"4", "5", "6", "-",
			"1", "2", "3", "x",
			".", "0", "=", "/",
			"C","\u2190","\u221A", "^", 
			"sin", "cos", "tan", "sec",
			"\u03C0", "(-)", "M+","M-", "MR", 
			"MC" 

	};

	/**
	 * Result of computation
	 */
	private double result = 0;      
	/**
	 * used to store the memory value
	 */
	private double mem;	//
	/*
	 * pi in a double form
	 */
	private static Double pi = Math.PI;
	/**
	 * Input number as String
	 */
	private String inStr = "0"; 
	/**
	 * used to store the last used operator
	 * Previous operator: ' '(nothing), '+', '-', '*', '/', '='
	 */
	private char lastOperator = ' ';
	/*
	 * used to format the trig functions
	 */
	private static final DecimalFormat trig = new DecimalFormat("0.00");
	/**
	 * Event handler for all the 30 Buttons
	 */
	EventHandler handler = evt -> {
		String currentBtnLabel = ((Button)evt.getSource()).getText();
		switch (currentBtnLabel) {
		// Number buttons
		case "0": case "1": case "2": case "3": case "4":
		case "5": case "6": case "7": case "8": case "9": 
		case ".": case "\u03C0": case "(-)":
			if(currentBtnLabel.equals("(-)")) {
				inStr = "-" + inStr;
			}

		else if (inStr.equals("0")) {
				if(currentBtnLabel.equals("\u03C0")) {
					inStr = String.valueOf(pi);
				}
				else{
					inStr = currentBtnLabel;  // no leading zero
				}
			} else {
				if(currentBtnLabel.equals("\u03C0")) {
					inStr =  String.valueOf((Double.parseDouble(inStr)*pi));
				}
				else {
					inStr += currentBtnLabel; // append input digit
				}
			}
			tfDisplay.setText(inStr);
			// Clear buffer if last operator is '='
			if (lastOperator == '=') {
				result = 0;
				lastOperator = ' ';
			}
			break;

			/*
			 *  Operator buttons: '+', '-', 'x', '/' and '='
			 */
		case "+":
			compute();
			lastOperator = '+';
			break;
		case "-":
			compute();
			lastOperator = '-';
			break;
		case "x":
			compute();
			lastOperator = '*';
			break;
		case "/":
			compute();
			lastOperator = '/';
			break;
		case "=":
			compute();
			lastOperator = '=';
			break;
			/*
			 *  Operator for square root
			 */
		case "√":
			compute();
			lastOperator = '\u221A';
			break;
			/*
			 * Operator for power functions
			 */
		case "^": 
			compute(); 
			lastOperator = '^'; 
			break;
			/*
			 * sine function
			 */
		case "sin":
			compute();
			lastOperator = 's';
			break;
			/*
			 * cosine function
			 */
		case "cos":
			compute();
			lastOperator = 'c';
			break;
			/*
			 * tangent function
			 */
		case "tan":
			compute();
			lastOperator = 't';
			break;
			/*
			 * secant function
			 */
		case "sec":
			compute();
			lastOperator = 'e';
			break;
			/*
			 * operator for delete key
			 */
		case "\u2190":
			String str = tfDisplay.getCharacters().toString();
			tfDisplay.setText(str.substring(0, str.length()-1));
			break;
			/*
			 * memory reference button
			 */
		case "MR":
			inStr = String.valueOf(mem);
			tfDisplay.setText(inStr);
			if((int) mem == mem)
			{
				memDisplay.setText("Memory = " +(int)mem);
			} 
			else {
				memDisplay.setText("Memory = " + mem);  
			}
			break;
			/*
			 * memory clear button
			 */
		case "MC":
			mem = 0;
			if((int) mem == mem)
			{
				memDisplay.setText("Memory = " +(int)mem);
			} 
			else {
				memDisplay.setText("Memory = " + mem);  
			}
			result = 0;
			inStr = "0";
			lastOperator = ' ';
			tfDisplay.setText("0");
			break;
			/*
			 * memory subtract button
			 */
		case "M-":
			mem -= Double.parseDouble(inStr);
			if((int) mem == mem)
			{
				memDisplay.setText("Memory = " +(int)mem);
			} 
			else {
				memDisplay.setText("Memory = " + mem);  
			}
			result = 0;
			inStr = "0";
			lastOperator = ' ';
			tfDisplay.setText("0");
			break;
			/*
			 * memory add button
			 */
		case "M+":
			mem += Double.parseDouble(inStr);
			if((int) mem == mem)
			{
				memDisplay.setText("Memory = " +(int)mem);
			} 
			else {
				memDisplay.setText("Memory = " + mem);  
			}
			result = 0;
			inStr = "0";
			lastOperator = ' ';
			tfDisplay.setText("0");
			break;
			/*
			 *  Clear button
			 */
		case "C":
			result = 0;
			inStr = "0";
			lastOperator = ' ';
			tfDisplay.setText("0");
			break;
		}
	};


	/**
	 *  User pushes '+', '-', '*', '/' 'sqrt' 'power' '=' or any of the memory buttons.
	 * Perform computation on the previous result and the current input number,
	 * based on the previous operator.
	 */
	private void compute() {
		double inNum = Double.parseDouble(inStr);
		inStr = "0";
		if (lastOperator == ' ') {
			result = inNum;
		} else if (lastOperator == '+') {
			result += inNum;
		} else if (lastOperator == '-') {
			result -= inNum;
		} else if (lastOperator == '*') {
			result *= inNum;
		} else if (lastOperator == '/') {
			result /= inNum;
		} else if (lastOperator == '=') {
			// Keep the result for the next operation
		} else if (lastOperator == '\u221A') {
			result = Math.sqrt(inNum);
		} else if (lastOperator == '^') {
			result = Math.pow(result, inNum);
			
			// next four else ifs have Double.parseDouble(trig.format(some trig funtion) this is to format it correctly so that it gives a more accurate answer
		} else if (lastOperator == 's') {
			result = Double.parseDouble(trig.format(Math.sin(inNum)));
		} else if (lastOperator == 'c') {
			result = Double.parseDouble(trig.format(Math.cos(inNum)));
		} else if (lastOperator == 't') {
			result = Double.parseDouble(trig.format(Math.tan(inNum)));
		} else if (lastOperator == 'e') {
			result = Double.parseDouble(trig.format(1/Math.cos(inNum)));
		} 

		if((int) result == result)
		{
			tfDisplay.setText((int)result + "");
		} 
		else {
			tfDisplay.setText(result + "");  
		}

	}


	/**
	 * Setup the UI
	 */
	@Override
	public void start(Stage primaryStage) {
		// Setup the Display TextField
		tfDisplay = new TextField("0");
		tfDisplay.setEditable(false);
		tfDisplay.setAlignment(Pos.CENTER_RIGHT);

		// Setup a GridPane for 4x4 Buttons
		int numCols = 4;
		int numRows = 5;
		GridPane paneButton = new GridPane();
		paneButton.setPadding(new Insets(15, 0, 15, 0));  // top, right, bottom, left
		paneButton.setVgap(5);  // Vertical gap between nodes
		paneButton.setHgap(5);  // Horizontal gap between nodes
		// Setup 4 columns of equal width, fill parent
		ColumnConstraints[] columns = new ColumnConstraints[numCols];
		for (int i = 0; i < numCols; ++i) {
			columns[i] = new ColumnConstraints();
			columns[i].setHgrow(Priority.ALWAYS) ;  // Allow column to grow
			columns[i].setFillWidth(true);  // Ask nodes to fill space for column
			paneButton.getColumnConstraints().add(columns[i]);
		}

		// Setup 28 Buttons and add to GridPane; and event handler
		btns = new Button[30];
		for (int i = 0; i < btns.length; ++i) {

			btns[i] = new Button(btnLabels[i]);
			btns[i].setOnAction(handler);  // Register event handler
			btns[i].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);  // full-width
			paneButton.add(btns[i], i % numCols, i / numCols);  // control, col, row
			if(btnLabels[i] == "M+"||btnLabels[i] == "M-" || btnLabels[i] == "MR"||btnLabels[i] == "MC") {
				btns[i].setStyle("-fx-color: blue");
			}
			if(btnLabels[i] == "C" || btnLabels[i] == "\u2190") {
				btns[i].setStyle("-fx-color: orange");
			}
		}

		// Setup up the scene graph rooted at a BorderPane (of 5 zones)
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(15, 15, 15, 15));  // top, right, bottom, left
		root.setTop(tfDisplay);     // Top zone contains the TextField
		root.setCenter(paneButton); // Center zone contains the GridPane of Buttons
		root.setBottom(memDisplay);

		// Set up scene and stage
		primaryStage.setScene(new Scene(root, 350, 330));
		primaryStage.setTitle("Java Calculator V2");
		primaryStage.show();
	}

	/**
	 * @param args
	 * launches the calculator
	 */
	public static void main(String[] args) {
		
		launch(args);
	}
}