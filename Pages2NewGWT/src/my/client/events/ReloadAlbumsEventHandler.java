package my.client.events;

import com.google.gwt.event.shared.EventHandler;


public interface ReloadAlbumsEventHandler extends EventHandler {
    void onDoReload(ReloadAlbumsEvent event);
}
