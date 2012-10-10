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

	private Button sendButt = new Button("Login!");

	private Label eMailLabel = new Label("Your email:");
	private TextBox eMailBox = new TextBox();

	private Label pass1Label = new Label("Password:");
	private PasswordTextBox pass1Box = new PasswordTextBox();
	private Label status = new Label("");

	private boolean isValidEmail;
	private boolean isValidPass1;
	
	public LoginPopup() {
		super(true);
		this.addStyleName("loginPopup");
		Log.debug("LoginView created ");
		this.add(panel);

		
		eMailLabel.addStyleName("text12_black_bold");
		pass1Label.addStyleName("text12_black_bold");
		status.addStyleName("text12_black_bold");
		
		eMailLabel.addStyleName("regFormLabel");
		pass1Label.addStyleName("regFormLabel");
		status.addStyleName("regFormLabel");
		//eMailLabel.addStyleName("regFormLabel");
		sendButt.addStyleName("regFormButt");
		
		panel.add(eMailLabel);
		panel.add(eMailBox);
		panel.add(pass1Label);
		panel.add(pass1Box);
		panel.add(sendButt);
		panel.add(status);

		eMailBox.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				 emailBoxValidator();
			}
		});


		pass1Box.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				pass1BoxValidator();
			}
		});
 



		sendButt.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {



				if (isValidPass1 && isValidEmail) {
					Log.debug("Form valid");
					sendToServer(eMailBox.getText(),pass1Box.getText());
				}


			}
		});

		//this.show();
		//this.initWidget(panel);
	}

	
	
	public void emailBoxValidator() {
		//this.isValidEmail
		isValidEmail = FieldVerifier.isValidEmailAddress(eMailBox.getText());
		if (isValidEmail) {
			Log.debug("Good email");
			eMailLabel.setText("Email OK");
			eMailLabel.setStyleName("text12_green_bold");
			eMailLabel.addStyleName("regFormLabel");
		} else {
			eMailLabel.setText("Enter valid email");
			eMailLabel.setStyleName("text12_red_bold");
			eMailLabel.addStyleName("regFormLabel");
			Log.debug("Bad email");
		}
	}
	
	
	public void pass1BoxValidator() {
		isValidPass1 = FieldVerifier.isLenghtOK(pass1Box.getText(),1,50);
		if (isValidPass1) {
			Log.debug("Good Password1");
			pass1Label.setText("Password OK");
			pass1Label.setStyleName("text12_green_bold");
			pass1Label.addStyleName("regFormLabel");
			//nickNameLabel.setText("Nick ene");
		} else {
			pass1Label.setText("Password must be 1 character at least");
			Log.debug("Bad Password1");
			pass1Label.setStyleName("text12_red_bold");
			pass1Label.addStyleName("regFormLabel");
		}
	}
	
	void sendToServer(String email, String pass1) {

		RPCServiceAsync communicatorSvc = GWT.create(RPCService.class);

		// Set up the callback object.
		AsyncCallback callback = new AsyncCallback() {

			public void onFailure(Throwable caught) {

				if (caught instanceof RPCServiceExeption) {
					Log.debug("exeption!!");
					status.setText(((RPCServiceExeption)caught).getErrorCode());
					Notifications notif = new Notifications(((RPCServiceExeption)caught).getErrorCode(), true, true);
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
