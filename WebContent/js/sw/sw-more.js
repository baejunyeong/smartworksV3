
	$('.js_more_list').live('click', function(e) {
		var anchor = $(e.target);
		if(!isEmpty(anchor.siblings('.js_progress_span').find('.js_progress_icon'))) 
			return false;
		smartPop.progressCont(anchor.siblings('.js_progress_span'));
		var runningPage = anchor.parents('.js_my_running_instance_list_page');
		var lastDate = runningPage.find('.js_more_instance_item:last').attr('dateValue');
		var assignedOnly = runningPage.find('a.js_view_assigned_instances').hasClass('current');
		var instanceCount;
		if(assignedOnly)
			instanceCount = runningPage.find('a.js_view_assigned_instances').attr('instanceCount');
		else
			instanceCount = runningPage.find('a.js_view_my_running_instances').attr('instanceCount');
		if(instanceCount > runningPage.length)
			runningPage.find('.js_more_list').show();
		else
			runningPage.find('.js_more_list').hide();
		
		$.ajax({
			url : anchor.attr('href'),
			data : {
				lastDate : lastDate,
				assignedOnly : assignedOnly
			},
			success : function(data, status, jqXHR) {
				console.log('data=', data, ", target=", runningPage.find('.js_instance_list_table'));
				$(data).appendTo(runningPage.find('.js_instance_list_table'));
				smartPop.closeProgress();
			},
			error : function(xhr, ajaxOptions, thrownError){
				smartPop.closeProgress();				
			}
		});
	
		return false;
	});

	$(window).scroll( function() {
		var more_anchor = $('#work_ing .js_more_list a');
		var more_smartcaster = $('.js_smartcaster_page a.js_space_more_history')
		if ($(window).scrollTop() == $(document).height() - $(window).height()){
			
			if(!isEmpty(more_anchor) && !more_anchor.isWaiting){
				more_anchor.isWaiting = true;
				setTimeout(function() {
					if ($(window).scrollTop() == $(document).height() - $(window).height())
						more_anchor.trigger('click');
					more_anchor.isWaiting = false;
				}, 2000);
			}else if(!isEmpty(more_smartcaster) && !more_smartcaster.isWaiting){
				more_smartcaster.isWaiting = true;
				setTimeout(function() {
					if ($(window).scrollTop() == $(document).height() - $(window).height())
						more_smartcaster.trigger('click');
					more_smartcaster.isWaiting = false;
				}, 2000);
			}
		}
	});
