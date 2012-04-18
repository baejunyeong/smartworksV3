$(function() {
	
	/*
	 * 어디에서든 class 값이 js_container로 지정된 anchor 가 선택이 되면, anchor 의 href 값으로 ajax 를 호출하여
	 * 가져온 값을 container(메인컨텐트)화면에 보여준다.
	 */
	$('.js_sera_content').swnavi({
		history : true,
		before : function(event){
			smartPop.progressCenter();				
		},
		target : 'sera_content',
		after : function(event){
			smartPop.closeProgress();
		}
	});

	$('.js_course_menu').live('click', function(e){
		var input = $(e.target).parent();
		input.siblings().removeClass('current');
		input.addClass('current');
		var courseHome = input.parents('.js_course_home_page');
		var courseId = courseHome.attr('courseId');
		var subMenu = courseHome.find('.js_course_sub_menu').hide();
		var subMenus = subMenu.children().hide();
		var pos = input.prevAll().length;
		$(subMenus[pos]).addClass('current');
		if(!isEmpty($(subMenus[pos]).children())){
			$(subMenus[pos]).show();
			subMenu.show();
		}
		if(pos==0){
			$.ajax({
				url : "courseInstanceList.sw",
				data : {courseId : courseId},
				success : function(data, status, jqXHR) {
					$('.js_course_content').html(data);
				}
			});
		}else if(pos==1){
			$.ajax({
				url : "courseMissionList.sw",
				data : {courseId : courseId},
				success : function(data, status, jqXHR) {
					$('.js_course_content').html(data);
				}
			});
		}else if(pos==2){
			$.ajax({
				url : "courseBoard.sw",
				data : {courseId : courseId},
				success : function(data, status, jqXHR) {
					$('.js_course_content').html(data);
				}
			});
		}else if(pos==3){
			$.ajax({
				url : "courseTeamCreate.sw",
				data : {courseId : courseId},
				success : function(data, status, jqXHR) {
					$('.js_course_content').html(data);
				}
			});
		}else if(pos==4){
			$.ajax({
				url : "courseGeneral.sw",
				data : {courseId : courseId},
				success : function(data, status, jqXHR) {
					$('.js_course_content').html(data);
				}
			});
		}else if(pos==5){
			if(isEmpty($(subMenus[pos]).children())){
				$.ajax({
					url : "courseSetting.sw",
					data : {courseId : courseId},
					success : function(data, status, jqXHR) {
						$('.js_course_content').html(data);
					}
				});
				
			}else{
				$('.js_course_setting_menu li.js_course_setting_profile a').click();
			}
		}
		return false;
	});
	
	$('.js_course_setting_menu').live('click', function(e){
		var input = $(e.target).parent();
		input.siblings().removeClass('current');
		input.addClass('current');
		var courseHome = input.parents('.js_course_home_page');
		var courseId = courseHome.attr('courseId');
		var url ="";
		if(input.hasClass('js_course_setting_profile')){
			url = "courseSettingProfile.sw";
		}else if(input.hasClass('js_course_setting_mentee')){
			url = "courseSettingMentee.sw";
		}else if(input.hasClass('js_course_setting_team')){
			url = "courseSettingTeam.sw";
		}
		$.ajax({
			url : url,
			data : {courseId : courseId},
			success : function(data, status, jqXHR) {
				$('.js_course_content').html(data);
			}
		});
		return false;
	});

	$('.js_create_mission').live('click', function(e){
		var input = $(e.target).parent();
		var courseHome = input.parents('.js_course_home_page');
		var courseId = courseHome.attr('courseId');
		$.ajax({
			url : "courseMissionCreate.sw",
			data : {
				courseId : courseId
			},
			success : function(data, status, jqXHR) {
				$('.js_course_content').html(data);
			}
		});
		return false;
	});
	
	$('.js_create_team').live('click', function(e){
		var input = $(e.target).parent();
		var courseHome = input.parents('.js_course_home_page');
		var courseId = courseHome.attr('courseId');
		$.ajax({
			url : "courseTeamCreate.sw",
			data : {
				courseId : courseId
			},
			success : function(data, status, jqXHR) {
				$('.js_course_content').html(data);
			}
		});
		return false;
	});
	
	$('.js_course_mission_menu').live('click', function(e){
		var input = $(e.target).parent();
		input.siblings().removeClass('current');
		input.addClass('current');
		var courseHome = input.parents('.js_course_home_page');
		var courseId = courseHome.attr('courseId');
		var url ="";
		var target = $('.js_course_content');
		if(input.hasClass('js_course_mission_space')){
			url = "courseMissionSpace.sw";			
		}else if(input.hasClass('js_course_mission_create')){
			url = "courseMissionCreate.sw";
		}else if(input.hasClass('js_course_mission_list')){
			url = "courseMissionList.sw";
		}else if(input.hasClass('js_course_mission_mine')){
			url = "courseMissionMine.sw";
		}
		$.ajax({
			url : url,
			data : {courseId : courseId},
			success : function(data, status, jqXHR) {
				target.html(data);
			}
		});
		return false;
	});

	$('.js_view_user_instances').live('click', function(e){
		var input = $(e.target);
		input.parent().siblings().find('a').removeClass('current');
		input.addClass('current');
		var userId = input.attr('userId');
		var courseInstanceList = input.parents('.js_course_instance_list_page');
		var courseId = (isEmpty(courseInstanceList)) ? null : courseInstanceList.attr('courseId');
		$.ajax({
			url : 'seraInstances.sw',
			data : {
				userId : userId,
				courseId : courseId
			},
			success : function(data, status, jqXHR) {
				$('.js_user_instance_list').html(data);
			}
		});
		return false;
	});

	$('.js_view_all_instances').live('click', function(e){
		var input = $(e.target);
		input.parent().siblings().find('a').removeClass('current');
		input.addClass('current');
		var courseInstanceList = input.parents('.js_course_instance_list_page');
		var courseId = (isEmpty(courseInstanceList)) ? null : courseInstanceList.attr('courseId');
		$.ajax({
			url : 'seraInstances.sw',
			data : {
				courseId : courseId
			},
			success : function(data, status, jqXHR) {
				$('.js_user_instance_list').html(data);
			}
		});
		return false;
	});
	
	$('.js_mentor_form_btn').live('click', function(e){
		var input = $(e.target).parents('.js_mentor_form_btn');
		var createCourse = input.parents('.js_create_course_page');
		if(createCourse.find('input[name="chkUserDefineDays"]').attr('checked')==='checked'){
			createCourse.find('input[name="txtCourseStartDate"]').addClass('required');
			createCourse.find('input[name="txtCourseEndDate"]').addClass('required');
			createCourse.find('input[name="txtCourseDays"]').removeClass('required').removeClass('error');			
			
		}else{
			createCourse.find('input[name="txtCourseStartDate"]').removeClass('required').removeClass('error');
			createCourse.find('input[name="txtCourseEndDate"]').removeClass('required').removeClass('error');
			createCourse.find('input[name="txtCourseDays"]').addClass('required');			
		}
		
		if(createCourse.find('input[name="chkCourseUsers"]:checked').attr('value')==='userInput'){
			createCourse.find('input[name="txtCourseUsers"]').addClass('required');
			
		}else{
			createCourse.find('input[name="txtCourseUsers"]').removeClass('required').removeClass('error');
		}
		
		if(createCourse.find('input[name="chkCourseFee"]:checked').attr('value')==='pay'){
			createCourse.find('input[name="txtCourseFee"]').addClass('required');
			
		}else{
			createCourse.find('input[name="txtCourseFee"]').removeClass('required').removeClass('error');
		}
		
		if (SmartWorks.GridLayout.validate(createCourse.find('form.js_validation_required'), createCourse.find('.sw_error_message'))) {		
			input.parents('.js_create_buttons').hide().siblings().css({clear:"both", display:"inline-block"});
			createCourse.find('.js_create_course_table').hide();
			createCourse.find('.js_mentor_profile_table').show();
			return false;
		}
		return false;
	});

	$('.js_create_course_btn').live('click', function(e){
		submitForms(e);
		return false;
	});
	
	$('.js_back_to_create_btn').live('click', function(e){
		var input = $(e.target).parents('.js_back_to_create_btn');		
		input.parents('.js_mentor_buttons').hide().siblings().css({clear:"both", display:"inline-block"});
		var createCourse = input.parents('.js_create_course_page');
		createCourse.find('.js_create_course_table').show();
		createCourse.find('.js_mentor_profile_table').hide();
		return false;
	});

	$('.js_join_course_request').live('click', function(e){
		var input = $(e.target).parents('.js_join_course_request');
		var paramsJson = {};
		paramsJson["courseId"] = input.parents('.js_course_content').attr('courseId');
		paramsJson["userId"] = currentUserId;
		$.ajax({
			url : 'join_group_request.sw',
			contentType : 'application/json',
			type : 'POST',
			data : JSON.stringify(paramsJson),
			success : function(data, status, jqXHR) {
				if(input.attr('autoApproval')==="true")
					smartPop.showInfo(smartPop.INFO, "코스가입 정상적으로 처리되었습니다. 코스에 방문하시면 미션들을 수행할 수 있습니다.");
				else
					smartPop.showInfo(smartPop.INFO, "코스가입 신청이 정상적으로 처리되었으며, 멘토의 가입승인을 기다리고 있습니다.");
				input.remove();
			},
			error : function(){
				smartPop.showInfo(smartPop.ERROR, "코스가입하기에 문제가 발생하였습니다. 관리자에게 문의하시기 바랍니다.");
			}
		});
		return false;
	});
	
	$('.js_prev_month_mission').live('click', function(e){
		var input = $(e.target);
		var missionCreate = input.parents('.js_mission_list_page');
		$.ajax({
			url : 'courseMissionSpace.sw',
			data : {
				courseId : missionCreate.attr('courseId'),
				today : missionCreate.attr('prevMonth')
			},
			success : function(data, status, jqXHR) {
				$('.js_course_content').html(data);
			}
		});
		return false;
	});
	
	$('.js_next_month_mission').live('click', function(e){
		var input = $(e.target);
		var missionCreate = input.parents('.js_mission_list_page');
		$.ajax({
			url : 'courseMissionSpace.sw',
			data : {
				courseId : missionCreate.attr('courseId'),
				today : missionCreate.attr('nextMonth')
			},
			success : function(data, status, jqXHR) {
				$('.js_course_content').html(data);
			}
		});
		return false;
	});
	
	$('.js_select_mission').live('click', function(e){
		var input = $(e.target).parent('a');
		var missionList = input.parents('.js_mission_list_page');
		var courseId = missionList.attr('courseId');
		var missionId = input.attr('missionId');
		$.ajax({
			url : 'courseMissionPerform.sw',
			data : {
				courseId :courseId,
				missionId : missionId
			},
			success : function(data, status, jqXHR) {
				$('.js_course_content').html(data);
			}
		});
		return false;
	});
	
	$('.js_create_mission_btn').live('click', function(e){
		submitForms(e);
		return false;
	});
	
	$('.js_note_buttons').live('click', function(e){
		var input = $(e.target);
		var noteAttachmentTable = input.parents('.js_note_buttons').siblings('.js_note_attachment_table');
		var targetTable = [];
		if(!isEmpty(input.hasClass('js_note_file_btn')))
			targetTable = noteAttachmentTable.find('.js_note_file');
		else if(!isEmpty(input.hasClass('js_note_image_btn')))
			targetTable = noteAttachmentTable.find('.js_note_image');
		else if(!isEmpty(input.hasClass('js_note_video_btn')))
			targetTable = noteAttachmentTable.find('.js_note_video');
		else if(!isEmpty(input.hasClass('js_note_link_btn')))
			targetTable = noteAttachmentTable.find('.js_note_link');
		if(noteAttachmentTable.is(':visible')){
			noteAttachmentTable.show();
			targetTable.toggle();
			if(isEmpty(noteAttachmentTable.find('tr:visible')))
				noteAttachmentTable.hide();
		}else{
			noteAttachmentTable.show();
			targetTable.show();
		}
		return false;
	});
	
	$('.js_create_note_btn').live('click', function(e){
		submitForms(e);
		return false;
	});
	
	$('.js_report_content').live('click', function(e){
		$(e.target).attr('rows', 24);
	});
	
	$('.js_create_team_btn').live('click', function(e){
		submitForms(e);
		return false;
	});

	$('.js_modify_profile_btn').live('click', function(e){
		submitForms(e);
		return false;
	});
	
	$('.js_return_on_sera_comment').live('keydown', function(e) {
		if(e.which != $.ui.keyCode.ENTER) return;
		var input = $(e.target);
		var subInstanceList = input.parents('.js_sub_instance_list');
		var comment = input.attr('value');
		if(isEmpty(comment)) return false;
		var	workInstanceId = subInstanceList.attr('instanceId');
		var	workType = subInstanceList.attr('workType');
		var paramsJson = {};
		paramsJson['workType'] = parseInt(workType);
		paramsJson['workInstanceId'] = workInstanceId;
		url = "add_comment_on_instance.sw";
		paramsJson['comment'] = comment;
		console.log(JSON.stringify(paramsJson));
		$.ajax({
			url : url,
			contentType : 'application/json',
			type : 'POST',
			data : JSON.stringify(paramsJson),
			success : function(data, status, jqXHR) {
				var target = subInstanceList.find('.js_comment_list');
				var showAllComments = target.find('.js_show_all_sera_comments');
				if(!isEmpty(showAllComments)){
					showAllComments.find('div').click();
					input.attr('value', '');
				}else{
					var newComment = target.find('.js_comment_instance').clone().show().removeClass('js_comment_instance');
					newComment.find('.js_comment_content').html(comment);
					target.append(newComment);
					input.attr('value', '');
				}
			},
			error : function(e) {
				// 서비스 에러시에는 메시지를 보여주고 현재페이지에 그래도 있는다...
				smartPop.showInfo(smartPop.ERROR, "댓글달기에 오류가 발생하였습니다. 관리자에게 문의하시기 바랍니다.", function(){
				});
				
			}
			
		});
		
	});
	
	$('.js_show_all_sera_comments').live('click', function(e) {
		var input = $(e.target).parents('.js_show_all_sera_comments');
		var subInstanceList = input.parents('.js_sub_instance_list');
		var href = input.attr('href');
		$.ajax({
			url : href,
			data : {},
			success : function(data, status, jqXHR) {
				var target = subInstanceList.find('.js_comment_list');
				target.find(':visible').remove();
				target.append(data);
				input.remove();
			},
			error : function(e) {
				// 서비스 에러시에는 메시지를 보여주고 현재페이지에 그래도 있는다...
				smartPop.showInfo(smartPop.ERROR, "댓글 모두보기에 오류가 발생하였습니다. 담당자에게 연락하시기 바랍니다.", function(){
				});
				
			}
			
		});
		return false;
	});
	
	$('a.js_add_sera_comment').live('click', function(e){
		var input = $(e.target).removeAttr('href');
		console.log('input=', input);
		input.parents('.js_sub_instance_list').find('.js_return_on_sera_comment').show();
		return false;
	});

	$('a.js_add_sera_like').live('click', function(e){
		var input = $(e.target).removeAttr('href');
		
	});
	
	$('.js_accept_friend_btn').live('click', function(e){
		var input = $(e.target).parents('.js_accept_friend_btn');
		var friendRequest = input.parents('.js_friend_request_item');
		var requesterCount = input.parents('.js_friend_page').find('.js_requester_count');
		var userId = friendRequest.attr('userId');
		var paramsJson = {};
		paramsJson['userId'] = userId;
		paramsJson['accepted'] = true;		
		$.ajax({
			url : 'reply_friend_request.sw',
			contentType : 'application/json',
			type : 'POST',
			data : JSON.stringify(paramsJson),
			success : function(data, status, jqXHR) {
				friendRequest.remove();
				var count = requesterCount.attr('count');
				if(!isEmpty(count) && (count!=='0')){
					count = parseInt(count)-1;
					requesterCount.html('(' + (count) + ')');
					requesterCount.attr('count', count);
				}
			},
			error : function(e) {
				// 서비스 에러시에는 메시지를 보여주고 현재페이지에 그래도 있는다...
				smartPop.showInfo(smartPop.ERROR, "친구요청 수락에 오류가 발생하였습니다. 관리자에게 문의하시기 바랍니다.", function(){
				});	
			}			
		});
	});

	$('.js_deny_friend_btn').live('click', function(e){
		var input = $(e.target).parents('.js_deny_friend_btn');
		var friendRequest = input.parents('.js_friend_request_item');
		var requesterCount = input.parents('.js_friend_page').find('.js_requester_count');
		var userId = friendRequest.attr('userId');
		var paramsJson = {};
		paramsJson['userId'] = userId;
		paramsJson['accepted'] = false;		
		$.ajax({
			url : 'reply_friend_request.sw',
			contentType : 'application/json',
			type : 'POST',
			data : JSON.stringify(paramsJson),
			success : function(data, status, jqXHR) {
				friendRequest.remove();
				var count = requesterCount.attr('count');
				if(!isEmpty(count) && (count!=='0')){
					count = parseInt(count)-1;
					requesterCount.html('(' + (count) + ')');
					requesterCount.attr('count', count);
				}
			},
			error : function(e) {
				// 서비스 에러시에는 메시지를 보여주고 현재페이지에 그래도 있는다...
				smartPop.showInfo(smartPop.ERROR, "친구요청 나중에확인에 오류가 발생하였습니다. 관리자에게 문의하시기 바랍니다.", function(){
				});				
			}
		});
	});

	$('.js_destroy_friendship_btn').live('click', function(e){
		var input = $(e.target).parents('.js_destroy_friendship_btn');
		var friend = input.parents('.js_friend_item');
		var friendCount = input.parents('.js_friend_page').find('.js_friend_count');
		var userId = friend.attr('userId');
		var paramsJson = {};
		paramsJson['userId'] = userId;
		$.ajax({
			url : 'destroy_friendship.sw',
			contentType : 'application/json',
			type : 'POST',
			data : JSON.stringify(paramsJson),
			success : function(data, status, jqXHR) {
				friend.remove();
				var count = friendCount.html();
				if(!isEmpty(count) && (count!=='0')){
					count = parseInt(count)-1;
					friendCount.html(count);
				}
			},
			error : function(e) {
				// 서비스 에러시에는 메시지를 보여주고 현재페이지에 그래도 있는다...
				smartPop.showInfo(smartPop.ERROR, "친구끊기에 오류가 발생하였습니다. 관리자에게 문의하시기 바랍니다.", function(){
				});				
			}
		});
	});

	$('.js_friend_request_btn').live('click', function(e){
		var input = $(e.target).parents('.js_friend_request_btn');
		var userId = input.attr('userId');
		var paramsJson = {};
		paramsJson['userId'] = userId;
		$.ajax({
			url : 'friend_request.sw',
			contentType : 'application/json',
			type : 'POST',
			data : JSON.stringify(paramsJson),
			success : function(data, status, jqXHR) {
				smartPop.showInfo(smartPop.INFORM, "친구요청이 성공적으로 이루어 졌습니다.", function(){
				});				
				input.remove();
			},
			error : function(e) {
				// 서비스 에러시에는 메시지를 보여주고 현재페이지에 그래도 있는다...
				smartPop.showInfo(smartPop.ERROR, "친구요청에 오류가 발생하였습니다. 관리자에게 문의하시기 바랍니다.", function(){
				});				
			}
		});
	});

});
