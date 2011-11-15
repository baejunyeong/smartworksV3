<%@ page import="net.smartworks.util.SmartUtil"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="net.smartworks.service.ISmartWorks"%>
<%@ page import="net.smartworks.model.community.*"%>
<%
	ISmartWorks smartWorks = (ISmartWorks) request.getAttribute("smartWorks");
	String key = request.getParameter("key");
	User[] users = smartWorks.searchUser(key);
%>

<ul>
	<%
		for (User user : users) {
			String picName = user.getMinPicture();
			String comContext = ISmartWorks.CONTEXT_PREFIX_USER_SPACE + user.getId();
			String targetContent = "user_name_text.sw";
			String comName = user.getName();
			String comId = user.getId();
	%>
	<li><a href="" uname="<%=user.getLongName()%>" uid="<%=comId %>" class="js_select_user"><img src="<%=picName%>" border="0"><%=comName%></a>
	</li>
	<%
		}
	%>
</ul>