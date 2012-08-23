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

public class LoginPopup extends PopupPanel {

	private FlowPanel panel = new FlowPanel();

	private Button sendButt = new Button("sendButt");

	private Label eMailLabel = new Label("eMailLabel:");
	private TextBox eMailBox = new TextBox();

	private Label pass1Label = new Label("pass1Label:");
	private PasswordTextBox pass1Box = new PasswordTextBox();
	private Label status = new Label("registration status");

	public LoginPopup() {
		super(true);
		this.addStyleName("loginPopup");
		Log.debug("LoginView created ");
		this.add(panel);

		panel.add(eMailLabel);
		panel.add(eMailBox);
		panel.add(pass1Label);
		panel.add(pass1Box);
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



				if (isValidPass1 && isValidEmail) {
					Log.debug("Form valid");
					sendToServer(eMailBox.getText(),pass1Box.getText());
				}


			}
		});

		//this.show();
		//this.initWidget(panel);
	}

	void sendToServer(String email, String pass1) {

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
				Notifications notif = new Notifications("You have logged", true, true);
				//ClientFactory.getEventBus().
				LoginPopup.this.hide();
			}
		};

		//	Make the call
		Log.debug("Make the call");
		 communicatorSvc.doLogin(email, pass1, callback);

	}
}
