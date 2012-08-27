package my.client.modelpage;

import java.util.ArrayList;
import java.util.Arrays;

import my.client.albumspage.AlbumsPlace;
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

public class ModelView extends Composite implements HavePresenter  {
	private Button myButt1 = new Button("ModelView");

	private FlowPanel panel = new FlowPanel();
	private ModelActivity presenter;
	


	

	
	public ModelView(final Activity presenter) {
		this.presenter = (ModelActivity)presenter;
		panel.setSize("300px", "400px");
		panel.getElement().getStyle().setProperty("border", "1px solid green");
		panel.getElement().getStyle().setProperty("cssFloat", "left");
		panel.getElement().getStyle().setProperty("left", "0px");
		panel.getElement().getStyle().setProperty("overflow", "scroll");
		
    	
    	myButt1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
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
