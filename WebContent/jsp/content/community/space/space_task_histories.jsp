<%@page import="net.smartworks.util.SmartTest"%>
<%@page import="net.smartworks.model.instance.info.InstanceInfo"%>
<%@page import="net.smartworks.model.community.info.GroupInfo"%>
<%@page import="net.smartworks.model.community.info.DepartmentInfo"%>
<%@page import="net.smartworks.model.work.info.SmartWorkInfo"%>
<%@page import="net.smartworks.model.community.info.UserInfo"%>
<%@page import="net.smartworks.model.instance.info.MemoInstanceInfo"%>
<%@page import="net.smartworks.model.instance.info.ImageInstanceInfo"%>
<%@page import="net.smartworks.model.instance.info.FileInstanceInfo"%>
<%@page import="net.smartworks.model.instance.info.EventInstanceInfo"%>
<%@page import="net.smartworks.model.instance.info.BoardInstanceInfo"%>
<%@page import="net.smartworks.model.community.info.WorkSpaceInfo"%>
<%@page import="net.smartworks.model.instance.Instance"%>
<%@page import="net.smartworks.model.work.SocialWork"%>
<%@page import="net.smartworks.model.work.Work"%>
<%@page import="net.smartworks.model.work.SmartWork"%>
<%@page import="net.smartworks.model.instance.info.WorkInstanceInfo"%>
<%@page import="net.smartworks.model.instance.info.TaskInstanceInfo"%>
<%@page import="net.smartworks.model.instance.TaskInstance"%>
<%@page import="net.smartworks.model.calendar.CompanyEvent"%>
<%@page import="net.smartworks.util.SmartMessage"%>
<%@page import="net.smartworks.model.calendar.WorkHourPolicy"%>
<%@page import="net.smartworks.model.community.WorkSpace"%>
<%@page import="java.util.Calendar"%>
<%@page import="net.smartworks.model.calendar.CompanyCalendar"%>
<%@page import="net.smartworks.model.community.User"%>
<%@page import="net.smartworks.util.SmartUtil"%>
<%@page import="net.smartworks.util.LocalDate"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="net.smartworks.service.ISmartWorks"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	ISmartWorks smartWorks = (ISmartWorks) request.getAttribute("smartWorks");
	User cUser = SmartUtil.getCurrentUser();

	TaskInstanceInfo[] tasksHistories = (TaskInstanceInfo[])session.getAttribute("taskHistories");
	
