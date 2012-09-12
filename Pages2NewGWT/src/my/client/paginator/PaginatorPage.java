package my.client.paginator;

import java.util.ArrayList;
import java.util.Date;

import my.client.common.ActivityHasPages;
import my.client.common.ClientFactory;
import my.client.modelpage.ModelActivity;
import my.client.modelpage.ModelPlace;
import my.client.rpcs.RPCService;
import my.client.rpcs.RPCServiceAsync;
import my.client.rpcs.RPCServiceExeption;
import my.shared.AlbumObj;
import my.shared.CommentObj;
import my.shared.CommentsObj;
import my.shared.ImgObj;
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
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

public class PaginatorPage extends Composite {

	private FlowPanel panel = new FlowPanel();
	private Button pageButt;
	private int pageNo;
	private ActivityHasPages activity;
	private  Paginator paginator;
	
	public PaginatorPage(int pageNoo, ActivityHasPages activityy, Paginator paginatorr, boolean isCurrPage) {
		super();
		this.pageNo = pageNoo;
		this.activity = activityy;
		this.paginator = paginatorr;
		if (isCurrPage) {
			pageButt = new Button("Nc=" + pageNo);
		}
		else {
			pageButt = new Button("N=" + pageNo);
		}
		 
		 
		pageButt.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					//String params = "albid=" + albumObj.getAlbid() + "&coverid=" + albumObj.getCoverImgObjID();
					//ClientFactory.getPlaceController().goTo(new ModelPlace(params,false, null));
					Log.debug("Paginator page pageNo= " + pageNo);
					paginator.gotoPage(pageNo, true);
					//paginator.gotoPage(page, forceClearOnFinish)
				}
			}); 

			panel.add(pageButt);


		initWidget(panel);
	}
	
	

}
