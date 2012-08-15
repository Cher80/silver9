package my.client.albumspage;

import my.client.helpers.HavePresenter;
import my.client.windows.RegisterPopup;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;

public class AlbumsView extends Composite implements HavePresenter {
	private Button myButt1 = new Button("AlbumsView");
	private ScrollPanel panel = new ScrollPanel();
	private Activity presenter;
	
	public AlbumsView(Activity presenter) {
		this.presenter = presenter;
		panel.setSize("300px", "300px");
    	panel.getElement().getStyle().setProperty("border", "1px solid green");
    	panel.getElement().getStyle().setProperty("cssFloat", "left");
    	panel.getElement().getStyle().setProperty("left", "0px");
		
    	
    	myButt1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RegisterPopup registerView = new RegisterPopup();
				registerView.center(); 
				registerView.show();
				//panel.add(registerView);
				
			}
		});
    	
		panel.add(myButt1);
		initWidget(panel);
	}
	@Override
	public Activity getPresenter() {
		// TODO Auto-generated method stub
		return presenter;
	}
}
