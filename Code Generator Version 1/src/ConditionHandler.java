import java.util.*;
import java.io.*;

public class ConditionHandler {
	// Data members:
	private String [] files = {	"/home/dj/workspace/Code Generator Version 1/src/Database/Condition_Db/for_loop.txt", 
								"/home/dj/workspace/Code Generator Version 1/src/Database/Condition_Db/while_loop.txt" };
	private Vector<String> words;
	private String input = "";
	Code code;
	String tokens[];
	int tIndex = 0;
	ExpressionHandler exp = null;
	
	// Constructors:
	ConditionHandler() {
		code = new Code();
		exp = new ExpressionHandler();		
		words = new Vector<String>();
	}
	
	// Methods:
	private void readFiles(){
		String in = "";
		BufferedReader br;
		try{
			br = new BufferedReader(new FileReader(files[0]));
			while((in = br.readLine()) != null)
				words.add(in);
			
			br = new BufferedReader(new FileReader(files[1]));
			while((in = br.readLine()) != null)
				words.add(in);			
		}
		catch(FileNotFoundException fnfe){
			System.out.println(fnfe);
		}
		catch(IOException ioe){
			System.out.println(ioe);
		}		
	}
	
	private Code conditionProcessor(String inputStmt){
		input = inputStmt;
		tokens = input.split("[ ]+");
		
		return null;
	}
}