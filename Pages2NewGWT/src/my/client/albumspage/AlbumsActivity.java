package my.client.albumspage;

import java.util.ArrayList;

import my.client.common.ActivityHasPages;
import my.client.common.ClientFactory;
import my.client.common.GoogleAnalytics;
import my.client.common.MyActivity;
import my.client.common.ViewHasPages;
import my.client.events.NewCommentEvent;
import my.client.events.ReloadAlbumsEvent;
import my.client.events.ReloadAlbumsEventHandler;
import my.client.forum.ForumPlace;
import my.client.paginator.Paginator;
import my.client.rpcs.RPCService;
import my.client.rpcs.RPCServiceAsync;
import my.client.rpcs.RPCServiceExeption;
import my.client.windows.Notifications;
import my.shared.AlbumObj;
import my.shared.AlbumsObj;
import my.shared.AlbumsPageObj;
import my.shared.Settings;
import my.shared.User;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;

public class AlbumsActivity extends MyActivity implements ActivityHasPages, ReloadAlbumsEventHandler{

	//private int albumsTotalCount = 0;
	//private int currentPage = 0;
	private Paginator paginator = null;
	//private boolean isFirstTimeLoaded=true;
	private String tagType = null;
	private int statusPublished = 1;
	private AlbumsPageObj albumsPageObj;

	public AlbumsActivity(AlbumsPlace place) {
		super();
		super.setPlace(place);
		//this.currentPage = 0;

	}




	public void getAlbums(int page) {
		RPCServiceAsync communicatorSvc = GWT.create(RPCService.class);

		// Set up the callback object.
		AsyncCallback callback = new AsyncCallback() {

			public void onFailure(Throwable caught) { 

				if (caught instanceof RPCServiceExeption) {
					Log.debug("exeption!!" + ((RPCServiceExeption)caught).getErrorCode());
					if (paginator.isForceClearOnFinish()) {
						//clearViewAlbums();
						//isFirstTimeLoaded=true;
						
						
						((ViewHasPages)AlbumsActivity.this.getView()).clearWidget(0);
						paginator.removeFromParent();
						Notifications notif = new Notifications(((RPCServiceExeption)caught).getErrorCode(), true, true);
						
					}
				}
			}

			@Override
			public void onSuccess(Object result) {
				Log.debug("UserArea onSuccess ");
				AlbumsObj albumsObj = (AlbumsObj)result;
				populateAlbumsView(albumsObj);
			}
		};

		Log.debug("Make the call");
		int offest = Settings.ALBUMS_PER_PAGE*page;
		int limit =  Settings.ALBUMS_PER_PAGE;

		communicatorSvc.getAlbumsByTime( offest, limit, tagType, statusPublished, callback);
	}


	public void populateAlbumsView(AlbumsObj albumsObj) {
		AlbumsView albumsView = (AlbumsView)this.getView();
		//Log.debug("isFirstTimeLoaded=" + isFirstTimeLoaded);
		
		/*
		if (paginator!=null && paginator.isFirstTimeLoaded()) {
			//isFirstTimeLoaded=true;
			paginator.removeFromParent();
			paginator=null;
		}*/
		
		if (paginator==null) {
			//Log.debug("do new paginator");
			paginator = new Paginator(albumsObj.getTotalCount(),this, (ViewHasPages)this.getView());
			albumsView.setPaginator(paginator);
			//paginator.setCurrentPage(0);		
			//paginator.setFirstTimeLoaded(false);
			//isFirstTimeLoaded=false;
		}
		
		


		//this.albumsTotalCount = albumsObj.getTotalCount();
		
		
			
		
		paginator.onSuccessLoad();

		albumsView.populateAlbumsView(albumsObj);

		

		/*
		for (int i=0; i<albumObjs.size(); i++) {
			AlbumObj albumObj = albumObjs.get(i);
			Log.debug("albumObj.getAlbname()" + albumObj.getAlbname());
			Log.debug("albumObj.getAlbpage()" + albumObj.getAlbpage());
			AlbumsView albumsView = (AlbumsView)this.getView();
			albumsView.addAlbumThumb(albumObj);
		}*/
	}

	/*
	public void clearViewAlbums() {
		AlbumsView albumsView = (AlbumsView)this.getView();
		albumsView.clearWidget(0);
		albumsView.scrollToTop();
	}*/

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {

		ClientFactory.getEventBus().addHandler(ReloadAlbumsEvent.TYPE, this);

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
		//getAlbums(0,false);
		doInitPageLoad();



	}

	public void doInitPageLoad() {
		RPCServiceAsync communicatorSvc = GWT.create(RPCService.class);

		AsyncCallback callback = new AsyncCallback() {

			public void onFailure(Throwable caught) { 

				if (caught instanceof RPCServiceExeption) {
					Log.debug("exeption!!" + ((RPCServiceExeption)caught).getErrorCode());
					Notifications notif = new Notifications(((RPCServiceExeption)caught).getErrorCode(), true, true);
				}
			}

			@Override
			public void onSuccess(Object result) {
				Log.debug("UserArea onSuccess ");
				albumsPageObj = (AlbumsPageObj)result;
				/*
				for (int i=0; i<albumsObj.getAlbums().size(); i++) {
					AlbumObj albumObj = albumsObj.getAlbums().get(i);
					//Log.debug("albumObj.getAlbname()" + albumObj.getAlbname());
					//Log.debug("albumObj.getAlbpage()" + albumObj.getAlbpage());
				}*/

				renderBlocks(albumsPageObj);

				populateAlbumsView(albumsPageObj.getAlbumsObj());
				
			
			}
		};

		Log.debug("Make the call");

		communicatorSvc.getAlbumsPageObj(callback);

	}

	public void renderBlocks(AlbumsPageObj albumsPageObj) {
		 ((AlbumsView)getView()).renderBlocks(albumsPageObj);
		// ((AlbumsView)getView()).renderBlocks(albumsPageObj);
		 
	}

	/*
	public int getAlbumsTotalCount() {
		return albumsTotalCount;
	}


	public void setAlbumsTotalCount(int albumsTotalCount) {
		this.albumsTotalCount = albumsTotalCount;
	}*/


	@Override
	public void gotoPage(int page) {
		// TODO Auto-generated method stub
		getAlbums(page);
	}




	@Override
	public Paginator getPaginator() {
		// TODO Auto-generated method stub
		return paginator;
	}







	@Override
	public void onDoReload(ReloadAlbumsEvent event) {
		// TODO Auto-generated method stub
		Log.debug("event.getTagType()  " + event.getTagType());
		this.tagType = event.getTagType(); 
		this.statusPublished =  event.getStatus();
		//isFirstTimeLoaded = true;
		//paginator.setFirstTimeLoaded(true);
		
		paginator.removeFromParent();
		paginator=null;
		
		getAlbums(0);

	}

}
