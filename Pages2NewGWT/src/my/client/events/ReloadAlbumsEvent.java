package my.client.events;

import com.google.gwt.event.shared.GwtEvent;


public class ReloadAlbumsEvent extends GwtEvent<ReloadAlbumsEventHandler> {

    private final String tagType;
    private final int status;
    

    public ReloadAlbumsEvent(String tagType, int status) {
        super();
        this.tagType = tagType;
        this.status = status;
    }

    public static final Type<ReloadAlbumsEventHandler> TYPE = new Type<ReloadAlbumsEventHandler>();

    @Override
    protected void dispatch(ReloadAlbumsEventHandler handler) {
        handler.onDoReload(this); 
    }

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ReloadAlbumsEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	public String getTagType() {
		return tagType;
	}

	public int getStatus() {
		return status;
	}


	


}

