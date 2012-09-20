package my.client.events;


import com.google.gwt.event.shared.GwtEvent;



public class PageChangedEvent extends GwtEvent<PageChangedEventHandler> {

    private final int stackSize;

    public PageChangedEvent(int stackSizee) {
        super();
        this.stackSize = stackSizee;
    }

    public static final Type<PageChangedEventHandler> TYPE = new Type<PageChangedEventHandler>();

    @Override
    protected void dispatch(PageChangedEventHandler handler) {
        handler.onPageChanged(this);
    }

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PageChangedEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}



	public int getStackSize() {
		return stackSize;
	}


}

