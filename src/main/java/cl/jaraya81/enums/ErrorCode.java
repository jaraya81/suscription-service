package cl.jaraya81.enums;

public enum ErrorCode {

	ERROR("Error"), 
	
	DRIVER_NOT_FOUND("Driver not found"),
	
	NOT_IMPLEMENTED("Not implemented"),
	
	FILE_NOT_FOUND("File not found"),
	
	PARAM_NULL("Param null");
	
	private String code;

	private ErrorCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
