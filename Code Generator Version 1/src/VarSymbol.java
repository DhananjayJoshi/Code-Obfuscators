
public class VarSymbol {
	// Data members:
	private String varName;		// Name of variable
	private String dataType;	// Data type
	private int size = 0; 		// 0: for single variable; =no._of_array_elements: in case of array
	
	// Constructor:
	public VarSymbol(){}
	public VarSymbol(String varName, String dataType, int size) {
		this.varName = varName;
		this.dataType = dataType;
		this.size = size;
	}
	
	// Methods:
	public String getVarName() {
		return varName;
	}
	public void setVarName(String varName) {
		this.varName = varName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}	
}