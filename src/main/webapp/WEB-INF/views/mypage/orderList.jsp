<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>
<section>
   <%@ include file="sub_image_menu.jsp" %>
   <article>
      <form name="orderForm">
         <h2> Order List </h2>
         <div class="tb">

            <div class="row">
                     <div class="col" style="flex:1">주문자성명</div>
                     <div class="col" style="flex:3;  justify-content: flex-start;">${loginUser.name}</div>
            </div>
            <div class="row">
                     <div class="col" style="flex:1">주문자 전화번호</div>
                     <div class="col" style="flex:3; justify-content: flex-start;">${loginUser.phone}</div>
            </div>
            <div class="row">
                     <div class="col" style="flex:1">우편번호</div>
                     <div class="col" style="flex:3; justify-content: flex-start;">
                        ${orderDetail.zip_num} ${orderDetail.address1} ${orderDetail.address2} ${orderDetail.address3}
                     </div>
            </div>

            <br /><br />

            <div class="row">
               <div class="coltitle">상품명</div>
               <div class="coltitle">수 량</div>
               <div class="coltitle">가 격</div>
               <div class="coltitle">주문일</div>
               <div class="coltitle">진행상태</div>
            </div>
            <c:forEach items="${orderList}" var="orderVO">
               <div class="row">
                  <div class="col">${orderVO.pname}</div>
                  <div class="col">${orderVO.quantity}</div>
                  <div class="col">
                     <fmt:formatNumber value="${orderVO.price2}"  type="currency"/>
                  </div>
                  <div class="col">
                     <fmt:formatDate value="${orderVO.indate}" type="date"/>
                  </div>
                  <div class="col">
                     <c:choose>
                        <c:when test="${orderVO.result==1}" >결제완료</c:when>
                        <c:when test="${orderVO.result==2}" >배송중</c:when>
                        <c:when test="${orderVO.result==3}" >배송완료</c:when>
                        <c:otherwise>구매확정</c:otherwise>
                     </c:choose>
                  </div>
               </div>
            </c:forEach>

            <div class="row">
               <div class="col"> 총결제금액 </div>
               <div class="col">
                  <fmt:formatNumber value="${totalPrice}" type="currency"/>
               </div>
               <div class="col">
                  <input type="button" value="쇼핑계속하기"
                        onClick="location.href='/'" />
                 </div>
            </div>

         </div>
      </form>
   </article>
</section>
<%@ include file="../footer.jsp" %>