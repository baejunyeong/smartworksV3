<%@page import="net.smartworks.util.SmartUtil"%>
<%@page import="net.smartworks.model.community.User"%>
<%@page import="net.smartworks.service.ISmartWorks"%>
<%@ page contentType="text/html; charset=utf-8"%>

<%
	// 스마트웍스 서비스들을 사용하기위한 핸들러를 가져온다. 그리고 현재사용자 정보도 가져온다.	
	ISmartWorks smartWorks = (ISmartWorks) request.getAttribute("smartWorks");
	User cUser = SmartUtil.getCurrentUser();
	String cid = request.getParameter("cid");
	if (SmartUtil.isBlankObject(cid))
		cid = ISmartWorks.CONTEXT_PREFIX_HOME + cUser.getId();
	String wid = request.getParameter("wid");
	if (SmartUtil.isBlankObject(wid))
		wid = cUser.getId();
%>
<!-- Panel Section -->
<div class="panel_section">
	<div>
		<ul class="panel_area">
			<!-- photo-->
			<li class="">
				<div class="photo_bg">
					<img src="../images/photo_default72.jpg" />
					<div class="rgt_name">사랑사랑</div>
				</div>
				<div class="grade">
					<div class="icon_mentor current"></div>
					<div class="icon_star"></div>
					<div class="icon_heart"></div>
				</div>
			</li>
			<!-- photo//-->
			<!-- comment -->
			<li class="fr">
				<div class="point"></div>
				<div class="panel_block fr">
					<dl class="content">
						<dt class="name">
							[코스명1] <span class="t_redb">[미션1-자화상 그리기]</span> <span
								class="icon_delete fr"><a href="">삭제</a> </span>
						</dt>
						<dd>
							<div class="text">시간이 지날수록 제 얼굴이 찐빵으로 그려지네요~시간이 지날수록 제 얼굴이
								찐빵으로 그려지네요~시간이 지날수록 제 얼굴이 찐빵으로 그려지네요~시간이 지날수록 제 얼굴이 찐빵으로
								그려지네요~시간이 지날수록 제 얼굴이 찐빵으로 그려지네요~시간이 지날수록 제 얼굴이 찐빵으로 그려지네요~시간이
								지날수록 제 얼굴이 찐빵으로 그려지네요~시간이 지날수록 제 얼굴이 찐빵으로 그려지네요~시간이 지날수록 제 얼굴이
								찐빵으로 그려지네요~시간이 지날수록 제 얼굴이 찐빵으로 그려지네요~시간이 지날수록 제 얼굴이 찐빵으로 그려지네요~</div>
							<!-- Thum Image-->
							<div class="thum_image">
								<img src="../images/thum_image.jpg" />
							</div>
							<!-- Thum Image//-->
						</dd>
						<!-- Util -->
						<dd class="util">
							<span><a href="">답변달기</a> | </span> <span><a href="">댓글달기</a>
								| </span> <span><a href="">공감하기</a> | </span> <span><a href="">더보기</a>
								| </span> <span class="date">2012 년 03 월 09 일</span>
						</dd>
						<!-- Util //-->
					</dl>
					<div class="stat_notice" style="display: none">7개의 댓글이 모두보기</div>
					<!-- Reply-->
					<div class="reply_section">
						<div class="photo">
							<img src="../images/photo_mid48.jpg" />
						</div>
						<div class="reply_text">
							<span class="name">닉네임은 일곱자 : </span> 점점 시간이 지날수록 얼굴이 찐빵으로
							그려지네요~~점점 시간이 지날수록 얼굴이 찐빵으로 그려지네요~~점점 시간이 지날수록 얼굴이 찐빵으로 그려지네요~~점점
							시간이 지날수록 얼굴이 찐빵으로 그려지네요~~점점 시간이 지날수록 얼굴이 찐빵으로 그려지네요~~점점 시간이 지날수록
							얼굴이 찐빵으로 그려지네요~~
							<div class="icon_date">3월 5일</div>
						</div>
					</div>
					<!-- Reply//-->
					<!-- Reply-->
					<div class="reply_section">
						<div class="photo">
							<img src="../images/photo_mid48.jpg" />
						</div>
						<div class="reply_text">
							<span class="name">닉네임은 일곱자 : </span> 점점 시간이 지날수록 얼굴이 찐빵으로
							그려지네요~~점점 시간이 지날수록 얼굴이 찐빵으로 그려지네요~~점점 시간이 지날수록 얼굴이 찐빵으로 그려지네요~~점점
							시간이 지날수록 얼굴이 찐빵으로 그려지네요~~점점 시간이 지날수록 얼굴이 찐빵으로 그려지네요~~점점 시간이 지날수록
							얼굴이 찐빵으로 그려지네요~~
							<div class="icon_date">3월 5일</div>
						</div>
					</div>
					<!-- Reply//-->
					<!-- Reply-->
					<div class="reply_section end">
						<div class="photo">
							<img src="../images/photo_mid48.jpg" />
							<div class="name mt2">닉네임은</div>
						</div>
						<div class="reply_text">
							<textarea name="" cols="" rows="" class="fr" style="width: 100%"
								placeholder="댓글을 남겨주세요!"></textarea>
						</div>
						<div class="btn_default_l cb fr mt5">
							<div class="btn_default_r">등록</div>
						</div>
					</div>
					<!-- Reply//-->
				</div>
			</li>
			<!-- comment //-->
		</ul>
	</div>

	<div>
		<ul class="panel_area">
			<!-- photo-->
			<li class="">
				<div class="photo_bg">
					<img src="../images/photo_default72.jpg" />
					<div class="rgt_name">사랑사랑</div>
				</div>
				<div class="grade">
					<div class="icon_mentor current"></div>
					<div class="icon_star"></div>
					<div class="icon_heart"></div>
				</div>
			</li>
			<!-- photo//-->
			<!-- comment -->
			<li class="fr">
				<div class="point"></div>
				<div class="panel_block fr">
					<dl class="content">
						<dt class="name">
							[코스명1] [미션4- 미션제목]<span class="icon_delete fr"><a href="">삭제</a>
							</span>
						</dt>
						<dd>
							<div class="text">시간이 지날수록 제 얼굴이 찐빵으로 그려지네요~시간이 지날수록 제 얼굴이
								찐빵으로 그려지네요~시간이 지날수록 제 얼굴이 찐빵으로 그려지네요~시</div>
							<!-- Thum Image-->
							<div class="thum_image">
								<img src="../images/thum_image.jpg" />
							</div>
							<!-- Thum Image//-->
						</dd>
						<!-- Util -->
						<dd class="util">
							<span><a href="">답변달기</a> | </span> <span><a href="">댓글달기</a>
								| </span> <span><a href="">공감하기</a> | </span> <span><a href="">더보기</a>
								| </span> <span class="date">2012 년 03 월 09 일</span>
						</dd>
						<!-- Util //-->
					</dl>
					<div class="stat_notice" style="display: none">7개의 댓글이 모두보기</div>
					<!-- Reply-->
					<div class="reply_section end">
						<div class="photo">
							<img src="../images/photo_mid48.jpg" />
							<div class="name mt2">닉네임은</div>
						</div>
						<div class="reply_text">
							<textarea name="" cols="" rows="" class="fr" style="width: 100%"
								placeholder="답변을 보내세요!"></textarea>
						</div>
						<!-- 우측 버튼 영역 -->
						<div class="attach_file">
							<ul>
								<li class="t_s11"><span class="t_red">0</span> /1000kbyte</li>
								<li class="icon_memo bb ml10"><a href=""> </a></li>
								<li class="icon_video bb"><a href=""> </a></li>
								<li class="icon_photo bb"><a href=""> </a></li>
								<li class="icon_link bb"><a href=""> </a></li>
								<!-- Btn 등록-->
								<li class="btn_default_l ml10">
									<div class="btn_default_r">등록</div>
								</li>
								<!-- Btn 등록//-->
							</ul>
						</div>
						<!-- 우측 버튼 영역 //-->
					</div>
					<!-- Reply//-->
				</div>
			</li>
			<!-- comment //-->
		</ul>
	</div>

	<div>
		<ul class="panel_area">
			<!-- photo-->
			<li class="">
				<div class="photo_bg">
					<img src="../images/photo_default72.jpg" />
					<div class="rgt_name">사랑사랑</div>
				</div>
				<div class="grade">
					<div class="icon_mentor current"></div>
					<div class="icon_star"></div>
					<div class="icon_heart"></div>
				</div>
			</li>
			<!-- photo//-->
			<!-- comment -->
			<li class="fr">
				<div class="point"></div>
				<div class="panel_block fr">
					<dl class="content">
						<dt class="name">
							[코스명1] [미션3- 미션제목]<span class="icon_delete fr"><a href="">삭제</a>
							</span>
						</dt>
						<dd>
							<div class="text">시간이 지날수록 제 얼굴이 찐빵으로 그려지네요~시간이 지날수록 제 얼굴이
								찐빵으로 그려지네요~시간이 지날수록 제 얼굴이 찐빵으로 그려지네요~시</div>
							<!-- Thum Image-->
							<div class="thum_image">
								<img src="../images/thum_image.jpg" />
							</div>
							<!-- Thum Image//-->
						</dd>
						<!-- Util -->
						<dd class="util">
							<span><a href="">답변달기</a> | </span> <span><a href="">댓글달기</a>
								| </span> <span><a href="">공감하기</a> | </span> <span><a href="">더보기</a>
								| </span> <span class="date">2012 년 03 월 09 일</span>
						</dd>
						<!-- Util //-->
					</dl>
					<div class="stat_notice" style="display: none">7개의 댓글이 모두보기</div>
					<!-- Reply-->
					<div class="reply_section end">
						<div class="photo">
							<img src="../images/photo_mid48.jpg" />
							<div class="name mt2">닉네임은</div>
						</div>
						<div class="reply_text">
							<textarea name="" cols="" rows="" class="fr" style="width: 100%"
								placeholder="답변을 보내세요!"></textarea>
						</div>
						<!-- 우측 버튼 영역 -->
						<div class="attach_file">
							<ul>
								<li class="t_s11"><span class="t_red">0</span> /1000kbyte</li>
								<li class="icon_memo bb ml10"><a href=""> </a></li>
								<li class="icon_video bb"><a href=""> </a></li>
								<li class="icon_photo bb"><a href=""> </a></li>
								<li class="icon_link bb"><a href=""> </a></li>
								<!-- Btn 등록-->
								<li class="btn_default_l ml10">
									<div class="btn_default_r">등록</div>
								</li>
								<!-- Btn 등록//-->
							</ul>
						</div>
						<!-- 우측 버튼 영역 //-->
					</div>
					<!-- Reply//-->
				</div>
			</li>
			<!-- comment //-->
		</ul>
	</div>

</div>
<!-- Panel Section //-->