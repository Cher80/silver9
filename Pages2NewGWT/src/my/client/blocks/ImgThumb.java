package my.client.blocks;

import java.util.Date;

import my.client.common.ClientFactory;
import my.client.common.IconButt;
import my.client.events.ModelGoPicEvent;
import my.client.modelpage.ModelActivity;
import my.client.modelpage.ModelPlace;
import my.client.rpcs.RPCService;
import my.client.rpcs.RPCServiceAsync;
import my.client.rpcs.RPCServiceExeption;
import my.client.windows.Notifications;
import my.shared.AlbumObj;
import my.shared.ImgObj;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;

public class ImgThumb extends Composite {


	private Button adminSetPublishedButt = new Button("adminSetPublishedButt");
	private Button adminSetCoverButt = new Button("adminSetCoverButt");

	private FlowPanel panel = new FlowPanel();
	private Button show1500pxButt = new Button("show1500px");
	private Button showOrigpxButt = new Button("showOrigpx");
	private ImgObj imgObj;
	private AlbumObj albumObj;
	private ModelActivity modelActivity;
	
	private IconButt statusAdmin =new IconButt();
	private IconButt coverAdmin =new IconButt();
	private IconButt delAdmin =new IconButt();
	
	 private FocusPanel albumImage = new FocusPanel();
	    private FlowPanel albumImageUnder = new FlowPanel();

	public ImgThumb(AlbumObj albumObjj, ImgObj imgObjj) {
		super();

		this.albumObj = albumObjj;
		this.imgObj = imgObjj;
		//this.modelActivity = _modelActivity;
		//if (imgObj.getImgGridfs_id_1()
		panel.addStyleName("ImgThumb");

		Date date = new java.util.Date((long)(imgObj.getImgTimestamp())*1000);

		albumImage.addStyleName("ModelImagePic");
  		albumImage.getElement().getStyle().setProperty("background",  "url(\"/extranewgwt/getphoto?photoid=" + imgObj.getImgGridfs_id_1() +  "\") no-repeat center");
  		
  		albumImageUnder.addStyleName("ModelImagePicUnder");
  		
  		albumImageUnder.add(albumImage);
		
  		albumImageUnder.add(albumImage);
		panel.add(albumImageUnder);

		albumImage.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//modelActivity.gotoImg(imgObj);
				ModelGoPicEvent modelGoPicEvent =new ModelGoPicEvent(imgObj);
				ClientFactory.getEventBus().fireEvent(modelGoPicEvent);
				/*
				String coverObjID = imgObj.getImgID();
				String params = "albid=" + albumObj.getAlbid() + "&coverid=" + coverObjID;
				ClientFactory.getPlaceController().goTo(new ModelPlace(params,true, ImgThumb.this));
				*/
			}
		});
		
		HTML html = new HTML(
				"<br/><br/><b>=====================</b>" +
						"<br/><b>Image timestamp</b><br/>" + date.toString() +
						"<br/><b>Is has original:</b><br/>" +  imgObj.getImgIshasorig().toString() +
						"<br/><b>AlbumID:</b><br/>" +  imgObj.getImgAlbum() +
						"<br/><b>Photo img 245px:</b> <br/><img src=\"/extranewgwt/getphoto?photoid=" +  imgObj.getImgGridfs_id_1() + "\"/>"
						, true);

		//panel.add(html);
		//panel.add(show1500pxButt);
		//panel.add(showOrigpxButt);

		
		
		String publishStr = null;
		if (ClientFactory.getUser().getUserRole()==2) {
			
			statusAdmin.addStyleName("statusAdminImg");
			statusAdmin.icon.addStyleName("AdminButtonIcon");
			statusAdmin.content.addStyleName("statusAdminContent");
			statusAdmin.text.addStyleName("AdminButtonText");
			statusAdmin.text.addStyleName("text11_White");
			
			//panel.add(adminSetPublishedButt);
			//panel.add(adminSetCoverButt);

			if (imgObj.getImgStatus()==2) {
				//adminSetPublishedButt.setText("adminSetPublishedButt to " + 1);
				publishStr = "p";
			} else {
				//adminSetPublishedButt.setText("adminSetPublishedButt to " + 2);
				publishStr = "u";
			}
			
			statusAdmin.setText(publishStr);
			panel.add(statusAdmin);
			
			
			
			coverAdmin.addStyleName("coverAdminImg");
			coverAdmin.icon.addStyleName("AdminButtonIcon");
			coverAdmin.content.addStyleName("statusAdminContent");
			coverAdmin.text.addStyleName("AdminButtonText");
			coverAdmin.text.addStyleName("text11_White");
			coverAdmin.setText("c");
			panel.add(coverAdmin);
			
			
			delAdmin.addStyleName("delAdminImg");
			delAdmin.icon.addStyleName("AdminButtonIcon");
			delAdmin.content.addStyleName("delAdminContent");
			delAdmin.text.addStyleName("AdminButtonText");
			delAdmin.text.addStyleName("text11_White");
			delAdmin.setText("dAlb");
			panel.add(delAdmin);

			

		}


		delAdmin.panel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doDeleteAlbum();
			}
		}); 

		statusAdmin.panel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//Window.open("http://stackoverflow.com", "_blank", null);
				if (imgObj.getImgStatus()==2) {
					statusAdmin.setText("u");
					doSetPublished(1);
					
				} else {
					statusAdmin.setText("p");
					doSetPublished(2);
					
				}
				
				//doSetPublished(int statusPublished)
			}
		});
		
		
		coverAdmin.panel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doSetCover();
			}
		});
		

		show1500pxButt.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//Window.open("http://stackoverflow.com", "_blank", null);
				Window.open("http://" + Window.Location.getHost() + "/extranewgwt/getphoto?photoid=" +  imgObj.getImgGridfs_id_m(), "_blank", "");
				Log.debug("showOrigpxButt.addClickHandler = " + Window.Location.getHost());
			}
		});

		showOrigpxButt.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//Window.open("http://stackoverflow.com", "_blank", null);
				Window.open("http://" +Window.Location.getHost() + "/extranewgwt/getphoto?photoid=" +  imgObj.getImgGridfs_id_0(), "_blank", "");
				Log.debug("showOrigpxButt.addClickHandler = " + Window.Location.getHost());
			}
		});

		initWidget(panel);
	}
	
	
	
	public void doSetCover() {
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
				Notifications notif = new Notifications("Img Status has changed", true, true);
				
			}
		};

		communicatorSvc.doImgCover(albumObj, imgObj, callback);

	}
	
	
	public void doSetPublished(final int statusPublished) {
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
				Notifications notif = new Notifications("Img Status has changed", true, true);
				imgObj.setImgStatus(statusPublished);
				/*
				if (imgObj.getImgStatus()==2) {
					imgObj.setImgStatus(1);
				}
				else {
					imgObj.setImgStatus(2);
				}*/
			}
		};

		communicatorSvc.doImgStatus(imgObj, statusPublished, callback);

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
				
			}
		};

		communicatorSvc.doDeAlbum(albumObj, callback);

	}
}
