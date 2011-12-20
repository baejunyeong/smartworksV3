SmartWorks.FormRuntime = SmartWorks.FormRuntime || {};

SmartWorks.FormRuntime.ImageBoxBuilder = {};

SmartWorks.FormRuntime.ImageBoxBuilder.build = function(config) {
	var options = {
		mode : 'view', // view or edit
		container : $('<div></div>'),
		entity : null,
		dataField : '',
		layoutInstance : null
	};

	SmartWorks.extend(options, config);
	var value = (options.dataField && options.dataField.value) || '';
	var $entity = options.entity;
	var $graphic = $entity.children('graphic');

	var readOnly = $graphic.attr('readOnly') === 'true' || options.mode === 'view';
	var id = $entity.attr('id');
	var name = $entity.attr('name');
	
	var $label = $('<span class="form_label">' + name + '</span>');
	var required = $entity[0].getAttribute('required');
	if(required === 'true' && !readOnly){
		$('<span class="essen_n"></span>').appendTo($label);
		required = ' class="sw_required"';
	}else{
		required = '';
	}
	$label.appendTo(options.container);
	
	var $image = null;
		
	$image = $('<span class="form_value form_value_max_width" style="width:' + valueWidth + '%"><img src=""/>' + '<span id="' + id + '"' + required + '></span></span>');
	if ($graphic.attr('hidden') == 'true'){
		$label.hide();
		$image.hide();		
	}
	$image.appendTo(options.container);

	if (!readOnly) {
		createUploader(value, $('#'+id), false, true);
	}
	return options.container;

};

SmartWorks.FormRuntime.ImageBoxBuilder.serializeObject = function(imageBoxs){
	var fileUploaders = imageBoxs.find('.qq-uploader');
	var filesJson = {};
	for(var i=0; i<fileUploaders.length; i++){
		var fileUploader = $(fileUploaders[i]);
		var fieldId = fileUploader.parent('span').attr('id');
		var fileJson = { groupId : fileUploader.attr('groupId')};
		var files = fileUploader.find('.qq-upload-success');
		var fileInfos = new Array();
		for(var j=0; j<files.length; j++){
			var file = $(files[j]);
			fileInfos.push({fileId : file.attr('fileId')}, {fileName : file.attr('fileName')});
		}
		fileJson['files'] = fileInfos;
		filesJson[fieldId] =  fileJson;
	}
	return filesJson;
};

SmartWorks.FormRuntime.ImageBoxBuilder.validate = function(imageBoxs){
	var fileUploaders = imageBoxs.find('.sw_required').find('.qq-uploader');
	var imagesValid = true;
	for(var i=0; i<fileUploaders.length; i++){
		var fileUploader = $(fileUploaders[i]);
		var files = fileUploader.find('.qq-upload-success');
		if(files.length == 0){
			fileUploader.parents('.js_type_imageBox:first').find('span.sw_required').addClass("sw_error");
			imagesValid = false;
		}
	}
	return imagesValid;
};

SmartWorks.FormRuntime.ImageBoxBuilder.dataField = function(config){
	var options = {
			fieldName: '',
			formXml: '',
			groupId: '',
			isTempfile: false,
			fileId: '',
			fileName: '',
			fileText: '',
			fileSize: 0,
			isMultiple: false,
			isProfile:false
	};

	SmartWorks.extend(options, config);
	$formXml = $(options.formXml);
	var dataField = {};
	var fieldId = $formXml.find('formEntity[name="'+options.fieldName+'"]').attr('id');
	if(isZeroLength($formXml) || isEmpty(fieldId)) return dataField;
	
	dataField = {
			id: fieldId,
			value: options.groupId,
			isTempfile: options.isTempfile,
			fileId: options.fileId,
			fileName: options.fileName,
			fileText: options.fileText,
			fileSize: options.fileSize,
			isMultiple: options.isMultiple,
			isProfile: options.isProfiel
	};
	return dataField;
};