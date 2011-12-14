<%@page import="net.smartworks.server.engine.common.util.CommonUtil"%>
<%@page import="net.smartworks.model.KeyMap"%>
<%@page import="net.smartworks.model.work.InformationWork"%>
<%@page import="net.smartworks.model.work.FormField"%>
<%@page import="net.smartworks.model.report.ChartReport"%>
<%@page import="net.smartworks.model.report.Report"%>
<%@page import="net.smartworks.model.work.SmartWork"%>
<%@page import="net.smartworks.model.work.Work"%>
<%@page import="net.smartworks.util.SmartUtil"%>
<%@page import="net.smartworks.util.SmartTest"%>
<%@page import="net.smartworks.model.community.User"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="net.smartworks.service.ISmartWorks"%>

<script type="text/javascript">
	function submitForms(e) {
		if ($('form.js_validation_required').validate().form()) {
			var scheduleWork = document.getElementsByName('frmScheduleWork');
			if (scheduleWork[0].chkScheduleWork.value === 'on') {
				scheduleWork[0].hdnSchedulePerformer.value = $(
						scheduleWork[0].txtSchedulePerformer).attr('uid');
			}
			var params = $('form').serialize();
			var url = "create_new_iwork.sw";
			$.ajax({
				url : url,
				type : 'POST',
				data : params,
				success : function(data, status, jqXHR) {
					document.location.href = data.href;
				},
				error : function(e) {
					alert(e);
				}
			});
		} else {
			return;
		}
		return;
	}
</script>
<%
	ISmartWorks smartWorks = (ISmartWorks) request.getAttribute("smartWorks");
	String workId = request.getParameter("workId");
	String reportId = request.getParameter("reportId");
	User cUser = SmartUtil.getCurrentUser(request, response);

	SmartWork work = null;
	Report report = null;
	String filterId = "";
	int reportType = Report.TYPE_CHART;
	if (workId != null)
		work = (SmartWork) smartWorks.getWorkById(cUser.getCompanyId(), cUser.getId(), workId);
	if (reportId != null) {
		report = smartWorks.getReportById(cUser.getCompanyId(), cUser.getId(), reportId);
		reportType = report.getType();
		if (report.getSearchFilter() != null)
			filterId = report.getSearchFilter().getId();
	}
%>
<fmt:setLocale value="<%=cUser.getLocale() %>" scope="request" />
<fmt:setBundle basename="resource.smartworksMessage" scope="request" />
<!--  전체 레이아웃 -->
<div class="form_wrap up up_padding">


	<!-- 컨텐츠 -->
	<div class="form_title">
		<div class="ico_stworks title_noico">
			<fmt:message key="report.title.new_report" />
		</div>
		<div class="solid_line"></div>
	</div>

	<div class="form_contents">
		<table class="table_nomal js_report_title">
			<tr>
				<td width="17%"><fmt:message key="report.title.report_name" /><span
					class="essen_n"></span></td>
				<td width="83%" colspan="4"><input id="" type="text"
					class="fieldline" name="txtWorkReportName"
					value="<%if (report != null) {%><fmt:message key='<%=report.getName() %>'/><%}%>">
				</td>
			</tr>

			<tr class="js_work_report_type">
				<td><fmt:message key="report.title.report_type" /><span
					class="essen_n"></span></td>
				<td colspan="4" class=""><input name="rdoWorkReportType"
					class="required" type="radio" value="<%=Report.TYPE_CHART%>"
					url="work_report_chart.sw?workId=<%=workId%>&reportId=<%=CommonUtil.toNotNull(reportId)%>"
					<%if (reportType == Report.TYPE_CHART) {%> checked <%}%>> <fmt:message
						key="report.type.chart" /><input name="rdoWorkReportType"
					type="radio" value="<%=Report.TYPE_MATRIX%>"
					url="work_report_chart.sw?workId=<%=workId%>&reportId=<%=CommonUtil.toNotNull(reportId)%>"
					<%if (reportType == Report.TYPE_MATRIX) {%> checked <%}%>>
					<fmt:message key="report.type.matrix" /><input
					name="rdoWorkReportType" type="radio"
					value="<%=Report.TYPE_TABLE%>"
					url="work_report_table.sw?workId=<%=workId%>&reportId=<%=CommonUtil.toNotNull(reportId)%>"
					<%if (reportType == Report.TYPE_TABLE) {%> checked <%}%>> <fmt:message
						key="report.type.table" />
				</td>
			</tr>
		</table>
		<table class="table_nomal js_form_by_report_type">
				<%
					if (reportType == Report.TYPE_CHART || reportType == Report.TYPE_MATRIX) {
				%>
				<jsp:include page="/jsp/content/work/report/work_report_chart.jsp">
					<jsp:param name="workId" value="<%=workId %>" />
					<jsp:param name="reportId" value="<%=CommonUtil.toNotNull(reportId) %>" />
					<jsp:param name="reportType" value="<%=reportType %>" />
				</jsp:include>
				<%
					} else if (reportType == Report.TYPE_TABLE) {
				%>
				<jsp:include page="/jsp/content/work/report/work_report_table.jsp">
					<jsp:param name="workId" value="<%=workId %>" />
					<jsp:param name="reportId" value="<%=CommonUtil.toNotNull(reportId) %>" />
					<jsp:param name="reportType" value="<%=reportType %>" />
				</jsp:include>
				<%
					}
				%>
		</table>
		<table class="table_nomal">
			<tr class="js_toggle_chart_search_filter"
				url="search_filter.sw?workId=<%=workId%>&filterId=<%=CommonUtil.toNotNull(filterId)%>">
				<td <%if(filterId!=null && !filterId.equals("")){ %>style="display:none"<%} %>><a href=""><fmt:message key="report.button.add_search_filter" /></a></td>
				<td actionType="remove" <%if(filterId==null || filterId.equals("")){ %>style="display:none"<%} %>><a href=""><fmt:message key="report.button.remove_search_filter" /></a></td>
			</tr>
			<tr class="js_chart_search_filter" style="display: none">
			</tr>
		</table>
	</div>

	<!-- 등록 취소 버튼 -->
	<div class="glo_btn_space">

		<div class="float_right padding_r10">
			<span class="btn_gray"> <a href=""> <span
					class="Btn01Start"></span> <span class="Btn01Center">저장</span> <span
					class="Btn01End"></span> </a> </span> <span class="btn_gray space_l5">
				<a href=""> <span class="Btn01Start"></span> <span
					class="Btn01Center">취소</span> <span class="Btn01End"></span> </a> </span>
		</div>

		<div class="float_right padding_r10">

			<form class="float_right form_space">
				<img class="bu_read"> <select>
					<option>전체공개</option>
					<option>비공개</option>
				</select>
			</form>
		</div>
	</div>
	<!-- 등록 취소 버튼//-->

</div>
<!-- 전체 레이아웃//-->