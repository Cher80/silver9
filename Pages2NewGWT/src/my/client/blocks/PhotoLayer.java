package my.client.blocks;

import java.util.Date;

import my.client.albumspage.AlbumsPlace;
import my.client.common.ClientFactory;
import my.client.common.GoogleAnalytics;
import my.client.events.ReloadAlbumsEvent;
import my.client.helpers.HavePlace;
import my.client.modelpage.ModelActivity;
import my.client.modelpage.ModelPlace;
import my.shared.AlbumObj;
import my.shared.ImgObj;
import my.shared.ImgsObj;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class PhotoLayer extends Composite {


	//private FlowPanel preloader = new FlowPanel();
	private FlowPanel panel = new FlowPanel();
	private FlowPanel imagePanel = new FlowPanel();
	private ImgsObj imgsObj;
	private ImgObj curImgObj;
	private ImgObj curImgObjPrev;
	private ImgObj curImgObjNext;
	private ModelActivity curActivity;
	private HTML html = null;
	private Button prevButt = new Button("prevButt");
	private Button nextButt = new Button("nextButt");

	private Image imageCurOld;
	private Image imageCur;
	private Image imagePrev;
	private Image imageNext;
	private boolean nowLoading = false;
	private int widthLayout;
	private int heightLayout;
	
	private FocusPanel leftPanel = new FocusPanel();
	private FocusPanel rightPanel = new FocusPanel();
	
	private FocusPanel bigLeftPanel = new FocusPanel();
	private FocusPanel bigRightPanel = new FocusPanel();
	
	private FocusPanel photoClosePanel = new FocusPanel();
	private FocusPanel photoOriginalPanel = new FocusPanel();
	private FocusPanel photoTagPanel = new FocusPanel();
	
	private Label imgPosition = new Label("allo");


	public void setLayout (int _widthLayout, int _heightLayout) {
		heightLayout = _heightLayout;
		widthLayout  =_widthLayout;
		//max-height:400px; max-width:400px
		panel.getElement().getStyle().setProperty("width", widthLayout + "px");
		panel.getElement().getStyle().setProperty("height", heightLayout + "px");

		leftPanel.getElement().getStyle().setProperty("left","0px");
		leftPanel.getElement().getStyle().setProperty("top",heightLayout/2 - 185/2 +  "px");
		rightPanel.getElement().getStyle().setProperty("left",widthLayout - 67 - 15 + "px");
		rightPanel.getElement().getStyle().setProperty("top",heightLayout/2 - 185/2 +  "px");

		bigLeftPanel.getElement().getStyle().setProperty("left","0px");
		bigLeftPanel.getElement().getStyle().setProperty("top","0px");
		bigLeftPanel.getElement().getStyle().setProperty("width","200px");
		bigLeftPanel.getElement().getStyle().setProperty("height",heightLayout + "px");

		bigRightPanel.getElement().getStyle().setProperty("left",widthLayout - 200 + "px");
		bigRightPanel.getElement().getStyle().setProperty("top","0px");
		bigRightPanel.getElement().getStyle().setProperty("width","200px");
		bigRightPanel.getElement().getStyle().setProperty("height",heightLayout + "px");

		
		photoTagPanel.getElement().getStyle().setProperty("left",widthLayout - 67 - 15 + "px");
		photoTagPanel.getElement().getStyle().setProperty("top",heightLayout - 60  + "px");
		
		photoClosePanel.getElement().getStyle().setProperty("left",widthLayout - 67 - 15 + "px");
		photoClosePanel.getElement().getStyle().setProperty("top","0px");
		
		photoOriginalPanel.getElement().getStyle().setProperty("left",widthLayout - 67 - 15 + "px");
		photoOriginalPanel.getElement().getStyle().setProperty("top", heightLayout/2 - 185/2 - 67 +  "px");
		
		//preloader.getElement().getStyle().setProperty("left",widthLayout/2 - 12 + "px");
		//preloader.getElement().getStyle().setProperty("top", heightLayout/2 - 12 +  "px"); 
		
		
		//leftPanel
		//panel.getElement().getStyle().setProperty("background",  "url(\"/extranewgwt/getphoto?photoid=" + curImgObj.getImgGridfs_id_m() +  "\") no-repeat center");

		doRenderPhoto();
	}


	public void getPrevNext() {
		this.curImgObjPrev = imgsObj.getPrevImg(curImgObj.getImgID());
		this.curImgObjNext = imgsObj.getNextImg(curImgObj.getImgID());
	}

	public PhotoLayer(ImgsObj imgsObjj, String curID, ModelActivity curActivityP) {
		super();
		this.imgsObj = imgsObjj;
		this.curActivity = curActivityP;
		panel.addStyleName("PhotoLayer");
		panel.addStyleName("preloader_anim");
		
		//panel.addStyleName("back_pattern_line");
		imagePanel.addStyleName("imagePanel");
		//preloader.addStyleName("preloader");
		//preloader.addStyleName("preloader_anim");
		
		
		bigLeftPanel.addStyleName("bigLeftPanel");
		bigRightPanel.addStyleName("bigRightPanel");
		panel.add(bigLeftPanel);
		panel.add(bigRightPanel);
		
		
		
		leftPanel.addStyleName("leftPanel");
		rightPanel.addStyleName("rightPanel");
		panel.add(leftPanel);
		panel.add(rightPanel);
		
		
		photoClosePanel.addStyleName("photoClosePanel");
		photoOriginalPanel.addStyleName("photoOriginalPanel");
		photoTagPanel.addStyleName("photoTagPanel");
		panel.add(photoClosePanel);
		
		
		panel.add(photoTagPanel);
		
		
		imgPosition.addStyleName("layerImgPosition");
		imgPosition.addStyleName("text_11_grey");
		
		panel.add(imgPosition);
		
		
		//panel.add(prevButt);
		//panel.add(nextButt);
		//panel.add(preloader);
		panel.add(imagePanel);
		//	Date date = new java.util.Date((long)(imgObj.getImgTimestamp())*1000);

		if (curID==null||curID.equals("")||curID.equals("null")) {
			curImgObj = new ImgObj();
		}
		else {
			curImgObj = imgsObj.getImgByID(curID);
			getPrevNext();
		}
		
		//if (curImgObj!=null&&curImgObj.getImgIshasorig()) {
		if (curImgObj!=null) {
			panel.add(photoOriginalPanel);
			photoOriginalPanel.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					//Window.open("http://stackoverflow.com", "_blank", null);
					//if (curImgObj.getImgIshasorig()) {		
					GoogleAnalytics.trackEvent("Pinbelle", "PhotoLayer_ShowOriginal_Clicked", "default");
					Window.open("http://" +Window.Location.getHost() + "/extranewgwt/getphoto?photoid=" +  curImgObj.getImgGridfs_id_0(), "_blank", "");
//					Log.debug("showOrigpxButt.addClickHandler = " + Window.Location.getHost());
				}
			});
		}


		
		
		
		photoClosePanel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				
				if (ClientFactory.getHistoryKeeper().getStackSize() > 1 ) {
					Activity curActivity = ClientFactory.getHistoryKeeper().getPrevious();
					Place oldPlace = ((HavePlace) curActivity).getPlace();
					ClientFactory.getPlaceController().goTo(oldPlace);
				}
				else {
					ClientFactory.getHistoryKeeper().doEmptyStack();
					ClientFactory.getPlaceController().goTo(new AlbumsPlace(""));
					
				}
				GoogleAnalytics.trackEvent("Pinbelle", "PhotoLayer_Close_Clicked", "default");
				/*
				curImgObj = imgsObj.getPrevImg(curImgObj.getImgID());
				getPrevNext();
				((ModelActivity)curActivity).doPrevImg(curImgObj);
				doRenderPhoto();
				*/
			}
		});


		
		bigLeftPanel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				curImgObj = imgsObj.getPrevImg(curImgObj.getImgID());
				getPrevNext();
				((ModelActivity)curActivity).doPrevImg(curImgObj);
				doRenderPhoto();
				
			}
		});

		bigRightPanel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				curImgObj = imgsObj.getNextImg(curImgObj.getImgID());
				getPrevNext();
				((ModelActivity)curActivity).doNextImg(curImgObj);
				doRenderPhoto();
			}
		});

		leftPanel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				curImgObj = imgsObj.getPrevImg(curImgObj.getImgID());
				getPrevNext();
				((ModelActivity)curActivity).doPrevImg(curImgObj);
				doRenderPhoto();
			}
		});

		rightPanel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				curImgObj = imgsObj.getNextImg(curImgObj.getImgID());
				getPrevNext();
				((ModelActivity)curActivity).doNextImg(curImgObj);
				doRenderPhoto();
			}
		});




		initWidget(panel);
	}

	
	public void doRenderPhoto(ImgObj _imgObj) {
		curImgObj = _imgObj;
		doRenderPhoto();
	}
	
	public void doRenderPhoto() {
		if (html != null) {
			panel.remove(html);
		}

		int curPosDisplay = imgsObj.getImgPos(curImgObj.getImgID());
		
		imgPosition.setText(curPosDisplay + 1  + "/" + imgsObj.getImages().size());
		//if ()
		//imagePanel.getElement().getStyle().setProperty("background",  "url(\"/extranewgwt/getphoto?photoid=" + curImgObj.getImgGridfs_id_m() +  "\") no-repeat center");
		// panel.add(imagePanel);
		/*
		 *  html = new HTML(
				 "<h3>Photo layer</h3>" +
				 "<img style=\"max-height:400px; max-width:400px\" src=\"/extranewgwt/getphoto?photoid=" + curImgObj.getImgGridfs_id_m() + "\"/>"
 , true);
		 * 
		 */
		// html = new HTML(
		//		 "<img style=\"max-height:400px; max-width:400px\" src=\"/extranewgwt/getphoto?photoid=" + curImgObj.getImgGridfs_id_m() + "\"/>"
		//, true);
		//panel.add(html);
		if (!nowLoading) {
			
			
			
			nowLoading = true;
			if (imageCur!=null) {
				imageCur.setVisible(false);
				imageCurOld = imageCur;
			}
			imageCur = new Image();
			imageCur.setVisible(false);
			
			imageCur.addLoadHandler(new LoadHandler() {

				@Override
				public void onLoad(LoadEvent event) {
					if (imageCurOld!=null) {
						imageCurOld.removeFromParent();
					}
					//imageCur.getElement().setPropertyString("width", "auto");
					//imageCur.getElement().setPropertyString("height", "auto");
					//imageCur.getElement().setPropertyString("width", "33px");
					//imageCur.getElement().setPropertyString("height", "33px");
					imageCur.getElement().setAttribute("width", "auto");
					imageCur.getElement().setAttribute("height", "auto");
					imageCur.setVisible(true);
					nowLoading = false;
					imageNext = new Image();
					imageNext.setUrl("/extranewgwt/getphoto?photoid=" + curImgObjNext.getImgGridfs_id_m());
					imagePrev = new Image();
					imagePrev.setUrl("/extranewgwt/getphoto?photoid=" + curImgObjPrev.getImgGridfs_id_m());
					
					GoogleAnalytics.trackEvent("Pinbelle", "PhotoLayer_Image_Shown", "default");
				}

			});

			imageCur.setUrl("/extranewgwt/getphoto?photoid=" + curImgObj.getImgGridfs_id_m());

			imagePanel.add(imageCur);
		}
		/*
		String imgStrCur = "";
		String imgStrPrev = "";
		String imgStrNext = "";


				 imgStrCur = "<img src=\"/extranewgwt/getphoto?photoid=" + curImgObj.getImgGridfs_id_m() + "\"/>";
				if (imgsObj.getImages().size()>1) {
				 imgStrPrev = "<img style=\"display:none;\" src=\"/extranewgwt/getphoto?photoid=" + curImgObjPrev.getImgGridfs_id_m() + "\"/>";
				}
				if (imgsObj.getImages().size()>2) {
				 imgStrNext = "<img style=\"display:none;\" src=\"/extranewgwt/getphoto?photoid=" + curImgObjNext.getImgGridfs_id_m() + "\"/>";
				}
				String imgStr = imgStrCur + imgStrPrev	+ imgStrNext;	


						;
		imagePanel.getElement().setInnerHTML(imgStr);
		 */
		// imagePanel.add(html);
	}
	


}
