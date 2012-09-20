package my.shared;

import java.io.Serializable;

public class CookieObj implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String email = "";
	private String md5session = "";
	private boolean isFBUser = false;
	private int userRole = 0;
	private String nick = "Anonymous";
	 
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		email = email.replace("\"", "");
		this.email = email;
	}
	public boolean isFBUser() {
		return isFBUser;
	}
	public void setFBUser(boolean isFBUser) {
		this.isFBUser = isFBUser;
	}
	public int getUserRole() {
		return userRole;
	}
	public void setUserRole(int userRole) {
		this.userRole = userRole;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		nick = nick.replace("\"", "");
		this.nick = nick;
	}
	
	public String generateCookieString(String md5session) {
		this.setMd5session(md5session);
		String cookieStr =  this.getEmail() + "###" + md5session + "###" + this.isFBUser() + "###" + this.getUserRole() + "###" + this.getNick();
		return cookieStr;
	}
	
	public void generateFromString(String cookie) {
		
		//Log.debug("generateFromString " + cookie);
		
		cookie = cookie.replace("\"", "");

		String[] tokens = cookie.split("###");
		if (tokens.length>=1)
		this.email = tokens[0];
		
		//Log.debug("generateFromString 1 " + cookie);
		
		if (tokens.length>=2)
		this.setMd5session(tokens[1]);
		
		//Log.debug("generateFromString 3 " + cookie);
		
		if (tokens.length>=3)
		this.isFBUser = Boolean.parseBoolean(tokens[2]);
		
		//Log.debug("generateFromString 4 " + cookie);
		
		if (tokens.length>=4)
		this.userRole = Integer.parseInt(tokens[3]);
		
		//Log.debug("generateFromString 5 " + cookie);
		
		if (tokens.length>=5)
		this.nick = tokens[4];
		
		//Log.debug("generateFromString 6 " + cookie);
		//Log.debug("CookieObj generateFromString md5session " + md5session);
		///Log.debug("CookieObj generateFromString isFBUser " + isFBUser);
		//Log.debug("CookieObj generateFromString userRole " + userRole);
		//Log.debug("CookieObj generateFromString nick " + nick);

		

	}
	public String getMd5session() {
		return md5session;
	}
	public void setMd5session(String md5session) {
		this.md5session = md5session;
	}

}
