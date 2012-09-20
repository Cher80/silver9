package my.client.common;

import my.client.paginator.Paginator;

import com.google.gwt.event.dom.client.ScrollEvent;

public interface ViewHasPages {
	
	//public void freezeScroll();
	public void setPaginator(Paginator paginator);
	public Paginator getPaginator();
	public void clearWidget(int fromPos);
}
