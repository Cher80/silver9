package my.client.blocks;

import my.client.common.ClientFactory;
import my.client.forum.ForumViewInterface.Presenter;
import my.client.helpers.HavePlace;
import my.client.modelpage.ModelPlace;
import my.client.windows.RegisterPopup;
import my.shared.ActivityObj;
import my.shared.AlbumObj;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;

public class BestUnit extends Composite {

    private FlowPanel panel = new FlowPanel();
    private FocusPanel albumImage = new FocusPanel();
    private Button gotoAlbum = new Button("gotoAlbum");
    private AlbumObj albumObj;
    private LikesStatBlock likesStatBlock;
    private int pos;
    private Label posLabel;
    private Label nameLabel;
     //AdminsStuffBlock
    
    public BestUnit(AlbumObj albumObjj, int poss) {
    	this.albumObj = albumObjj;
    	this.pos =poss;
    	
    	panel.addStyleName("bestUnit");
    	
  ///////Album Image/////////
		
  		albumImage.addStyleName("albumImageBest");
  		albumImage.getElement().getStyle().setProperty("background",  "url(\"/extranewgwt/getphoto?photoid=" + albumObj.getCoverPicID() +  "\") no-repeat center");
  		panel.add(albumImage);
  		
  		///////Rating/////
  		likesStatBlock = new LikesStatBlock(albumObj,true);
		likesStatBlock.addStyleName("likesStatBlockBest");
		panel.add(likesStatBlock);
    	
		
		///////Position Label//////
		posLabel = new Label(this.pos +1 + "");
		posLabel.addStyleName("posLabel");
		posLabel.addStyleName("text12_white_bold");
    	panel.add(posLabel);
    	
  ///////Name Label//////
    	nameLabel = new Label(albumObj.getAlbname());
    	nameLabel.addStyleName("nameLabel");
    	nameLabel.addStyleName("text11_White");
      	panel.add(nameLabel);
    	
    	

    	//gotoAlbum.setText("Best" + albumObj.getAlbname());
    	
    	albumImage.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//History.back();
				String params = "albid=" + albumObj.getAlbid() + "&coverid=" + albumObj.getCoverImgObjID();
				ClientFactory.getPlaceController().goTo(new ModelPlace(params,false, null));

			}
		});
    			

		
	
		initWidget(panel);
	}
}
