package my.client.windows;

import my.client.common.ClientFactory;
import my.client.events.GrantFBEvent;
import my.client.events.ReloadAlbumsEvent;
import my.client.rpcs.RPCService;
import my.client.rpcs.RPCServiceAsync;
import my.client.rpcs.RPCServiceExeption;
import my.shared.FieldVerifier;
import my.shared.User;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;

public class AdminsPopup extends PopupPanel {

	private FlowPanel panel = new FlowPanel();

	private Button showUnpublishedAlbums = new Button("showUnpublishedAlbums");
	private Button grantFBButt = new Button("grant FB page permission");

	public AdminsPopup() {
		super(true);
		this.addStyleName("loginPopup");
		Log.debug("LoginView created ");
		this.add(panel);

		panel.add(showUnpublishedAlbums);
		panel.add(grantFBButt);



		showUnpublishedAlbums.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				ReloadAlbumsEvent eventReload = new ReloadAlbumsEvent(null,2);
				ClientFactory.getEventBus().fireEvent(eventReload);	
			}
		});
		
		
		grantFBButt.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				GrantFBEvent grantFBEvent = new GrantFBEvent();
				ClientFactory.getEventBus().fireEvent(grantFBEvent);	
			}
		});


		

	
		//initWidget(panel);
	}

}
