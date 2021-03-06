<%@page import="net.smartworks.model.work.MailWork"%>
<%@page import="net.smartworks.model.mail.MailFolder"%>
<%@page import="net.smartworks.model.report.ChartReport"%>
<%@page import="net.smartworks.model.report.info.ReportInfo"%>
<%@page import="net.smartworks.model.filter.info.SearchFilterInfo"%>
<%@page import="net.smartworks.model.report.Report"%>
<%@page import="net.smartworks.model.work.FormField"%>
<%@page import="net.smartworks.model.work.SmartForm"%>
<%@page import="net.smartworks.model.filter.SearchFilter"%>
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

<script type="text/javascript">
	getIntanceList = function(paramsJson, progressSpan, isGray){
		if(isEmpty(progressSpan))
			progressSpan = $('.js_work_list_title').find('.js_progress_span:first');
		if(isGray)
			smartPop.progressContGray(progressSpan);
		else
			smartPop.progressCont(progressSpan);
		console.log(JSON.stringify(paramsJson));
		var url = "set_instance_list_params.sw";
		$.ajax({
			url : url,
			contentType : 'application/json',
			type : 'POST',
			data : JSON.stringify(paramsJson),
			success : function(data, status, jqXHR) {
				$('#mail_instance_list_page').html(data);
				smartPop.closeProgress();
			},
			error : function(xhr, ajaxOptions, thrownError) {
				smartPop.closeProgress();
				smartPop.showInfo(smartPop.ERROR, smartMessage.get('workListError'));
			}
		});
	};
	
	selectListParam = function(progressSpan, isGray){
		var mailList = $('.js_mail_list_page');
		var forms = mailList.find('form:visible');
		var paramsJson = {};
		var workId = mailList.attr('workId');
		paramsJson["href"] = "jsp/content/work/list/mail_instance_list.jsp?workId=" + workId;
		var searchFilters = mailList.find('form[name="frmSearchFilter"]');
		for(var i=0; i<forms.length; i++){
			var form = $(forms[i]);
			if(form.attr('name') !== "frmSearchFilter" && !(!isEmpty(searchFilters) && form.attr('name') === "frmSearchInstance")){
				paramsJson[form.attr('name')] = mergeObjects(form.serializeObject(), SmartWorks.GridLayout.serializeObject(form));
			}
		}
		if(!isEmpty(searchFilters)){
			var searchFilterArray = new Array();
			for(var i=0; i<searchFilters.length; i++){
				var searchFilter = $(searchFilters[i]);
				if(searchFilter.is(':visible'))
					searchFilterArray.push(searchFilter.find(':visible').serializeObject());
			}
			paramsJson['frmSearchFilters'] = searchFilterArray;
		}
		if(isEmpty(progressSpan)) progressSpan = mailList.find('.js_search_filter').next('span.js_progress_span:first');
		getIntanceList(paramsJson, progressSpan, isGray);		
	};
</script>
<%
	
	ISmartWorks smartWorks = (ISmartWorks) request.getAttribute("smartWorks");
	String cid = request.getParameter("cid");
	String wid = request.getParameter("wid");
	session.setAttribute("cid", cid);
	session.setAttribute("wid", wid);

	String folderId = cid;
	User cUser = SmartUtil.getCurrentUser();
	MailWork work = new MailWork(folderId, MailFolder.getFolderNameById(folderId), "");
	MailFolder mailFolder = smartWorks.getMailFolderById(folderId);
	session.setAttribute("smartWork", work);
	session.removeAttribute("workInstance");
%>
<fmt:setLocale value="<%=cUser.getLocale() %>" scope="request" />
<fmt:setBundle basename="resource.smartworksMessage" scope="request" />

<!-- 컨텐츠 레이아웃-->
<div class="section_portlet js_mail_list_page js_work_list_page" workId=<%=work.getId()%>>
	<div class="portlet_t"><div class="portlet_tl"></div></div>
	<div class="portlet_l" style="display: block;">
		<ul class="portlet_r" style="display: block;">

			<!-- 타이틀 -->
			<div class="body_titl">
				<div class="body_titl_area ti_mail title">
					<div class="title myspace_h"><%=mailFolder.getName() %>
						<span class="t_mail"><span class="t_s11"><fmt:message key="mail.title.count.unread"/></span><span class="new_mail"><%=mailFolder.getUnreadItemCount() %></span><span class="bar"> / </span><%=mailFolder.getTotalItemCount() %></span><span class=" t_s11"><fmt:message key="mail.title.count"/></span>
					</div>
				</div>

				<!-- 메일 검색 -->
				<div class="mail_srch">
					<div class="srch_wh srch_wsize_mail">
						<input id="" class="nav_input" type="text" href="" placeholder="<fmt:message key="search.search_mail"/>" title="<fmt:message key="search.search_mail"/>">
						<button onclick="" title="<fmt:message key="search.search"/>"></button>
					</div>
				</div>
				<!-- 메일 검색//-->

				<div class="solid_line cb"></div>
			</div>
			<!-- 타이틀 //-->

			<!-- 컨텐츠 -->
			<div class="contents_space">
				<div class="buttonSet">
					<button><fmt:message key="common.button.mail.select_all"/><span class="icon_in_down"><a href=""></a></span></button>
					<button><span class="icon_mail_delet"></span><fmt:message key="common.button.delete"/></button>
					<button><fmt:message key="common.button.mail.register_spam"/></button>
					<button><fmt:message key="common.button.mail.reply"/></button>
					<button><fmt:message key="common.button.mail.reply_all"/></button>
					<button><fmt:message key="common.button.mail.forward"/></button>
					<button><fmt:message key="common.button.move"/><span class="icon_in_down"><a href=""> </a></span></button>
					<button class="fr t_bold"><span class="icon_mail_write"></span><fmt:message key="common.button.mail.new"/></button>
				</div>

				<!-- 메일 리스트-->
				<div class="list_contents mail_list_section">
					<div id='mail_instance_list_page' >
						<jsp:include page="/jsp/content/work/list/mail_instance_list.jsp"></jsp:include>
					</div>
				</div>
				<!-- 메일 리스트//-->
			</div>
			<!-- 컨텐츠 //-->

		</ul>
	</div>
	<div class="portlet_b" style="display: block;"></div>
</div>
<!-- 컨텐츠 레이아웃//-->