%>
<!--  다국어 지원을 위해, 로케일 및 다국어 resource bundle 을 설정 한다. -->
<fmt:setLocale value="<%=cUser.getLocale() %>" scope="request" />
<fmt:setBundle basename="resource.smartworksMessage" scope="request" />

	<%
	if(!SmartUtil.isBlankObject(tasksHistories) && tasksHistories.length > 0){
		for(int i=0; i<tasksHistories.length; i++){
			TaskInstanceInfo taskInstance = tasksHistories[i];
			if(taskInstance.getType()<0){
				String lastDateStr = (i>0) ? (new LocalDate(tasksHistories[i-1].getLastModifiedDate().getTime())).toLocalDateString2() : ""; 
	%>
				<li class="t_nowork"><a href="" class="js_space_more_history" lastDate="<%=lastDateStr%>"><fmt:message key="common.message.more_work_task"></fmt:message></a></li>
	<%
				break;
			}
			InstanceInfo workInstance = taskInstance.getWorkInstance();
			SmartWorkInfo work = (SmartWorkInfo)workInstance.getWork();
			UserInfo owner = workInstance.getOwner();
			String userDetailInfo = SmartUtil.getUserDetailInfo(owner);
			WorkSpaceInfo workSpace = workInstance.getWorkSpace();
			if(SmartUtil.isBlankObject(workSpace)) workSpace = workInstance.getOwner();
			boolean onWorkSpace = false;
			if(workSpace.getClass().equals(DepartmentInfo.class)){
				onWorkSpace = true;
			}else if(workSpace.getClass().equals(GroupInfo.class)){
				onWorkSpace = true;
			}
			BoardInstanceInfo board=null;
			EventInstanceInfo event=null;
			FileInstanceInfo file=null;
			ImageInstanceInfo image=null;
			MemoInstanceInfo memo=null;
	%>
			<li class="sub_instance_list" instanceId="<%=workInstance.getId() %>" taskInstId="<%=taskInstance.getId()%>">
				<%
				switch(workInstance.getType()){
				
				// 태스크가 게시판인 경우...									
				case Instance.TYPE_BOARD:
					board = (BoardInstanceInfo)workInstance;
				%>
					<div class="det_title">
						<div class="noti_pic"><a class="js_pop_user_info" href="<%=owner.getSpaceController() %>?cid=<%=owner.getSpaceContextId()%>" userId="<%=owner.getId()%>" profile="<%=owner.getOrgPicture()%>" userDetail="<%=userDetailInfo%>"><img src="<%=owner.getMidPicture()%>" class="profile_size_m"></a></div>
						<div class="noti_in_m">
							<a href="<%=owner.getSpaceController() %>?cid=<%=owner.getSpaceContextId()%>"><span class="t_name"><%=owner.getLongName()%></span></a>
							<%if(onWorkSpace){ %><span class="arr">▶</span><a href="<%=workSpace.getSpaceController()%>?cid=<%=workSpace.getSpaceContextId()%>"><span class="<%=workSpace.getIconClass()%>"><%=workSpace.getName() %></span></a><%} %>
							<a href="<%=board.getController() %>?cid=<%=board.getContextId() %>&wid=<%=workSpace.getId() %>&workId=<%=work.getId() %>">
								<div>
									<span class="<%=work.getIconClass()%>"></span>
									<div><%=board.getSubject() %></div>
								</div>
								<div><%=board.getBriefContent()%></div>
							</a>
							<%if(!SmartUtil.isBlankObject(board.getFiles())){ %><div><%=SmartUtil.getFilesDetailInfo(board.getFiles()) %></div><%} %>
							<!-- 인스턴스 마지막수정일자 -->
							<div class="vAlignBottom hAlignRight"><span class="t_date"><%=workInstance.getLastModifiedDate().toLocalString()%></span></div>
							<!-- 인스턴스 마지막수정일자 //-->
						</div>
					</div>
				<%
					break;
				// 태스크가 이벤트인 경우...									
				case Instance.TYPE_EVENT:
					event = (EventInstanceInfo)workInstance;
				%>
					<div class="det_title">
						<div class="noti_pic"><a class="js_pop_user_info" href="<%=owner.getSpaceController() %>?cid=<%=owner.getSpaceContextId()%>" userId="<%=owner.getId()%>" profile="<%=owner.getOrgPicture()%>" userDetail="<%=userDetailInfo%>"><img src="<%=owner.getMidPicture()%>" class="profile_size_m"></a></div>
						<div class="noti_in_m">
							<a href="<%=owner.getSpaceController() %>?cid=<%=owner.getSpaceContextId()%>"><span class="t_name"><%=owner.getLongName()%></span></a>
							<%if(onWorkSpace){ %><span class="arr">▶</span><a href="<%=workSpace.getSpaceController()%>?cid=<%=workSpace.getSpaceContextId()%>"><span class="<%=workSpace.getIconClass()%>"><%=workSpace.getName() %></span></a><%} %>
							<a href="<%=event.getController() %>?cid=<%=event.getContextId() %>&wid=<%=workSpace.getId() %>&workId=<%=work.getId() %>">
								<div>
									<span class="<%=work.getIconClass()%>"></span>
									<div><%=event.getSubject() %></div>
								</div>
								<div><fmt:message key="common.upload.event.start_date"/> : <%=event.getStart().toLocalString() %> 
									<%if(!SmartUtil.isBlankObject(event.getEnd())) {%><fmt:message key="common.upload.event.end_date"/> : <%=event.getEnd().toLocalString() %> <%} %></div>
							</a>
							<!-- 인스턴스 마지막수정일자 -->
							<div class="vAlignBottom hAlignRight"><span class="t_date"><%=workInstance.getLastModifiedDate().toLocalString()%></span></div>
							<!-- 인스턴스 마지막수정일자 //-->
						</div>
					</div>
				<%
					break;
				// 태스크가 파일인 경우...									
				case Instance.TYPE_FILE:
					file = (FileInstanceInfo)workInstance;
				%>
					<div class="det_title">
						<div class="noti_pic"><a class="js_pop_user_info" href="<%=owner.getSpaceController() %>?cid=<%=owner.getSpaceContextId()%>" userId="<%=owner.getId()%>" profile="<%=owner.getOrgPicture()%>" userDetail="<%=userDetailInfo%>"><img src="<%=owner.getMidPicture()%>" class="profile_size_m"></a></div>
						<div class="noti_in_m">
							<a href="<%=owner.getSpaceController() %>?cid=<%=owner.getSpaceContextId()%>"><span class="t_name"><%=owner.getLongName()%></span></a>
							<%if(onWorkSpace){ %><span class="arr">▶</span><a href="<%=workSpace.getSpaceController()%>?cid=<%=workSpace.getSpaceContextId()%>"><span class="<%=workSpace.getIconClass()%>"><%=workSpace.getName() %></span></a><%} %>
							<%if(!SmartUtil.isBlankObject(file.getFiles())){ %><div><%=SmartUtil.getFilesDetailInfo(file.getFiles()) %></div><%} %>
							<%if(!SmartUtil.isBlankObject(file.getContent())){ %><div><%=file.getContent() %></div><%} %>
							<!-- 인스턴스 마지막수정일자 -->
							<div class="vAlignBottom hAlignRight"><span class="t_date"><%=workInstance.getLastModifiedDate().toLocalString()%></span></div>
							<!-- 인스턴스 마지막수정일자 //-->
						</div>
					</div>
				<%
					break;
				// 태스크가 사진인 경우...									
				case Instance.TYPE_IMAGE:
					image = (ImageInstanceInfo)workInstance;
				%>
					<div class="det_title">
						<div class="noti_pic"><a class="js_pop_user_info" href="<%=owner.getSpaceController() %>?cid=<%=owner.getSpaceContextId()%>" userId="<%=owner.getId()%>" profile="<%=owner.getOrgPicture()%>" userDetail="<%=userDetailInfo%>"><img src="<%=owner.getMidPicture()%>" class="profile_size_m"></a></div>
						<div class="noti_in_m">
							<a href="<%=owner.getSpaceController() %>?cid=<%=owner.getSpaceContextId()%>"><span class="t_name"><%=owner.getLongName()%></span></a>
							<%if(onWorkSpace){ %><span class="arr">▶</span><a href="<%=workSpace.getSpaceController()%>?cid=<%=workSpace.getSpaceContextId()%>"><span class="<%=workSpace.getIconClass()%>"><%=workSpace.getName() %></span></a><%} %>
							<div><a href="" class=""><img src="<%=image.getImgSource()%>" style="min-height:20px;width:200px;"></a></div>
							<%if(!SmartUtil.isBlankObject(image.getContent())){ %><div><%=image.getContent() %></div><%} %>
							<!-- 인스턴스 마지막수정일자 -->
							<div class="vAlignBottom hAlignRight"><span class="t_date"><%=workInstance.getLastModifiedDate().toLocalString()%></span></div>
							<!-- 인스턴스 마지막수정일자 //-->
						</div>
					</div>
				<%
					break;
				// 태스크가 메모인 경우...									
				case Instance.TYPE_MEMO:
					memo = (MemoInstanceInfo)workInstance;
				%>
					<div class="det_title">
						<div class="noti_pic"><a class="js_pop_user_info" href="<%=owner.getSpaceController() %>?cid=<%=owner.getSpaceContextId()%>" userId="<%=owner.getId()%>" profile="<%=owner.getOrgPicture()%>" userDetail="<%=userDetailInfo%>"><img src="<%=owner.getMidPicture()%>" class="profile_size_m"></a></div>
						<div class="noti_in_m">
							<a href="<%=owner.getSpaceController() %>?cid=<%=owner.getSpaceContextId()%>"><span class="t_name"><%=owner.getLongName()%></span></a>
							<%if(onWorkSpace){ %><span class="arr">▶</span><a href="<%=workSpace.getSpaceController()%>?cid=<%=workSpace.getSpaceContextId()%>"><span class="<%=workSpace.getIconClass()%>"><%=workSpace.getName() %></span></a><%} %>
							<a href="<%=memo.getController() %>?cid=<%=memo.getContextId() %>&wid=<%=workSpace.getId() %>&workId=<%=work.getId() %>">
								<div>
									<span class="<%=work.getIconClass()%>"></span>
									<div><%=memo.getContent() %></div>
								</div>
							</a>
							<!-- 인스턴스 마지막수정일자 -->
							<div class="vAlignBottom hAlignRight"><span class="t_date"><%=workInstance.getLastModifiedDate().toLocalString()%></span></div>
							<!-- 인스턴스 마지막수정일자 //-->
						</div>
					</div>
				<%
					break;
				default:
				%>
					<div class="det_title">
						<div class="noti_pic"><a class="js_pop_user_info" href="<%=owner.getSpaceController() %>?cid=<%=owner.getSpaceContextId()%>" userId="<%=owner.getId()%>" profile="<%=owner.getOrgPicture()%>" userDetail="<%=userDetailInfo%>"><img src="<%=owner.getMidPicture()%>" class="profile_size_m"></a></div>
						<div class="noti_in_m">
							<a href="<%=owner.getSpaceController() %>?cid=<%=owner.getSpaceContextId()%>"><span class="t_name"><%=owner.getLongName()%></span></a>
							<%if(onWorkSpace){ %><span class="arr">▶</span><a href="<%=workSpace.getSpaceController()%>?cid=<%=workSpace.getSpaceContextId()%>"><span class="<%=workSpace.getIconClass()%>"><%=workSpace.getName() %></span></a><%} %>
						<%
						String runningTaskName = taskInstance.getName();
						switch(taskInstance.getTaskType()){
						case TaskInstance.TYPE_APPROVAL_TASK_ASSIGNED:
						%>
							<fmt:message key="content.sentence.stask_forwarded">
								<fmt:param>
									<a href="<%=taskInstance.getController()%>?cid=<%=taskInstance.getContextId()%>&wid=<%=workInstance.getWorkSpace().getId()%>">
										<span class="t_woname"><%=runningTaskName%></span> 
									</a>
								</fmt:param>
							</fmt:message>
						<%
							break;
						case TaskInstance.TYPE_APPROVAL_TASK_FORWARDED:
						%>
							<fmt:message key="content.sentence.stask_forwarded">
								<fmt:param>
									<a href="<%=taskInstance.getController()%>?cid=<%=taskInstance.getContextId()%>&wid=<%=workInstance.getWorkSpace().getId()%>">
										<span class="t_woname"><%=runningTaskName%></span> 
									</a>
								</fmt:param>
							</fmt:message>
						<%
							break;
						case TaskInstance.TYPE_INFORMATION_TASK_ASSIGNED:
						%>
							<fmt:message key="content.sentence.itask_assigned">
								<fmt:param>
									<a href='<%=taskInstance.getController()%>?cid=<%=taskInstance.getContextId()%>&wid=<%=workInstance.getWorkSpace().getId()%>'>
										<span class='t_woname'><%=runningTaskName%></span> 
									</a>
								</fmt:param>
							</fmt:message>
						<%
							break;
						case TaskInstance.TYPE_INFORMATION_TASK_CREATED:
							break;
						case TaskInstance.TYPE_INFORMATION_TASK_DELETED:
							break;
						case TaskInstance.TYPE_INFORMATION_TASK_FORWARDED:
						%>
							<fmt:message key="content.sentence.itask_forwarded">
								<fmt:param>
									<a href="<%=taskInstance.getController()%>?cid=<%=taskInstance.getContextId()%>&wid=<%=workInstance.getWorkSpace().getId()%>">
										<span class="t_woname"><%=runningTaskName%></span>
									</a>
								</fmt:param>
							</fmt:message>
						<%
							break;
						case TaskInstance.TYPE_INFORMATION_TASK_UDATED:
							break;
						case TaskInstance.TYPE_PROCESS_TASK_ASSIGNED:
						%>
							<fmt:message key="content.sentence.ptask_assigned">
								<fmt:param>
									<a href="<%=taskInstance.getController()%>?cid=<%=taskInstance.getContextId()%>&wid=<%=workInstance.getWorkSpace().getId()%>">
										<span class="t_woname"><%=runningTaskName%></span> 
									</a>
								</fmt:param>
							</fmt:message>
							<%
							break;
						case TaskInstance.TYPE_PROCESS_TASK_FORWARDED:
						%>
							<fmt:message key="content.sentence.ptask_forwarded">
								<fmt:param>
									<a href="<%=taskInstance.getController()%>?cid=<%=taskInstance.getContextId()%>&wid=<%=workInstance.getWorkSpace().getId()%>">
										<span class="t_woname"><%=runningTaskName%></span> 
									</a>
								</fmt:param>
							</fmt:message>
						<%
							break;
						case TaskInstance.TYPE_SCHEDULE_TASK_ASSIGNED:
						%>
							<fmt:message key="content.sentence.stask_assigned">
								<fmt:param>
									<a href="<%=taskInstance.getController()%>?cid=<%=taskInstance.getContextId()%>&wid=<%=workInstance.getWorkSpace().getId()%>">
										<span class="t_woname"><%=runningTaskName%></span> 
									</a>
								</fmt:param>
							</fmt:message>
						<%
							break;
						case TaskInstance.TYPE_SCHEDULE_TASK_FORWARDED:
						%>
							<fmt:message key="content.sentence.stask_forwarded">
								<fmt:param>
									<a href="<%=taskInstance.getController()%>?cid=<%=taskInstance.getContextId()%>&wid=<%=workInstance.getWorkSpace().getId()%>">
										<span class="t_woname"><%=runningTaskName%></span> 
									</a>
								</fmt:param>
							</fmt:message>
						<%
							break;
						}
						%>
						</div>			
						<div>
							<a href="<%=work.getController()%>?cid=<%=work.getContextId()%>&wid=<%=cUser.getId()%>">
								<span class="<%=work.getIconClass()%>"></span>
								<span class="t_date"><%=work.getFullpathName()%></span>
							</a>
							<a href="<%=((WorkInstanceInfo)workInstance).getController()%>?cid=<%=((WorkInstanceInfo)workInstance).getContextId()%>&wid=<%=workInstance.getWorkSpace().getId()%>&workId=<%=work.getId()%>">
								<span class="t_bold"><%=workInstance.getSubject()%></span> 
							</a>
						</div>
						<!-- 인스턴스 마지막수정일자 -->
						<div class="vAlignBottom hAlignRight"><span class="t_date"><%=workInstance.getLastModifiedDate().toLocalString()%></span></div>
						<!-- 인스턴스 마지막수정일자 //-->
					</div>
				<%
				}
				
				%>
				<!-- 댓글 -->
			   <div class="replay_point posit_default"></div>
			   <div class="replay_section">  
			        <div class="list_replay">
			        	<%
			        	WorkInstanceInfo instance = (WorkInstanceInfo)workInstance;
/// TEST PURPOSE			        	
/// TEST PURPOSE			        	
			        	instance.setSubInstanceCount(21);
			        	InstanceInfo[] instances = new InstanceInfo[]{SmartTest.getWorkInstanceInfo1(), SmartTest.getWorkInstanceInfo2(), SmartTest.getWorkInstanceInfo3()};
			        	instance.setSubInstances(instances);
/// TEST PURPOSE			        	
/// TEST PURPOSE			        	
			        	if(instance.getSubInstanceCount()>0){
			        	%>
				            <ul>
					            <li><img class="repl_tinfo"><a href=""><strong><%=instance.getSubInstanceCount() %></strong>개의 댓글 모두 보기</a></li>
								<%
								if (instance.getSubInstances() != null) {
									for (InstanceInfo subInstance : instance.getSubInstances()) {
										UserInfo commentor = subInstance.getOwner();
								%>
										<li>
											<div class="noti_pic">
												<img src="<%=commentor.getMinPicture()%>" align="bottom" />
											</div>
											<div class="noti_in">
												<span class="t_name"><%=commentor.getLongName()%></span><span
													class="t_date"><%=subInstance.getLastModifiedDate().toLocalString()%></span>
												<div><%=subInstance.getSubject()%></div>
											</div>
										</li>
								<%
									}
								}
								%>
							</ul>
						<%
						}
						%>
			        </div>
			        
			        <div class="replay_input commentBox js_return_on_comment">
						<textarea class="up_textarea" name="txtaCommentContent" placeholder="<fmt:message key='work.message.leave_comment'/>"></textarea>
			        </div>
			    
			    </div>
			    <!-- 댓글 //-->
			</li>
	<%		
		}
	}
	%>
