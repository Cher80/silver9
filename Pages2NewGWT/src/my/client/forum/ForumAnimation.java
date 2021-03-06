package my.client.forum;

import my.client.common.MyFlowPanel;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.dom.client.Style;
public class ForumAnimation extends Animation {

	private Widget widget;
	private MyFlowPanel flowPanel;
	private int startX;
    private int startY;
    private int finalX;
    private int finalY;
    
	 public ForumAnimation(Widget widget, MyFlowPanel flowPanel)
	   {
	        this.widget = widget;
	        this.flowPanel = flowPanel;
	   }
	 
	 public void scrollTo(int x, int y, int milliseconds)
	    {
	        this.finalX = x;
	        this.finalY = y;
	 
	        startX = widget.getElement().getOffsetLeft();
	        startY =widget.getElement().getOffsetTop();
	 
	        run(milliseconds);
	    }
	 
	@Override
	protected void onUpdate(double progress) {
		// TODO Auto-generated method stub
		double positionX = startX + (progress * (this.finalX - startX));
        double positionY = startY + (progress * (this.finalY - startY));
   	 	//System.out.println("onUpdate X = " + positionX);

        //this.widget.getElement().
       // this.widget.getElement().getStyle().setLeft(positionX, Style.Unit.PX);
        //this.widget.getElement().getStyle().setTop(positionY, Style.Unit.PX);
        widget.getElement().getStyle().setProperty("left",positionX + "PX");
	}
	
	@Override
    protected void onComplete()
    {
        super.onComplete();
        flowPanel.doClearance();
        //this.element.getStyle().setLeft(this.finalX, Style.Unit.PX);
        //this.element.getStyle().setTop(this.finalY, Style.Unit.PX);
    }

}
