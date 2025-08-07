<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>

<section>
    <%@ include file="sub_image_menu.jsp" %>
    <article>
        <form  action="updateMember" method="post" name="joinForm">
            <h2>Edit Member</h2><br />

            <h3>Basic Info</h3>
            <div class="field">
                <label>User ID</label>
                <div><input type="text" name="userid" size="12" value="${dto.userid}" readonly></div>
            </div>
            <c:choose>
                <c:when test="${dto.provider=='non'}">
                    <div class="field"><label>Password</label><input type="password"  name="pwd" ></div>
                    <div class="field"><label>Retype Password</label><input type="password"  name="pwdCheck" ></div>
                </c:when>
                <c:otherwise>
                     <div class="field"><label>Password</label><input type="password"  name="pwd"  readonly></div>
                    <div class="field"><label>Retype Password</label><input type="password"  name="pwdCheck" readonly ></div>
                </c:otherwise>
            </c:choose>

            <div class="field"><label>Name</label><input type="text"  name="name" value="${dto.name}"></div>
            <div class="field"><label>Phone</label><input type="text"  name="phone" value="${dto.phone}" /></div>
            <div class="field"><label>E-Mail</label><input type="text"  name="email" value="${dto.email}" /></div>
            <br />

            <h3>Optional Info</h3>
            <div class="field">
                <label>Zip Code</label>
                <div>
                    <input type="text"  id="my_postcode" name="zip_num"   value="${dto.zip_num}" readonly>
                    <input type="button" value="우편번호 찾기" onclick="findZipNum()">
                </div>
            </div>
            <div class="field">
                <label>Address</label><input type="text"   id="my_address"   name="address1"   value="${dto.address1}" readonly />
            </div>
            <div class="field">
                <label>Detail Address</label><input type="text"    id="my_detailAddress"   name="address2"  value="${dto.address2}"/>
            </div>
            <div class="field">
                <label>Extra Address</label><input type="text" id="my_extraAddress" name="address3"  value="${dto.address3}"/>
            </div>

            <!-- 다음 카카오 도로명 주소 검색을 위한 자바스크립트 코드들 -->
            <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
            <script type="text/javascript">
                function findZipNum(){
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
            <div class="btn">
            <input type="submit" value="회원수정" >
               <input type="button" value="메인으로" onClick="location.href='/'">
         </div>
         <div class="field">${message}</div>

        </form>
    </article>
</section>

<%@ include file="../footer.jsp" %>
