package my.client.albumspage;

import java.util.ArrayList;

import my.client.common.ActivityHasPages;
import my.client.common.ClientFactory;
import my.client.common.MyActivity;
import my.client.common.ViewHasPages;
import my.client.forum.ForumPlace;
import my.client.paginator.Paginator;
import my.client.rpcs.RPCService;
import my.client.rpcs.RPCServiceAsync;
import my.client.rpcs.RPCServiceExeption;
import my.shared.AlbumObj;
import my.shared.AlbumsObj;
import my.shared.User;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;

public class AlbumsActivity extends MyActivity implements ActivityHasPages{

	private int albumsTotalCount = 0;
	private int currentPage = 0;
	private Paginator paginator;
	private boolean isFirstTimeLoaded=true;

	public AlbumsActivity(AlbumsPlace place) {
		super();
		super.setPlace(place);
		this.currentPage = 0;

	}




	public void getAlbums(int page, final boolean forceClearOnFinish) {
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
				AlbumsObj albumsObj = (AlbumsObj)result;
				/*
				for (int i=0; i<albumsObj.getAlbums().size(); i++) {
					AlbumObj albumObj = albumsObj.getAlbums().get(i);
					//Log.debug("albumObj.getAlbname()" + albumObj.getAlbname());
					//Log.debug("albumObj.getAlbpage()" + albumObj.getAlbpage());
				}*/

				populateAlbumsView(albumsObj,forceClearOnFinish);
			}
		};

		Log.debug("Make the call");
		int offest = 6*page;
		int limit = 6;

		communicatorSvc.getAlbumsByTime( offest, limit, callback);
	}


	public void populateAlbumsView(AlbumsObj albumsObj, boolean forceClearOnFinish) {
		AlbumsView albumsView = (AlbumsView)this.getView();

		if (isFirstTimeLoaded) {
			paginator = new Paginator(albumsObj.getTotalCount(),this, (ViewHasPages)this.getView());
			albumsView.setPaginator(paginator);
			paginator.setCurrentPage(0);		
			isFirstTimeLoaded=false;
		}


		this.albumsTotalCount = albumsObj.getTotalCount();
		if (forceClearOnFinish) {
			albumsView.clearWidget(0);
			albumsView.scrollToTop();
		}

		
		albumsView.populateAlbumsView(albumsObj);
		paginator.onSuccessLoad();


		/*
		for (int i=0; i<albumObjs.size(); i++) {
			AlbumObj albumObj = albumObjs.get(i);
			Log.debug("albumObj.getAlbname()" + albumObj.getAlbname());
			Log.debug("albumObj.getAlbpage()" + albumObj.getAlbpage());
			AlbumsView albumsView = (AlbumsView)this.getView();
			albumsView.addAlbumThumb(albumObj);
		}*/
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
		getAlbums(0,false);



	}


	public int getAlbumsTotalCount() {
		return albumsTotalCount;
	}


	public void setAlbumsTotalCount(int albumsTotalCount) {
		this.albumsTotalCount = albumsTotalCount;
	}


	@Override
	public void gotoPage(int page, boolean forceClearOnFinish) {
		// TODO Auto-generated method stub
		getAlbums(page,forceClearOnFinish);
	}




	@Override
	public Paginator getPaginator() {
		// TODO Auto-generated method stub
		return paginator;
	}




	@Override
	public void scrollToTop() {
		// TODO Auto-generated method stub

	}

}
