package my.client.albumspage;



import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class AlbumsPlace extends Place {

	
	private String placeName;

    public AlbumsPlace(String token) {
    	System.out.println("AlbumsPlace constructor");
        this.placeName = token;
    }

    public String getPlaceName() {
        return placeName;
    }

    @Prefix("AlbumsPref")
    public static class Tokenizer implements PlaceTokenizer<AlbumsPlace> {
        @Override
        public String getToken(AlbumsPlace place) {
            return place.getPlaceName();
        }

        @Override
        public AlbumsPlace getPlace(String token) {
        	//System.out.println("MyComposite2Place getPlace token = " + token);
      	return new AlbumsPlace(token);

        }
    }
}
