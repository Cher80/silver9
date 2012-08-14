package my.client.rpcs;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("makerpc")
public interface RPCService extends RemoteService {

	public int doRegister(int uid);
}
