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
import my.shared.ImgObj;
import my.shared.ImgsObj;
import my.shared.ModelPageObj;
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
	protected ModelPageObj modelPageObjCur;
	
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
				//ArrayList<AlbumObj> albumObjs = (ArrayList<AlbumObj>)result;
				//this.modelPageObjCur = modelPageObj;
				ModelPageObj modelPageObj = (ModelPageObj)result;
				ModelActivity.this.modelPageObjCur = modelPageObj;
				
				renderPhotos(modelPageObj.getImages());
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
	
	public void renderPhotos(ImgsObj imgsObj) {
		ModelView modelView = (ModelView) this.getView();
		modelView.renderPhotoLayer(imgsObj,coverid);
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

}
