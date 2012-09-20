package my.client.paginator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import my.client.common.ActivityHasPages;
import my.client.common.ClientFactory;
import my.client.common.ViewHasPages;
import my.client.modelpage.ModelActivity;
import my.client.modelpage.ModelPlace;
import my.client.rpcs.RPCService;
import my.client.rpcs.RPCServiceAsync;
import my.client.rpcs.RPCServiceExeption;
import my.shared.AlbumObj;
import my.shared.CommentObj;
import my.shared.CommentsObj;
import my.shared.ImgObj;
import my.shared.ModelPageObj;
import my.shared.Settings;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

public class Paginator extends Composite {
	private boolean forceClearOnFinish = false;
	private boolean isFirstTimeLoaded=true;
	private final static int ITEMS_PER_PAGE = Settings.ALBUMS_PER_PAGE;
	private final static int CURR_PAGES_SIZE = 8;
	private int totalItems; 
	private int totalPages;
	private FlowPanel panel = new FlowPanel();
	private int firstPage = -1;
	private int lastPage= -1;
	private ArrayList<Integer> currPages;
	private int currPage;
	private ArrayList<Integer> afterCurrentPages = new ArrayList<Integer>();
	private ArrayList<Integer> beforeCurrentPages = new ArrayList<Integer>();
	private ActivityHasPages activity;
	private Map<Integer,Integer> loadPositions= new HashMap<Integer,Integer>();
	private int prevScroll = 0;
	private int curScrollatLoad = 0;
	private ViewHasPages view;
	private boolean isScrollFrezed = false;
	private int maxLoadedOnScrollPage = 0;

	public Paginator(int totalItemss, ActivityHasPages activityy, ViewHasPages vieww) {
		super();
		this.activity = activityy;
		//We count from 0
		this.totalItems = totalItemss - 1;
		this.totalPages = totalItems/ITEMS_PER_PAGE;
		this.view = vieww;

		

		HTML toRender = new HTML("pager");


		//doGetComments(albumObj);
		//Date date = new java.util.Date((long)(imgObj.getImgTimestamp())*1000);




		panel.add(toRender);
		//	 panel.add(postComment);
		//panel.add(showOrigpxButt);





		initWidget(panel);
		
		panel.getElement().getStyle().setProperty("border", "1px solid green");
		panel.getElement().getStyle().setProperty("position","absolute");
		panel.getElement().getStyle().setProperty("left", "30px");
		panel.getElement().getStyle().setProperty("top", "30px");
		
		this.setCurrentPage(0);		
		this.setFirstTimeLoaded(false);

	}


	
	public void removeFromParent() {
		isFirstTimeLoaded = true;
		super.removeFromParent();
		this.view.clearWidget(0);
	}
	//getNex


	public void setCurrentPage(int currPagee) {

		this.currPage = currPagee;
		//int ITEMS_PER_PAGE = 18;
		//int CURR_PAGES_SIZE = 16;
		//int totalItems = 1650; 
		//int totalPages;
		//int firstPage = -1;
		//int lastPage= -1;
		//ArrayList<Integer> currPages = new ArrayList<Integer>();
		//int currPage;
		//totalPages = totalItems/ITEMS_PER_PAGE -1; //49
		System.out.println("totalPages="+totalPages );

		/*
		if (totalPages )
		if ((currPage + CURR_PAGES_SIZE/2 < totalPages)&& (currPage - CURR_PAGES_SIZE/2 > 0)) {
			firstPage = 0;
			lastPage = totalPages;
		}*/


		if (currPage < CURR_PAGES_SIZE/2+1) {
			firstPage = -1;
		}
		else {
			firstPage = 0;
		}

		if (currPage > totalPages - CURR_PAGES_SIZE/2-1) {
			lastPage = -1;
		}
		else {
			lastPage = totalPages;
		}

		beforeCurrentPages = new ArrayList<Integer>();
		int beforeCurrentPagesCount = 0;
		if (currPage>=CURR_PAGES_SIZE/2) {
			beforeCurrentPagesCount = CURR_PAGES_SIZE/2;
			for (int i=0; i<beforeCurrentPagesCount;i++) {
				beforeCurrentPages.add(i, currPage - CURR_PAGES_SIZE/2 + i);
			}
		}
		else {
			beforeCurrentPagesCount = currPage;
			for (int i=0; i<beforeCurrentPagesCount;i++) {
				beforeCurrentPages.add(i, i);
			}
		}



		afterCurrentPages = new ArrayList<Integer>();
		int afterCurrentPagesCount = 0;
		if (currPage<totalPages - CURR_PAGES_SIZE/2) {
			afterCurrentPagesCount = CURR_PAGES_SIZE/2;
			for (int i=0; i<afterCurrentPagesCount;i++) {
				afterCurrentPages.add(i, currPage + i+1);
			}
		}
		else {
			afterCurrentPagesCount = totalPages - currPage;
			for (int i=0; i<afterCurrentPagesCount;i++) {
				afterCurrentPages.add(i, currPage + i + 1);
			}
		}



		/*
		for (int i=0; i<CURR_PAGES_SIZE; i++) {
			currPages.set(i, currPagee - CURR_PAGES_SIZE/2 + i);
		}*/

		System.out.println("firstPage="+firstPage + " lastPage="+lastPage );
		System.out.println("beforeCurrentPages= "+ beforeCurrentPages.toString() );
		System.out.println("afterCurrentPages= "+ afterCurrentPages.toString() );

		renderCurPages();

	}


