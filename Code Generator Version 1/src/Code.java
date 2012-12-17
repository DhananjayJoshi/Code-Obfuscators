
class Code {
	// Data members:
	int errCode;		// TODO to be declared as an array to handle multiple error and their codes
	String [] errParam;
	String codeText;  // TODO: Remove from this class as same feature provided by 'IErrorCode' interface
	
	// Constructor:
	Code() {
		errCode = IErrorCodes.SUCCESS;
		codeText = "";
	}
	
	// Methods:
	public int getErrCode() {
		return errCode;
	}
	
	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}
	
	public String[] getErrParam() {
		return errParam;
	}
	
	public void setErrParam(String[] errParam) {
		this.errParam = errParam;
	}
	
	public String getCodeText() {
		return codeText;
	}
	
	public void setCodeText(String codeText) {
		this.codeText = codeText;
	}	
}