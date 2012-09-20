package my.client.common;

import my.client.paginator.Paginator;

public interface ActivityHasPages {

	public void gotoPage(int page);
	public Paginator getPaginator();
	//public void freezeScrollingEvents();
}
