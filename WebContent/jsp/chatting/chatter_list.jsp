<%@page import="net.smartworks.model.instance.info.InstanceInfo"%>
<%@page import="net.smartworks.model.instance.info.TaskInstanceInfo"%>
<%@page import="net.smartworks.model.community.info.UserInfo"%>
<%@page import="net.smartworks.model.instance.info.EventInstanceInfo"%>
<%@page import="net.smartworks.util.SmartUtil"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="net.smartworks.service.ISmartWorks"%>
<%@ page import="net.smartworks.model.community.*"%>
<%@ page import="net.smartworks.model.notice.*"%>
<%@ page import="net.smartworks.model.instance.*"%>
<%@ page import="net.smartworks.model.work.*"%>
<%@ page import="net.smartworks.util.LocalDate"%>
<script>
	function updateAvailableChatters(userInfos) {
		if (userInfos != null) {
			var data = "";
			var length = userInfos.length;
			for ( var i = 0; i < length; i++) {
				data = data
						+ "<li><a href='' userId='" + userInfos[i].userId + "'><span><img src='" + userInfos[i].minPicture + "' title='" + userInfos[i].longName + "' class='online'/>"
						+ userInfos[i].longName + "</span></a></li>";
			}
			$("#available_chatter_list").html(data).parents(
					'div.js_chatter_list').find('span.js_chatters_number')
					.html("(" + length + ")");
		}
	}
</script>
<%
	User cUser = SmartUtil.getCurrentUser();
	ISmartWorks smartWorks = (ISmartWorks) request.getAttribute("smartWorks");
	UserInfo[] chatters = smartWorks.getAvailableChatter();
%>
<fmt:setLocale value="<%=cUser.getLocale() %>" scope="request" />
<fmt:setBundle basename="resource.smartworksMessage" scope="request" />

<!-- 채팅Default -->	 
<div class="chat_de_section js_chatter_list">
	<!-- 상단 -->
	<div class="top_group">
		<a href="" class="js_toggle_chatter_list">
			<div class="chatic_titl sera"><fmt:message key="chat.title.chatting"/><span class="t_white js_chatters_number">(<%=chatters.length%>)</span></div>
		</a>
		<!-- 상단우측 아이콘-->
		<div class="txt_btn">
			<div class="ch_right btn_admin">
				<a href=""><span></span></a>
			</div>
		</div>
		<!-- 상단 우측 아이콘//-->
	</div>
	<!-- 상단 //-->

	<!--온라인 일때는 class="chat_online", 오프라인 일때는 chat_offline -->
	<div class="js_chatter_search_area" style="display: none">

		<!-- Body -->
		<div class="chat_de_list js_chatter_list" id="available_chatter_list">
			<ul>
				<%
				for (UserInfo chatter : chatters) {
				%>
					<li>
						<a href="" userId="<%=chatter.getId()%>">
							<img src="<%=chatter.getMinPicture()%>" class="profile_size_s" title="<%=chatter.getLongName()%>" /><%=chatter.getLongName()%>
							<span class="chat_offline"></span>
						</a>
					</li>
				<%
				}
				%>
			</ul>
		</div>
		<!-- Body //-->

		<!-- 검색영역 -->
		<div class="chat_input_section js_chatter_names">
			<div class="chat_input_area">
				<input id="" class="input js_auto_complete" type="text" href="chatter_name.sw" placeholder="<fmt:message key='search.search_people_depart_group'/>" title="<fmt:message key='search.search_people_depart_group'/>">
			</div>
		</div>
	</div>
	<!-- 검색영역//-->
</div>
<!-- 채팅Default //-->

<!-- 이동 화살표-->
	<div class="chat_num_section js_chatting_group_prev" style="display:none">
	<a href="">
		<div class="top_group">
			<span class="cha_num js_group_prev_count"></span>
			<span class="cha_prev"></span>
		</div>
	</a>
</div>

<!-- 채팅창 생성 -->
<div class="js_chatting_box_list"></div>

<!-- 이동 화살표-->
	<div class="chat_num_section js_chatting_group_next" style="display:none">
	<a href="">
		<div class="top_group">
			<span class="cha_next"></span>
			<span class="cha_num js_group_next_count"></span>
		</div>
	</a>
</div>
<!-- 이동 화살표 //-->