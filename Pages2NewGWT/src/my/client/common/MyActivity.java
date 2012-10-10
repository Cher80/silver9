package my.client.common;

import java.util.HashMap;

import my.client.helpers.HavePlace;
import my.client.helpers.HaveView;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;

public class MyActivity extends AbstractActivity implements HavePlace, HaveView {

	private Composite activityView;
	private Place activityPlace;
	
	public MyActivity() {
		super();
	}
	
	protected String getParams(String paramsLine, String vartoget) {
		String value = null;
		String delimiter = "&";
		/* given string will be split by the argument delimiter provided. */
		String[] paramsArray;
		
		paramsArray = paramsLine.split(delimiter);
		for (int i=0;i<paramsArray.length; i++) {
			Log.debug("paramsArray = " + i + " " + paramsArray[i] );
			
			String delimiter2 = "=";
			String[] paramsArray2;
			paramsArray2 = paramsArray[i].split(delimiter2);
			if (paramsArray2[0].trim().equals(vartoget)) {
				Log.debug("We got it = " + paramsArray2[0] + " " + paramsArray2[1] );
				 value = paramsArray2[1];
				 return value;
			}
		}
		return null;
	}
	
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		// TODO Auto-generated method stub
		//System.out.println("MyActivity extends AbstractActivity start");
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
