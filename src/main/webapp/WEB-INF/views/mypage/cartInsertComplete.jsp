<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<script type="text/javascript">
    if(confirm('장바구니에 상품이 추가되었습니다. 장바구니로 이동할까요?')){
        location.href='cartList';
    }else{
        location.href='productDetail?pseq=${pseq}';
    }
</script>
</body>
</html>
