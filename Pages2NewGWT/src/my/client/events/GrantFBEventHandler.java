package my.client.events;

import my.client.windows.UserHasLoggedEvent;

import com.google.gwt.event.shared.EventHandler;


public interface GrantFBEventHandler extends EventHandler {
    void onGrantFB(GrantFBEvent event);
}
