package my.client.albumspage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import my.client.blocks.ActivityBlock;
import my.client.blocks.AlbumThumb;
import my.client.blocks.AlbumViewHeader;
import my.client.blocks.BestBlock;
import my.client.blocks.TagAlbumsBlock;
import my.client.common.ActivityHasPages;
import my.client.common.ViewHasPages;
import my.client.helpers.HavePresenter;
import my.client.paginator.Paginator;
import my.client.windows.RegisterPopup;
import my.shared.AlbumObj;
import my.shared.AlbumsObj;
import my.shared.AlbumsPageObj;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
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
	private FlowPanel panelContent = new FlowPanel();
	private FlowPanel panelThumbsAndBlocks = new FlowPanel();
	private FlowPanel panelBlocks = new FlowPanel();
	private ScrollPanel wrapperScroll = new ScrollPanel();
	private FlowPanel panel = new FlowPanel();
	private Activity presenter;
	private Paginator paginator;
	private AlbumViewHeader albumViewHeader= new AlbumViewHeader();
	//private boolean isScrollFrezed = false;
	private TagAlbumsBlock tagAlbumsBlock;




	void scrollToTop() {
		wrapperScroll.scrollToTop(); 
	}

	public void clearWidget(int fromPos) { 
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
		scrollToTop();
	}

	public void makeLayotCalculation() {
		wrapperTop.getElement().getStyle().setProperty("width", Window.getClientWidth() + "px");
		wrapperTop.getElement().getStyle().setProperty("height", Window.getClientHeight()-36 + "px");
		int multiplier = (Window.getClientWidth()-135 - 54)/293; 
		panel.getElement().getStyle().setProperty("width", 293*multiplier + "px");
		
	}


	public AlbumsView(Activity presenter) {
		//Window.addResizeHandler(handler)

		Window.addResizeHandler(new ResizeHandler() {




			@Override
			public void onResize(ResizeEvent event) {
				// TODO Auto-generated method stub
				makeLayotCalculation();
			}
		});

		this.presenter = presenter;
		wrapperTop.addStyleName("ScrolableWrapperTop");
		//Window.getClientWidth();
		makeLayotCalculation();

		//wrapperTop.setSize("700px", "500px");
		//wrapperTop.getElement().getStyle().setProperty("border", "1px solid green");
		//wrapperTop.getElement().getStyle().setProperty("cssFloat", "left");
		//wrapperTop.getElement().getStyle().setProperty("left", "0px");
		//wrapperTop.getElement().getStyle().setProperty("position", "relative");
		//wrapperScroll.setSize("700px", "500px");
		wrapperScroll.addStyleName("ScrolableWrapperSroll");

		//wrapperScroll.getElement().getStyle().setProperty("width", Window.getClientWidth()/2 + "px");
		//wrapperScroll.getElement().getStyle().setProperty("border", "1px solid green");
		//wrapperScroll.getElement().getStyle().setProperty("cssFloat", "left");
		//wrapperScroll.getElement().getStyle().setProperty("left", "0px");
		//wrapper.getElement().getStyle().setProperty("overflow", "scroll");

		TagAlbumsBlock tagAlbumsBlock = new TagAlbumsBlock();
		panelContent.add(tagAlbumsBlock);

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
		wrapperScroll.add(panelContent);
		
		panel.addStyleName("thumbsPanel");
		panelThumbsAndBlocks.add(albumViewHeader);
		panelThumbsAndBlocks.add(panel);
		panelThumbsAndBlocks.add(panelBlocks);
		panelThumbsAndBlocks.addStyleName("panelThumbsAndBlocks");
		panelBlocks.addStyleName("panelBlocks");
		
		panelContent.add(panelThumbsAndBlocks);
		initWidget(wrapperTop);
	}



	public void populateAlbumsView(AlbumsObj albumsObj) {

		HTML html = new HTML(
				"<h3>Albums page. Total count=" + albumsObj.getTotalCount() + "</h3>"
				, true);

		//panel.add(html);

		for (int i=0; i<albumsObj.getAlbums().size(); i++) {
			AlbumObj albumObj = albumsObj.getAlbums().get(i);
			Log.debug("albumObj.getAlbname()" + albumObj.getAlbname());
			Log.debug("albumObj.getAlbpage()" + albumObj.getAlbpage());
			//AlbumsView albumsView = (AlbumsView)this.getView();
			this.addAlbumThumb(albumObj);
		}
		//paginator.s
		//isScrollFrezed = false;
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

		if (!paginator.isScrollFrezed()) {
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

	/*
	@Override
	public void freezeScroll() {
		// TODO Auto-generated method stub
		isScrollFrezed = true;
	}*/

	@Override
	public Paginator getPaginator() {
		// TODO Auto-generated method stub
		return paginator;
	}


	public void renderBlocks(AlbumsPageObj albumsPageObj) {
		ActivityBlock activityBlock = new ActivityBlock(albumsPageObj.getActivitiesObj());
		panelBlocks.add(activityBlock);

		BestBlock bestBlock = new BestBlock(albumsPageObj.getBestAlbumsObj());
		panelBlocks.add(bestBlock);
	}
}
