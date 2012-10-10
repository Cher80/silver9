package my.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import my.client.rpcs.RPCServiceExeption;
import my.server.exutor.Anonim;
import my.server.exutor.SilverCookie;
import my.server.exutor.UserCookie;
import my.shared.CookieObj;
import my.shared.User;


public class Permissions {
	private HttpServletResponse response;
	private HttpServletRequest request;
	
	public Permissions(HttpServletResponse _response, HttpServletRequest _request) {
		this.response = _response; 
		this.request = _request;
	}
	private User allowPermissions(int[] allowedrole) throws RPCServiceExeption {
//		LOG.info("allowPermissions");
		User userToRet = new User(); 
		int curuserrole = 0;
		SilverCookie silverCookie = new SilverCookie(response, request);
		CookieObj cookieObj = silverCookie.getCookie();
		if (cookieObj==null) {
			curuserrole = 0;
			Anonim anonim = new Anonim();
			userToRet = anonim.getAnonimUser();
			//userToRet = new A
		}
		else {
			UserCookie userCookie =  new UserCookie();
			try {
			
			userToRet =  userCookie.getUserByCookie(cookieObj);
			curuserrole = userToRet.getUserRole();
			}
			catch (RPCServiceExeption e) {
	            //System.exit(0);
	        } 
		}
		//LOG.info("Email not found ");
//		LOG.info("curuserrole" + curuserrole);
		boolean isCurUserInAllowPermissions = false;
		for (int i=0;i<allowedrole.length;i++) {
			if (curuserrole==allowedrole[i]) {
				isCurUserInAllowPermissions = true;
			}
		}

		if (!isCurUserInAllowPermissions) {
			throw new RPCServiceExeption("Error: no permissions");
		}

		return userToRet;

	}
}
