<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp"%>

<section>
    <%@ include file="sub_image_menu.jsp" %>
    <article>
        <form name="cartFrm" method="post">
            <h2>Order Form</h2>
            <div class="tb">
                <div class="row">
                    <div class="col" style="flex: 1">주문자 성명</div>
                    <div class="col" style="flex: 3; justify-content: flex-start;">${loginUser.name}</div>
                </div>
                <div class="row">
                    <div class="col" style="flex: 1">주문자 전화번호</div>
                    <div class="col" style="flex: 3; justify-content: flex-start;">${loginUser.phone}</div>
                </div>
                <div class="row">
                    <div class="col" style="flex:1">우편번호</div>
                    <div class="col" style="flex:3; justify-content: flex-start;">
                        <input type="text" name="zip_num" size="25"   id="my_postcode"  readonly/>
                        <input type="button" value="우편번호 찾기"  onclick="findZipnum()">
                    </div>
                </div>
                <div class="row">
                    <div class="col" style="flex:1">주소</div>
                    <div class="col"  style="flex:3; justify-content: flex-start;">
                        <input type="text" name="address1" size="45"     id="my_address" readonly/>
                    </div>
                </div>
                <div class="row">
                    <div class="col" style="flex:1">상세 주소</div>
                    <div class="col" style="flex:3; justify-content: flex-start;">
                        <input type="text" name="address2"  size="45"      id="my_detailAddress" />
                    </div>
                </div>
                <div class="row">
                    <div class="col" style="flex:1">Zip Code</div>
                    <div class="col" style="flex:3; justify-content: flex-start;">
                        <input type="text" name="address3"  size="45"    id="my_extraAddress"/>
                    </div>
                </div>

                <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
                <script>
                    function findZipnum() {
                        new daum.Postcode( {
                            oncomplete: function(data) {
                                var addr = '';
                                var extraAddr = '';

                                if (data.userSelectedType === 'R') {
                                    addr = data.roadAddress;
                                } else {
                                    addr = data.jibunAddress;
                                }

                                if(data.userSelectedType === 'R'){
                                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                                        extraAddr += data.bname;
                                    }
                                    if(data.buildingName !== '' && data.apartment === 'Y'){
                                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                                    }
                                   if(extraAddr !== ''){
                                        extraAddr = ' (' + extraAddr + ')';
                                    }
                                     document.getElementById("my_extraAddress").value = extraAddr;

                                } else {
                                    document.getElementById("my_extraAddress").value = '';
                                }

                                document.getElementById('my_postcode').value = data.zonecode;
                                document.getElementById("my_address").value = addr;
                                document.getElementById("my_extraAddress").focus();
                            }
                        }).open();
                    }
                </script>

                <c:if test="${not empty loginUser.zip_num}">
                    <div class="row">
                        <div class="col" style="flex: 1; justify-content: flex-start;">
                            <input type="checkbox" name="address" onclick="loadAddress('${loginUser.zip_num}',
                            '${loginUser.address1}', '${loginUser.address2}', '${loginUser.address3}')"/>
                            로그인 유저와 주소가 동일합니다
                        </div>
                    </div>
                </c:if><br>

                <div class="row">
                    <div class="coltitle">상품명</div>
                    <div class="coltitle">수량</div>
                    <div class="coltitle">가격</div>
                    <div class="coltitle">주문일</div>
                </div>
                <c:forEach items="${cartList}" var="cartVO" >
                    <div class="row">
                        <div class="col">${cartVO.pname}</div>
                        <div class="col">${cartVO.quantity}</div>
                        <div class="col"><fmt:formatNumber value="${cartVO.price2}"    type="currency" /></div>
                        <div class="col">
                            <fmt:formatDate value="${cartVO.indate}" type="date"/>
                            <input type="hidden" name="cseq" value="${cartVO.cseq}" />
                        </div>
                    </div>
                </c:forEach>
                <div class="row">
                    <div class="col"> 총금액 </div>
                    <div class="col"><fmt:formatNumber value="${totalPrice}" type="currency"/></div>
                </div>

                <div class="btn">
                    <input type="button" value="뒤로" onclick="history.go(-1)">
                    <input type="button" value="주문하기" onclick="go_order()">
                </div>

            </div>
        </form>
    </article>
</section>

<%@ include file="../footer.jsp"%>