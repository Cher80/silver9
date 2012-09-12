package my.shared;

import java.io.Serializable;
import java.util.ArrayList;

public class ActivitiesObj implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private ArrayList <ActivityObj> activities = new ArrayList<ActivityObj>();
	public ArrayList <ActivityObj> getActivities() {
		return activities;
	}
	public void setActivities(ArrayList <ActivityObj> activities) {
		this.activities = activities;
	};

}
