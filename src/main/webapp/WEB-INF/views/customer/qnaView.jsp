<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp"%>

<section>
    <%@ include file="sub_image_menu.jsp" %>
    <article>
        <form name="frm">
            <h2> Q & A View </h2>
            <div class="field" style="border-top: 2px solid #006493;">
                <label>작성자</label><div>${qnaVO.userid}</div>
            </div>
            <div class="field" style="border-top: 2px solid #006493;">
                <label>제목</label><div>${qnaVO.subject}</div>
            </div>
            <div class="field" style="border-top: 2px solid #006493;">
                <label>등록일</label>
                <fmt:formatDate value="${qnaVO.indate}" type="date"/>
            </div>
            <div class="field" style="border-top: 2px solid #006493;">
                <label>질문내용</label><div><pre>${qnaVO.userid}</pre></div>
            </div>
            <div class="field" style="border-bottom:2px solid #006493; border-top: 2px solid #006493;">
                <label>답변내용</label><div style="padding: 10px;">${qnaVO.reply}&nbsp;</div>
            </div>
            <div class="btn">
                <input type="button" value="목록으로" onclick="location.href='qnaList'">
            </div>
        </form>
    </article>
</section>

<%@ include file="../footer.jsp"%>