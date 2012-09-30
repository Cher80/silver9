package my.client.blocks;

import java.util.Date;

import my.client.common.ClientFactory;
import my.client.events.NewCommentEvent;
import my.client.events.PageTitleEvent;
import my.client.events.PageTitleEventHandler;
import my.client.modelpage.ModelActivity;
import my.client.modelpage.ModelPlace;
import my.client.rpcs.RPCService;
import my.client.rpcs.RPCServiceAsync;
import my.client.rpcs.RPCServiceExeption;
import my.client.windows.Notifications;
import my.client.windows.UserHasLoggedEvent;
import my.shared.AlbumObj;
import my.shared.CommentObj;
import my.shared.ImgObj;
import my.shared.ModelPageObj;
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

public class AlbumViewHeader extends Composite implements PageTitleEventHandler {



	private Label headerLabel = new Label();
	private FlowPanel panel = new FlowPanel();
	//private int 
	
	public AlbumViewHeader() {
		super();
		headerLabel.setText("");
		headerLabel.addStyleName("text12_white_bold");
		panel.addStyleName("AlbumViewHeader");
		panel.add(headerLabel);
		ClientFactory.getEventBus().addHandler(PageTitleEvent.TYPE, this);
		
		initWidget(panel);
	}

	@Override
	public void onSetPageTitle(PageTitleEvent event) {
		// TODO Auto-generated method stub
		headerLabel.setText(event.getPageHeader());
		
	}

	


}
