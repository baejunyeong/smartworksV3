<%@page import="net.smartworks.model.work.info.SmartFormInfo"%>
<%@page import="net.smartworks.model.community.info.UserInfo"%>
<%@page import="net.smartworks.model.work.info.SmartTaskInfo"%>
<%@page import="net.smartworks.model.work.SmartForm"%>
<%@page import="net.smartworks.model.work.SmartTask"%>
<%@page import="net.smartworks.model.work.SmartDiagram"%>
<%@page import="net.smartworks.model.work.ProcessWork"%>
<%@page import="net.smartworks.model.instance.CommentInstance"%>
<%@page import="net.smartworks.model.instance.MemoInstance"%>
<%@page import="net.smartworks.model.community.User"%>
<%@page import="net.smartworks.model.security.EditPolicy"%>
<%@page import="net.smartworks.model.security.WritePolicy"%>
<%@page import="net.smartworks.model.security.AccessPolicy"%>
<%@page import="net.smartworks.model.work.InformationWork"%>
<%@page import="net.smartworks.model.work.SmartWork"%>
<%@page import="net.smartworks.model.work.Work"%>
<%@page import="net.smartworks.util.SmartUtil"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="net.smartworks.service.ISmartWorks"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	ISmartWorks smartWorks = (ISmartWorks) request.getAttribute("smartWorks");
	User cUser = SmartUtil.getCurrentUser();

	ProcessWork work = (ProcessWork)session.getAttribute("smartWork");
	String workId = work.getId();
	CommentInstance[] comments = smartWorks.getRecentCommentsInWorkManual(workId, 3);
	SmartDiagram diagram = work.getDiagram();
	SmartTaskInfo[] tasks = null;
	if (diagram != null)
		tasks = diagram.getTasks();
%>
<fmt:setLocale value="<%=cUser.getLocale() %>" scope="request" />
<fmt:setBundle basename="resource.smartworksMessage" scope="request" />

