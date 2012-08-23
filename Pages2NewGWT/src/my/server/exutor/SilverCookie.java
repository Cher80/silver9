package my.server.exutor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import my.server.Commons;
import my.server.MongoPool;

public class SilverCookie {

	private HttpServletResponse response;
	private String email;
	
	public SilverCookie(HttpServletResponse response, String email) {
		this.response = response;
		this.email = email;
	}
	
	public void setCookie() {
		String md5session = Commons.MD5(MongoPool.getSecretKey() + email);
		
		Cookie cookie=new Cookie("silver9session", email + "###" + md5session);
		cookie.setPath("/");
		cookie.setMaxAge(999999999);
		response.addCookie(cookie);
	}
}
