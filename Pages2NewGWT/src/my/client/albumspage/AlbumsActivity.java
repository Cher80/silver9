package my.client.albumspage;

import java.util.ArrayList;

import my.client.common.ClientFactory;
import my.client.common.MyActivity;
import my.client.forum.ForumPlace;
import my.client.rpcs.RPCService;
import my.client.rpcs.RPCServiceAsync;
import my.client.rpcs.RPCServiceExeption;
import my.shared.AlbumObj;
import my.shared.User;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;

public class AlbumsActivity extends MyActivity{
	
	public AlbumsActivity(AlbumsPlace place) {
		super();
		super.setPlace(place);
		
	}
	
	
	public void getAlbums() {
		RPCServiceAsync communicatorSvc = GWT.create(RPCService.class);

		// Set up the callback object.
		AsyncCallback callback = new AsyncCallback() {

			public void onFailure(Throwable caught) {

				if (caught instanceof RPCServiceExeption) {
					Log.debug("exeption!!" + ((RPCServiceExeption)caught).getErrorCode());
				}
			}

			@Override
			public void onSuccess(Object result) {
				Log.debug("UserArea onSuccess ");
				ArrayList<AlbumObj> albumObjs = (ArrayList<AlbumObj>)result;
				for (int i=0; i<albumObjs.size(); i++) {
					AlbumObj albumObj = albumObjs.get(i);
					//Log.debug("albumObj.getAlbname()" + albumObj.getAlbname());
					//Log.debug("albumObj.getAlbpage()" + albumObj.getAlbpage());
				}
				populateAlbumsView(albumObjs);
			}
		};

		Log.debug("Make the call");
		int offest = 0;
		int limit = 20;
		
	communicatorSvc.getAlbumsByTime( offest, limit, callback);
	}
	
	
	public void populateAlbumsView(ArrayList<AlbumObj> albumObjs) {
		for (int i=0; i<albumObjs.size(); i++) {
			AlbumObj albumObj = albumObjs.get(i);
			Log.debug("albumObj.getAlbname()" + albumObj.getAlbname());
			Log.debug("albumObj.getAlbpage()" + albumObj.getAlbpage());
			AlbumsView albumsView = (AlbumsView)this.getView();
			albumsView.addAlbumThumb(albumObj);
		}
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		
		AlbumsView albumsView = new AlbumsView(this);
		
		 	//String params = plac;
	        String paramsLine = ((AlbumsPlace)this.getPlace()).getPlaceName();
	        /* delimiter */
	        String delimiter = "&";
	        /* given string will be split by the argument delimiter provided. */
	        String[] paramsArray;
	        paramsArray = paramsLine.split(delimiter);
	        Log.debug("paramsArray = " + paramsArray + paramsArray.toString());
		
		
		this.setView(albumsView);
		panel.setWidget(albumsView.asWidget());
		getAlbums();
		
		
		
	}

}
