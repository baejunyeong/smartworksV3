package net.smartworks.model.community.info;

import net.smartworks.model.BaseObject;
import net.smartworks.model.community.Community;
import net.smartworks.model.community.Department;
import net.smartworks.model.community.Group;
import net.smartworks.model.community.User;
import net.smartworks.util.SmartUtil;

public class CommunityInfo extends BaseObject {

	private String smallPictureName;

	public String getSmallPictureName() {
		return smallPictureName;
	}
	public void setSmallPictureName(String smallPictureName) {
		this.smallPictureName = smallPictureName;
	}
	public String getMidPictureName() {
		return this.smallPictureName;
	}
	public String getMinPictureName() {
		return this.smallPictureName;
	}

	public String getMidPicture() {
		if(this.getMidPictureName() == null || this.getMidPictureName().equals("")) {
			if(this.getClass().equals(UserInfo.class))
				return Community.NO_PICTURE_PATH + User.NO_USER_PICTURE + "_mid.jpg";
			else if(this.getClass().equals(DepartmentInfo.class))
				return Community.NO_PICTURE_PATH + Department.DEFAULT_DEPART_PICTURE + "_mid.gif";
			else if(this.getClass().equals(GroupInfo.class))
				return Community.NO_PICTURE_PATH + Group.DEFAULT_GROUP_PICTURE + "_mid.gif";
		}
		return getPath() + this.getMidPictureName();
	}
	public String getMinPicture() {
		if(this.getMinPictureName() == null || this.getMidPictureName().equals("")) {
			if(this.getClass().equals(UserInfo.class))
				return Community.NO_PICTURE_PATH + User.NO_USER_PICTURE + "_min.jpg";
			else if(this.getClass().equals(DepartmentInfo.class))
				return Community.NO_PICTURE_PATH + Department.DEFAULT_DEPART_PICTURE + "_min.gif";
			else if(this.getClass().equals(GroupInfo.class))
				return Community.NO_PICTURE_PATH + Group.DEFAULT_GROUP_PICTURE + "_min.gif";
		}
		return getPath() + this.getMinPictureName();
	}
	public String getPath(){
		if(SmartUtil.getCurrentUser() == null)
			return null;
		return Community.PICTURE_PATH + SmartUtil.getCurrentUser().getCompanyId() + "/" + Community.PROFILES_DIR + "/";
	}
	public CommunityInfo(){
		super();
	}
	public CommunityInfo(String id, String name){
		super(id, name);
	}

}