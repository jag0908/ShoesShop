<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="sub_img">
    <img src="images/member/sub_img.jpg" />
</div>

<div class="sub_page">
    <nav class="sub_menu">
        <c:choose>
            <c:when test="${empty loginUser}">
                <div><a href="login">LOGIN</a></div>
                <div><a href="contract">JOIN</a></div>
                <div><a href="findacc">FIND ACC</a></div>
            </c:when>
            <c:otherwise>
            <div><a href="updateMember">EDIT MEMBER</a></div>
            <div><a href="logout">LOGOUT</a></div>
            </c:otherwise>
        </c:choose>
    </nav>
</div>