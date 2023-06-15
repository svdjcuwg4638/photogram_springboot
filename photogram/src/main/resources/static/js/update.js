// (1) 회원정보 수정
function update(userId,event) {
	event.preventDefault(); //폼태그 엑션을 막기
	//	폼태그의 id=profileUpdate의 serialize를 하게되면 name=value&age=value 식으로 값이 넘어온다
	let data = $("#profileUpdate").serialize();
	console.log(data)
	
	$.ajax({
		type:"put",
		url:`/api/user/${userId}`,
		data : data,
//		콘솔에서 확인했듯이 key=value형태는 x-www-form-urlencoded타입이다.
		contentType:"application/x-www-form-urlencoded; charset=utf-8",
		dataType:"json"
	}).done(res=>{ // httpStatus 상태코드 200번대
		console.log("성공",res);
		location.href=`/user/${userId}`
	}).fail(error=>{ // httpStatus 상태코드 200번대가 아닐 때 
		if(error.data == null){
			alert(error.responseJSON.message)
		}else{
			alert(JSON.stringfy(error.responseJSON.data));	
		}
//		alert(JSON.stringify(error.responseJSON.data)) 
	})
}