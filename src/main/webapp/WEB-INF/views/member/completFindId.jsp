<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp"%>

<section>
  <%@ include file="sub_image_menu.jsp" %>
  <article>
    <form method="post" name="findid" id="findidfrm">
      <h1>검색하신 아이디는 ${userid} 입니다</h1>
      <input type="button" value="비밀번호 찾기" onclick="location.href='findpwd?userid=${userid}'" />
    </form>
  </article>
</section>


<%@ include file="../footer.jsp"%>