<!-- 업무 설명 보기 -->
<div class="contents_space js_pwork_manual_page">

	<!-- 보더 -->
	<div class="border">
	
		<!-- 업무 정의 -->
		<!-- <div class=""><%if(!SmartUtil.isBlankObject(work.getDesc())) {%><%=work.getDesc()%><%}else{ %><fmt:message key="common.message.no_work_desc" /><%} %></div> -->
		<!-- 업무 정의 //-->
	
		<!-- 프로세스 영역 -->
		<div class="define_space padding0" style="height:59px">
		
		 <!-- 방향 Prev -->
		       <a href="" class="js_manual_tasks_left" style="display::block"><div class="proc_btn_prev float_left"></div></a>
			<!-- 방향 Prev //-->
			
	        <div class="proce_section">
	        
				<!--  태스크 시작 -->
				<div class="proce_space js_manual_tasks_holder" style="overflow:hidden; width:92%">
					<div class="js_manual_tasks">
						<ul><li class="proc_task not_yet js_manual_task_placeholder" style="display:none"></li></ul>
						
						<ul>
						<%
						if (tasks != null) {
							int count = 0;
							for (SmartTaskInfo task : tasks) {
								count++;
								UserInfo assignedUser = task.getAssignedUser();
								String assigningName = task.getAssigningName();
						%>
								<!-- 태스크 -->
								<li class="proc_task not_yet js_manual_task">
									<a class="js_select_task_manual" href="" taskId="<%=task.getId() %>"> 
											<img class="float_left" src="<%if (assignedUser != null) {%><%=assignedUser.getMidPicture()%><%}%>">
											<div class="noti_in_s"><%=count%>) <%=task.getName()%>
												<div class="t_date"><%=task.getAssigningName()%></div>
											</div>
									</a>
								</li>
								<!-- 태스크 //-->
						<%
							}
						}
						%>
						</ul>
					</div>
				</div>
				<!--  태스크 시작// -->
			</div>
			<!-- 방향 Next -->
		   <a href="" class="js_manual_tasks_right" style="display:none"><div class="proc_btn_next float_right" style="top:-97px"></div></a>
		  	<!-- 방향 Next //-->  
		</div>
		<!--프로세스 영역//-->

		<!-- 업무설명 영역 -->
		<%
		if(tasks!=null){
			for(int i=0; i<tasks.length; i++){				
				SmartFormInfo form = tasks[i].getForm();
				if(form!=null){
		%>
					<div class="js_task_manual" id="<%=tasks[i].getId() %>" <%if(i!=0){ %>style="display:none"<%} %>>
						<div class="up_point posit_default"></div>
						<div class="form_wrap up up_padding">
							<div class="area">
								<!-- 업무설명 -->
								<div class="det_contents">
									<table>
										<tbody>
											<tr>
												<td><img src="<%=form.getOrgImage() %>" width="349" height="289" /></td>
												<%if(SmartUtil.isBlankObject(form.getDescription())){ %><td><fmt:message key="common.message.no_form_desc"/></td><%}else{ %><td><%=form.getDescription() %></td><%} %>
											</tr>
										</tbody>
									</table>
								</div>
								<!-- 업무 설명 //-->
							</div>
						</div>
					</div>
		<%
				}
			}
		}
		%>
		<!-- 업무설명 영역 //-->
		
	   <!-- 댓글 -->
	   <div class="replay_point posit_default"></div>
	   <div class="replay_section">  
	        <div class="list_replay">
	            <ul>
		            <li><img class="repl_tinfo"><a href=""><strong>7</strong>개의 댓글 모두 보기</a></li>
					<%
					if (comments != null) {
						for (CommentInstance comment : comments) {
							User commentor = comment.getCommentor();
					%>
							<li>
								<div class="noti_pic">
									<img src="<%=commentor.getMinPicture()%>" align="bottom" />
								</div>
								<div class="noti_in">
									<span class="t_name"><%=commentor.getLongName()%></span><span
										class="t_date"><%=comment.getLastModifiedDate().toLocalString()%></span>
									<div><%=comment.getComment()%></div>
								</div>
							</li>
					<%
						}
					}
					%>
				</ul>
	        </div>
	        
	        <div class="replay_input">
				<textarea class="up_textarea" rows="5" cols="" name="txtaEventContent"
					placeholder="<fmt:message key='work.message.leave_question'/>"><fmt:message key='work.message.leave_question' />
				</textarea>
	        </div>
	    
	    </div>
	    <!-- 댓글 //-->

	    <!-- 라인 -->
		<div class="solid_line_s margin_t10 margin_b5"></div>		

		<!-- 우측 버튼 -->
		<div class="txt_btn txt_btn_height25">	

			<!-- 수정하기 -->
			<div class="float_right space_l5">
				<%
				if (cUser.getUserLevel() == User.USER_LEVEL_AMINISTRATOR) {
				%>
					<span class="btn_gray"> 
						<span class="Btn01Start"></span>
						<span class="Btn01Center"><fmt:message key='common.button.modify' /> </span> 
						<span class="Btn01End"></span>
					</span>
				<%
				}
				%>
			</div>
			<!-- 수정하기 //-->
		
			<!-- 최종수정자 -->
			<div class="po_left">
				<img class="pho_user profile_size_s" title="<fmt:message key="common.title.last_modifier" />" src="<%=work.getLastModifier().getMinPicture()%>">
				<span class="t_name"><%=work.getLastModifier().getLongName()%></span> 
				<span class="t_date"><%=work.getLastModifiedDate().toLocalString()%></span>
			</div>
			<!-- 최종수정자 //-->
		
			<span class="po_left">
				<%
				if (work.getManualFileName() != null) {
				%>
					<a href="" class="bu_video space_r2" title="<fmt:message key='work.title.manual_file'/>"></a> 
				<%
				}
				if (work.getHelpUrl() != null) {
				%> 
					<a href="<%=work.getHelpUrl()%>" class="bu_webex" title="<fmt:message key='work.title.help_url'/>" target="_blank"></a>
				<%
				}
				%>
			</span>
	
			<!-- 우측 권한 아이콘 -->
			<span>
				<%
				switch (work.getAccessPolicy().getLevel()) {
				case AccessPolicy.LEVEL_PUBLIC:
				%>
					<span class="ch_right margin_t5"><fmt:message key="common.security.access.public" /></span>
				<%
					break;
				case AccessPolicy.LEVEL_PRIVATE:
				%>
					<span class="ch_right margin_t5"><fmt:message key="common.security.access.private" /></span>
				<%
					break;
				case AccessPolicy.LEVEL_CUSTOM:
				%>
					<span class="ch_right margin_t5"><fmt:message key="common.security.access.custom" /></span>
				<%
					break;
				}
				%>
		
				<span class="float_right margin_t5"><span class="bu_read"  title="<fmt:message key='common.security.title.access'/>"></span></span>
		
				<%
				switch (work.getWritePolicy().getLevel()) {
				case WritePolicy.LEVEL_PUBLIC:
				%>
					<span class="ch_right  margin_t5"><fmt:message key="common.security.write.public" /></span>
				<%
					break;
				case WritePolicy.LEVEL_CUSTOM:
				%>
					<span class="ch_right  margin_t5"><fmt:message key="common.security.write.custom" /></span>
				<%
					break;
				}
				%>
		
				<span class="float_right margin_t5"><span class="bu_regit" title="<fmt:message key='common.security.title.write'/>"></span></span>
		
				<%
				switch (work.getEditPolicy().getLevel()) {
				case EditPolicy.LEVEL_WIKI:
				%>
					<span class="ch_right  margin_t5"><fmt:message key="common.security.edit.wiki" /></span>
				<%
					break;
				case EditPolicy.LEVEL_BLOG:
				%>
					<span class="ch_right  margin_t5"><fmt:message key="common.security.edit.blog" /></span>
				<%
					break;
				}
				%>
		
				<span class="float_right margin_t5"><span class="bu_modfy" title="<fmt:message key='common.security.title.edit'/>"></span></span>
		
			</span>
			<!-- 우측 권한 아이콘//-->
		</div>
		<!-- 우측 버튼 //-->
	</div>
	<!-- 보더 // -->			
</div>
<!-- 업무 설명 보기 -->

