package my.server.exutor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import my.client.rpcs.RPCServiceExeption;
import my.server.CommonsServer;
import my.server.MongoPool;
import my.shared.CookieObj;

public class SilverCookie {

	private HttpServletResponse response;
	private HttpServletRequest request;
	public static final Logger LOG=Logger.getLogger(SilverCookie.class);
	/*
	private String email;
	private boolean isFBUser;
	private int userRole;
	private String nick;*/
	private CookieObj cookieObj;
	
	public SilverCookie(HttpServletResponse response, HttpServletRequest request) {
		this.response = response;
		this.request = request;
		//this.cookieObj = cookieObj;

	}
	
	public void setCookie(CookieObj cookieObj) {
		String md5session = CommonsServer.MD5(MongoPool.getSecretKey() + cookieObj.getEmail());
		String cookieStr = cookieObj.generateCookieString(md5session);
		//String cookieStr =  cookieObj.getEmail() + "###" + md5session + "###" + cookieObj.isFBUser() + "###" + cookieObj.getUserRole() + "###" + cookieObj.getNick();
		//String cookieEnc = null;
		/*
		try {
			cookieEnc = URLEncoder.encode(cookieStr, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
	 		e.printStackTrace();
		} */
		Cookie cookie=new Cookie("silver9session",cookieStr);
		cookie.setVersion(1);
		cookie.setPath("/");
		cookie.setMaxAge(999999999);
		response.addCookie(cookie);
	}
	
	public CookieObj getCookie() throws RPCServiceExeption {
		//String serverInfo = getServletContext().getServerInfo();
		String userAgent = request.getHeader("User-Agent");
		LOG.info("userAgent " + userAgent);
		

		
		Cookie[] cookies=request.getCookies();

		//Cookie clientCookie=null;

		//cookies.
		/*
		if(cookies!=null){
			clientCookie=cookies[0];
		}*/
		CookieObj cookieObj = new CookieObj();
		if (cookies !=null) {
			for (int i=0; i<cookies.length; i++) {
				LOG.info("cookies[i].getName() " + cookies[i].getName());
				if (cookies[i].getName().equals("silver9session")) {
					LOG.info("cookies silver Found");
					cookieObj.generateFromString(cookies[i].getValue());
					return cookieObj;
				}
			}
			
			//CookieObj
		}
		else {
			LOG.info("Error: No Cookie!");
			return cookieObj = null;
			//Cookie cookie=new Cookie("silver9session","1234olo");
			//getThreadLocalResponse().addCookie(cookie);
			//getThreadLocalResponse()cookies
			//this.getThreadLocalResponse()
			
			//throw new RPCServiceExeption("Error: No Cookie!");
			
		}
		return cookieObj = null;
		//return cookieObj;
	}
	
}
