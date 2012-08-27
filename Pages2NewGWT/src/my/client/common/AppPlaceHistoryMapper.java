package my.client.common;


import my.client.forum.ForumPlace;
import my.client.albumspage.AlbumsPlace;
import my.client.modelpage.ModelPlace;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({ForumPlace.Tokenizer.class, ModelPlace.Tokenizer.class, AlbumsPlace.Tokenizer.class})
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {

}
