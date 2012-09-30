package my.client.rpcs;

import my.shared.AlbumObj;
import my.shared.AlbumsObj;
import my.shared.AlbumsPageObj;
import my.shared.CommentObj;
import my.shared.CookieObj;
import my.shared.ImgObj;
import my.shared.ResponseStatus;
import my.shared.TagObj;
import my.shared.TopBlockObj;
import my.shared.User;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RPCServiceAsync { 

	void doRegister(String nick, String email, String pass1, String pass2,
			AsyncCallback<User> callback);

	void doLogin(String email, String pass1, AsyncCallback<User> callback);

	void getUserByCookie(CookieObj cookieObj, AsyncCallback<User> callback);

	void getAlbumsByTime(int offest, int limit, String tagType,
			int statusPublished, AsyncCallback<AlbumsObj> callback);

	void getModelPage(String modelID, String photoID, AsyncCallback callback);
 
	void doPostComment(CommentObj commentObj, AlbumObj albumObj,
			AsyncCallback<CommentObj> callback);

	void doGetComments(String albid, AsyncCallback callback);

	void doSetTag(TagObj tagObj, AlbumObj albumObj, User user, AsyncCallback callback);

	void doAlbumStatus(AlbumObj albumObj, int statusPublished,
			AsyncCallback<ResponseStatus> callback);

	void doDeAlbum(AlbumObj albumObj, AsyncCallback<ResponseStatus> callback);
 
	void getAlbumsPageObj(AsyncCallback<AlbumsPageObj> callback);

	void doImgStatus(ImgObj imgObj, int statusPublished, 
			AsyncCallback<ResponseStatus> callback);
 
	void doImgCover(AlbumObj albumObj, ImgObj imgObj, AsyncCallback<ResponseStatus> callback);

	void getTopBlock(CookieObj cookieObj, AsyncCallback<TopBlockObj> callback);
   
	//void doSetTag(TagObj tagObj, AlbumObj albumObj, User user, AsyncCallback callback); 


    
	
	

}
