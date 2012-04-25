package net.smartworks.model.instance.info;

import net.smartworks.model.community.info.UserInfo;
import net.smartworks.model.instance.Instance;
import net.smartworks.model.work.info.WorkInfo;
import net.smartworks.util.LocalDate;

public class CommentInstanceInfo extends InstanceInfo {

	private int commentType=-1;
	private InstanceInfo workInstance;
	private UserInfo commentor;
	private String comment;

	public int getCommentType() {
		return commentType;
	}

	public void setCommentType(int commentType) {
		this.commentType = commentType;
	}

	public InstanceInfo getWorkInstance() {
		return workInstance;
	}

	public void setWorkInstance(InstanceInfo workInstance) {
		this.workInstance = workInstance;
	}

	public UserInfo getCommentor() {
		return commentor;
	}

	public void setCommentor(UserInfo commentor) {
		this.commentor = commentor;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public CommentInstanceInfo() {
		super();
		super.setType(Instance.TYPE_COMMENT);
	}

	public CommentInstanceInfo(String id, int commentType, String comment,
			UserInfo commentor, LocalDate lastModifiedDate) {
		super(id, "", Instance.TYPE_COMMENT, commentor, commentor,
				lastModifiedDate);
		this.commentType = commentType;
		this.commentor = commentor;
		this.comment = comment;
	}
}
