/**
	2. 스토리 페이지
	(1) 스토리 로드하기
	(2) 스토리 스크롤 페이징하기
	(3) 좋아요, 안좋아요
	(4) 댓글쓰기
	(5) 댓글삭제
 */

// (0) 현재 로긴한 사용자 아이디
let principalId = $("#principalId").val();


// (1) 스토리 로드하기
let page  = 0;

function storyLoad() {

	$.ajax({
		url : `/api/image?page=${page}`,
		dataType : "json"
	}).done(res=>{
		console.log(res);
		res.data.content.forEach((image)=>{
			let stoyItem = getStoryItem(image);
			$("#storyList").append(stoyItem);
		})
	}).fail(err=>{
		console.log("오류",err)
	})

}

storyLoad();

function getStoryItem(image) {
// onerror는 만약 사진이없다면 이사진으로 대체하는 기능이다.
item = `
<div class="story-list__item">
  <div class="sl__item__header">
    <div>
      <img class="profile-image" src="/upload/${image.user.profileimageUrl}" 
      onerror="this.src='/images/person.jpeg'" />
    </div>
    <div>${image.user.username}</div>
  </div>

  <div class="sl__item__img">
    <img src="/upload/${image.postImageUrl}" />
  </div>

  <div class="sl__item__contents">
    <div class="sl__item__contents__icon">

      <button>`;
		
		if(image.likeState){
        	item += `<i class="fas fa-heart active" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`
		}else{
			item += `<i class="far fa-heart" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`
		}      
        
       item +=`
      </button>
    </div>

    <span class="like"><b id="storyLikeCount-${image.id}">${image.likeCount}</b>likes</span>

    <div class="sl__item__contents__content">
      <p>${image.caption}</p>
    </div>
    <div id="storyCommentList-${image.id}">`;
    
    image.comments.forEach((comment)=>{
		item +=`
		<div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}">
					<p>
						<b>${comment.user.username}</b>${comment.content}
					</p>`;
		
		if(principalId == comment.user.id){
			item +=
			`
				<button onclick="deleteComment(${comment.id})">
					<i class=" fas fa-times"></i>
		        </button>
		    `
		}
	        
	    item += 
	    `</div>`
		;
	});
	item +=
	`
    </div>
	    <div class="sl__item__input">
	      <input type="text" placeholder="댓글 달기..." id="storyCommentInput-${image.id}" />
	      <button type="button" onClick="addComment(${image.id})">게시</button>
	    </div>
	
	  </div>
	</div>
	`
	return item;
}

// (2) 스토리 스크롤 페이징하기
$(window).scroll(() => {
	
	let checkNum = $(window).scrollTop() -($(document).height() - $(window).height());
	
	if(checkNum < 10 && checkNum > -10){
		page++;
		storyLoad();
	}
});


// (3) 좋아요, 안좋아요
function toggleLike(imageId) {
	let likeIcon = $(`#storyLikeIcon-${imageId}`);
	if (likeIcon.hasClass("far")) { // 좋아요 하겠다
		$.ajax({
			type:"post",
			url:`/api/image/${imageId}/likes`,
			dataType:"json"
		}).done(res=>{
			let likeCountStr = $(`#storyLikeCount-${imageId}`).text();
			let likeCount = Number(likeCountStr) +1;
			$(`#storyLikeCount-${imageId}`).text(likeCount)
			
			likeIcon.addClass("fas");
			likeIcon.addClass("active");
			likeIcon.removeClass("far");
		}).fail(err=>{
			console.log("오류",err)
		})
	} else { // 좋아요 취소
				$.ajax({
			type:"delete",
			url:`/api/image/${imageId}/likes`,
			dataType:"json"
		}).done(res=>{
			let likeCountStr = $(`#storyLikeCount-${imageId}`).text();
			let likeCount = Number(likeCountStr) -1;
			$(`#storyLikeCount-${imageId}`).text(likeCount)
			
			likeIcon.removeClass("fas");
			likeIcon.removeClass("active");
			likeIcon.addClass("far");
		}).fail(err=>{
			console.log("오류",err)
		})
	
	}
}

// (4) 댓글쓰기
function addComment(imageId) {

	let commentInput = $(`#storyCommentInput-${imageId}`);
	let commentList = $(`#storyCommentList-${imageId}`);

	let data = {
		imageId:imageId,
		content: commentInput.val()
	}
	// data에 내가 서버에 보내고싶은 데이터를 담아놓는다
	if (data.content === "") {
		alert("댓글을 작성해주세요!");
		return;
	}
	
	$.ajax({
		type:"post",
		url:"/api/comment",
		data:JSON.stringify(data),
		contentType:"application/json;charset=utf-8",
		// 문자가 안깨지도록 utf-8을 설정
		dataType:"json"
	}).done(res=>{
	let comment = 	res.data;
		
	let content = `
	  <div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}"> 
	    <p>
	      <b>${comment.user.username}</b>
	      ${comment.content}
	    </p>
	    <button onclick="deleteComment(${comment.id})"><i class="fas fa-times"></i></button>
	  </div>
	`;
	commentList.prepend(content);
		
	}).fail(err=>{
		console.log("에러",err.responseJSON.data.content);
		alert(err.responseJSON.data.content)
	});

	// perpend는 위에서부터 채워주고
	// append는 밑에서부터 채워준다
	// 즉 최신 댓글이 맨위로 오게된다.
	commentInput.val("");
}

// (5) 댓글 삭제
function deleteComment(commentId) {

	$.ajax({
		type:"delete",
		url:`/api/comment/${commentId}`,
		dataType:"json"
	}).done(res=>{
		console.log("성공",res)		
		$(`#storyCommentItem-${commentId}`).remove();
	}).fail(err=>{
		console.log("실패",err)
	})
}







