SmartWorks.FormRuntime = SmartWorks.FormRuntime || {};

SmartWorks.FormRuntime.CurrencyInputBuilder = {};

SmartWorks.FormRuntime.CurrencyInputBuilder.build = function(config) {
	var options = {
		mode : 'edit', // view or edit
		container : $('<div></div>'),
		entity : null,
		dataField : ''
	};

	SmartWorks.extend(options, config);
	var value = (options.dataField && options.dataField.value) || '';
	var $entity = options.entity;
	var $graphic = $entity.children('graphic');

	var readOnly = $graphic.attr('readOnly') === 'true' || options.mode === 'view';
	var id = $entity.attr('id');
	var name = $entity.attr('name');
	
	var currency = $entity.children('format').children('currency').text();
	currency = '$';//TODO ? 로 깨짐
	var $label = $('<td>' + name + '</td>');
	var required = $entity[0].getAttribute('required');
	if(required === 'true'){
		$('<span class="essen_n"></span>').appendTo($label);
		required = " class='js_currency_input fieldline required' ";
	}else{
		required = " class='js_currency_input fieldline' ";
	}
	$label.appendTo(options.container);
	
	var $currency = null;
	if(readOnly){
		$currency = $('<td fieldId="' + id + '"></td>').text(value).formatCurrency({ symbol: currency ,colorize: true, negativeFormat: '-%s%n', roundToDecimalPlace: -1, eventOnDecimalsEntered: true });
	}else{	
		$currency = $('<td><input type="text" symbol="' + currency +'" fieldId="' + id + '" name="' + id + '"' + required + '></td>').attr('value',value).formatCurrency({ symbol: currency ,colorize: true, negativeFormat: '-%s%n', roundToDecimalPlace: -1, eventOnDecimalsEntered: true });
		//if save mode = $currency.toNumber().attr('value');
	}
	$currency.appendTo(options.container);

	return options.container;
};

$('input.js_currency_input').live('keyup', function(e) {
	var e = window.event || e;
	var keyUnicode = e.charCode || e.keyCode;
	if (e !== undefined) {
		switch (keyUnicode) {
			case 16: break; // Shift
			case 17: break; // Ctrl
			case 18: break; // Alt
			case 27: this.value = ''; break; // Esc: clear entry
			case 35: break; // End
			case 36: break; // Home
			case 37: break; // cursor left
			case 38: break; // cursor up
			case 39: break; // cursor right
			case 40: break; // cursor down
			case 78: break; // N (Opera 9.63+ maps the "." from the number key section to the "N" key too!) (See: http://unixpapa.com/js/key.html search for ". Del")
			case 110: break; // . number block (Opera 9.63+ maps the "." from the number block to the "N" key (78) !!!)
			case 190: break; // .
			default: $(this).formatCurrency({ symbol: $(this).attr('symbol') ,colorize: true, negativeFormat: '-%s%n', roundToDecimalPlace: -1, eventOnDecimalsEntered: true });
		}
	}
});
