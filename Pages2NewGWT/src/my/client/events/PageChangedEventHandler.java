package my.client.events;

import my.client.windows.UserHasLoggedEvent;

import com.google.gwt.event.shared.EventHandler;


public interface PageChangedEventHandler extends EventHandler {
    void onPageChanged(PageChangedEvent event);
}
