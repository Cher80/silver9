package my.client.events;

import my.client.windows.UserHasLoggedEventHandler;
import my.shared.CommentObj;
import my.shared.ImgObj;
import my.shared.User;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;


public class ModelGoPicEvent extends GwtEvent<ModelGoPicEventHandler> {

    private final ImgObj imgObj;

    public ModelGoPicEvent(ImgObj _imgObj) {
        super();
        this.imgObj = _imgObj;
    }

    public static final Type<ModelGoPicEventHandler> TYPE = new Type<ModelGoPicEventHandler>();

    @Override
    protected void dispatch(ModelGoPicEventHandler handler) {
        handler.onGoPic(this);
    }

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ModelGoPicEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	public ImgObj getImgObj() {
		return imgObj;
	}


	

	


}

