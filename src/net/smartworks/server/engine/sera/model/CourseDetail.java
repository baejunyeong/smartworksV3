/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2012. 4. 4.
 * =========================================================
 * Copyright (c) 2012 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.server.engine.sera.model;

import net.smartworks.util.LocalDate;

public class CourseDetail {
	private String courseId;
	private String object;
	private String categories;
	private String keywords;
	private int duration;
	private LocalDate start;
	private LocalDate end;
	private int maxMentees;
	private boolean autoApproval;
	private boolean payable;
	private int fee;
	private String teamId;
	private int targetPoint;
	private int achievedPoint;

	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public String getCategories() {
		return categories;
	}
	public void setCategories(String categories) {
		this.categories = categories;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
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
	public int getMaxMentees() {
		return maxMentees;
	}
	public void setMaxMentees(int maxMentees) {
		this.maxMentees = maxMentees;
	}
	public boolean isAutoApproval() {
		return autoApproval;
	}
	public void setAutoApproval(boolean autoApproval) {
		this.autoApproval = autoApproval;
	}
	public boolean isPayable() {
		return payable;
	}
	public void setPayable(boolean payable) {
		this.payable = payable;
	}
	public int getFee() {
		return fee;
	}
	public void setFee(int fee) {
		this.fee = fee;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public int getTargetPoint() {
		return targetPoint;
	}
	public void setTargetPoint(int targetPoint) {
		this.targetPoint = targetPoint;
	}
	public int getAchievedPoint() {
		return achievedPoint;
	}
	public void setAchievedPoint(int achievedPoint) {
		this.achievedPoint = achievedPoint;
	}
	public static CourseDetail pickupCourseDetail(String courseId, CourseDetail[] courseDetails) {
		if (courseDetails == null || courseDetails.length == 0)
			return null;
		for (CourseDetail courseDetail : courseDetails) {
			if (courseDetail.getCourseId().equalsIgnoreCase(courseId)) {
				return courseDetail;
			}
		}
		return null;
	}
}
