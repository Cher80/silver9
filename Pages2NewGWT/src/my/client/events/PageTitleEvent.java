package my.client.events;

import my.client.windows.UserHasLoggedEventHandler;
import my.shared.CommentObj;
import my.shared.User;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;


public class PageTitleEvent extends GwtEvent<PageTitleEventHandler> {

    private final String pageHeader;

    public PageTitleEvent(String pageHeader) {
        super();
        this.pageHeader = pageHeader;
    }

    public static final Type<PageTitleEventHandler> TYPE = new Type<PageTitleEventHandler>();

    @Override
    protected void dispatch(PageTitleEventHandler handler) {
        handler.onSetPageTitle(this);
    }

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PageTitleEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}



	public String getPageHeader() {
		return pageHeader;
	}


}

