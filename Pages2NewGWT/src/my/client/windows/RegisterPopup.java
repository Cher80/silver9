package my.client.windows;

import my.client.rpcs.RPCService;
import my.client.rpcs.RPCServiceAsync;
import my.client.rpcs.RPCServiceExeption;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;

public class RegisterPopup extends PopupPanel {
	
	private FlowPanel panel = new FlowPanel();
	
	private Button sendButt = new Button("sendButt");
	
	private Label nickNameLabel = new Label("nickName:");
	private TextBox nickNameBox = new TextBox();
	private Label eMailLabel = new Label("eMailLabel:");
	private TextBox eMailBox = new TextBox();
	
	private Label pass1Label = new Label("pass1Label:");
	private PasswordTextBox pass1Box = new PasswordTextBox();
	
	private Label pass2Label = new Label("pass2Label:");
	private PasswordTextBox pass2Box = new PasswordTextBox();
	   
	public RegisterPopup() {
		super(true);
		this.addStyleName("registerPopup");
		this.addStyleName("olo");
		Log.debug("RegisterView created ");
		this.add(panel);
		
		panel.add(nickNameLabel);
		panel.add(nickNameBox);
		panel.add(eMailLabel);
		panel.add(eMailBox);
		panel.add(pass1Label);
		panel.add(pass1Box);
		panel.add(pass2Label);
		panel.add(pass2Box);
		panel.add(sendButt);
		
		sendButt.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					RPCServiceAsync communicatorSvc = GWT.create(RPCService.class);

					// Set up the callback object.
					AsyncCallback callback = new AsyncCallback() {
						
					public void onFailure(Throwable caught) {
						
						if (caught instanceof RPCServiceExeption) {
							Log.debug("exeption!!");
						}

					}

					@Override
					public void onSuccess(Object result) {
						// TODO Auto-generated method stub
				
					}
				};

			// Make the call
			communicatorSvc.doRegister(2, callback);

			}
			});

		//this.show();
		//this.initWidget(panel);
	}
}
