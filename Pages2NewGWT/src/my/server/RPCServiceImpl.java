package my.server;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

import my.client.rpcs.RPCService;
import my.client.rpcs.RPCServiceExeption;
import my.server.exutor.Albums;
import my.server.exutor.Images;
import my.server.exutor.Login;
import my.server.exutor.Register;
import my.server.exutor.UserCookie;
import my.shared.AlbumObj;
import my.shared.ImgsObj;
import my.shared.ModelPageObj;
import my.shared.User;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class RPCServiceImpl extends RemoteServiceServlet implements RPCService {

	Logger LOG=Logger.getLogger(RPCServiceImpl.class);
	private static final long serialVersionUID = 1L;
	
	private void getUser () {
		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");
		LOG.info("userAgent " + userAgent);
		LOG.info("serverInfo " + serverInfo);

		
		Cookie[] cookies=getThreadLocalRequest().getCookies();

		//Cookie clientCookie=null;

		//cookies.
		/*
		if(cookies!=null){
			clientCookie=cookies[0];
		}*/
		if (cookies !=null) {
			for (int i=0; i<cookies.length; i++) {
				LOG.info("cookies[i].getName() " + cookies[i].getName());
			}
		}
		else {
			//Cookie cookie=new Cookie("silver9session","1234olo");
			//getThreadLocalResponse().addCookie(cookie);
			//getThreadLocalResponse()cookies
			//this.getThreadLocalResponse()
			LOG.info("Error: Please login!");
			throw new RPCServiceExeption("Error: Please login!");
			
		}
	}

	@Override
	public User doRegister(String nick,String email,String pass1,String pass2) throws RPCServiceExeption {
		LOG.info("doRegister LOG4J!");
		Register register =  new Register();
		User result = register.executeRegister( nick, email, pass1, pass2, getThreadLocalResponse(), this.getThreadLocalRequest());
		
		return result;
	}

	@Override
	public User doLogin(String email, String pass1) throws RPCServiceExeption {
		LOG.info("doLogin!");
		Login login =  new Login();
		User result = login.executeLogin( email, pass1, getThreadLocalResponse(), this.getThreadLocalRequest());

		
		return result;
	}
 
	@Override
	public User getUserByCookie(String cookie) throws RPCServiceExeption {
		// TODO Auto-generated method stub
		LOG.info("do getUserByCookie!");
			UserCookie userCookie =  new UserCookie();
			User user =  userCookie.getUserByCookie(cookie);
		/*
		User user = new User();
		user.setEmail("999@999.999");
		user.setNick("bob");
		*/
		return user;
		
		
	}

	@Override
	public ArrayList<AlbumObj> getAlbumsByTime(int offest, int limit) throws RPCServiceExeption {
		// TODO Auto-generated method stub
		LOG.info("do getAlbumsByTime!");
		Albums albums =  new Albums();
		ArrayList<AlbumObj> albumObjs= albums.getAlbumsByTime(offest, limit, null);
		return albumObjs;
	}
	
	

	@Override
	public ModelPageObj getModelPage(String albid, String photoID) throws RPCServiceExeption {
		// TODO Auto-generated method stub
		LOG.info("do getModelPage! albid=" + albid + " photoID=" + photoID);
		ModelPageObj modelPageObj = new ModelPageObj();		
		
		Albums albums =  new Albums();
		ArrayList<AlbumObj> albumObjs= albums.getAlbumsByTime(0, 0, albid);		
		modelPageObj.setAlbumObj(albumObjs.get(0)); 
		LOG.info("do getModelPage  4");
		Images images =  new Images();
		ImgsObj imgsObj= images.getImages(albid);
		
		modelPageObj.setImages(imgsObj); 
		
		return modelPageObj;
	}

}
