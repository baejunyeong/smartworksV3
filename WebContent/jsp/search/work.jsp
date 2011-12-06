<%@page import="net.smartworks.model.community.User"%>
<%@page import="net.smartworks.model.work.info.SmartWorkInfo"%>
<%@ page import="net.smartworks.util.SmartUtil"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="net.smartworks.service.ISmartWorks"%>
<%@ page import="net.smartworks.model.work.*"%>
<%
	User cUser = SmartUtil.getCurrentUser(request, response);

	ISmartWorks smartWorks = (ISmartWorks) request.getAttribute("smartWorks");
	String key = request.getParameter("key");
	SmartWorkInfo[] works = smartWorks.searchWork(cUser.getCompanyId(), cUser.getId(), key);
%>

<ul>
	<%
		if (works != null) {
			for (SmartWorkInfo work : works) {
				String iconType = null;
				String workContext = null;
				String targetContent = null;
				if (work.getType() == SmartWork.TYPE_PROCESS) {
					iconType = "ico_pworks";
					workContext = ISmartWorks.CONTEXT_PREFIX_PWORK_LIST + work.getId();
					targetContent = "pwork_list.sw";
				} else if (work.getType() == SmartWork.TYPE_INFORMATION) {
					iconType = "ico_iworks";
					workContext = ISmartWorks.CONTEXT_PREFIX_IWORK_LIST + work.getId();
					targetContent = "iwork_list.sw";
				} else if (work.getType() == SmartWork.TYPE_SCHEDULE) {
					iconType = "ico_sworks";
					workContext = ISmartWorks.CONTEXT_PREFIX_SWORK_LIST + work.getId();
					targetContent = "swork_list.sw";
				}
	%>
	<li>
	<a href="<%=targetContent%>?cid=<%=workContext%>" class="js_content">
		<span class="<%=iconType%>"></span>
		<span class="nav_subtitl_area"><%=work.getFullpathName()%></span>
	</a>
	</li>
	<%
		}
		}
	%>
</ul>
