package my.client.blocks;

import java.util.Date;

import my.client.common.ClientFactory;
import my.client.modelpage.ModelPlace;
import my.shared.AlbumObj;
import my.shared.ImgObj;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

public class ImgThumb extends Composite {

	

	private FlowPanel panel = new FlowPanel();
	private Button show1500pxButt = new Button("show1500px");
	private Button showOrigpxButt = new Button("showOrigpx");
	private ImgObj imgObj;

	public ImgThumb(ImgObj imgObjj) {
		super();
		this.imgObj = imgObjj;
		
		Date date = new java.util.Date((long)(imgObj.getImgTimestamp())*1000);

		
		 HTML html = new HTML(
				 "<br/><br/><b>=====================</b>" +
	"<br/><b>Image timestamp</b><br/>" + date.toString() +
 	"<br/><b>Is has original:</b><br/>" +  imgObj.getImgIshasorig().toString() +
  "<br/><b>AlbumID:</b><br/>" +  imgObj.getImgAlbum() +
  "<br/><b>Photo img 245px:</b> <br/><img src=\"/extranewgwt/getphoto?photoid=" +  imgObj.getImgGridfs_id_1() + "\"/>"
  , true);
		
		 panel.add(html);
		 panel.add(show1500pxButt);
		 panel.add(showOrigpxButt);
		 
		 
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
}
