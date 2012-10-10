package my.client.blocks;

import java.util.Date;

import my.client.common.ClientFactory;
import my.client.events.NewCommentEvent;
import my.client.events.NewCommentEventHandler;
import my.client.modelpage.ModelActivity;
import my.client.modelpage.ModelPlace;
import my.client.rpcs.RPCService;
import my.client.rpcs.RPCServiceAsync;
import my.client.rpcs.RPCServiceExeption;
import my.client.windows.UserHasLoggedEvent;
import my.shared.AlbumObj;
import my.shared.CommentObj;
import my.shared.CommentsObj;
import my.shared.ImgObj;
import my.shared.ImgsObj;
import my.shared.ModelPageObj;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

public class ImgsBlock extends Composite {


	private final static int imgLimit  = 5;
	private int calculatedLimit  = 0;
	private boolean isShowMore  = false;
	private Button showMoreLabel = new Button("more");
	
	private FlowPanel panel = new FlowPanel();

	private ImgsObj imgsObj;
	private Label panelHeader = new Label("Album photos:");
	//private HTML headerHTML = new HTML("<h3>Album images</h3>");
	private AlbumObj albumObj;

	public ImgsBlock(AlbumObj albumObjj, ImgsObj _imgsObj) {
		super();
		this.imgsObj = _imgsObj;
		this.albumObj =  albumObjj;
		panel.addStyleName("ImgsBlock");

	//	panel.addStyleName("CommentsBlock");
		showMoreLabel.addStyleName("showMoreLabel");
		
		panelHeader.addStyleName("CommentsBlockHeader");
		panelHeader.addStyleName("text12_white_bold");


		panel.add(panelHeader);

		/*
		 HTML html = new HTML(
				 "<h3>Album images: " + imgsObj.getImages().get(0).getImgAlbum() + "<h3>"
	  , true);*/



		//for (int i=0; i<imgsObj.getImages().size(); i++) {
		if (imgsObj.getImages().size() >imgLimit) {
			isShowMore = true;
			calculatedLimit = imgLimit;
		}
		else {
			isShowMore = false;
			calculatedLimit = imgsObj.getImages().size();
		}
		
		for (int i=0; i<calculatedLimit; i++) {

			ImgThumb imgThumb = new ImgThumb(albumObj,imgsObj.getImages().get(i));
			panel.add(imgThumb);
		}

		if (isShowMore) {
			panel.add(showMoreLabel);
			showMoreLabel.addClickHandler(new ClickHandler() {
				
				
				public void onClick(ClickEvent event) {
					showMoreLabel.removeFromParent();
					for (int ii = calculatedLimit; ii<imgsObj.getImages().size(); ii++) {

						ImgThumb imgThumb = new ImgThumb(albumObj,imgsObj.getImages().get(ii));
						panel.add(imgThumb);
					}
					
				}
			}); 
		}


		initWidget(panel);
	}




}
