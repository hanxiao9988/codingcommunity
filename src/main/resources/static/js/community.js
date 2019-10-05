function post() {
    var parentId = $("#parent_id").val();
    var content = $("#content").val();
    console.log(parentId);
    console.log(content);
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": parentId,
            "content": content,
            "type": 1
        }),
        success: function (response) {
            if (response.code == 200) {
                $("#comment_section").hide();
                alert(response.message);
            } else {
                if (response.code == 2003) {
                    var confirmValue = confirm(response.message);
                    if (confirmValue) {
                        window.open("https://github.com/login/oauth/authorize?client_id=de6af14bed3587befff3&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                        console.log("dddddddddddddddddd,儿童卡");
                    }
                } else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });


}