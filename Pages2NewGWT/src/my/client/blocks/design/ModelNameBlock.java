package my.client.blocks.design;

import java.util.Date;

import my.client.albumspage.AlbumsPlace;
import my.client.common.ClientFactory;
import my.client.events.PageTitleEvent;
import my.client.events.ReloadAlbumsEvent;
import my.client.forum.ForumViewInterface.Presenter;
import my.client.helpers.HavePlace;
import my.client.modelpage.ModelPlace;
import my.client.windows.RegisterPopup;
import my.shared.ActivityObj;
import my.shared.AlbumObj;
import my.shared.ImgsObj;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;

public class ModelNameBlock extends Composite {

	private FlowPanel panel = new FlowPanel();
	
private AlbumObj albumObj;
private Label nameYO = new Label();
private Label modelPage = new Label();

private FlowPanel modelNameUnder = new FlowPanel();
private FlowPanel pageUnder = new FlowPanel();

private FlowPanel modelPhotoIco = new FlowPanel();
private Label modelPhotosCount = new Label();
private Label modelAddedDate = new Label();
private ImgsObj imgsObj;
    //private Button gotoAlbum = new Button("gotoAlbum");
    //private AlbumObj albumObj;
    
     //AdminsStuffBlock
    
    public ModelNameBlock(AlbumObj _albumObj, ImgsObj _imgsObj) {
    	//this.albumObj = albumObjj;
    	this.albumObj = _albumObj;
    	this.imgsObj = _imgsObj;
    	
    	nameYO.setText(albumObj.getAlbname() + ", age " + albumObj.getAlbage() );
    	modelPage.setText("Her page: "   + albumObj.getAlbpage());
    	panel.addStyleName("ModelNameBlock");
    	panel.addStyleName("back_pattern");
    	nameYO.addStyleName("modelNameYO");
    	nameYO.addStyleName("text_16_black_bold");
    	
    	modelPage.addStyleName("modelPage");
    	modelPage.addStyleName("text_11_grey");
    	
    	modelNameUnder.addStyleName("modelNameUnder");
    	
    	modelPhotoIco.addStyleName("modelPhotoIco");
    	modelPhotosCount.addStyleName("modelPhotosCount");
    	modelPhotosCount.addStyleName("text_11_white_bold");
    	
    	
    	modelAddedDate.addStyleName("modelAddedDate");
    	modelAddedDate.addStyleName("text9_white");
    	
    	modelPhotosCount.setText(imgsObj.getImages().size() + " Photos in set");
    	
    	
    	Date dateAdded = new Date ();
		dateAdded.setTime(albumObj.getTimestamp()*1000);
		DateTimeFormat fmt = DateTimeFormat.getFormat("dd/MM/yy");
		// prints Monday, December 17, 2007 in the default locale
		String dateAddedString = (fmt.format(dateAdded));
		
    	modelAddedDate.setText("added " + dateAddedString);
    	
    	panel.add(modelNameUnder);
    	panel.add(nameYO);
    	panel.add(modelPage);
    	
    	panel.add(modelPhotoIco);
    	panel.add(modelPhotosCount);
    	panel.add(modelAddedDate);
    	
    	
    //	nameYO = new Label();
    	//private Label modelPage = new Label();
    	
    			

		
	
		initWidget(panel);
	}
}
