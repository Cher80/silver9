package my.client.events;

import com.google.gwt.event.shared.EventHandler;


public interface PageTitleEventHandler extends EventHandler {
    void onSetPageTitle(PageTitleEvent event);
}
