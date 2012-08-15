package my.client.common;

import my.client.helpers.HavePlace;
import my.client.helpers.HaveView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;

public class MyActivity extends AbstractActivity implements HavePlace, HaveView {

	private Composite activityView;
	private Place activityPlace;
	
	public MyActivity() {
		super();
	}
	
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		// TODO Auto-generated method stub
		System.out.println("MyActivity extends AbstractActivity start");
	}

	

	
	@Override
	public Composite getView() {
		// TODO Auto-generated method stub
		return activityView;
	}

	@Override
	public Place getPlace() {
		// TODO Auto-generated method stub
		return activityPlace;
	}


	@Override
	public void setView(Composite view) {
		this.activityView = view;
		
	}


	@Override
	public void setPlace(Place place) {
		// TODO Auto-generated method stub
		this.activityPlace = place;
		
	}

}
