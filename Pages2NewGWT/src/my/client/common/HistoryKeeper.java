package my.client.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import my.client.events.PageChangedEvent;
import my.client.forum.ForumActivity;
import my.client.forum.ForumView;
import my.client.helpers.HavePlace;
import my.client.helpers.HavePresenter;
import my.client.helpers.HaveView;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class HistoryKeeper {

	private Stack <Activity>activityStack =  new Stack<Activity>();
	private Activity CurActivityAfterAnimation;
	private Boolean needToClearStack = false;
	private Boolean needToRemove = false;
	
	private Boolean needToClearAll = false;
	private List <Activity>needToClearAllList;
	//private ClientFactory clientFactory;
	public HistoryKeeper() {
		//this.clientFactory = clientFactory;
	}

	public Activity getCurActivity() {
		return activityStack.peek();
	}

	public int getStackSize() {
		return activityStack.size();
	}

	public Activity checkIsVisited(Place newPlace) {
		//
		//		String newToken = ((ForumView) widget).getPresenter().getName();
		String newToken =	ClientFactory.getHistoryMapper().getToken(newPlace);
		System.out.println("checkIsVisited iterator newToken = " + newToken);
		Log.debug("checkIsVisited iterator newToken = " + newToken);
		Iterator<Activity> it = activityStack.iterator();
		while(it.hasNext()){

			Activity curActivity = (Activity) it.next();
			Place oldPlace = ((HavePlace) curActivity).getPlace();
			String oldToken =	ClientFactory.getHistoryMapper().getToken(oldPlace);

			System.out.println("checkIsVisited iterator oldToken = " + oldToken);
			if (newToken.equals(oldToken)) {
				System.out.println("Sovpadenie!");
				needToClearStack = true;
				CurActivityAfterAnimation = curActivity;
				return curActivity;
			}

		}
		return null;  
	}



	public Activity getPrevious() {

		Log.debug("activityStack.size() = " + activityStack.size());
		Log.debug("activityStack.size() - 1 = " + (activityStack.size() - 1));
		Activity curActivity = activityStack.elementAt(activityStack.size() - 1);
		Place oldPlace = ((HavePlace) curActivity).getPlace();
		String oldToken =	ClientFactory.getHistoryMapper().getToken(oldPlace);
		Log.debug("oldToken " + oldToken);

		Activity prevActivity = activityStack.elementAt(activityStack.size() - 2);
		//activityStack.pop();
		needToRemove = true;
		return prevActivity;
	}


	public void popWidget(Widget widget) {
		//activityStack.pop();
		Iterator<Activity> it = activityStack.iterator();
		//		int historyLengh = activityStack.size();
		//		int i = 0;

		int curActivityPosition;
		int stackSize;
		int numToPop = 0; 

		if (needToClearStack) {
			curActivityPosition = activityStack.indexOf(CurActivityAfterAnimation);
			Log.debug("curActivityPosition " + curActivityPosition); 

			stackSize = activityStack.size();
			Log.debug("stackSize " + stackSize); 

			numToPop = stackSize - curActivityPosition - 1;
			Log.debug("numToPop " + numToPop); 
			needToClearStack = false;
		}

		for (int i=0;i<numToPop;i++) {
			//Widget curWidget = ((HaveView)curActivity).getView().asWidget();

			Activity activityToPop = activityStack.pop();
			Widget widgetToPop = ((HaveView)activityToPop).getView().asWidget();
			widgetToPop.removeFromParent();
		}

		firePlaceChangedEvent();


		/*
		while(it.hasNext()){

	    	Activity curActivity = it.next();
	    	Widget curWidget = ((HaveView)curActivity).getView().asWidget();
	    	if (widget.equals(curWidget)) {
	    		System.out.println("popWidget sovpalo!" + activityStack.indexOf(curActivity));
	    		//it.remove();
	    		return; 
	    	}


	    	//this.activityStack.peek()
	    	//Place oldPlace = ((HavePlace) curActivity).getPlace();
			//String oldToken =	clientFactory.getHistoryMapper().getToken(oldPlace);
	    	//System.out.println("getHistoryWidget!");


	      }*/



		/*
		if (needToRemove == true) {
			activityStack.pop();
			Log.debug("activityStack.pop()");
			widget.removeFromParent();
			needToRemove = false;
		}*/
		/*
		while(it.hasNext()){

	    	Activity curActivity = it.next();
	    	Widget curWidget = ((HaveView)curActivity).getView().asWidget();
	    	if (widget.equals(curWidget)) {
	    		System.out.println("popWidget sovpalo!");
	    		it.remove();
	    		return;
	    	}


	    	//this.activityStack.peek()
	    	//Place oldPlace = ((HavePlace) curActivity).getPlace();
			//String oldToken =	clientFactory.getHistoryMapper().getToken(oldPlace);
	    	//System.out.println("getHistoryWidget!");


	      }

		 */


	}


	public void firePlaceChangedEvent() {
		PageChangedEvent pageChangedEvent = new PageChangedEvent(activityStack.size());
		ClientFactory.getEventBus().fireEvent(pageChangedEvent);
	}

	public void pushNewActivity(Activity newWidget) {

		activityStack.push(newWidget);

		Iterator<Activity> it = activityStack.iterator();
		//		int historyLengh = activityStack.size();
		//		int i = 0;

		while(it.hasNext()){

			Activity curActivity = it.next();
			//Widget curWidget = ((HaveView)curActivity).getView().asWidget();


			Place thePlace = ((HavePlace) curActivity).getPlace();
			String oldToken =	ClientFactory.getHistoryMapper().getToken(thePlace);
			Log.debug("oldToken " + oldToken + " at position " + activityStack.indexOf(curActivity));


		}

		firePlaceChangedEvent();

	}

	public Stack <Widget> getWidgetsToMove() {
		Stack <Widget>widgetsStack =  new Stack<Widget>();
		Iterator<Activity> it = activityStack.iterator();
		while(it.hasNext()){
			Activity curActivity = (Activity) it.next();
			Widget curWidget = ((HaveView)curActivity).getView().asWidget();
			System.out.println("getWidgetsToMove!" + curWidget.getAbsoluteLeft());
			widgetsStack.push(curWidget);
		}

		return widgetsStack;
		//activityStack.push(newWidget);
	}

	public void doEmptyStackClearWidgets() {
		if (needToClearAll) {
			
			for (int i=0; i<needToClearAllList.size();i++) {
				Activity curActivity = needToClearAllList.get(i);
				Widget curWidget = ((HaveView)curActivity).getView().asWidget();
				curWidget.removeFromParent();
			}
			needToClearAll = false;
			needToClearAllList = new ArrayList<Activity>();
		}
	}

	
	public void doEmptyStack() {
		needToClearAll = true;
		needToClearAllList = new ArrayList<Activity>();
	    	    
	    Iterator<Activity> it = activityStack.iterator();
		while(it.hasNext()){
			Activity curActivity = it.next();
			needToClearAllList.add(curActivity);
		}		
		
		activityStack =  new Stack<Activity>();

		//private Boolean needToClearAll = false;
		//private Stack <Activity>needToClearAllStack =  new Stack<Activity>();
		/*
		
		Iterator<Activity> it = activityStack.iterator();
		//int historyLengh = activityStack.size();
		int i = 0;
		//int currShowedWidgetPosition = 0;
		while(it.hasNext()){

			Activity curActivity = it.next();
			i++;
			Widget curWidget = ((HaveView)curActivity).getView().asWidget();
			curWidget.removeFromParent();
		}
		*/

	}

	public Boolean isNeedToRemove (Widget movedWidget, Widget currShowedWidget) {

		Iterator<Activity> it = activityStack.iterator();
		int historyLengh = activityStack.size();
		int i = 0;
		int currShowedWidgetPosition = 0;
		while(it.hasNext()){

			Activity curActivity = it.next();
			i++;
			Widget curWidget = ((HaveView)curActivity).getView().asWidget();
			if (currShowedWidget.equals(curWidget)) {
				//System.out.println("currShowedWidgetPosition =" + i);
				currShowedWidgetPosition = i;
			}
		}

		Iterator<Activity> it2 = activityStack.iterator();
		int ii = 0;
		int movedWidgetPosition = 0;
		while(it2.hasNext()){

			Activity curActivity = it2.next();
			ii++;
			Widget curWidget = ((HaveView)curActivity).getView().asWidget();
			if (movedWidget.equals(curWidget)) {
				movedWidgetPosition = ii;
				System.out.println("movedWidget =" + ii);

			}
		}


		if (movedWidgetPosition > currShowedWidgetPosition) {
			System.out.println("movedWidget need to remove =" + movedWidgetPosition + "currShowedWidgetPosition =" + currShowedWidgetPosition);
			return true;
		} else {
			return false;
		}

	}

	public Boolean isHistoryWidget (Widget widget) {

		Iterator<Activity> it = activityStack.iterator();
		int historyLengh = activityStack.size();
		int i = 0;

		while(it.hasNext()){

			Activity curActivity = it.next();
			i++;
			Widget curWidget = ((HaveView)curActivity).getView().asWidget();
			if (widget.equals(curWidget) && i!=historyLengh) {
				System.out.println("getHistoryWidget sovpalo!");
				return true;
			}
			//Place oldPlace = ((HavePlace) curActivity).getPlace();
			//String oldToken =	clientFactory.getHistoryMapper().getToken(oldPlace);
			System.out.println("getHistoryWidget!");


		}

		return false;
		//return widgets;
	}

	/*
	public void pushToHistory(Widget w) {
		Widget widget = Widget.asWidgetOrNull(w);

		if (widget != null) {


		   	  String newToken = ((ForumView) widget).getPresenter().getName();
		      System.out.println("newToken = " + newToken);

		      widgetsStack.push(widget);

		      int offsetDir = -300;
		      Boolean findedInHistory = false;
		      Iterator it = widgetsStack.iterator();
		      while(it.hasNext() && !findedInHistory){
			    	 // if (!isFirst) {
			    	  Widget curWidget = (Widget) it.next();
				   	  String oldToken = ((HavePresenter) curWidget).getPresenter().getName();
				   	  System.out.println("oldToken = " + oldToken);
				   	  if (newToken.equals(oldToken)) {
				   		  System.out.println("Sovpadenie tokenov");
				   		  //System.out.println("Sovpadenie tokenov");
				    	  System.out.println("widget.getElement().getOffsetLeft() = " + widget.getElement().getOffsetLeft());
				    	  System.out.println("curWidget.getElement().getOffsetLeft() = " + curWidget.getElement().getOffsetLeft());

				    	  //int positionOne = Window.getClientWidth()/2 - 150;
				    	  //offsetDir = positionOne - curWidget.getElement().getOffsetLeft();
				    	  findedInHistory = true;
				   	  }
		      }



		}
	 */



}

