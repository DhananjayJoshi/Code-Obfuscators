import java.io.*;
import java.util.*;

public class CodeGenerator {
	// Data members:
	private ExpressionHandler exp;		// Expression Context
	private LoopHandler loop;			// Loop Context
	private FunctionHandler function;	// Function Context
	private ConditionHandler condition;	// Condition Context
	private VariableHandler variable;	// Variable declaration Context
	private String input;				// Input statement
	private Vector <String> words;		// Word-list to matched
	private String [] files;			// File list
	private String [] tokens;			// Array of tokens obtained from input statement
	
	// Constructor:
	public CodeGenerator() {
		input = "";
		words = new Vector <String> (10);
		files = new String[5];
		files[0] = "/home/dj/workspace/Code Generator Version 1/src/Database/Classification_Db/Classify_Loop.txt";
		files[1] = "/home/dj/workspace/Code Generator Version 1/src/Database/Classification_Db/Classify_Condition.txt";
		files[2] = "/home/dj/workspace/Code Generator Version 1/src/Database/Classification_Db/Classify_Expression.txt";
		files[3] = "/home/dj/workspace/Code Generator Version 1/src/Database/Classification_Db/Classify_Function.txt";
		files[4] = "/home/dj/workspace/Code Generator Version 1/src/Database/Classification_Db/Classify_Declaration.txt";
	}
	
	// Methods:
	//Reading statement and spliting into tokens
	void readStatement(){
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			input = br.readLine();
			input = input.toLowerCase();
			tokens = input.split("[ ]+");
		}
		catch(Exception ioe){
			System.out.println(ioe);
		}
	}
	//Identifies the context of given statement. 
	private int classifyStatement(){
		
		for(int i = 0; i < files.length; i++){
			readFile(files[i]);
			for(int j=0; j < words.size(); j++){
				String tempWord = words.get(j);
				for(int k=0; k < tokens.length; k++){
					if(tempWord.equalsIgnoreCase(tokens[k])){
						switch(i)
						{
							case 0: return IContext.LOOP;
							case 1: return IContext.COND;
							case 2: return IContext.EXP;
							case 3: return IContext.FUNC;
							case 4: return IContext.VAR;							
						}
					}
				}
			}
		}
		
		return IContext.NOCONTEXT;
	}
	
	//Generating Code construct corresponding to identified context 
	void generateCode(){
		int temp = classifyStatement();
		
		switch(temp){
			case IContext.LOOP: loop = new LoopHandler();								 
								//System.out.println("Loop: " + loop.loopProcessor(input));
								loop.loopProcessor(input);
								break;
			case IContext.COND: System.out.println("Condition"); break;
			case IContext.EXP: System.out.println("Exp"); break;
			case IContext.FUNC: System.out.println("Func"); break;
			case IContext.VAR: System.out.println("Var"); break;
			case IContext.NOCONTEXT: System.out.println("NoContext"); break;
		}
	}
	
	//Reading words from classification database.
	private void readFile(String fileName){
		String token=null;
		words.removeAllElements();
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			while((token = br.readLine()) != null){
				words.add(token);
			}
		}		
		catch(FileNotFoundException fnfe){
			System.out.println(fnfe);
		}
		catch(Exception except){
			System.out.println(except);
		}		
	}
}
