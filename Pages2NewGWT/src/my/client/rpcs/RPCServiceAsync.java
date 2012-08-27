package my.client.rpcs;

import my.shared.User;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RPCServiceAsync {

	void doRegister(String nick, String email, String pass1, String pass2,
			AsyncCallback<User> callback);

	void doLogin(String email, String pass1, AsyncCallback<User> callback);

	void getUserByCookie(String cookie, AsyncCallback<User> callback);

	void getAlbumsByTime(int offest, int limit, AsyncCallback callback);

	void getModelPage(String modelID, String photoID, AsyncCallback callback);  
	
	

}
