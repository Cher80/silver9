package my.client.common;

import java.util.Stack;


import my.client.albumspage.AlbumsActivity;
import my.client.albumspage.AlbumsPlace;
import my.client.forum.ForumActivity;
import my.client.forum.ForumPlace;
import my.client.helpers.HavePlace;
import my.client.modelpage.ModelActivity;
import my.client.modelpage.ModelPlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.Widget;

public class AppActivityMapper implements ActivityMapper {

	private ClientFactory clientFactory;

	public AppActivityMapper(ClientFactory clientFactory) {
        super();
        
        this.clientFactory = clientFactory;
    }

	@Override
	public Activity getActivity(Place place) {
		// TODO Auto-generated method stub
		if (place instanceof ForumPlace)
			return new ForumActivity((ForumPlace) place, clientFactory);
		else if (place instanceof AlbumsPlace)
            return new AlbumsActivity((AlbumsPlace) place);
		else if (place instanceof ModelPlace)
            return new ModelActivity((ModelPlace) place);
		
        return null;
		
	}

}
