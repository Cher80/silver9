package my.client.windows;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;


public class Notifications extends PopupPanel {

    public Notifications(String text, boolean autoHide, boolean modal) {
       // super(autoHide, modal);
    	 super(false, false);
        this.addStyleName("notifications");
        setWidget(new Label(text));
        
        int left=Window.getClientWidth()-200;
        setPopupPosition(left, 30); 
        show(3000);
    }

    void show(int delayMilliseconds) {
        show();
        Timer t = new Timer() {
            @Override
            public void run() {
            	
            	Notifications.this.setVisible(false);
            	Notifications.this.hide();
            }
        };

        // Schedule the timer to close the popup in 3 seconds.
        t.schedule(delayMilliseconds);
        //Log.debug("Timer started");
    }
}