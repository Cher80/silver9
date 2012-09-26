package my.client.blocks;



import java.util.Date;

import javax.servlet.http.Cookie;

import my.client.common.ClientFactory;
import my.client.common.IconButt;
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
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;

public class AlbumThumb extends Composite  {


	private Button adminSetPublishedButt = new Button("adminSetPublishedButt");
	private FlowPanel panel = new FlowPanel();
	private FocusPanel albumImage = new FocusPanel();
	private FlowPanel albumNameYO = new FlowPanel();
	private Label albumName = new Label();
	private Label albumYO = new Label();
	//private Button showAlbumButt = new Button("showAlbum");
	//private Button adminDelAlbumButt = new Button("adminDelAlbumButt");
	private AlbumObj albumObj;
	
	private LikesStatBlock likesStatBlock;

	
	private Label albumPageLabel = new Label();
	
	private FocusPanel layerHover = new FocusPanel();
	private FlowPanel hoverUnder = new FlowPanel();
	private FlowPanel hoverIco = new FlowPanel();
	private Label hoverPhotoCount= new Label();
	private Label hoverDateAdded = new Label();
	
	private IconButt delAdmin = new IconButt();
	private IconButt statusAdmin = new IconButt();
	
	public AlbumThumb(AlbumObj albumObjj) {
		super();
		this.albumObj = albumObjj;
		
		panel.addStyleName("AlbumThumb");
		
		
		////////Page  ///////
		
		albumPageLabel.addStyleName("albumPageLabel");
		albumPageLabel.addStyleName("text_11_grey");
		albumPageLabel.setText("" + albumObj.getAlbpage());
		panel.add(albumPageLabel);
		
		
		
		
		
		
	
		
		/////Model name and YO//////
		albumNameYO.addStyleName("albumNameYO");
		albumNameYO.addStyleName("grey_Plate");
		albumName.addStyleName("albumName");
		albumName.addStyleName("blackNameHeader");
		albumYO.addStyleName("albumYO");
		albumYO.addStyleName("blackNameHeader");
		
		albumName.setText(albumObj.getAlbname());
		albumYO.setText("Age " + albumObj.getAlbage() + "");
		
		albumNameYO.add(albumYO);
		albumNameYO.add(albumName);
		panel.add(albumNameYO);
		
		
		//////Rating////
		
		likesStatBlock = new LikesStatBlock(albumObj,false);
		likesStatBlock.addStyleName("likesStatBlockAlbum");
		panel.add(likesStatBlock);
		
		///////Album Image/////////
		
		//background: #00ff00 url('smiley.gif') no-repeat center;
		albumImage.addStyleName("albumImage");
		albumImage.getElement().getStyle().setProperty("background",  "url(\"/extranewgwt/getphoto?photoid=" + albumObj.getCoverPicID() +  "\") no-repeat center");
		panel.add(albumImage);
		//Date date = new java.util.Date((long)(albumObj.getTimestamp())*1000);
		
		albumImage.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				gotoAlbPage();
			}
		}); 
		
		
		////////Hover layer //////

		layerHover.addStyleName("layerHover");
		hoverUnder.addStyleName("hoverUnder");
		hoverIco.addStyleName("hoverIco");
		hoverPhotoCount.addStyleName("hoverPhotoCount");
		hoverPhotoCount.addStyleName("text12_white_bold");
		hoverPhotoCount.setText(albumObj.getPhotocount() + " photos");
		
		hoverDateAdded.addStyleName("hoverDateAdded");
		hoverDateAdded.addStyleName("text9_white");
		System.out.println(" albumObj.getTimestamp() " + String.valueOf(albumObj.getTimestamp()));
		Date dateAdded = new Date ();
		dateAdded.setTime(albumObj.getTimestamp()*1000);
		 DateTimeFormat fmt = DateTimeFormat.getFormat("dd/MM/yy");
		    // prints Monday, December 17, 2007 in the default locale
		    String dateAddedString = (fmt.format(dateAdded));
		
		//Format formatter = new SimpleDateFormat("dd/MM/yy");
		 //String s = formatter.format(dateAddedString);
		  System.out.println(dateAddedString);

		hoverDateAdded.setText("added " + dateAddedString  + "");
		
		panel.add(layerHover);
		layerHover.add(hoverUnder);
		hoverUnder.add(hoverIco);
		hoverUnder.add(hoverPhotoCount);
		hoverUnder.add(hoverDateAdded);
		
		layerHover.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				gotoAlbPage();
			}
		}); 
		
		
		////////ADMIN BUTT/////////////
		
		String statusDigit = "";
		 
		 if (ClientFactory.getUser().getUserRole()==2) {
				panel.add(statusAdmin);
				panel.add(delAdmin);
				
				if (albumObj.getStatus()==2) {
					statusDigit = "published";
					//adminSetPublishedButt.setText("adminSetPublishedButt to " + 1);
				} else {
					statusDigit = "unpublished";
					//adminSetPublishedButt.setText("adminSetPublishedButt to " + 2);
				}
				
			}
		
		//private IconButt delAdmin = new IconButt();
		//private IconButt statusAdmin = new IconButt();
		
		 //statusAdmin = new IconButt(); 
		 statusAdmin.addStyleName("statusAdmin");
		 statusAdmin.icon.addStyleName("AdminButtonIcon");
		 statusAdmin.content.addStyleName("statusAdminContent");
		 statusAdmin.text.addStyleName("AdminButtonText");
		 statusAdmin.text.addStyleName("text11_White");
		 statusAdmin.setText("Set " + statusDigit);
		
		 //panel.add(statusAdmin);
		 
		 statusAdmin.panel.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					if (albumObj.getStatus()==2) {
						doSetPublished(1);
					} else {
						doSetPublished(2);
					}
				}
			}); 
		 
		 
		 
		 
		 
		 //delAdmin = new IconButt(); 
		 delAdmin.addStyleName("delAdmin");
		 delAdmin.icon.addStyleName("AdminButtonIcon");
		 delAdmin.content.addStyleName("delAdminContent");
		 delAdmin.text.addStyleName("AdminButtonText");
		 delAdmin.text.addStyleName("text11_White");
		 delAdmin.setText("Delete");
		
		 //panel.add(delAdmin);
		 
		 delAdmin.panel.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					doDeleteAlbum();
				}
			}); 
		 
		 
		//Need to check null objects in future

		/*
 HTML html = new HTML(
				 "<br/><br/><b>=====================</b>" +
				"<br/><b>Model age</b><br/>" + albumObj.getAlbage() +
				"<br/><b>Model city</b><br/>" +  albumObj.getAlbcity() + 
 "<br/><b>Model name</b><br/>" + albumObj.getAlbname() +
 "<br/><b>Model page</b><br/>" + albumObj.getAlbpage() +
 "<br/><b>Model status</b><br/>" + albumObj.getStatus() +
 "<br/><b>Model timestamp</b><br/>" + date.toString() +
 "<br/><b>Model cover photo objID</b><br/>" +  albumObj.getCoverImgObjID() +
  "<br/><b>Model photo amount</b><br/>" +  albumObj.getPhotocount() +
  "<br/><b>Photo:</b> <br/><img src=\"/extranewgwt/getphoto?photoid=" +  albumObj.getCoverPicID() + "\"/>"
  
  , true);*/
		
		// panel.add(html);
	
		 
		 /*
		 showAlbumButt.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					String params = "albid=" + albumObj.getAlbid() + "&coverid=" + albumObj.getCoverImgObjID();
					ClientFactory.getPlaceController().goTo(new ModelPlace(params,false, null));
				}
			}); */
		 
		
		 
		
		 
		 
		 
		initWidget(panel);
	}
	
	
	public void gotoAlbPage() {
		String params = "albid=" + albumObj.getAlbid() + "&coverid=" + albumObj.getCoverImgObjID();
		ClientFactory.getPlaceController().goTo(new ModelPlace(params,false, null));

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
				
				//if (albumObj.getStatus())
				if (albumObj.getStatus()==2) {
					albumObj.setStatus(1);
					statusAdmin.setText("Set unpublished");
				} else {
					albumObj.setStatus(2);
					statusAdmin.setText("Set published");
				}
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
