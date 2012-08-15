package my.client.rpcs;

import java.io.Serializable;

public class RPCServiceExeption extends RuntimeException implements Serializable {
	private static final long serialVersionUID = 1L;

//test
	private String errorCode;


	public RPCServiceExeption() {};

	public RPCServiceExeption(String errorCode) {
//super();
		this.errorCode = errorCode;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}	


