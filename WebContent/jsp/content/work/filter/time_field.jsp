<%@page import="java.text.DecimalFormat"%>
<%@page import="net.smartworks.model.community.User"%>
<%@page import="net.smartworks.util.LocalDate"%>
<%@page import="net.smartworks.model.filter.KeyMap"%>
<%@page import="net.smartworks.model.filter.ConditionOperator"%>
<%@page import="net.smartworks.model.work.SmartWork"%>
<%@page import="net.smartworks.util.SmartUtil"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="net.smartworks.service.ISmartWorks"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String operator = request.getParameter("operator");
	String operandValue = request.getParameter("operandValue");
	ISmartWorks smartWorks = (ISmartWorks) request.getAttribute("smartWorks");
	User cUser = SmartUtil.getCurrentUser(request);
	LocalDate date = new LocalDate();
	String today = date.toLocalDateSimpleString();
	String curTime = date.toLocalTimeShortString();
	KeyMap[] timeOpers = ConditionOperator.timeOperators;
%>
<fmt:setLocale value="<%=cUser.getLocale() %>" scope="request" />
<fmt:setBundle basename="resource.smartworksMessage" scope="request" />

<select name="selFilterTimeOperator" class="selb_size_sec">
	<%
		for (KeyMap timeOper : timeOpers) {
	%>
	<option value="<%=timeOper.getId()%>"
		<%if (operator != null && operator.equals(timeOper.getId())) {%>
		selected <%}%>>
		<fmt:message key="<%=timeOper.getKey() %>" />
	</option>
	<%
		}
	%>
</select>
<span class="str_field"><input name="selFilterTimeOperand" class="inputline time_input"
	type="text"
	value="<%if (operandValue != null) {%><%=operandValue%><%} else {%><%=curTime%><%}%>">
</span>
<span class="btn_x_grb_posi">
	<button class="btn_x_grb js_remove_condition"></button> </span>