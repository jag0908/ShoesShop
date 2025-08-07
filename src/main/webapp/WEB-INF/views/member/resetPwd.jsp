<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp"%>

<section>
    <%@ include file="sub_image_menu.jsp" %>
    <article>
        <form method="post" action="resetPwd">
            <input type="hidden" name="userid" value="${userid}" />
            <h2>RESET PASSWORD</h2><br />
            <div class="field">
                <label>새 비밀번호</label><div><input type="password" name="pwd" /></div>
            </div>
            <div class="field">
                <label>비밀번호 확인</label><div><input type="password" name="pwdCheck" /></div>
            </div>
            <div class="field">
                <input type="submit" value="변경" />
            </div>
            <div class="field">${msg}</div>
        </form>
    </article>
</section>


<%@ include file="../footer.jsp"%>