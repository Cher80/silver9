package my.client.windows;

import com.google.gwt.event.shared.EventHandler;


public interface UserHasLoggedEventHandler extends EventHandler {
    void onUserLogged(UserHasLoggedEvent event);
}
