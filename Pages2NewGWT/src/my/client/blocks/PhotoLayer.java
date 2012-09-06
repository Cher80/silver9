package my.client.blocks;

import java.util.Date;

import my.client.common.ClientFactory;
import my.client.modelpage.ModelActivity;
import my.client.modelpage.ModelPlace;
import my.shared.AlbumObj;
import my.shared.ImgObj;
import my.shared.ImgsObj;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

public class PhotoLayer extends Composite {

	

	private FlowPanel panel = new FlowPanel();
	private ImgsObj imgsObj;
	private ImgObj curImgObj;
	private ModelActivity curActivity;
	private HTML html = null;
	private Button prevButt = new Button("prevButt");
	private Button nextButt = new Button("nextButt");

	
	public PhotoLayer(ImgsObj imgsObjj, String curID, ModelActivity curActivityP) {
		super();
		this.imgsObj = imgsObjj;
		this.curActivity = curActivityP;
		
		panel.add(prevButt);
		panel.add(nextButt);
		
	//	Date date = new java.util.Date((long)(imgObj.getImgTimestamp())*1000);

		curImgObj = imgsObj.getImgByID(curID); 
		 
		doRenderPhoto();
		
		 
		 
		 prevButt.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
						
					curImgObj = imgsObj.getPrevImg(curImgObj.getImgID());
					((ModelActivity)curActivity).doPrevImg(curImgObj);
					doRenderPhoto();
				}
			});
	    	
			nextButt.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					
					curImgObj = imgsObj.getNextImg(curImgObj.getImgID());
					((ModelActivity)curActivity).doNextImg(curImgObj);
					doRenderPhoto();
				}
			});
		 
			
			 
			 
		initWidget(panel);
	}
	
	public void doRenderPhoto() {
	  if (html != null) {
		  panel.remove(html);
	  }
		html = new HTML(
				 "<h3>Photo layer</h3>" +
				 "<img style=\"max-height:400px; max-width:400px\" src=\"/extranewgwt/getphoto?photoid=" + curImgObj.getImgGridfs_id_m() + "\"/>"
 , true);
		panel.add(html);
	}
	
}
