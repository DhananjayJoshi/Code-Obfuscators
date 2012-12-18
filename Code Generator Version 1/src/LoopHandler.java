import java.io.*;
import java.util.*;

public class LoopHandler {
	// Data members:
	String [] tokens;			// Stores tokens in input string
	String [] loopParams;		// Parameters to be filled to generate the final code
	int wordLoc = 0;			// Stores the location of word matched in Vector 'words' 
	Code code;					// Stores the code to be returned
	String input;				// Input statement
	Vector <String> words;		// Vector to store words to be matched
	String [] files = {"/home/dj/workspace/Code Generator Version 1/src/Database/Loop_Db/for_loop.txt", 
					   "/home/dj/workspace/Code Generator Version 1/src/Database/Loop_Db/while_loop.txt", 
					   "/home/dj/workspace/Code Generator Version 1/src/Database/Loop_Db/do_while_loop.txt"};
	int tIndex = 0;				// Index to 'tokens' array
	ExpressionHandler exp = null;
	String condition= "";
	
	// Constructor:
	LoopHandler(){
		code = new Code();
		exp = new ExpressionHandler();
		words = new Vector <String> (10);
		loopParams = new String[3];
		loopParams[0] = null;
		loopParams[1] = null;
		loopParams[2] = null;
	}
	
	// Methods:
	public Code loopProcessor(String input){
		this.input = input;
		int loopType = identifyLoop();
		generateLoop(loopType);
		return code;
	}
	
