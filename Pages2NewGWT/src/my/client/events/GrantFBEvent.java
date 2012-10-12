package my.client.events;

import my.client.windows.UserHasLoggedEventHandler;
import my.shared.CommentObj;
import my.shared.ImgObj;
import my.shared.User;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;


public class GrantFBEvent extends GwtEvent<GrantFBEventHandler> {


    public GrantFBEvent() {
        super();
    }

    public static final Type<GrantFBEventHandler> TYPE = new Type<GrantFBEventHandler>();

    @Override
    protected void dispatch(GrantFBEventHandler handler) {
        handler.onGrantFB(this);
    }

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<GrantFBEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}


	

	


}

