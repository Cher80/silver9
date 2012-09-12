package my.client.blocks;

import java.util.Date;

import javax.servlet.http.Cookie;

import my.client.common.ClientFactory;
import my.client.helpers.HavePlace;
import my.client.modelpage.ModelPlace;
import my.client.modelpage.ModelView;
import my.client.rpcs.RPCService;
import my.client.rpcs.RPCServiceAsync;
import my.client.rpcs.RPCServiceExeption;
import my.client.windows.LoginPopup;
import my.client.windows.Notifications;
import my.client.windows.RegisterPopup;
import my.client.windows.UserHasLoggedEvent;
import my.client.windows.UserHasLoggedEventHandler;
import my.shared.AlbumObj;
import my.shared.AlbumsObj;
import my.shared.User;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;

public class AlbumThumb extends Composite  {


	private Button adminSetPublishedButt = new Button("adminSetPublishedButt");
	private FlowPanel panel = new FlowPanel();
	private Button showAlbumButt = new Button("showAlbum");
	private Button adminDelAlbumButt = new Button("adminDelAlbumButt");
	private AlbumObj albumObj;

	public AlbumThumb(AlbumObj albumObjj) {
		super();
		this.albumObj = albumObjj;
		
		Date date = new java.util.Date((long)(albumObj.getTimestamp())*1000);
		
		//Need to check null objects in future
		 HTML html = new HTML(
				 "<br/><br/><b>=====================</b>" +
				"<br/><b>Model age</b><br/>" + albumObj.getAlbage() +
				"<br/><b>Model city</b><br/>" +  albumObj.getAlbcity() + 
 "<br/><b>Model name</b><br/>" + albumObj.getAlbname() +
 "<br/><b>Model page</b><br/>" + albumObj.getAlbpage() +
 "<br/><b>Model status</b><br/>" + albumObj.getStatus() +
 "<br/><b>Model timestamp</b><br/>" + date.toString() 
// "<br/><b>Model cover photo objID</b><br/>" +  albumObj.getCoverImgObjID() +
 // "<br/><b>Model photo amount</b><br/>" +  albumObj.getPhotocount() +
//  "<br/><b>Photo:</b> <br/><img src=\"/extranewgwt/getphoto?photoid=" +  albumObj.getCoverPicID() + "\"/>"
  
  , true);
		
		 panel.add(html);
		 panel.add(showAlbumButt);
		 
		 if (ClientFactory.getCookieObj().getUserRole()==2) {
				panel.add(adminSetPublishedButt);
				panel.add(adminDelAlbumButt);
				
				if (albumObj.getStatus()==2) {
					adminSetPublishedButt.setText("adminSetPublishedButt to " + 1);
				} else {
					adminSetPublishedButt.setText("adminSetPublishedButt to " + 2);
				}
				
			}
		 
		 
		 showAlbumButt.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					String params = "albid=" + albumObj.getAlbid() + "&coverid=" + albumObj.getCoverImgObjID();
					ClientFactory.getPlaceController().goTo(new ModelPlace(params,false, null));
				}
			}); 
		 
		 adminSetPublishedButt.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					if (albumObj.getStatus()==2) {
						doSetPublished(1);
					} else {
						doSetPublished(2);
					}
				}
			}); 
		 
		 adminDelAlbumButt.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					doDeleteAlbum();
				}
			}); 
		 
		 
		 
		initWidget(panel);
	}

	public void doSetPublished(int statusPublished) {
		RPCServiceAsync communicatorSvc = GWT.create(RPCService.class);

		// Set up the callback object.
		AsyncCallback callback = new AsyncCallback() {

			public void onFailure(Throwable caught) { 

				if (caught instanceof RPCServiceExeption) {
				}
			}

			@Override
			public void onSuccess(Object result) {
				Log.debug("UserArea onSuccess ");
				Notifications notif = new Notifications("Status has changed", true, true);
			}
		};

		communicatorSvc.doAlbumStatus(albumObj, statusPublished, callback);

	}
	
	
	
	public void doDeleteAlbum() {
		RPCServiceAsync communicatorSvc = GWT.create(RPCService.class);

		// Set up the callback object.
		AsyncCallback callback = new AsyncCallback() {

			public void onFailure(Throwable caught) { 

				if (caught instanceof RPCServiceExeption) {
				}
			}

			@Override
			public void onSuccess(Object result) {
				Log.debug("UserArea onSuccess ");
				Notifications notif = new Notifications("Album deleted", true, true);
				doClear();
			}
		};

		communicatorSvc.doDeAlbum(albumObj, callback);

	}
	
	
	void doClear() {
		this.removeFromParent();
	}
	


}
