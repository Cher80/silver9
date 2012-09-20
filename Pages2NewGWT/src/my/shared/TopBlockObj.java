package my.shared;

import java.io.Serializable;

public class TopBlockObj implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private StatObj statObj;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public StatObj getStatObj() {
		return statObj;
	}
	public void setStatObj(StatObj statObj) {
		this.statObj = statObj;
	}
}
