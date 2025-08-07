<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp"%>
<section>
    <%@ include file="sub_image_menu.jsp" %>
    <article>
        <form method="post" action="login" name="loginForm">
            <h2>Login</h2>
            <div class="field">
                <label>User ID</label><input name="userid" type="text" value="${dto.userid}" />
            </div>
            <div class="field">
                <label>Password</label><input name="pwd" type="password" >
            </div>

            <div class="btn">
                <input type="submit" value="LOGIN">
                <input type="button" value="JOIN" onclick="location.href='contract'">
                <input type="button" value="FIND ID" onclick="location.href='findacc'">
            </div>
            <div class="btn">
                <input type="button" value="Kakao" style="background: #f8df00; color: black" onclick="location.href='kakaostart'" />
                <input type="button" value="Naver" style="background: #03c559" />
                <input type="button" value="Google" style="background: #e84235; color: white" />
            </div>
            <div style="font-size: 80%; font-weight: bold">${message}</div>
        </form>
    </article>
</section>

<%@ include file="../footer.jsp"%>