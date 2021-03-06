package my.client.modelpage;

import java.util.ArrayList;
import java.util.Arrays;

import my.client.albumspage.AlbumsPlace;
import my.client.blocks.AlbumThumb;
import my.client.blocks.CommentPost;
import my.client.blocks.CommentsBlock;
import my.client.blocks.ImgThumb;
import my.client.blocks.ImgsBlock;
import my.client.blocks.PhotoLayer;
import my.client.blocks.TagsSetBlock;
import my.client.blocks.design.ModelNameBlock;
import my.client.helpers.HavePresenter;
import my.client.windows.RegisterPopup;
import my.shared.AlbumObj;
import my.shared.CommentsObj;
import my.shared.ImgObj;
import my.shared.ImgsObj;
import my.shared.TagsObj;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class ModelView extends Composite implements HavePresenter  {
	private Button prevButt = new Button("prevButt");
	private Button nextButt = new Button("nextButt");

	//private FlowPanel wrapperTop = new FlowPanel();
	//private FlowPanel panelContent = new FlowPanel();
	//private FlowPanel panelThumbsAndBlocks = new FlowPanel();
	//private FlowPanel panelBlocks = new FlowPanel();
	private ScrollPanel wrapperScroll = new ScrollPanel();
	
	private FlowPanel panel = new FlowPanel();
	private FlowPanel commentsContainer = new FlowPanel();
	
	private ModelActivity presenter;
	private PhotoLayer photoLayer;
	private CommentPost commentPost;
	private CommentsBlock commentsBlock;
	private AlbumObj albumObj;
	private TagsSetBlock tagsSetBlock;

	private FlowPanel modelNameTags = new FlowPanel();
	
	public AlbumObj getAlbumObj() {
		return this.albumObj;
	}
	
	public void setAlbumObj(AlbumObj albumObj) {
		this.albumObj = albumObj;
	}
	
	public void makeLayotCalculation() {
		wrapperScroll.getElement().getStyle().setProperty("width", Window.getClientWidth() + "px");
		wrapperScroll.getElement().getStyle().setProperty("height", Window.getClientHeight()-36 + "px");
		//photoLayer.getElement().getStyle().setProperty("width", Window.getClientWidth() + "px");
		//photoLayer.getElement().getStyle().setProperty("height", Window.getClientHeight()-36 - 50 + "px");
		//int multiplier = (Window.getClientWidth()-135 - 54)/293; 
		//panel.getElement().getStyle().setProperty("width", 293*multiplier + "px");
		
	}
	 
	public void makePhotoLayotCalculation() {
		//wrapperScroll.getElement().getStyle().setProperty("width", Window.getClientWidth() + "px");
		//wrapperScroll.getElement().getStyle().setProperty("height", Window.getClientHeight()-36 + "px");
		photoLayer.setLayout(Window.getClientWidth(), Window.getClientHeight()-36 - 50);
		//photoLayer.setLayoutHeight(Window.getClientHeight());
		//photoLayer.getElement().getStyle().setProperty("height", Window.getClientHeight()-36 - 50 + "px");
		//int multiplier = (Window.getClientWidth()-135 - 54)/293; 
		//panel.getElement().getStyle().setProperty("width", 293*multiplier + "px");
		
	}
	
	public ModelView(final Activity presenter) {
		this.presenter = (ModelActivity)presenter;
		
		
		Window.addResizeHandler(new ResizeHandler() {




			@Override
			public void onResize(ResizeEvent event) {
				// TODO Auto-generated method stub
				makeLayotCalculation();
				makePhotoLayotCalculation();
			}
		});
		
		//this.albumObj = this.presenter.getModelPageObjCur().getAlbumObj();
		//panel.setSize("700px", "500px");
		//panel.getElement().getStyle().setProperty("onclick", "void(0)");
		//panel.getElement().getStyle().setProperty("cssFloat", "left");
		//panel.getElement().getStyle().setProperty("left", "0px");
		//panel.getElement().getStyle().setProperty("overflow", "scroll");
		
    	
		prevButt.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//((ModelActivity)presenter).doPrevImg();	
			}
		});
    	
		nextButt.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//((ModelActivity)presenter).doNextImg();
			}
		});
    	
    	
		
		//renderCommentPost(albumObj);
		//renderCommentsBlock(albumObj);
		//panel.add(prevButt);
		//panel.add(nextButt);
		
		
		
		wrapperScroll.addStyleName("ScrolableWrapperSroll");
		makeLayotCalculation();
		wrapperScroll.add(panel);
		initWidget(wrapperScroll);
	}
	
	
	
	public void renderTags(TagsObj tagsObj) {
		ArrayList<String> types = new ArrayList<String>();
		types.add("LIKE");
		types.add("DISLIKE");
		tagsSetBlock = new TagsSetBlock(tagsObj, types, this.albumObj);
		modelNameTags.add(tagsSetBlock); 
	}
	
	public void renderPhotoLayer(ImgsObj imgsObj, String coverid) {
		photoLayer = new PhotoLayer(imgsObj,coverid,(ModelActivity)this.getPresenter());
		makePhotoLayotCalculation();
		panel.add(photoLayer);
		//renderNameAndTags();
	}

	public void setPhotoLayerTo(ImgObj _imgObj) {
		photoLayer.doRenderPhoto(_imgObj);
		//photoLayer = new PhotoLayer(imgsObj,coverid,(ModelActivity)this.getPresenter());
		//makePhotoLayotCalculation();
		//panel.add(photoLayer);
		//renderNameAndTags();
	}
	
	public void renderName(AlbumObj albumObj, ImgsObj imgsObj) {
		modelNameTags.addStyleName("modelNameTags");
		modelNameTags.addStyleName("back_pattern_dark");
		
		panel.add(modelNameTags);
		ModelNameBlock modelNameBlock = new ModelNameBlock(albumObj,imgsObj);
		modelNameTags.add(modelNameBlock);
	}
	 
	
	
	public void renderCommentPost(AlbumObj albumObj) {
		
		commentsContainer.addStyleName("commentsContainer");
		panel.add(commentsContainer);
		commentPost = new CommentPost(albumObj);
		commentsContainer.add(commentPost);
	}
	
	public void renderCommentsBlock(CommentsObj commentsObj) {
		CommentsBlock commentsBlock = new CommentsBlock(commentsObj);
		commentsContainer.add(commentsBlock);
	}
	
	public void renderPhotos(ImgsObj imgsObj) {
		
		ImgsBlock imgsBlock  = new ImgsBlock (albumObj, imgsObj);
		panel.add(imgsBlock);
		

	}
	
	
	@Override
	public Activity getPresenter() {
		// TODO Auto-generated method stub
		return presenter;
	}



	public CommentsBlock getCommentsBlock() {
		return commentsBlock;
	}



	public void setCommentsBlock(CommentsBlock commentsBlock) {
		this.commentsBlock = commentsBlock;
	}
	
}
