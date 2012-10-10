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
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
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

	private Button sendButt = new Button("Register!");

	private Label nickNameLabel = new Label("Provide nickname:");

	private TextBox nickNameBox = new TextBox();
	private Label eMailLabel = new Label("Your email (will be used as Login, no spam):");
	private TextBox eMailBox = new TextBox();

	private Label pass1Label = new Label("Password:");
	private PasswordTextBox pass1Box = new PasswordTextBox();

	private Label pass2Label = new Label("Password again:");
	private PasswordTextBox pass2Box = new PasswordTextBox();

	private Label status = new Label("");

	private boolean isValidEmail;
	private boolean isValidPass1;
	private boolean isValidPass2;
	private boolean isValidNick;
	private boolean isValidPassMutch;

	public RegisterPopup() {
		super(true);


		//ClientFactory.getEventBus().

		this.addStyleName("registerPopup");
		this.addStyleName("popusCommon");
		//this.addStyleName("olo");
		Log.debug("RegisterView created ");
		this.add(panel);

		nickNameLabel.addStyleName("text12_black_bold");
		eMailLabel.addStyleName("text12_black_bold");
		pass1Label.addStyleName("text12_black_bold");
		pass2Label.addStyleName("text12_black_bold");
		status.addStyleName("text12_black_bold");
		
		nickNameLabel.addStyleName("regFormLabel");
		eMailLabel.addStyleName("regFormLabel");
		pass1Label.addStyleName("regFormLabel");
		pass2Label.addStyleName("regFormLabel");
		status.addStyleName("regFormLabel");
		
		sendButt.addStyleName("regFormButt");

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
				emailBoxValidator();
				// TODO Auto-generated method stub
			}
		});


		nickNameBox.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				nickNameBoxValidator();
				// TODO Auto-generated method stub
			}
		});

		pass1Box.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				pass1BoxValidator();
				// TODO Auto-generated method stub
			}
		});

		pass2Box.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				// TODO Auto-generated method stub
				pass2BoxValidator();
			}			
		});

		pass2Box.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				pass2BoxValidator();
				// TODO Auto-generated method stub
			}
		});




		sendButt.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {


				/*
				Boolean isValidEmail = FieldVerifier.isValidEmailAddress(eMailBox.getText());
				if (isValidEmail) {
					Log.debug("Good email");
				} else {
					eMailLabel.setText("Enter valid email");
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
*/
				emailBoxValidator();
				nickNameBoxValidator();
				pass1BoxValidator();
				pass2BoxValidator();

				if (isValidNick && isValidPass2 && isValidPass1 && isValidEmail && isValidPassMutch) {
					Log.debug("Form valid");
					sendToServer(nickNameBox.getText(),eMailBox.getText(),pass1Box.getText(), pass2Box.getText());
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
	
	public void nickNameBoxValidator() {
		
		isValidNick = FieldVerifier.isLenghtOK(nickNameBox.getText(),1,20);
		if (isValidNick) {
			Log.debug("Good Nick");
			nickNameLabel.setText("Nickname OK");
			nickNameLabel.setStyleName("text12_green_bold");
			nickNameLabel.addStyleName("regFormLabel");
			//nickNameLabel.setText("Nick ene");
		} else {
			nickNameLabel.setText("Nickname must be 1 character at least (max 20)");
			nickNameLabel.setStyleName("text12_red_bold");
			nickNameLabel.addStyleName("regFormLabel");
			Log.debug("Bad Nick");
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

	public void pass2BoxValidator() {
		

		isValidPassMutch = pass2Box.getText().equals(pass1Box.getText());
		if (isValidPassMutch &&  pass2Box.getText().length()!=0) {
			pass2Label.setText("Password OK");
			pass2Label.setStyleName("text12_green_bold");
			pass2Label.addStyleName("regFormLabel");
			Log.debug("Good Password2");
			//nickNameLabel.setText("Nick ene");
		} else {
			pass2Label.setText("Passwords don't match");
			Log.debug("Bad Password - no match");
			pass2Label.setStyleName("text12_red_bold");
			pass2Label.addStyleName("regFormLabel");
		}
		isValidPass2= true;
		/*
		isValidPass2 = FieldVerifier.isLenghtOK(pass2Box.getText(),1,50);
		if (isValidPass2) {
			Log.debug("Good Password2");
			pass2Label.setText("Password OK");
			pass2Label.setStyleName("text12_green_bold");
			//nickNameLabel.setText("Nick ene");
		} else {
			pass2Label.setText("Password must be 1 character at least");
			pass2Label.setStyleName("text12_red_bold");
			Log.debug("Bad Password2");
		}*/
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
