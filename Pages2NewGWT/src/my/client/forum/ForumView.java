package my.client.forum;

import my.client.helpers.HaveClientFactory;
import my.client.helpers.HavePresenter;

import my.client.theme.ThemeActivity;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollListener;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class ForumView extends Composite implements ForumViewInterface, HavePresenter, ScrollHandler {
	
	private ScrollPanel panel = new ScrollPanel();
	private Presenter presenter;
    private String name;
    private Button myButt1 = new Button("ForumView");
    private FlowPanel commentsPanel = new FlowPanel();
    
    public ForumView() {
    	//System.out.println("ForumView constructor");
		//panel.getElement().getStyle().setProperty("cssFloat", "left");
		//panel.getElement().getStyle().setProperty("border", "1px solid red");
		//panel.add(myButt1);
		
    	//panel.getElement().getStyle().setProperty("border", "3px solid red");
    	
    	
    	
    	//ScrollPanel panel = new ScrollPanel();
    	//Window browserWindow = new Window();
    	
    	//int positionOne = Window.getClientWidth()/2 - 150;
    	//panel.getElement().getStyle().setProperty("position", "absolute");
    	panel.setSize("300px", "300px");
    	panel.getElement().getStyle().setProperty("border", "1px solid green");
    	panel.getElement().getStyle().setProperty("cssFloat", "left");
    	panel.getElement().getStyle().setProperty("left", "0px");
    	
    	
    	
    	for (int i=0;i<30;i++) {
    		
    		//ThemeActivity myThemeActivity = new ThemeActivity ();
    		//myThemeActivity.start(panel, (new ClientFactoryImpl()).getEventBus());
    		/*
    		SimplePanel commentPanel = new SimplePanel();
    		//commentPanel.setSize("200px", "120px");
    		//commentPanel.addStyleName("demo-panel");

    		Label label = new Label("Label");
    		//label.setWidth("100px");
    		//label.setStyleName("demo-label");
    		commentPanel.add(label);
    		commentsPanel.add(commentPanel);
    		//panel.*/
    	}
    	
    	panel.add(commentsPanel);
		//myEventBus.addHandler(ComposedEvent.TYPE, new MyCompositeEventHandler2());
		//SimpleEventBusSingleton.getInstance().addHandler(ComposedEvent.TYPE, this);
		
		myButt1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//MyButt1.setText("FromPCCliknul!");
				//myButt1.setText("Cliknul!");
				//myButt1.setText("OereCliknul!");
				//myButt1.setText("VfrOereCliknul!");
				//myButt1.setText("BiBiVfrOereCliknul!");
				//myButt1.setText("MacBiBiVfrOereCliknul!");
				myButt1.setText("ForumViewCliknul!");
				//presenter.goToCompos1New();
				//ComposedEvent myEvent = new ComposedEvent(654);
				//myEvent.dispatch(new MyCompositeEventHandler());
			}
		});
		
		//HandlerRegistration addScrollHandler = ScrollPanel.addScrollHandler(null);
		this.panel.addScrollHandler(this);
		
		initWidget(panel);
	}
    
    
    public void populate() {
    	for (int i=0;i<30;i++) {
    		int forumId = this.presenter.getForumId();
    		
    		ThemeActivity myThemeActivity = new ThemeActivity (commentsPanel, ((HaveClientFactory)presenter).getClientFactory(),forumId + i);
    		//myThemeActivity.setForumId(forumId + i);
    		//populate ()
    		//myThemeActivity.start(panel, presenter.getClientFactory().getEventBus());
    		/*
    		SimplePanel commentPanel = new SimplePanel();
    		//commentPanel.setSize("200px", "120px");
    		//commentPanel.addStyleName("demo-panel");

    		Label label = new Label("Label");
    		//label.setWidth("100px");
    		//label.setStyleName("demo-label");
    		commentPanel.add(label);
    		commentsPanel.add(commentPanel);
    		//panel.*/
    	}
    }
    
 

    
    @Override
	public void setPresenter(Presenter presenter) {
		// TODO Auto-generated method stub
		this.presenter = presenter;
		//this.forumNumber
	}

	@Override
	public void setName(String composName) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Activity getPresenter() {
		// TODO Auto-generated method stub
		return (Activity) this.presenter;
	}


	@Override
	public void onScroll(ScrollEvent event) {
		// TODO Auto-generated method stub
		////System.out.println("scroll" + panel.getMaximumHorizontalScrollPosition() + panel.getHorizontalScrollPosition());
		//System.out.println(panel.getVerticalScrollPosition() + " xxxx " + panel.getMaximumVerticalScrollPosition());
		int maxScroll = panel.getMaximumVerticalScrollPosition();
		int curScrol = panel.getVerticalScrollPosition();
		if ((maxScroll - curScrol) <150 ) {
			this.presenter.loadMore();
		}
	}




}
