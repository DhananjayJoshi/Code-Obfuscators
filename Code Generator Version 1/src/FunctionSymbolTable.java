import java.util.HashMap;


public class FunctionSymbolTable {
	// Data members:
	private HashMap<String, FuncSymbol> symTab = null;
	
	// Constructor:
	public FunctionSymbolTable() {
		symTab = new HashMap<String, FuncSymbol> ();
	}
	
	// Methods:
	void insert(String funcName, FuncSymbol symbol){
		symTab.put(funcName, symbol);
	}
	
	FuncSymbol delete(String key){
		return symTab.remove(key);
	}
	
	FuncSymbol searchSymbol(String symbol){
		return symTab.get(symbol);
	}
}