	public void renderCurPages() {
		panel.clear();
		if (firstPage>=0) {
			PaginatorPage paginatorPage = new PaginatorPage(firstPage,this.activity,this, false);
			panel.add(paginatorPage);
		}

		for (int i=0;i<beforeCurrentPages.size();i++) {
			PaginatorPage paginatorPage = new PaginatorPage(beforeCurrentPages.get(i),this.activity,this,false);
			panel.add(paginatorPage);
		}

		PaginatorPage paginatorPage2 = new PaginatorPage(currPage,this.activity,this, true);
		panel.add(paginatorPage2);

		for (int i=0;i<afterCurrentPages.size();i++) {
			PaginatorPage paginatorPage = new PaginatorPage(afterCurrentPages.get(i),this.activity,this,false);
			panel.add(paginatorPage);
		}

		if (lastPage>=0) {
			PaginatorPage paginatorPage = new PaginatorPage(lastPage,this.activity,this,false);
			panel.add(paginatorPage);
		}
	}


	public void gotoPage(int page, boolean forceClearOnFinish) {
		if (page<=totalPages) {
			if (forceClearOnFinish) {
				loadPositions= new HashMap<Integer,Integer>();
			}
			
			currPage = page;
			maxLoadedOnScrollPage = 0;
			setCurrentPage(currPage);
			Log.debug("Paginator click  pageNo= " + currPage);
			this.forceClearOnFinish = forceClearOnFinish;
			this.setScrollFrezed(true);
			activity.gotoPage(currPage);
			//activity.scrollToTop();
		}
	}

	
	public void onSuccessLoad() {
		
		if (forceClearOnFinish) {
			view.clearWidget(0);
			forceClearOnFinish = false;
		} 
		
		loadPositions.put(currPage, curScrollatLoad - 20);
		Log.debug("loadPositions " + loadPositions.toString());
		this.setScrollFrezed(false);
	}

	public void getNext() {
		
		if (currPage<totalPages && totalPages!=maxLoadedOnScrollPage) {
			Log.debug("paginator getNext");
			//view.freezeScroll();
			this.setScrollFrezed(true);
			currPage = currPage + 1;
			maxLoadedOnScrollPage = currPage;
			Log.debug("Paginator scroll  pageNo= " + currPage);
			setCurrentPage(currPage);
			forceClearOnFinish = false;
			this.setScrollFrezed(true);
			activity.gotoPage(currPage);
		}

	}

	public void onScroll(int maxScroll, int curScroll) {

		
		//Log.debug(wrapperScroll.getVerticalScrollPosition() + " xxxx " + wrapperScroll.getMaximumVerticalScrollPosition());
		//int maxScroll = wrapperScroll.getMaximumVerticalScrollPosition();
		//int curScrol = wrapperScroll.getVerticalScrollPosition();
		
		//Log.debug("maxScroll - curScrol = " + (maxScroll - curScroll));
		if ((maxScroll - curScroll) <150 ) {
			//this.presenter.loadMore();
			Log.debug("loadMore");
			//(ActivityHasPages)presenter
			//ActivityHasPages presenterWithPages = (ActivityHasPages)presenter;
			//presenterWithPages.getPaginator().getNext();
			
			/*
			if (currPage<totalPages) {
				loadPositions.put(currPage+1, curScroll);
				Log.debug("loadPositions " + loadPositions.toString());
			}*/
			this.curScrollatLoad = curScroll;
			this.getNext();
			checkCrossLoadPoints(curScroll);

		}
		else {
			checkCrossLoadPoints(curScroll);
		}
		prevScroll = curScroll;
	}
	
	public void checkCrossLoadPoints(int curScrol) {
		//Log.debug("checkCrossLoadPoints curScrol " + curScrol);
		//Log.debug("checkCrossLoadPoints prevScroll " + prevScroll);
		//Log.debug("checkCrossLoadPoints loadPositions " + loadPositions.toString());
		
		for (Map.Entry<Integer, Integer> entry : loadPositions.entrySet()) {
		    int page = entry.getKey();
		    int srollPoint = entry.getValue();
		    //Log.debug("checkCrossLoadPoints page " + page);
		    //Log.debug("checkCrossLoadPoints srollPoint " + srollPoint);
		    if (srollPoint<prevScroll&&srollPoint>curScrol) {
		    	setCurrentPage(page-1);
		    }
		    if (srollPoint>prevScroll&&srollPoint<curScrol) {
		    	setCurrentPage(page);
		    }
		    // ...
		}


		
	}



	public boolean isFirstTimeLoaded() {
		return isFirstTimeLoaded;
	}



	public void setFirstTimeLoaded(boolean isFirstTimeLoaded) {
		this.isFirstTimeLoaded = isFirstTimeLoaded;
	}



	public boolean isForceClearOnFinish() {
		return forceClearOnFinish;
	}



	public void setForceClearOnFinish(boolean forceClearOnFinish) {
		this.forceClearOnFinish = forceClearOnFinish;
	}



	public boolean isScrollFrezed() {
		return isScrollFrezed;
	}



	public void setScrollFrezed(boolean isScrollFrezed) {
		this.isScrollFrezed = isScrollFrezed;
	}
	


}
