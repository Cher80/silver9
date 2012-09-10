package my.shared;

import java.io.Serializable;

public class TagObj implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7109087128787403906L;
	private String tagType = "";
	private String tagReadableName = "";
	private int tagTotalPluses = 0;
	private boolean tagIsWinner = false;
	private boolean allowVoteToUser = true;
	private boolean currentUserVoted = false;
	private String tagGroup = "";
	
	public String getTagType() {
		return tagType;
	}
	public void setTagType(String tagType) {
		this.tagType = tagType.trim();
	}
	public boolean isCurrentUserVoted() {
		return currentUserVoted;
	}
	public void setCurrentUserVoted(boolean currentUserVoted) {
		this.currentUserVoted = currentUserVoted;
	}
	public boolean isAllowVoteToUser() {
		return allowVoteToUser;
	}
	public void setAllowVoteToUser(boolean allowVoteToUser) {
		this.allowVoteToUser = allowVoteToUser;
	}
	public boolean isTagIsWinner() {
		return tagIsWinner;
	}
	public void setTagIsWinner(boolean tagIsWinner) {
		this.tagIsWinner = tagIsWinner;
	}
	public int getTagTotalPluses() {
		return tagTotalPluses;
	}
	public void setTagTotalPluses(int tagTotalPluses) {
		this.tagTotalPluses = tagTotalPluses;
	}
	public String getTagReadableName() {
		return tagReadableName;
	}
	public void setTagReadableName(String tagReadableName) {
		this.tagReadableName = tagReadableName;
	}
	public String getTagGroup() {
		return tagGroup;
	}
	public void setTagGroup(String tagGroup) {
		this.tagGroup = tagGroup.trim();
	}
	

}
