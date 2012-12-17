/**
 * @author Code obfuscaters
 * @version 1.0
 * Date: 13th December, 2012
 */

import java.util.*;

public class Controller {
	// Members:
	static CodeGenerator classify;
	public static FunctionSymbolTable funSym;
	public static VariableSymbolTable varSym;
	
	// TODO Add hash map for operators
	public static HashMap <String, String> oprHash;
	
	// Constructor:
	public Controller() {
		funSym = new FunctionSymbolTable();
		varSym = new VariableSymbolTable();
	}
	
	// Methods:
	public static void main(String args[]){
		// Main function initialises the symbol tables and 'Classifier' object
		classify = new CodeGenerator();
		System.out.print("\nEnter a string: ");
		classify.readStatement();
		classify.generateCode();
	}
}

