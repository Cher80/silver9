package my.client.albumspage;

import java.util.ArrayList;
import java.util.Arrays;

import my.client.blocks.AlbumThumb;
import my.client.helpers.HavePresenter;
import my.client.windows.RegisterPopup;
import my.shared.AlbumObj;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class AlbumsView extends Composite implements HavePresenter,ClickHandler {
	private Button myButt1 = new Button("AlbumsView");
	private FocusPanel wrapper = new FocusPanel();

	private FlowPanel panel = new FlowPanel();
	private Activity presenter;
	
	  private static class Contact {
		    private final String address;
		    private final String name;

		    public Contact(String name, String address) {
		      this.name = name;
		      this.address = address;
		    }
		  }

	  /*ArrayList <Contact> CONTACTS = (ArrayList<Contact>) Arrays.asList(
			    new Contact("John", "123 Fourth Road"),
			    new Contact("Mary", "222 Lancer Lane"));*/

	

	
	public AlbumsView(Activity presenter) {
		this.presenter = presenter;
		wrapper.setSize("300px", "400px");
		wrapper.getElement().getStyle().setProperty("border", "1px solid green");
		wrapper.getElement().getStyle().setProperty("cssFloat", "left");
		wrapper.getElement().getStyle().setProperty("left", "0px");
		wrapper.getElement().getStyle().setProperty("overflow", "scroll");
		
    	
    	myButt1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RegisterPopup registerView = new RegisterPopup();
				registerView.center(); 
				registerView.show();
				//event.
				//panel.add(registerView);
				
			}
		});
    	
    	
    	
    	
    	wrapper.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//RegisterPopup registerView = new RegisterPopup();
				//registerView.center(); 
				//registerView.show();
				Log.debug("event.getSource().getClass().toString(); = " + event.getSource().getClass().toString());
				event.getSource().getClass().toString();
				event.getRelativeElement().getClass().toString();
				
				//panel.get
				Element target = (Element) Element.as(event.getNativeEvent().getEventTarget());
				if (target.equals(myButt1.getElement())) {
					Log.debug("Eto knopka");
				}
				
				Widget clickedWidget;
				for (int i=0; i<panel.getWidgetCount(); i++) {
					Log.debug("Searching");
					if (target.equals(panel.getWidget(i).getElement())) {
						Log.debug("Nashli v cikle " + panel.getWidget(i).getClass().toString());
						clickedWidget = panel.getWidget(i);
					}
					
				}
				//panel.
				//event.getNativeEvent().getEventTarget();
				
				//Element target = Element.as(event.getNativeEvent().getEventTarget());

				
				//event.
				//panel.add(registerView);
				
			}
		});

    	CellTable<Contact> table = new CellTable<Contact>();

    	
		panel.add(myButt1);
		wrapper.add(panel);
		initWidget(wrapper);
	}
	
	
	public void addAlbumThumb(AlbumObj albumObj) {
		AlbumThumb albumThumb = new AlbumThumb(albumObj);
		panel.add(albumThumb);
		
	}
	
	
	
	@Override
	public Activity getPresenter() {
		// TODO Auto-generated method stub
		return presenter;
	}
	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub
		Log.debug("event.getSource().getClass().toString(); = " + event.getSource().getClass().toString());
	}
}
