package my.client.rpcs;

import java.util.ArrayList;

import my.shared.AlbumObj;
import my.shared.AlbumsObj;
import my.shared.AlbumsPageObj;
import my.shared.CommentObj;
import my.shared.CommentsObj;
import my.shared.CookieObj;
import my.shared.ImgObj;
import my.shared.ModelPageObj;
import my.shared.ResponseStatus;
import my.shared.TagObj;
import my.shared.TopBlockObj;
import my.shared.User;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("getrpc")
public interface RPCService extends RemoteService {

	public User doRegister(String nick,String email,String pass1,String pass2) throws RPCServiceExeption;

	public User doLogin(String email, String pass1) throws RPCServiceExeption;
	public User getUserByCookie(CookieObj cookieObj) throws RPCServiceExeption;
   
	
	AlbumsPageObj getAlbumsPageObj() throws RPCServiceExeption;
	AlbumsObj getAlbumsByTime(int offest, int limit, String tagType, int statusPublished) throws RPCServiceExeption;

	ModelPageObj getModelPage(String modelID, String photoID) throws RPCServiceExeption;
	TopBlockObj getTopBlock(CookieObj cookieObj) throws RPCServiceExeption;

	public CommentObj doPostComment(CommentObj commentObj, AlbumObj albumObj) throws RPCServiceExeption;

	CommentsObj doGetComments(String albid) throws RPCServiceExeption;

	TagObj doSetTag(TagObj tagObj, AlbumObj albumObj, User user)  throws RPCServiceExeption;
	ResponseStatus doAlbumStatus(AlbumObj albumObj, int statusPublished)  throws RPCServiceExeption;
	ResponseStatus doDeAlbum(AlbumObj albumObj)  throws RPCServiceExeption;
	ResponseStatus doImgStatus(ImgObj imgObj,  int statusPublished) throws RPCServiceExeption;
	ResponseStatus doImgCover(AlbumObj albumObj, ImgObj imgObj) throws RPCServiceExeption;
	ResponseStatus postFBPage(AlbumObj albumObj, User user) throws RPCServiceExeption;

}
