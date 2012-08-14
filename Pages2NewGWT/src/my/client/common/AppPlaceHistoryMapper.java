package my.client.common;


import my.client.forum.ForumPlace;
import my.client.albumspage.AlbumsPlace;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({ForumPlace.Tokenizer.class,AlbumsPlace.Tokenizer.class})
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {

}
