<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp"%>

<section>

    <div class="sub_img"><img src="images/product/sub_img.jpg" /></div>
    <div class="sub_page">
        <article style="display:flex;flex-direction:column; align-items:center">
            <h1 sytle="margin-left:20px">${product.name}</h1>
            <form  method="post" name="formm" style="display:flex; flex-direction:column; align-content:center; border:1px solid blue;">

                <div id="itemdetail" style="display:flex; flex-direction:column;">
                    <div class="itemdetail-img-title">
                        <div><img src="product_images/${product.savefilename}" /></div>
                        <div>
                            <label> 가 격 :  </label>${product.price2} 원 <br/>
                            <label> 수 량 :  </label><input  type="text" name="quantity" size="2" value="1">
                            <input  type="hidden" name="pseq" value="${product.pseq}">
                        </div>
                    </div>
                    <div class="itemdetail-content"><h3 style="font-size:170%;">${product.content}</h3></div>
                    <div class="btn">
                        <input type="button" value="장바구니에 담기"   onclick="go_cart()">
                        <input type="button" value="즉시 구매"  onclick="go_orderOne( )">
                        <input type="button"  value="메인으로"    onclick="location.href='/'">
                    </div>
                </div>

            </form>
        </article>
    </div>

</section>

<%@ include file="../footer.jsp"%>