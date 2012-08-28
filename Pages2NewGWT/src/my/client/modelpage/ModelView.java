package my.client.modelpage;

import java.util.ArrayList;
import java.util.Arrays;

import my.client.albumspage.AlbumsPlace;
import my.client.blocks.AlbumThumb;
import my.client.blocks.ImgThumb;
import my.client.blocks.PhotoLayer;
import my.client.helpers.HavePresenter;
import my.client.windows.RegisterPopup;
import my.shared.AlbumObj;
import my.shared.ImgsObj;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class ModelView extends Composite implements HavePresenter  {
	private Button prevButt = new Button("prevButt");
	private Button nextButt = new Button("nextButt");

	private FlowPanel panel = new FlowPanel();
	private ModelActivity presenter;
	private PhotoLayer photoLayer;
	


	

	
	public ModelView(final Activity presenter) {
		this.presenter = (ModelActivity)presenter;
		panel.setSize("700px", "500px");
		panel.getElement().getStyle().setProperty("border", "1px solid green");
		panel.getElement().getStyle().setProperty("cssFloat", "left");
		panel.getElement().getStyle().setProperty("left", "0px");
		panel.getElement().getStyle().setProperty("overflow", "scroll");
		
    	
		prevButt.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//((ModelActivity)presenter).doPrevImg();	
			}
		});
    	
		nextButt.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//((ModelActivity)presenter).doNextImg();
			}
		});
    	
    	
		

    	
		//panel.add(prevButt);
		//panel.add(nextButt);
		
		initWidget(panel);
	}
	
	
	
	public void renderPhotoLayer(ImgsObj imgsObj, String coverid) {
		photoLayer = new PhotoLayer(imgsObj,coverid,(ModelActivity)this.getPresenter());
		panel.add(photoLayer);
	}
	 
	public void renderPhotos(ImgsObj imgsObj) {
		
		
		
		 HTML html = new HTML(
				 "<h3>Show album: " + imgsObj.getImages().get(0).getImgAlbum() + "<h3>"
	  , true);
		
		 panel.add(html);
		
		for (int i=0; i<imgsObj.getImages().size(); i++) {
			ImgThumb imgThumb = new ImgThumb(imgsObj.getImages().get(i));
			panel.add(imgThumb);
		}
	}
	
	
	@Override
	public Activity getPresenter() {
		// TODO Auto-generated method stub
		return presenter;
	}
	
}
