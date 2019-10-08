/**
 * 提交回复
 */
function post() {
    var parentId = $("#parent_id").val();
    var content = $("#content").val();
    coomentTarget(parentId, content);

}

function coomentTarget(targetId, content) {
    if (!content) {
        alert("亲~要填写评论内容哦~");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                if (response.code == 2003) {
                    var confirmValue = confirm(response.message);
                    if (confirmValue) {
                        window.open("https://github.com/login/oauth/authorize?client_id=de6af14bed3587befff3&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}

function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();

    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous + ',' + value);
        } else {
            $("#tag").val(value);
        }
    }
}

function showSelectTag() {
    $("#select-tag").show();
}


