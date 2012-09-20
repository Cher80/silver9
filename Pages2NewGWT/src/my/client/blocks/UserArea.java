package my.client.blocks;

import javax.servlet.http.Cookie;

import my.client.blocks.design.AdminButt;
import my.client.common.ClientFactory;
import my.client.common.IconButt;
import my.client.helpers.HavePlace;
import my.client.rpcs.RPCService;
import my.client.rpcs.RPCServiceAsync;
import my.client.rpcs.RPCServiceExeption;
import my.client.windows.LoginPopup;
import my.client.windows.Notifications;
import my.client.windows.RegisterPopup;
import my.client.windows.UserHasLoggedEvent;
import my.client.windows.UserHasLoggedEventHandler;
import my.shared.CookieObj;
import my.shared.User;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class UserArea extends Composite implements UserHasLoggedEventHandler {

	public static final int UNLOGGED = 1;
	public static final int LOGGED = 2;
	  private AdminButt adminsStuffBlock;// = new AdminButt();
	private FlowPanel panel = new FlowPanel();
	private IconButt regButt;
	private IconButt fbLogg;
	private IconButt loginButt;
	private IconButt logout;
	private Label nickHolder;
	private User user;
	//private CookieObj cookieObj;

	public UserArea(User userr) {
		super();
		this.user = userr;
		if (user==null) {
			this.setAnonim();
		}
		else {
			setUser(user);
		}
		//getUserFromCookie();
		ClientFactory.getEventBus().addHandler(UserHasLoggedEvent.TYPE, this);
		panel.addStyleName("UserArea");
		//nick = new Label("Anonymous");




		//HandlerRegistration addScrollHandler = ScrollPanel.addScrollHandler(null);

		initWidget(panel);
	}


	void clearWidget() {
		int wcount = panel.getWidgetCount();
		for (int i=0; i<wcount; i++) {
			Log.debug("User Area panel.getWidgetCount() " + panel.getWidgetCount());
			Log.debug("User Area remove widget " + i);
			//panel.remove(i);
			panel.getWidget(i).setVisible(false);
			//panel.getWidget(i).r
		}
	}


	void checkCoockie() {

		Timer t = new Timer() {
			@Override
			public void run() {


				String cookie = Cookies.getCookie("silver9session");
				Log.debug("UserArea FB cookie = " + cookie);
				if (cookie!=null) {
					Log.debug("UserArea FB cookie DONE = " + cookie);  
					this.cancel();
					CookieObj cookieObj = new CookieObj();
					cookieObj.generateFromString(cookie);

					ClientFactory.setCookieObj(cookieObj);
					getUserFromCookie();
				}
				else {
					Log.debug("UserArea FB cookie NO = " + cookie);  
				}


			}
		};

		//t.schedule(1000);
		t.scheduleRepeating(500);
	}

	void setState(int STATE) {

		if(STATE == UNLOGGED) {
			clearWidget();
			//fbLogg
			
			fbLogg = new IconButt(); 
			fbLogg.icon.addStyleName("FBButtonIcon");
			fbLogg.content.addStyleName("FBButtonContent");
			fbLogg.text.addStyleName("FBButtonText");
			fbLogg.text.addStyleName("text11_White");
			fbLogg.setText("Facebook login");
			
			
			regButt = new IconButt(); 
			regButt.icon.addStyleName("RegButtonIcon");
			regButt.content.addStyleName("RegButtonContent");
			regButt.text.addStyleName("RegButtonText");
			regButt.text.addStyleName("text11_White");
			regButt.setText("Register");
			
			
			loginButt = new IconButt(); 
			loginButt.icon.addStyleName("LoginButtonIcon");
			loginButt.content.addStyleName("LoginButtonContent");
			loginButt.text.addStyleName("LoginButtonText");
			loginButt.text.addStyleName("text11_White");
			loginButt.setText("Login");
			
			
			//fbLogg = new Button("fbLogg");
			//regButt = new Button("Register");
			//loginButt = new Button("Login");
			//logout = new Button("Logout");
			nickHolder = new Label("Anonymous?");
			nickHolder.addStyleName("nickHolder");
			nickHolder.addStyleName("text11_White");
			
			fbLogg.panel.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					String fbURL = "http://www.facebook.com/dialog/oauth/?" +
							"client_id=217710151589139" +
							"&redirect_uri=http://test.ru:8888/extranewgwt/fbcreateUser" +
							"&state=allo" +
							"&scope=user_about_me,email"
							;
					Window.open(fbURL, "_blank", null);

					checkCoockie();

					// Schedule the timer to close the popup in 3 seconds.





				}
			});

			loginButt.panel.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					//regButt.setText("doLogin");
					LoginPopup loginView = new LoginPopup();
					loginView.center(); 
					loginView.show();
				}
			});

			regButt.panel.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					//regButt.setText("doRegister");
					RegisterPopup registerView = new RegisterPopup();
					registerView.center(); 
					registerView.show();
				}
			});
			panel.add(nickHolder);
			panel.add(fbLogg);
			panel.add(regButt);
			panel.add(loginButt);
			
			

		}

		if(STATE == LOGGED) {
			clearWidget();
			
			nickHolder = new Label("Anonymous");
			nickHolder.addStyleName("nickHolder");
			nickHolder.addStyleName("text11_White");
			
			
			
			
			//regButt = new Button("Register");
			//loginButt = new Button("Login");
			logout = new IconButt(); 
			logout.icon.addStyleName("LogoutButtonIcon");
			logout.content.addStyleName("LogoutButtonContent");
			logout.text.addStyleName("LogoutButtonText");
			logout.text.addStyleName("text11_White");
			logout.setText("Logout");
			
				

			logout.panel.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					//regButt.setText("doLogin");
					//LoginPopup loginView = new LoginPopup();
					//loginView.center(); 
					//loginView.show();
					setAnonim();
				}
			});
			
			panel.add(nickHolder);
			panel.add(logout);
			
			
			adminsStuffBlock = new AdminButt();
			if (ClientFactory.getUser().getUserRole()==2) {
				panel.add(adminsStuffBlock);
			}

		}
	}


	public void setAnonim() {
		this.user = new User();
		
		ClientFactory.setUser(user);
		setState(UNLOGGED);
		nickHolder.setText("Anonymous?");
		Cookies.removeCookie("silver9session", "/");
		//Cookie cookie=new Cookie("silver9session", email + "###" + md5session);
		//cookie.setPath("/");
	}

	public void setUser(User user) {
		this.user = user;
		ClientFactory.setUser(user);
		setState(LOGGED);	
		nickHolder.setText(user.getNick());
	}

	@Override
	public void onUserLogged(UserHasLoggedEvent event) {
		Log.debug("UserArea onUserLogged = " + event.getUser());
		setUser(event.getUser());
	}


	void getUserFromCookie() {

		CookieObj cookieObj = ClientFactory.getCookieObj();
		//String cookie = Cookies.getCookie("silver9session");
		//Log.debug("UserArea cookie = " + cookie);
		if (cookieObj!=null) {
			//CookieObj cookieObj = new CookieObj();
			//cookieObj.generateFromString(cookie);
			RPCServiceAsync communicatorSvc = GWT.create(RPCService.class);

			// Set up the callback object.
			AsyncCallback callback = new AsyncCallback() {

				public void onFailure(Throwable caught) {

					if (caught instanceof RPCServiceExeption) {
						Log.debug("exeption!!" + ((RPCServiceExeption)caught).getErrorCode());
						setState(UNLOGGED);
						setAnonim();
						//status.setText(((RPCServiceExeption)caught).getErrorCode());
					}

				}

				@Override
				public void onSuccess(Object result) {
					Log.debug("UserArea onSuccess ");

					setUser((User)result);
					
					Log.debug("UserArea result.getClass() = " + result.getClass());
					// TODO Auto-generated method stub
					/*
				Log.debug("onSuccess");
				status.setText("well done");
				UserHasLoggedEvent event = new UserHasLoggedEvent((User)result);
				ClientFactory.getEventBus().fireEvent(event);
				Log.debug("UserHasLoggedEvent fired");
				Notifications notif = new Notifications("You have logged", true, true);
				//ClientFactory.getEventBus().
				LoginPopup.this.hide();
					 */
				}
			};

			//	Make the call
			Log.debug("Make the call");
			communicatorSvc.getUserByCookie(cookieObj, callback);
		}
		else {
			Log.debug("No cookie");
			setAnonim();
			//setState(UNLOGGED);
		}
	}

}
