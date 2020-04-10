/**
 * 提交评论/二级评论
 */
function postComment() {
    var questionId = $("#question_id").val();//jquery取值
    var content = $("#comment_content").val();
    post(questionId, content, 1);
}

function postSubComment(e) {
    var id = e.getAttribute("data-id");
    var content = $("#input-" + id).val();
    post(id, content, 2);
}

function post(targetId, content, type) {
    if (!content) {
        alert("输入内容不能为空");
        return;
    }
    console.log(content);
    $.ajax({    //ajax.post
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                //$("#return_section").hide();
                window.location.reload();
            } else if (response.code == 2003) {
                var isAccepted = confirm(response.message);//弹窗返回值
                if (isAccepted) {
                    window.open("https://github.com/login/oauth/authorize?client_id=Iv1.5b4d5e7a1a13bf8c&redirect_uri=http://localhost:8888/callback&scope=user&state=1");
                    window.localStorage.setItem("closable", true);
                    //window.location.reload();
                }
            } else {
                alert(response.message);
            }
            console.log(response);
        },
        dataType: "json"
    });
}

/**
 * 展开二级评论
 */
function collapseComment(e) {
    var id = e.getAttribute("data-id");
    var subComment = $("#comment-" + id);
    subComment.toggleClass("in");
    e.classList.toggle("active-icon");

    var subbComment = $("#comment-" + id);
    if (subComment.hasClass("in")/*&&subCommentContainer.children().length != 1*/) {
        $.getJSON("/comment/" + id, function (data) {
            $.each(data.data.reverse(), function (index, comment) {
                console.log(data.data);
                var mediaLeftElement = $("<div/>", {
                    "class": "media-left"
                }).append($("<img/>", {
                    "class": "media-object img-rounded",
                    "src": comment.user.avatarUrl
                }));

                var mediaBodyElement = $("<div/>", {
                    "class": "media-body"
                }).append($("<h5/>", {
                    "class": "media-heading",
                    "html": comment.user.name
                })).append($("<div/>", {
                    "html": comment.content
                })).append($("<div/>", {
                    "class": "menu"
                }).append($("<span/>", {
                    "class": "pull-right",
                    "html": moment(comment.gmtCreate).format('YYYY-MM-DD  h:mm:ss')
                })));

                var mediaElement = $("<div/>", {
                    "class": "media"
                }).append(mediaLeftElement).append(mediaBodyElement);

                var commentElement = $("<div/>", {
                    "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12"
                }).append(mediaElement).append($("<hr/>", {
                    "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12"
                }));

                subbComment.prepend(commentElement);
            });
        })
    }
}

function selectTags(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    var previouses = previous.split('；');
    if (previouses.indexOf(value) != -1) {

    } else {
        if (previous) {
            $("#tag").val(previous + '；' + value);
        } else {
            $("#tag").val(value);
        }
    }
}

function showSelectTags() {
    $("#select-tags").show();

}
