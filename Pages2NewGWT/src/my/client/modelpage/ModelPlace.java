package my.client.modelpage;



import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class ModelPlace extends Place {

	
	private String placeName;

    public ModelPlace(String token) {
    	System.out.println("ModelPlace constructor");
        this.placeName = token;
    }

    public String getPlaceName() {
        return placeName;
    }
    
    public void gotoModel() {
    	
    }

    @Prefix("ModelPref")
    public static class Tokenizer implements PlaceTokenizer<ModelPlace> {
        @Override
        public String getToken(ModelPlace place) {
            return place.getPlaceName();
        }

        @Override
        public ModelPlace getPlace(String token) {
        	//System.out.println("MyComposite2Place getPlace token = " + token);
      	return new ModelPlace(token);

        }
    }
}
