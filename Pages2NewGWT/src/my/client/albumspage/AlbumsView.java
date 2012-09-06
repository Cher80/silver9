package my.client.albumspage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import my.client.blocks.AlbumThumb;
import my.client.common.ActivityHasPages;
import my.client.common.ViewHasPages;
import my.client.helpers.HavePresenter;
import my.client.paginator.Paginator;
import my.client.windows.RegisterPopup;
import my.shared.AlbumObj;
import my.shared.AlbumsObj;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class AlbumsView extends Composite implements HavePresenter,ClickHandler, ScrollHandler, ViewHasPages {
	private Button myButt1 = new Button("AlbumsView");
	
	private FlowPanel wrapperTop = new FlowPanel();
	private ScrollPanel wrapperScroll = new ScrollPanel();
	private FlowPanel panel = new FlowPanel();
	private Activity presenter;
	private Paginator paginator;
	private boolean isScrollFrezed = false;
	
	
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

	  void scrollToTop() {
		  wrapperScroll.scrollToTop(); 
	  }
	
	  void clearWidget(int fromPos) {
			int wcount = panel.getWidgetCount();
			Log.debug("clearWidget getWidgetCount=" + wcount);
			
			/*
			for (int i=fromPos; i<wcount; i++) {
				//Log.debug("User Area panel.getWidgetCount() " + panel.getWidgetCount());
				//Log.debug("User Area remove widget " + i);
				//panel.remove(i);
				panel.getWidget(i).removeFromParent();
				Log.debug("clearWidget removeFromParent=" + i);
				
				//panel.getWidget(i).r
			}*/
			panel.clear();
		}
	  

	
	public AlbumsView(Activity presenter) {
		this.presenter = presenter;
		wrapperTop.setSize("700px", "500px");
		wrapperTop.getElement().getStyle().setProperty("border", "1px solid green");
		wrapperTop.getElement().getStyle().setProperty("cssFloat", "left");
		wrapperTop.getElement().getStyle().setProperty("left", "0px");
		wrapperScroll.setSize("700px", "500px");
		wrapperScroll.getElement().getStyle().setProperty("border", "1px solid green");
		wrapperScroll.getElement().getStyle().setProperty("cssFloat", "left");
		wrapperScroll.getElement().getStyle().setProperty("left", "0px");
		//wrapper.getElement().getStyle().setProperty("overflow", "scroll");
		
    	
    	myButt1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RegisterPopup registerView = new RegisterPopup();
				registerView.center(); 
				registerView.show();
				//event.
				//panel.add(registerView);
				
			}
		});
    	
    	
    	
    	/*
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
				
				
			}
		});
    	*/

    	
		//panel.add(myButt1);
    	this.wrapperScroll.addScrollHandler(this);  	
    	wrapperTop.add(wrapperScroll);
    	wrapperScroll.add(panel);
		initWidget(wrapperTop);
	}
	
	
	
	public void populateAlbumsView(AlbumsObj albumsObj) {
		
		 HTML html = new HTML(
				 "<h3>Albums page. Total count=" + albumsObj.getTotalCount() + "</h3>"
	  , true);
		
		 panel.add(html);
		
		for (int i=0; i<albumsObj.getAlbums().size(); i++) {
			AlbumObj albumObj = albumsObj.getAlbums().get(i);
			Log.debug("albumObj.getAlbname()" + albumObj.getAlbname());
			Log.debug("albumObj.getAlbpage()" + albumObj.getAlbpage());
			//AlbumsView albumsView = (AlbumsView)this.getView();
			this.addAlbumThumb(albumObj);
		}
		isScrollFrezed = false;
	}
	
	
	public void addAlbumThumb(AlbumObj albumObj) {
		AlbumThumb albumThumb = new AlbumThumb(albumObj);
		panel.add(albumThumb);
		
	}
	
	@Override
	public void setPaginator(Paginator paginator) {
		wrapperTop.add(paginator);
		this.paginator =paginator;
		//paginator.setStyleName("position:absolute");
		
		//paginator.getElement().getStyle().setProperty("overflow", "scroll");
	}
	
	@Override
	public void onScroll(ScrollEvent event) {
		// TODO Auto-generated method stub
		//System.out.println("scroll" + panel.getMaximumHorizontalScrollPosition() + panel.getHorizontalScrollPosition());
		
		if (!isScrollFrezed) {
			int maxScroll = wrapperScroll.getMaximumVerticalScrollPosition();
			int curScrol = wrapperScroll.getVerticalScrollPosition();
			paginator.onScroll(maxScroll,curScrol);
		}
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

	@Override
	public void freezeScroll() {
		// TODO Auto-generated method stub
		isScrollFrezed = true;
	}

	@Override
	public Paginator getPaginator() {
		// TODO Auto-generated method stub
		return paginator;
	}
}
