<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp"%>

<div id="main_img">
    <img src="/images/main_img.jpg" />
</div>

<%-- 베스트 상품 --%>
<div id="BestItem">
    <H2>Best Items</H2>
    <div class="products">
        <c:forEach items="${bestList}" var="pvo">
            <div class="item" onclick="location.href='productDetail?pseq=${pvo.pseq}'">
                <div><img src="/product_images/${pvo.savefilename}" /></div>
                <div>${pvo.name} - <fmt:formatNumber value="${pvo.price2}" type="currency" /></div>
            </div>
        </c:forEach>
    </div>
</div>

<%-- 신상품 --%>
<div id="NewItem">
    <H2>New Items</H2>
    <div class="products">
        <c:forEach items="${newList}" var="pvo">
            <div class="item" onclick="location.href='productDetail?pseq=${pvo.pseq}'">
                <div><img src="/product_images/${pvo.savefilename}" /></div>
                <div>${pvo.name} - <fmt:formatNumber value="${pvo.price2}" type="currency" /></div>
            </div>
        </c:forEach>
    </div>
</div>
<%@ include file="footer.jsp"%>