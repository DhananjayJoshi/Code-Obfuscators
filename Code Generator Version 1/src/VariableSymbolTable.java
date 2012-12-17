import java.util.*;

public class VariableSymbolTable {
	// Data members:
	private HashMap<String, VarSymbol> symTab = null;
	
	// Constructor:
	public VariableSymbolTable() {
		symTab = new HashMap<String, VarSymbol> ();
	}
	
	// Methods:
	void insert(String varName, VarSymbol symbol){
		symTab.put(varName, symbol);
	}
	
	VarSymbol delete(String key){
		return symTab.remove(key);
	}
	
	VarSymbol searchSymbol(String symbol){
		return symTab.get(symbol);
	}
}
