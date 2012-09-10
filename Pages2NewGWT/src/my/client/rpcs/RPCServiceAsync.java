package my.client.rpcs;

import my.shared.AlbumObj;
import my.shared.AlbumsObj;
import my.shared.CommentObj;
import my.shared.CookieObj;
import my.shared.TagObj;
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

	void doPostComment(CommentObj commentObj, AsyncCallback callback);

	void doGetComments(String albid, AsyncCallback callback);

	void doSetTag(TagObj tagObj, AlbumObj albumObj, User user, AsyncCallback callback);

	//void doSetTag(TagObj tagObj, AlbumObj albumObj, User user, AsyncCallback callback); 


    
	
	

}
