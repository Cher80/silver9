package my.client.blocks;

import java.util.Date;

import my.client.albumspage.AlbumsActivity;
import my.client.common.ClientFactory;
import my.client.events.NewCommentEvent;
import my.client.events.ReloadAlbumsEvent;
import my.client.modelpage.ModelActivity;
import my.client.modelpage.ModelPlace;
import my.client.rpcs.RPCService;
import my.client.rpcs.RPCServiceAsync;
import my.client.rpcs.RPCServiceExeption;
import my.client.windows.AdminsPopup;
import my.client.windows.LoginPopup;
import my.client.windows.Notifications;
import my.client.windows.UserHasLoggedEvent;
import my.shared.AlbumObj;
import my.shared.CommentObj;
import my.shared.ImgObj;
import my.shared.ModelPageObj;
import my.shared.StatObj;
import my.shared.TagObj;
import my.shared.User;

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

public class StatBlock extends Composite {

	
	private FlowPanel panel = new FlowPanel();
	private Label showSomeStat;
	//	private String tag;

	private StatObj statObj;
	//private Button doTagMark;

	//private int 

	public StatBlock(StatObj statObjj) {

		super();
		panel.addStyleName("StatBlock");
		this.statObj = statObjj;
		
		
		//showSomeStat = new Label("social networks belle collection // " + statObj.getTotalAlbums() + " models " + statObj.getTotalImgs() + " photos");
		showSomeStat = new Label("Social belle collection // " + statObj.getTotalImgs() + " photos");
		//+ statObj.getLastAlbums() + " LastImgs " + statObj.getLastImgs());
		showSomeStat.addStyleName("text11_White");
		panel.add(showSomeStat);
		initWidget(panel);
	}





}
