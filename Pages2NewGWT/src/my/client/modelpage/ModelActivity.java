package my.client.modelpage;

import java.util.ArrayList;

import my.client.albumspage.AlbumsPlace;
import my.client.common.ClientFactory;
import my.client.common.MyActivity;
import my.client.forum.ForumPlace;
import my.client.rpcs.RPCService;
import my.client.rpcs.RPCServiceAsync;
import my.client.rpcs.RPCServiceExeption;
import my.shared.AlbumObj;
import my.shared.CommentsObj;
import my.shared.ImgObj;
import my.shared.ImgsObj;
import my.shared.ModelPageObj;
import my.shared.TagObj;
import my.shared.TagsObj;
import my.shared.User;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;

public class ModelActivity extends MyActivity{

	private String albid;
	private String coverid;
	private ModelPageObj modelPageObjCur;
	
	public ModelActivity(ModelPlace place) {
		super();
		super.setPlace(place);

	}


	public void getModel() {
		
		RPCServiceAsync communicatorSvc = GWT.create(RPCService.class);

		// Set up the callback object.
		AsyncCallback callback = new AsyncCallback() {

			public void onFailure(Throwable caught) {

				if (caught instanceof RPCServiceExeption) {
					Log.debug("exeption!!" + ((RPCServiceExeption)caught).getErrorCode());
				}
			}

			@Override
			public void onSuccess(Object result) {
				Log.debug("ModelActivity onSuccess ");
				//this.populate
				//ArrayList<AlbumObj> albumObjs = (ArrayList<AlbumObj>)result;
				//this.modelPageObjCur = modelPageObj;
				ModelPageObj modelPageObj = (ModelPageObj)result;
				ModelActivity.this.setModelPageObjCur(modelPageObj);
				
				ModelView modelView = (ModelView)ModelActivity.this.getView();
				modelView.setAlbumObj(modelPageObj.getAlbumObj());
				//ModelActivity.this.setModelPageObjCur
				
				//ModelActivity.this.getView().set
				renderPhotoLayer(modelPageObj.getImages(),coverid);
				renderName(modelPageObj.getAlbumObj(),modelPageObj.getImages());
				renderPhotos(modelPageObj.getImages());
				renderCommentPost(modelPageObj.getAlbumObj());
				renderCommentsBlock(modelPageObj.getComments());
				//////////////////////
				
				/*
				TagObj tagObj1 = new TagObj();
				tagObj1.setTagReadableName("like");
				tagObj1.setTagType("LIKsE");
				tagObj1.setTagTotalPluses(23);
				tagObj1.setTagGroup("LIKES");
				
				TagObj tagObj2 = new TagObj();
				tagObj2.setTagReadableName("dislike");
				tagObj2.setTagType("DISLIKE");
				tagObj2.setTagTotalPluses(5);
				tagObj2.setTagGroup("LIKES");
				
				TagObj tagObj3 = new TagObj();
				tagObj3.setTagReadableName("blond");
				tagObj3.setTagType("BLOND");
				tagObj3.setTagTotalPluses(23);
				tagObj3.setTagGroup("HAIRS");
				
				TagObj tagObj4 = new TagObj();
				tagObj4.setTagReadableName("dark");
				tagObj4.setTagType("DARK");
				tagObj4.setTagTotalPluses(5);
				tagObj4.setTagGroup("HAIRS");
				
				TagObj tagObj5 = new TagObj();
				tagObj5.setTagReadableName("red");
				tagObj5.setTagType("RED");
				tagObj5.setTagTotalPluses(5);
				tagObj5.setTagGroup("HAIRS");
				
				TagsObj tagsObj = new TagsObj();
				tagsObj.getTagsObj().add(tagObj1);
				tagsObj.getTagsObj().add(tagObj2);
				tagsObj.getTagsObj().add(tagObj3);
				tagsObj.getTagsObj().add(tagObj4);
				tagsObj.getTagsObj().add(tagObj5);
				renderTags(tagsObj);
				*/
				
				renderTags(modelPageObj.getTagsObj());
				Log.debug("modelPageObj.getAlbumObj().getAlbname() " + modelPageObj.getAlbumObj().getAlbname());	
			}
		};

		Log.debug("Make the call");
		int offest = 0;
		int limit = 20;
		
		communicatorSvc.getModelPage( albid , coverid, callback);
	}

	public void doPrevImg(ImgObj curImgObj) { 
		String coverObjID = curImgObj.getImgID();
		String params = "albid=" + albid + "&coverid=" + coverObjID;
		ClientFactory.getPlaceController().goTo(new ModelPlace(params,true, this));
	}
	
	public void doNextImg(ImgObj curImgObj) { 
		String coverObjID = curImgObj.getImgID();
		String params = "albid=" + albid + "&coverid=" + coverObjID;
		ClientFactory.getPlaceController().goTo(new ModelPlace(params,true, this));
	}
	
	
	public void renderPhotoLayer(ImgsObj imgsObj, String coverid ) {
		ModelView modelView = (ModelView) this.getView();
		modelView.renderPhotoLayer(imgsObj,coverid);
	}
	
	public void renderName(AlbumObj albumObj, ImgsObj imgsObj) {
		ModelView modelView = (ModelView) this.getView();
		modelView.renderName(albumObj, imgsObj);
	}
	
	
	public void renderTags(TagsObj tagsObj) {
		ModelView modelView = (ModelView) this.getView();
		modelView.renderTags(tagsObj);
	}
	
	public void renderCommentPost(AlbumObj albumObj) {
		ModelView modelView = (ModelView) this.getView();
		//modelView.renderPhotoLayer(imgsObj,coverid);
		modelView.renderCommentPost(albumObj);
	} 
	
	public void renderCommentsBlock(CommentsObj commentsObj) {
		ModelView modelView = (ModelView) this.getView();
		modelView.renderCommentsBlock(commentsObj);
		//modelView.renderPhotos(imgsObj);
	} 
	
	public void renderPhotos(ImgsObj imgsObj) {
		ModelView modelView = (ModelView) this.getView();
		//
		modelView.renderPhotos(imgsObj);
	} 
	 
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {

		ModelView modelView = new ModelView(this);
		
		//String params = plac;
		String paramsLine = ((ModelPlace)this.getPlace()).getPlaceName();
		/* delimiter */
		albid = this.getParams(paramsLine,"albid");
		coverid = this.getParams(paramsLine,"coverid");
		

		this.setView(modelView);
		panel.setWidget(modelView.asWidget());
		getModel();

	}


	public ModelPageObj getModelPageObjCur() {
		return modelPageObjCur;
	}


	public void setModelPageObjCur(ModelPageObj modelPageObjCur) {
		this.modelPageObjCur = modelPageObjCur;
	}

}
