function getUserInfo(pageName){
    $.ajax({
        type:"get",
        url:"login",
        success: function(body){
            if(body.userId && body.userId > 0){
                console.log("用户" + body.username + "登陆成功");
                if(pageName == "blog_list.html"){
                    updateUserName(body.username);
                }
            }else{
                //前端跳转的函数
                alert("当前处于未登录状态,请先登录!")
                location.assign("blog_login.html");
            }
        },
        error: function(){
            alert("当前处于未登录状态,请先登录!")
                location.assign("blog_login.html");
        }
    });
}



function updateUserName(username){
    let h3 = document.querySelector('.card h3');
    h3.innerHTML = username;
}