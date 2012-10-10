package my.client.events;

import my.client.windows.UserHasLoggedEvent;

import com.google.gwt.event.shared.EventHandler;


public interface ModelGoPicEventHandler extends EventHandler {
    void onGoPic(ModelGoPicEvent event);
}
