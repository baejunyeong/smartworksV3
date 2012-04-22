/*	
 * $Id$
 * created by    : hsshin
 * creation-date : 2012. 4. 23.
 * =========================================================
 * Copyright (c) 2012 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.server.engine.sera.model;

import java.util.Date;

import net.smartworks.server.engine.common.model.MisObject;

public class CourseTeam extends MisObject {

	private static final long serialVersionUID = 1L;

	private String courseId;
	private String description;
	private int accessPolicy;
	private int memberSize;
	private Date startDate;
	private Date endDate;
	private CourseTeamUser[] courseTeamUsers;

	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getAccessPolicy() {
		return accessPolicy;
	}
	public int getMemberSize() {
		return memberSize;
	}
	public void setMemberSize(int memberSize) {
		this.memberSize = memberSize;
	}
	public void setAccessPolicy(int accessPolicy) {
		this.accessPolicy = accessPolicy;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public CourseTeamUser[] getCourseTeamUsers() {
		return courseTeamUsers;
	}
	public void setCourseTeamUsers(CourseTeamUser[] courseTeamUsers) {
		this.courseTeamUsers = courseTeamUsers;
	}
	public void addGroupMember(CourseTeamUser courseTeamUser) {
		if (courseTeamUser == null)
			return;
		this.setCourseTeamUsers(CourseTeamUser.add(this.getCourseTeamUsers(), courseTeamUser));
	}
	public void removeGroupMember(CourseTeamUser courseTeamUser) {
		if (courseTeamUser == null)
			return;
		this.setCourseTeamUsers(CourseTeamUser.remove(this.getCourseTeamUsers(), courseTeamUser));
	}
	public void resetGroupMembers() {
		this.courseTeamUsers = null;
	}

}