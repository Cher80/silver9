package my.client.events;

import my.client.windows.UserHasLoggedEvent;

import com.google.gwt.event.shared.EventHandler;


public interface NewCommentEventHandler extends EventHandler {
    void onNewComment(NewCommentEvent event);
}
