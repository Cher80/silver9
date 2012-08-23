package my.client.windows;

import my.shared.User;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;


public class UserHasLoggedEvent extends GwtEvent<UserHasLoggedEventHandler> {

    private final User user;

    public UserHasLoggedEvent(User user) {
        super();
        this.user = user;
    }

    public static final Type<UserHasLoggedEventHandler> TYPE = new Type<UserHasLoggedEventHandler>();

    @Override
    protected void dispatch(UserHasLoggedEventHandler handler) {
        handler.onUserLogged(this);
    }

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<UserHasLoggedEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}


	public User getUser(){
        return user;
    }


}

