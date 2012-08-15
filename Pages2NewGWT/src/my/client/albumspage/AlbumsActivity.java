package my.client.albumspage;

import my.client.common.ClientFactory;
import my.client.common.MyActivity;
import my.client.forum.ForumPlace;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;

public class AlbumsActivity extends MyActivity{
	
	public AlbumsActivity(AlbumsPlace place) {
		super();
		super.setPlace(place);
		
	}
	
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		AlbumsView albumsView = new AlbumsView(this);
		
		this.setView(albumsView);
		panel.setWidget(albumsView.asWidget());
		
	}

}
