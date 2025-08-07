<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp"%>

<section>
  <%@ include file="sub_image_menu.jsp" %>
  <article>
    <form method="post" name="findid" id="findidfrm">
      <h2>FIND ID</h2><br />
      <div class="field">
        <label>NAME</label>
        <div><input type="text" name="name" value="${name}" /></div>
      </div>
      <div class="field">
        <label>PHONE</label>
        <div><input type="text" name="phone" value="${phone}" /></div>
      </div>
      <div class="field">
        <label>EMAIL</label>
        <div>
          <input type="text" name="email" value="${email}" />
          <input type="button" value="SEND MAIL" id="sendmailforid" />
          <input type="hidden" name="sendok" id="sendok" />
        </div>
      </div>
      <div class="field">
        <label>INPUT CODE</label>
        <div>
          <input type="text" name="code"  />
          <input type="button" value="인증" onclick="confirmCode()" />
        </div>
      </div>
      <div class="field">${msg}</div>
    </form>
  </article>
</section>


<%@ include file="../footer.jsp"%>