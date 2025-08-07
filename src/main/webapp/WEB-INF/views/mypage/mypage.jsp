<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp"%>

<section>
    <%@ include file="sub_image_menu.jsp" %>
    <article>
        <form name="orderForm">
            <h2>${title}</h2>
            <c:choose>
                <c:when test="${finalList.size()==0}"><h3>주문내역이 없습니다</h3></c:when>
                <c:otherwise>
                    <div class="tb">
                        <div class="row">
                            <div class="coltitle">주문일</div><div class="coltitle">주문번호</div>
                            <div class="coltitle">상품명</div><div class="coltitle">결제금액</div>
                            <div class="coltitle">주문상세</div><div class="coltitle">처리상태</div>
                        </div>
                        <c:forEach items="${finalList}" var="orderVO">
                            <div class="row">
                                <div class="col"><fmt:formatDate value="${orderVO.indate}" type="date" /></div>
                                <div class="col">${orderVO.oseq}</div>
                                <div class="col">${orderVO.pname}</div>
                                <div class="col"><fmt:formatNumber value="${orderVO.price2}" type="currency" /></div>
                                <div class="col"><a href="orderDetail?oseq=${orderVO.oseq}">상세조회</a> </div>
                                <div class="col">
                                    <c:choose>
                                        <c:when test="${orderVO.result == 4}">종료</c:when>
                                        <c:otherwise>주문 진행중</c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </form>
    </article>
</section>

<%@ include file="../footer.jsp"%>