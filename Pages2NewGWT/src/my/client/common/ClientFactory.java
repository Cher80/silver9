package my.client.common;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.SimpleEventBus;

import my.client.compos.MyCompositeView;
import my.client.compos2.MyComposite2View;

public class ClientFactory {
	
	 //private final SimpleEventBusSingleton eventBus = new SimpleEventBusSingleton();
	 private static SimpleEventBus eventBus = new SimpleEventBus();
	 private static PlaceController placeController = new PlaceController(eventBus);
	 private static MyCompositeView myCompositeView = new MyCompositeView();
	 private static MyComposite2View myComposite2View = new MyComposite2View();
	 private static HistoryKeeper myHistoryKeeper = new HistoryKeeper();
	 private static AppPlaceHistoryMapper historyMapper= GWT.create(AppPlaceHistoryMapper.class);

	 //private final GoodbyeView goodbyeView = new GoodbyeViewImpl();


	public static SimpleEventBus getEventBus() {
		// TODO Auto-generated method stub
		return eventBus;
	}

	public static MyCompositeView getMyCompositeView() {
		// TODO Auto-generated method stub
		return myCompositeView;
	}
	
	public static MyComposite2View getMyComposite2View() {
		// TODO Auto-generated method stub
		return myComposite2View;
	}

	
	public static PlaceController getPlaceController() {
		return placeController;
	}

	public static HistoryKeeper getHistoryKeeper() {
		// TODO Auto-generated method stub
		return myHistoryKeeper;
	}

	public static AppPlaceHistoryMapper getHistoryMapper() {
		return historyMapper;
	}
}
