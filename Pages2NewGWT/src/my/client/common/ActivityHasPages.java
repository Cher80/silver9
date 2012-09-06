package my.client.common;

import my.client.paginator.Paginator;

public interface ActivityHasPages {

	public void gotoPage(int page, boolean forceClearOnFinish);
	public Paginator getPaginator();
	public void scrollToTop();
	//public void freezeScrollingEvents();
}
