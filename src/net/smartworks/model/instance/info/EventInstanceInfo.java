package net.smartworks.model.instance.info;

import net.smartworks.model.community.info.CommunityInfo;
import net.smartworks.model.community.info.UserInfo;
import net.smartworks.model.instance.Instance;
import net.smartworks.util.LocalDate;

public class EventInstanceInfo extends WorkInstanceInfo {

	private int 			views;
	private String			briefContent;
	private String			content;
	private String			place;
	private CommunityInfo[]	relatedUsers;
	private LocalDate		start;
	private LocalDate		end;

	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public String getBriefContent() {
		return briefContent;
	}
	public void setBriefContent(String briefContent) {
		this.briefContent = briefContent;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public CommunityInfo[] getRelatedUsers() {
		return relatedUsers;
	}
	public void setRelatedUsers(CommunityInfo[] relatedUsers) {
		this.relatedUsers = relatedUsers;
	}
	public LocalDate getStart() {
		return start;
	}
	public void setStart(LocalDate start) {
		this.start = start;
	}
	public LocalDate getEnd() {
		return end;
	}
	public void setEnd(LocalDate end) {
		this.end = end;
	}
	public EventInstanceInfo(){
		super();
		super.setType(Instance.TYPE_EVENT);
	}

	public EventInstanceInfo(String id, String subject, UserInfo owner, LocalDate lastModifiedDate){
			super(id, subject, owner, owner, lastModifiedDate);
			super.setType(Instance.TYPE_EVENT);
	}
}