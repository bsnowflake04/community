function post() {
    var questionId = $("#question_id").val();//jquery取值
    var content = $("#comment_content").val();
    console.log(questionId);
    console.log(content);
    $.ajax({    //ajax.post
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": questionId,
            "content": content,
            "type": 1
        }),
        success: function (response) {
            if (response.code == 200) {
                $("#return_section").hide();
            } else if (response.code == 2003) {
                var isAccepted = confirm(response.message);//弹窗返回值
                if (isAccepted) {
                    window.open("https://github.com/login/oauth/authorize?client_id=Iv1.5b4d5e7a1a13bf8c&redirect_uri=http://localhost:8888/callback&scope=user&state=1");
                    window.localStorage.setItem("closable", true);
                    //window.location.reload();
                }
            } else {
                alert(response.message());
            }
            console.log(response);
        },
        dataType: "json"
    });
}