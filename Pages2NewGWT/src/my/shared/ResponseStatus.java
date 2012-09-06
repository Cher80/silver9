package my.shared;

import java.io.Serializable;

public class ResponseStatus implements Serializable  {

	private static final long serialVersionUID = 1L;
	private boolean isOK;
	private String textStatus;
	
	public boolean isOK() {
		return isOK;
	}
	
	public void setOK(boolean isOK) {
		this.isOK = isOK;
	}
	
	public String getTextStatus() {
		return textStatus;
	}
	
	public void setTextStatus(String textStatus) {
		this.textStatus = textStatus;
	}
}
