package my.client.events;

import my.client.windows.UserHasLoggedEventHandler;
import my.shared.CommentObj;
import my.shared.User;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;


public class NewCommentEvent extends GwtEvent<NewCommentEventHandler> {

    private final CommentObj commentObj;

    public NewCommentEvent(CommentObj commentObj) {
        super();
        this.commentObj = commentObj;
    }

    public static final Type<NewCommentEventHandler> TYPE = new Type<NewCommentEventHandler>();

    @Override
    protected void dispatch(NewCommentEventHandler handler) {
        handler.onNewComment(this);
    }

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<NewCommentEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}


	

	public CommentObj getCommentObj() {
		return commentObj;
	}


}

