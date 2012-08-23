package my.client.windows;

import my.client.common.ClientFactory;
import my.client.rpcs.RPCService;
import my.client.rpcs.RPCServiceAsync;
import my.client.rpcs.RPCServiceExeption;
import my.shared.FieldVerifier;
import my.shared.User;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
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
	
	private Label status = new Label("registration status");

	public RegisterPopup() {
		super(true);

		
		//ClientFactory.getEventBus().
		
		this.addStyleName("registerPopup");
		//this.addStyleName("olo");
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
		panel.add(status);
		

		eMailBox.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				Boolean isValid = FieldVerifier.isValidEmailAddress(eMailBox.getText());
				if (isValid) {
					Log.debug("Good email");
				} else {
					eMailLabel.setText("Incorrect! Enter valid email!!");
					Log.debug("Bad email");
				}
				// TODO Auto-generated method stub
			}
		});


		nickNameBox.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				Boolean isValid = FieldVerifier.isLenghtOK(nickNameBox.getText(),1,20);
				if (isValid) {
					Log.debug("Good Nick");
					//nickNameLabel.setText("Nick ene");
				} else {
					nickNameLabel.setText("Nick name must be 1 character at least (max 20)!!");
					Log.debug("Bad Nick");
				}
				// TODO Auto-generated method stub
			}
		});

		pass1Box.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				Boolean isValid = FieldVerifier.isLenghtOK(pass1Box.getText(),1,50);
				if (isValid) {
					Log.debug("Good Password1");
					//nickNameLabel.setText("Nick ene");
				} else {
					pass1Label.setText("Password1 name must be 1 character at least!!");
					Log.debug("Bad Password1");
				}
				// TODO Auto-generated method stub
			}
		});


		pass2Box.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				Boolean isValid = FieldVerifier.isLenghtOK(pass2Box.getText(),1,50);
				if (isValid) {
					Log.debug("Good Password2");
					//nickNameLabel.setText("Nick ene");
				} else {
					pass2Label.setText("Password2 name must be 1 character at least!!");
					Log.debug("Bad Password2");
				}
				
				if (pass2Box.getText().equals(pass1Box.getText())) {
					Log.debug("Good Password2");
					//nickNameLabel.setText("Nick ene");
				} else {
					pass2Label.setText("Password2 doesnt match Password1");
					Log.debug("Bad Password2 - no match");
				}
				// TODO Auto-generated method stub
			}
		});



		sendButt.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {



				Boolean isValidEmail = FieldVerifier.isValidEmailAddress(eMailBox.getText());
				if (isValidEmail) {
					Log.debug("Good email");
				} else {
					eMailLabel.setText("Incorrect! Enter valid email!!");
					Log.debug("Bad email");
				}


				Boolean isValidPass1 = FieldVerifier.isLenghtOK(pass1Box.getText(),1,50);
				if (isValidPass1) {
					Log.debug("Good Password1");
					//nickNameLabel.setText("Nick ene");
				} else {
					pass1Label.setText("Password1 name must be 1 character at least!!");
					Log.debug("Bad Password1");
				}


				Boolean isValidPass2 = FieldVerifier.isLenghtOK(pass2Box.getText(),1,50);
				if (isValidPass2) {
					Log.debug("Good Password2");
					//nickNameLabel.setText("Nick ene");
				} else {
					pass2Label.setText("Password2 name must be 1 character at least!!");
					Log.debug("Bad Password2");
				}

				Boolean isValidNick = FieldVerifier.isLenghtOK(nickNameBox.getText(),1,20);;
				if (isValidNick) {
					Log.debug("Good Nick");
					//nickNameLabel.setText("Nick ene");
				} else {
					nickNameLabel.setText("Nick name must be 1 character at least!!");
					Log.debug("Bad Nick");
				}
				
				Boolean isValidPassMutch = pass2Box.getText().equals(pass1Box.getText());
				if (isValidPassMutch) {
					Log.debug("Good Password2");
					//nickNameLabel.setText("Nick ene");
				} else {
					pass2Label.setText("Password2 doesnt match Password1");
					Log.debug("Bad Password2 - no match");
				}


				if (isValidNick && isValidPass2 && isValidPass1 && isValidEmail && isValidPassMutch) {
					Log.debug("Form valid");
					sendToServer(nickNameBox.getText(),eMailBox.getText(),pass1Box.getText(), pass2Box.getText());
				}


			}
		});

		//this.show();
		//this.initWidget(panel);
	}

	void sendToServer(String nick, String email, String pass1, String pass2) {
		status.setText("working");
		RPCServiceAsync communicatorSvc = GWT.create(RPCService.class);

		// Set up the callback object.
		AsyncCallback callback = new AsyncCallback() {

			public void onFailure(Throwable caught) {

				if (caught instanceof RPCServiceExeption) {
					Log.debug("exeption!!");
					status.setText(((RPCServiceExeption)caught).getErrorCode());
				}

			}

			@Override
			public void onSuccess(Object result) {
				// TODO Auto-generated method stub
				Log.debug("onSuccess");
				status.setText("well done");
				UserHasLoggedEvent event = new UserHasLoggedEvent((User)result);
				ClientFactory.getEventBus().fireEvent(event);
				Log.debug("UserHasLoggedEvent fired");
				Notifications notif = new Notifications("You have registered", true, true);
				//ClientFactory.getEventBus().
				RegisterPopup.this.hide();
			}
		};

		//	Make the call
		Log.debug("Make the call");
		communicatorSvc.doRegister(nick, email, pass1, pass2, callback);

	}
}
