package my.client.blocks;

import my.client.common.ClientFactory;
import my.client.forum.ForumViewInterface.Presenter;
import my.client.helpers.HavePlace;
import my.client.modelpage.ModelPlace;
import my.client.windows.RegisterPopup;
import my.shared.ActivityObj;

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

public class ActivityUnit extends Composite {

    private FlowPanel panel = new FlowPanel();

    private Button gotoAlbum = new Button("gotoAlbum");
    private ActivityObj activityObj;
    private FocusPanel albumImage = new FocusPanel();
    private FlowPanel albumImageUnder = new FlowPanel();
    private Label name = new Label();
    private Label actType = new Label();
    private Label actText = new Label();
     //AdminsStuffBlock
    
    public ActivityUnit(ActivityObj activityObjj) {
    	this.activityObj = activityObjj;
    	
    	

    	//panel.add(gotoAlbum);
    	panel.addStyleName("ActivityUnit");
    	
    	
  ///////Album Image/////////
		
  		albumImage.addStyleName("albumImageActivity");
  		albumImage.getElement().getStyle().setProperty("background",  "url(\"/extranewgwt/getphoto?photoid=" + activityObj.getCoverPicObjID() +  "\") no-repeat center");
  		
  		albumImageUnder.addStyleName("albumImageActivityUnder");
  		
  		albumImageUnder.add(albumImage);
  		panel.add(albumImageUnder);
  		
  		
  		
  		/////////// texts //////////////
  		name.addStyleName("nameActivity");
  		name.addStyleName("text_11_white_bold");
  		name.setText(activityObj.getNick());
  		
  		actType.addStyleName("actType");
  		actType.addStyleName("text_11_grey");
  		actType.setText(activityObj.getActivityType());
  		
  		actText.addStyleName("actText");
  		actText.addStyleName("text11_White");
  		if (activityObj.getActivityType().equals("TAG")) {
  			actText.setText(activityObj.getTagReadableName());
  		}
  		if (activityObj.getActivityType().equals("COMMENT")) {
  			if (activityObj.getCommentText().length()>23) {
  				actText.setText(activityObj.getCommentText().substring(0, 23) + "...");
  			}
  			else {
  				actText.setText(activityObj.getCommentText());
  			}
  		}
  		
  		panel.add(actText);
  		panel.add(actType);
  		panel.add(name);
  		
  		
    	//gotoAlbum.setText("Activ " + activityObj.getActivityType() + " Tag " + activityObj.getTagType() + " Comment " + activityObj.getCommentText() + " album " + activityObj.getAlbname());
    	//gotoAlbum.setText("Activ " + activityObj.getActivityType());
    	
    	albumImage.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//History.back();
				String params = "albid=" + activityObj.getAlbid() + "&coverid=" + activityObj.getCoverImgObjID();
				ClientFactory.getPlaceController().goTo(new ModelPlace(params,false, null));

			}
		});
    			

		
		//HandlerRegistration addScrollHandler = ScrollPanel.addScrollHandler(null);
		
		initWidget(panel);
	}
}
