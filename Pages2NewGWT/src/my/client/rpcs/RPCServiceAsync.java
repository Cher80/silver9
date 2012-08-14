package my.client.rpcs;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RPCServiceAsync {

	void doRegister(int uid, AsyncCallback<Integer> callback); 

}
