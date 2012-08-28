package my.client.modelpage;



import my.client.common.ClientFactory;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class ModelPlace extends Place {

	
	private String placeName;
	private boolean isSameAlbum;
	private ModelActivity curModelActivity;

    public ModelPlace(String token, boolean isSameAlbum, ModelActivity curModelActivity ) {
    	System.out.println("ModelPlace constructor");
        this.placeName = token;
        this.isSameAlbum = isSameAlbum;
        this.setCurModelActivity(curModelActivity);
    }

    public String getPlaceName() {
        return placeName;
    }
    
    public void gotoModel() {
    	
    }

    public boolean isSameAlbum() {
		return isSameAlbum;
	}

	public void setSameAlbum(boolean isSameAlbum) {
		this.isSameAlbum = isSameAlbum;
	}

	public ModelActivity getCurModelActivity() {
		return curModelActivity;
	}

	public void setCurModelActivity(ModelActivity curModelActivity) {
		this.curModelActivity = curModelActivity;
	}

	@Prefix("ModelPref")
    public static class Tokenizer implements PlaceTokenizer<ModelPlace> {
        @Override
        public String getToken(ModelPlace place) {
            return place.getPlaceName();
        }

        @Override
        public ModelPlace getPlace(String token) {
        	//Handle browser back button. Check if it's new page load (direct link) 
        	//and stack = 0: generate new activity
        	if (ClientFactory.getHistoryKeeper().getStackSize()==0) {
        		return new ModelPlace(token, false, null);
        	}
        	else {
        		return new ModelPlace(token, true, (ModelActivity)ClientFactory.getHistoryKeeper().getCurActivity());
        	}
      	
        }
    }
}
