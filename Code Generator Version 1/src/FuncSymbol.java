
public class FuncSymbol {
	// Data members:
	private String funcName; 	// Method/Function name
	private String [] param;	// Data types of Parameters
	private String returnType;	// Return type
	
	// Constructor:
	public FuncSymbol(){}
	public FuncSymbol(String funcName, String [] param, String returnType) {
		this.funcName = funcName;
		this.param = param;
		this.returnType = returnType;
	}

	// Methods:
	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String[] getParam() {
		return param;
	}

	public void setParam(String[] param) {
		this.param = param;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
}
