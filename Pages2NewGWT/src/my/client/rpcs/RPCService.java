package my.client.rpcs;

import java.util.ArrayList;

import my.shared.AlbumObj;
import my.shared.AlbumsObj;
import my.shared.CommentObj;
import my.shared.CommentsObj;
import my.shared.CookieObj;
import my.shared.ModelPageObj;
import my.shared.TagObj;
import my.shared.User;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("getrpc")
public interface RPCService extends RemoteService {

	public User doRegister(String nick,String email,String pass1,String pass2) throws RPCServiceExeption;

	public User doLogin(String email, String pass1) throws RPCServiceExeption;
	public User getUserByCookie(CookieObj cookieObj) throws RPCServiceExeption;
  
	AlbumsObj getAlbumsByTime(int offest, int limit) throws RPCServiceExeption;

	ModelPageObj getModelPage(String modelID, String photoID) throws RPCServiceExeption;

	public CommentObj doPostComment(CommentObj commentObj) throws RPCServiceExeption;

	CommentsObj doGetComments(String albid) throws RPCServiceExeption;

	TagObj doSetTag(TagObj tagObj, AlbumObj albumObj, User user)  throws RPCServiceExeption; 
}
