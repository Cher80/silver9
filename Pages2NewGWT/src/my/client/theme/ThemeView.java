package my.client.theme;

import my.client.common.ClientFactory;
import my.client.windows.Notifications;
import my.client.windows.UserHasLoggedEvent;
import my.shared.User;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class ThemeView extends Composite implements ThemeViewInterface {

	private Presenter presenter;
    private String themeName;
    private FlowPanel flowPanel = new FlowPanel();

    
    public ThemeView() {
   }
    
    public void populate () {
    	////System.out.println("ThemeView constructor");
    	int forumId = presenter.getForumId();
    	//System.out.print("" + forumId);
    	Label label = new Label("ThemeViewLabel" + forumId);
    	Button myButt1 = new Button("ThemeViewButt" + forumId);
    	Button myButt2 = new Button("RPC" + forumId);
    	
		//label.setWidth("100px");
		//label.setStyleName("demo-label");
    	flowPanel.add(label);
    	flowPanel.add(myButt1);
    	flowPanel.add(myButt2);
		//commentsPanel.add(commentPanel);
		
    	
    	myButt1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				presenter.gotoForum();
				//ComposedEvent myEvent = new ComposedEvent(654);
				//myEvent.dispatch(new MyCompositeEventHandler());
			}
		});

    	myButt2.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//presenter.makeRPC();
				User user = new User();
				user.setEmail("email");
				user.setNick("nick");
				UserHasLoggedEvent eventToFire = new UserHasLoggedEvent(user);
				ClientFactory.getEventBus().fireEvent(eventToFire);
				Log.debug("UserHasLoggedEvent fired");
				Notifications notif = new Notifications("You have registered", true, true);
				//notif.show(3000);
				//ComposedEvent myEvent = new ComposedEvent(654);
				//myEvent.dispatch(new MyCompositeEventHandler());
			}
		});
    	
    	initWidget(flowPanel);
 
    }
    
    
	@Override
	public void setThemeName(String themeName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPresenter(Presenter presenter) {
		// TODO Auto-generated method stub
		this.presenter = presenter;
	}


	



}
