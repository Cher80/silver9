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
import my.server.exutor.ActivityExec;
import my.server.exutor.Albums;
import my.server.exutor.Anonim;
import my.server.exutor.CommentsExec;
import my.server.exutor.FBExec;
import my.server.exutor.Images;
import my.server.exutor.Login;
import my.server.exutor.Register;
import my.server.exutor.SilverCookie;
import my.server.exutor.StatExec;
import my.server.exutor.TagExec;
import my.server.exutor.UserCookie;
import my.shared.ActivitiesObj;
import my.shared.AlbumObj;
import my.shared.AlbumsObj;
import my.shared.AlbumsPageObj;
import my.shared.CommentObj;
import my.shared.CommentsObj;
import my.shared.CookieObj;
import my.shared.ImgObj;
import my.shared.ImgsObj;
import my.shared.ModelPageObj;
import my.shared.ResponseStatus;
import my.shared.Settings;
import my.shared.StatObj;
import my.shared.TagObj;
import my.shared.TagsObj;
import my.shared.TopBlockObj;
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

	private User allowPermissions(int[] allowedrole) throws RPCServiceExeption {
		LOG.info("allowPermissions");
		User userToRet;
		int curuserrole = 0;
		SilverCookie silverCookie = new SilverCookie(this.getThreadLocalResponse(), this.getThreadLocalRequest());
		CookieObj cookieObj = silverCookie.getCookie();
		if (cookieObj==null) {
			curuserrole = 0;
			Anonim anonim = new Anonim();
			userToRet = anonim.getAnonimUser();
			//userToRet = new A
		}
		else {
			UserCookie userCookie =  new UserCookie();
			userToRet =  userCookie.getUserByCookie(cookieObj);
			curuserrole = userToRet.getUserRole();
		}
		//LOG.info("Email not found ");
		LOG.info("curuserrole" + curuserrole);
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




	@Override
	public User doRegister(String nick,String email,String pass1,String pass2) throws RPCServiceExeption {
		LOG.info("doRegister LOG4J!");
		Register register =  new Register();
		User result = register.executeRegister( nick, email, pass1, pass2, false, 1, getThreadLocalResponse(), this.getThreadLocalRequest());

		return result;
	}

	@Override
	public User doLogin(String email, String pass1) throws RPCServiceExeption {
		LOG.info("doLogin!");
		Login login =  new Login();
		User result = login.executeLogin( email, pass1, false, getThreadLocalResponse(), this.getThreadLocalRequest());


		return result;
	}

	@Override
	public User getUserByCookie(CookieObj cookieObj) throws RPCServiceExeption {
		// TODO Auto-generated method stub
		LOG.info("do getUserByCookie!");
		UserCookie userCookie =  new UserCookie();
		User user =  userCookie.getUserByCookie(cookieObj);
		/*
		User user = new User();
		user.setEmail("999@999.999");
		user.setNick("bob");
		 */
		return user;


	}

	@Override
	public AlbumsObj getAlbumsByTime(int offest, int limit, String tagType, int statusPublished) throws RPCServiceExeption {
		// TODO Auto-generated method stub
		LOG.info("do getAlbumsByTime!");
		Albums albums =  new Albums();
		AlbumsObj albumsObj= albums.getAlbumsByTime(offest, limit, tagType, statusPublished, null);
		return albumsObj; 
	}



	@Override
	public ModelPageObj getModelPage(String albid, String photoID) throws RPCServiceExeption {
		// TODO Auto-generated method stub
		int[] permissions = {0,1,2};
		User user = allowPermissions(permissions);

		LOG.info("do getModelPage! albid=" + albid + " photoID=" + photoID);
		ModelPageObj modelPageObj = new ModelPageObj();		

		Albums albums =  new Albums();
		AlbumsObj albumsObj = albums.getAlbumsByTime(0, 0, null, 0, albid);	
		
		AlbumObj albumObj = null;
		if (albumsObj.getAlbums().size()>0) {
			albumObj = albumsObj.getAlbums().get(0);
			modelPageObj.setAlbumObj(albumObj); 
		} else {
			modelPageObj.setAlbumObj(new AlbumObj()); 
		}



		Images images =  new Images();
		ImgsObj imgsObj= images.getImages(albid);		
		modelPageObj.setImages(imgsObj); 

		CommentsExec commentsExec = new CommentsExec(); 
		CommentsObj commentsObj = commentsExec.doGetComments(albid);
		modelPageObj.setComments(commentsObj);

		TagExec tagExec = new TagExec();
		TagsObj tagsObj = tagExec.getTagObjs2(albumObj, user);
		modelPageObj.setTagsObj(tagsObj);


		return modelPageObj;
	}

	@Override
	public CommentObj doPostComment(CommentObj commentObj, AlbumObj albumObj) throws RPCServiceExeption {
		// TODO Auto-generated method stub

		int[] permissions = {0,1,2};
		User user = allowPermissions(permissions);
		commentObj.setCommentAuthorID(user.getUid());
		commentObj.setCommentAuthorNick(user.getNick());
		//SilverCookie silverCookie = new SilverCookie(this.getThreadLocalResponse(), this.getThreadLocalRequest());
		//silverCookie.getCookie();
		//getUser ();
		CommentsExec commentsExec = new CommentsExec(); 
		commentObj = commentsExec.executeCommentPost(commentObj, albumObj) ;

		return commentObj;
	}

	@Override
	public CommentsObj doGetComments(String albid) throws RPCServiceExeption {
		// TODO Auto-generated method stub

		CommentsExec commentsExec = new CommentsExec(); 
		CommentsObj commentsObj = commentsExec.doGetComments(albid);
		return commentsObj;
	}




	@Override
	public TagObj doSetTag(TagObj tagObj, AlbumObj albumObj, User user) throws RPCServiceExeption {

		int[] permissions = {1,2};
		user = allowPermissions(permissions);
		//SilverCookie silverCookie = new SilverCookie(this.getThreadLocalResponse(), this.getThreadLocalRequest());
		//silverCookie.getCookie();
		//getUser ();
		TagExec tagExec = new TagExec(); 
		//TagObj tagRetObj = tagExec.executeSetTag(tagObj,  albumObj, user);
		TagObj tagRetObj = tagExec.executeSetTag2(tagObj,  albumObj, user);

		return tagRetObj;
	}




	@Override
	public ResponseStatus doAlbumStatus(AlbumObj albumObj, int statusPublished)
			throws RPCServiceExeption {

		int[] permissions = {2};
		User user = allowPermissions(permissions);
		// TODO Auto-generated method stub
		Albums albums =  new Albums();
		ResponseStatus responseStatus = albums.doAlbumStatus(albumObj, statusPublished);	

		return responseStatus;
	}




	@Override
	public ResponseStatus doDeAlbum(AlbumObj albumObj)
			throws RPCServiceExeption {

		int[] permissions = {2};
		User user = allowPermissions(permissions);
		// TODO Auto-generated method stub
		Albums albums =  new Albums();
		ResponseStatus responseStatus = albums.doDeAlbum(albumObj);	


		return responseStatus;
	}




	@Override
	public AlbumsPageObj getAlbumsPageObj() throws RPCServiceExeption {

		int[] permissions = {0,1,2};
		User user = allowPermissions(permissions);

		Albums albums =  new Albums();
		AlbumsObj albumsObj = albums.getAlbumsByTime(0, Settings.ALBUMS_PER_PAGE, null, 1, null);

		ActivityExec activityExec = new ActivityExec();
		ActivitiesObj activitiesObj = activityExec.getActivitiesBlock();

		AlbumsObj albumsObjBlock = albums.getAlbumsBestBloc();

		AlbumsPageObj albumsPageObj = new AlbumsPageObj();
		albumsPageObj.setAlbumsObj(albumsObj);
		albumsPageObj.setActivitiesObj(activitiesObj);
		albumsPageObj.setBestAlbumsObj(albumsObjBlock);

		return albumsPageObj;
	}




	@Override
	public ResponseStatus doImgStatus(ImgObj imgObj,  int statusPublished) throws RPCServiceExeption {
		int[] permissions = {2};
		User user = allowPermissions(permissions);


		Images images = new Images();
		ResponseStatus responseStatus = images.doImgStatus(imgObj, statusPublished);
		// TODO Auto-generated method stub
		return responseStatus;
	}




	@Override 
	public ResponseStatus doImgCover(AlbumObj albumObj, ImgObj imgObj) throws RPCServiceExeption {
		// TODO Auto-generated method stub
		int[] permissions = {2};
		User user = allowPermissions(permissions);

		Images images = new Images();
		ResponseStatus responseStatus = images.doImgCover(albumObj, imgObj);

		return responseStatus;
	}




	@Override
	public TopBlockObj getTopBlock(CookieObj cookieObj)
			throws RPCServiceExeption {
		// TODO Auto-generated method stub
		User user = null;
		if (cookieObj!=null) {
			UserCookie userCookie =  new UserCookie();
			user =  userCookie.getUserByCookie(cookieObj);
		}

		//StatObj statObj = new StatObj();
		StatExec statExec = new StatExec();
		StatObj statObj = statExec.getStat();

		TopBlockObj topBlockObj = new TopBlockObj();
		topBlockObj.setUser(user);
		topBlockObj.setStatObj(statObj);

		return topBlockObj;
	}




	@Override
	public ResponseStatus postFBPage(AlbumObj albumObj, User user)
			throws RPCServiceExeption {
		// TODO Auto-generated method stub
		
		FBExec fbExec = new FBExec(albumObj, user);
		fbExec.fbPost();
		return null;
	}

}
