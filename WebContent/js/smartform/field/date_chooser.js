SmartWorks.FormRuntime = SmartWorks.FormRuntime || {};

SmartWorks.FormRuntime.DateChooserBuilder = {};

SmartWorks.FormRuntime.DateChooserBuilder.build = function(config) {
	var options = {
		mode : 'edit', // view or edit
		container : $('<div></div>'),
		entity : null,
		dataField : '',
		layoutInstance : null
	};

	SmartWorks.extend(options, config);
	options.container.html('');
	
	var value = (options.dataField && options.dataField.value) || '';
	var $entity = options.entity;
	//var $graphic = $entity.children('graphic');
	var $graphic = $entity.children('graphic');
	var readOnly = $graphic.attr('readOnly') === 'true' || options.mode === 'view';
	var id = $entity.attr('id');
	var name = $entity.attr('name');
	
	var labelWidth = (isEmpty(options.layoutInstance)) ? parseInt($graphic.attr('labelWidth')) : options.layoutInstance.getLabelWidth(id);
	var valueWidth = 100 - labelWidth;
	var $label = $('<div class="form_label" style="width:' + labelWidth + '%"><span>' + name + '</span></div>');
	var required = $entity[0].getAttribute('required');
	if(required === 'true' && !readOnly){
		$label.addClass('required_label');
		required = " class='fieldline js_todaypicker required' ";
	}else{
		required = " class='fieldline js_todaypicker' ";
	}
	$label.appendTo(options.container);
	
	var $text = null;
	if(readOnly){
		$text = $('<div class="form_value form_value_max_width" style="width:' + valueWidth + '%"></div>').text(value);
	}else{	
		$text = $('<div class="form_value form_value_max_width" style="width:' + valueWidth + '%"><div class="ico_fb_space form_date_input"><input readonly="readonly" type="text" name="' + id + '"' + required + '><a href="" class="js_todaypicker_button"><span class="ico_fb_date"></span></a></div></div>').attr('value', value);
	}
	if ($graphic.attr('hidden') == 'true'){
		$label.hide();
		$text.hide();		
	}
	$text.appendTo(options.container);
	
	smartCommon.liveTodayPicker();
	
	return options.container;
};

SmartWorks.FormRuntime.DateChooserBuilder.buildEx = function(config){
	var options = {
			container : $('<tr></tr>'),
			fieldId: '',
			fieldName: '',
			value: '',
			columns: 1,
			colSpan: 1, 
			required: false,
			readOnly: false		
	};
	SmartWorks.extend(options, config);

	var labelWidth = 10;
	if(options.columns >= 1 && options.columns <= 4 && options.colSpan <= options.columns) labelWidth = 10 * options.columns/options.colSpan;
	$formEntity =  $('<formEntity id="' + options.fieldId + '" name="' + options.fieldName + '" systemType="datetime" required="' + options.required + '" system="false">' +
						'<format type="dateChooser" viewingType="dateChooser"/>' +
					    '<graphic hidden="false" readOnly="'+ options.readOnly +'" labelWidth="'+ labelWidth + '"/>' +
					'</formEntity>');
	var $formCol = $('<td class="form_col js_type_dateChooser" fieldid="' + options.fieldId+ '" colspan="' + options.colSpan + '" width="500.61775800946384" rowspan="1">');
	$formCol.appendTo(options.container);
	SmartWorks.FormRuntime.DateChooserBuilder.build({
			mode : options.readOnly, // view or edit
			container : $formCol,
			entity : $formEntity,
			dataField : options.value			
	});
	
};

SmartWorks.FormRuntime.DateChooserBuilder.dataField = function(config){
	var options = {
			fieldName: '',
			formXml: '',
			value: ''
	};

	SmartWorks.extend(options, config);
	$formXml = $(options.formXml);
	var dataField = {};
	var fieldId = $formXml.find('formEntity[name="'+options.fieldName+'"]').attr('id');
	if(isEmpty(fieldId)) fieldId = ($formXml.attr("name") === options.fieldName) ? $formXml.attr('id') : "";
	if(isEmpty($formXml) || isEmpty(fieldId)) return dataField;
	
	dataField = {
			id: fieldId,
			value: options.value
	};
	return dataField;
};