	private void readFile(String fileName){
		String token = null;
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
	
	private int identifyLoop(){
		//return values:
		//0 for for loop.
		//1 for while loop
		//2 for do-while loop
		//3 for no loop identification
		
		int loopType = 3;
		for(int i = 0; i < files.length; i++){
			readFile(files[i]);
			for(int j=0; j < words.size(); j++){
				String tempWord = words.get(j);
				if(input.indexOf(tempWord) != -1){
					wordLoc = j;
					return i;
				}
			}
		}
		return loopType;	
	}
	
	private void generateLoop(int loopType){
		switch(loopType){
			case 0: for_loop(); break;
			case 1: while_loop(); break;
			case 2: do_while_loop(); break;
			case 3: break;
		}
	}
	
	private String searchOperator(int index){
		String key = null;
		Set <Map.Entry<String,String>> set = Controller.oprHash.entrySet();
		String str = tokens[index];
		
		if((str + tokens[index+1]).equalsIgnoreCase("plusplus"))
			return "plus plus";
		if((str + tokens[index+1]).equalsIgnoreCase("minusminus"))
			return "minus minus";
			
		for(Map.Entry<String,String> me : set){
			if((key = me.getKey()).equalsIgnoreCase(str)){
				tIndex++;
				return key;
			}
			else
			if(key.startsWith(str)){
				String tempStr = str;
				for(int i = index + 1; i < tokens.length ; i++){
					tempStr = tempStr + " " + tokens[i]; 
					if(key.equalsIgnoreCase(tempStr)){
						tIndex = i+1;
						return key;
					}
				}
			}
		}
		return null;
	}
	
	private String searchExpression(){
		FuncSymbol func;
		boolean flag = true;
		String expString = tokens[tIndex];
		String tempStr = "";
		tIndex++;
		if(tokens[tIndex].equalsIgnoreCase("from"))
			tokens[tIndex] = "equal to";
		flag = false;
		while(true){
			if((tempStr = searchOperator(tIndex)) != null){
				if(!tempStr.equalsIgnoreCase("plus plus") && !tempStr.equalsIgnoreCase("minus minus"))
						flag = true;
				expString = " " + tempStr;	// Note: tIndex automatically incremented by searchOperator() method
			}
			else
			if(Controller.varSym.searchSymbol(tokens[tIndex])!= null /*|| TODO Check whether next token is number */){
				if(flag == true){
					flag = false;								
					expString += " " + tokens[tIndex];
					tIndex++;
				}
				else
					break;
			}
			else
			if((func = Controller.funSym.searchSymbol(tokens[tIndex])) != null){
				if(func.getParam().length == 0){
					expString += (" " + tokens[tIndex]);
					flag = false;
					tIndex++;
				}
				else{
					int counter = 0;
					int index = tIndex + 1;
					VarSymbol var = null;
					expString += (" " + tokens[index] + " of");
					while(counter < func.getParam().length && index < tokens.length){
						if((var = Controller.varSym.searchSymbol(tokens[index])) != null){
							if(func.getParam()[counter].equals(var.getDataType())){
								counter++;
								expString += (" " + tokens[index]);
							}
						}
						index++;
					}
					if(index == tokens.length){
						// Error
					}
					else
						tIndex = index;
				}
			}				
			//else
				//break;
		}
		return expString;
	}
	
	private void for_loop(){
		VarSymbol v = null, tempVar = null;
		FuncSymbol f = null, tempFunc = null;
		int index = input.indexOf(words.get(wordLoc));	// Index in input string at which the word was encountered
		String expString="";		
		index += (words.get(wordLoc).length() + 1);
		String str = input.substring(index); 		// Further processing will be done on this string
		tokens = str.split("[ ]+");
		index = 0;
		boolean flag = false;
		String expression = null;
		String tempStr = null;
		String loopCounter = "";
		
		while(true){
			if((v = Controller.varSym.searchSymbol(tokens[tIndex])) != null) /*|| (f = Controller.funSym.searchSymbol(tokens[tIndex])) != null*/{
				loopCounter = v.getVarName();		// Holds the loopCounter variable
				expString = searchExpression();
				expression = exp.generateExpression(expString);	
				tempVar = v;
			}			
			else
			if(tokens[tIndex].equalsIgnoreCase("to")){								// If next token is not an operator
				loopParams[0] = expression;
				tIndex++;
				int tempNum = NumberGenerator.stringToNum(tokens[tIndex]);
				//if((tempStr = String.valueOf(toNumber(tokens[tIndex++])))){			// TODO Method 'toNumber()' to be added later
				if(String.valueOf(tempNum) != null){
					condition = tempStr;
					loopParams[1] = loopCounter + "<" + condition;
				}
				else
				if((v = Controller.varSym.searchSymbol(tokens[tIndex])) != null || (Controller.funSym.searchSymbol(tokens[tIndex]) != null)){
					tempStr = searchExpression();
					String tempString = exp.generateExpression(tempStr);
					int len = v.getVarName().length();
					if(tempString.startsWith(loopCounter) && tempString.charAt(len) == '='){
						condition = tempString.substring(len+1);
						loopParams[1] = loopCounter + "<" + condition;
					}
					else{
						condition = tempString;
						loopParams[1] = condition;
					}
				}
			}
			else
			if(tokens[tIndex].equalsIgnoreCase("increment") || (tokens[tIndex] + tokens[tIndex+1]).equalsIgnoreCase("plusplus") || tokens[tIndex].equalsIgnoreCase("postincrement") || tokens[tIndex].equalsIgnoreCase("preincrement")){
				expression = exp.generateExpression((str.substring(str.indexOf(tokens[tIndex]))));
				loopParams[2] = expression;
				break;
			}
			else
			if(tokens[tIndex].equalsIgnoreCase("decrement") || (tokens[tIndex] + tokens[tIndex+1]).equalsIgnoreCase("minus minus") || tokens[tIndex].equalsIgnoreCase("postdecrement") || tokens[tIndex].equalsIgnoreCase("predecrement")){
				expression = exp.generateExpression((str.substring(str.indexOf(tokens[tIndex]))));
				loopParams[2] = expression;
				break;
			}
			/*if(searchOperator(tIndex) != -1){
				//TODO Index variable missing error to be handled
			}*/
			/*
			if(token is number){
				number handling logic.
				e.g : for loop 0 to 100.
			}
			*/
			if((tIndex) == tokens.length){
				break;
			}
		}
		if(loopParams[2] == null){
			loopParams[2] = loopCounter + "++";
		}
		code.setCodeText("for(" + loopParams[0] + ";" + loopParams[1] + ";" + loopParams[2] + ")");
		code.setErrCode(IErrorCodes.SUCCESS);
		code.setErrParam(null);
	}
	
	// While loop handles the basic cases like declare while loop for i<10, iterate until i less than ten and j less than 20
	private void while_loop(){
		VarSymbol v = null;
		FuncSymbol f = null;
		int index = input.indexOf(words.get(wordLoc));	// Index in input string at which the word was encountered
		index += (words.get(wordLoc).length() + 1);
		String str = input.substring(index); 		// Further processing will be done on this string
		tokens = str.split("[ ]+");
		String expString = "";
		
		tIndex = 0;
		while(true){
			if((v = Controller.varSym.searchSymbol(tokens[tIndex])) != null /*TODO Check whether token is a number*/){
				expString = searchExpression();
				if(words.get(wordLoc).indexOf("until") != -1)
					code.setCodeText("while(!(" + expString + "))");
				else
					code.setCodeText("while(" + expString + ")");
				code.setErrCode(IErrorCodes.SUCCESS);
				code.setErrParam(null);
				break;
			}
			else
			if((f = Controller.funSym.searchSymbol(tokens[tIndex])) != null){
				int tempIndex = input.indexOf(tokens[tIndex]);
				expString = exp.generateExpression(input.substring(tempIndex));
				if(words.get(wordLoc).indexOf("until") != -1)
					code.setCodeText("while(!(" + expString + "))");
				else
					code.setCodeText("while(" + expString + ")");
				code.setErrCode(IErrorCodes.SUCCESS);
				code.setErrParam(null);
				break;
			}
			else
			if((tokens[tIndex].equals("true") || tokens[tIndex].equals("true")) && tIndex+1 == tokens.length){
				code.setCodeText("while(" + tokens[tIndex] + ")");
			}
		}
	}
	
	private void do_while_loop(){		
	}
}

/*
Expression Extraction code:
===========================
expString = tokens[tIndex];
tIndex++;
if(tokens[tIndex].equalsIgnoreCase("from"))
	tokens[tIndex] = "equal to";
tempStr = searchOperator(tIndex);
if(tempStr != null){ 				// If next token is operator
	expString = " " + tempStr;
	flag = true;
	while(true){
		//index = tIndex;
		if((tempStr = searchOperator(tIndex)) != null){
			flag = true;
			expString = " " + tempStr;	// Note: tIndex automatically incremented by searchOperator() method
		}
		else
		if(Controller.varSym.searchSymbol(tokens[tIndex])!= null || Controller.funSym.searchSymbol(tokens[tIndex]) != null){
			if(flag == true){
				flag = false;								
				expString += " " + tokens[tIndex];
				tIndex++;
			}
			else
				break;
		}
		else
			break;
	}					
}*/