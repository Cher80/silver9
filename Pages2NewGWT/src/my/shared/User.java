package my.shared;


import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private String uid;
	private String nick;
	private String email;



	public User() {

	}



	public String getUid() {
		return uid;
	}



	public void setUid(String uid) {
		this.uid = uid;
	}



	public String getNick() {
		return nick;
	}



	public void setNick(String nick) {
		this.nick = nick;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}
}