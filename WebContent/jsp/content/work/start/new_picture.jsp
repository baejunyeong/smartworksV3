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
	var sw_validate = SmartWorks.FormRuntime.FileFieldBuilder.validate($('.js_upload_picture'));
	if($('form.js_validation_required').validate({ showErrors: showErrors}).form() && sw_validate){
		var target = $('#form_import');
		$.ajax({
			url : "file_detail_form.sw",
			success : function(data, status, jqXHR) {
				target.html(data).hide();
				var form = $('form[name="frmNewPicture"]');
				var uploader = form.find('.qq-uploader');
				var comments = form.find('textarea[name="txtaFileDesc"]').text();
				var groupId = uploader.attr('groupId');
				var fileList = uploader.find('.qq-upload-list li');
				var fileName = $(fileList[0]).attr('fileName');
				if(isEmpty(fileName))
					fileName = "";
				var formContent = $('#form_import').find('div.js_form_content');
				if(!isEmpty(formContent)) {
					var workId = formContent.attr('workId');
					$.ajax({
						url : "get_form_xml.sw",
						data : {
							workId : workId
						},
						success : function(formXml, status, jqXHR) {
							var formXml = $(formXml);
							new SmartWorks.GridLayout({
								target : formContent,
								formXml : formXml,
								formValues : createFileDataFields({
									formXml : formXml,
									groupId : groupId,
									fileName : fileName,
									fileList : fileList,
									comments : comments								
								}),
								mode : "edit"
							});
							$frmSmartForm = formContent.children('form');
							var forms = $('form');
							var paramsJson = {};
							for(var i=0; i<forms.length; i++){
								var form = $(forms[i]);
								if(form.attr('name') === 'frmSmartForm'){
									paramsJson['formId'] = form.attr('formId');
									paramsJson['formName'] = form.attr('formName');
									paramsJson[form.attr('name')] = mergeObjects(form.serializeObject(), SmartWorks.GridLayout.serializeObject(form));
	
								}else{
									paramsJson[form.attr('name')] = form.serializeObject();				
								}
							}
							console.log("JSON", JSON.stringify(paramsJson));
							alert('wait');
							var url = "upload_new_picture.sw";
							$.ajax({
								url : url,
								contentType : 'application/json',
								type : 'POST',
								data : JSON.stringify(paramsJson),
								success : function(data, status, jqXHR) {
									document.location.href = data.href;
								},
								error : function(e) {
									alert(e);
								}
							});
						}
					});
				}
			}
		});
	}
	return;	
}
</script>

<%
	ISmartWorks smartWorks = (ISmartWorks) request.getAttribute("smartWorks");
	User cUser = SmartUtil.getCurrentUser();
%>
<fmt:setLocale value="<%=cUser.getLocale() %>" scope="request" />
<fmt:setBundle basename="resource.smartworksMessage" scope="request" />

<div class="up_wrap">
	<div class="up_point posit_file"></div>
	<div class="up up_padding">
		<!-- 폼- 확장 -->
		<form name="frmNewPicture" class="form_wrap js_validation_required">
			<div class="form_title">

				<textarea class="up_textarea" name='txtaPictureDesc' rows="5" placeholder="<fmt:message key="common.upload.message.picture_desc" />"></textarea>

				<div class="btn_gray padding_t5 js_upload_picture">
					<img style="width:300px;" src="" class="js_auto_picture"></img>
					<div class="js_file_uploader sw_required"></div>
 				</div>
			</div>
			<div class="form_contents">
				<!-- 상세 정보 추가시 화면 -->
				<div id="form_import"></div>
				<!-- 상세 정보 추가시 화면 //-->
			</div>
			<!-- 하단 등록,취소 버튼 -->
			<jsp:include page="/jsp/content/upload/upload_buttons.jsp"></jsp:include>
			<!-- 하단 등록,취소 버튼 -->
	</form>
</div>
</div>