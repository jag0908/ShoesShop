<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/css/header_footer.css" />
    <link rel="stylesheet" href="/css/main.css" />
    <link rel="stylesheet" href="/css/section.css" />
    <link rel="stylesheet" href="/css/product.css" />
    <link rel="stylesheet" href="/css/mypage.css" />

    <script src="/script/jquery-3.7.1.min.js"></script>
    <script src="/script/hmenu.js"></script>
    <script src="/script/member.js"></script>
    <script src="/script/mypage.js"></script>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script type="text/javascript">
        $(function (){

            $('#sendmailforpwd').click(function (){
                if (document.findpwd.userid.value == '') {
                    alert('아이디를 입력하세요');
                    return;
                }else if (document.findpwd.name.value == '') {
                    alert('이름을 입력하세요');
                    return;
                } else if (document.findpwd.phone.value == '') {
                    alert('전화번호를 입력하세요');
                    return;
                } else if (document.findpwd.email.value == '') {
                    alert('이메일을 입력하세요');
                    return;
                }
                var selectForm = $('#findpwdfrm')[0];
                var formData = new FormData(selectForm);

                $.ajax({
                    url: "<%=request.getContextPath() %>/sendMailForPwd",
                    type: "POST",
                    async: false,
                    data: formData,
                    timeout: 10000,
                    contentType: false,
                    processData: false,
                    success:function (data){
                        alert(data.findpwdmsg);
                        if(data.state == 'ok')
                            $('#sendok').val('ok');
                    },
                    error:function (){
                    }
                });
            })

            $('#sendmailforid').click(function () {
                if (document.findid.name.value == '') {
                    alert('이름을 입력하세요');
                    return;
                } else if (document.findid.phone.value == '') {
                    alert('전화번호를 입력하세요');
                    return;
                } else if (document.findid.email.value == '') {
                    alert('이메일을 입력하세요');
                    return;
                }
                var selectForm = $('#findidfrm')[0];
                var formData = new FormData(selectForm);

                $.ajax({
                        url: "<%=request.getContextPath() %>/sendMail",
                        type: "POST",
                        async: false,
                        data: formData,
                        timeout: 10000,
                        contentType: false,
                        processData: false,
                        success:function (data){
                            alert(data.findidmsg);
                            if(data.state == 'ok')
                                $('#sendok').val('ok');
                        },
                        error:function (){
                        }
                    });
                })


            $('#idcheckbutton').click(function (){
                if(document.joinForm.userid.value == ''){

                }
                var selectForm = $('#joinFrm')[0];  // 사용할 폼 선택
                var formData = new FormData(selectForm);  // 폼을 서버에 전송할 데이터 형태로 변환

                // 화면 전환없이 서버와 송신 수신을 하기 위한 ajax 객체 사용
                $.ajax(
                    {
                        url:"<%=request.getContextPath() %>/idcheck",
                        type:"POST",
                        async:false,
                        data:formData,
                        timeout:10000,
                        contentType:false,
                        processData:false,

                        // 여기로 서버의 응답이 들어옴
                        success:function (data){
                            // alert(data.idcheckmsg + ' ' + data.userid);
                            if(data.idcheckmsg == 1){
                                $('#idmessage').html("&nbsp;&nbsp;<span style='color:blue'>사용 가능합니다</span>");
                                $('#reid').val(data.userid);
                            }else{
                                $('#idmessage').html("&nbsp;&nbsp;<span style='color:red'>사용중인 아이디입니다</span>");
                                $('#reid').val("");
                            }
                        },
                        error:function (){
                            alert('중복 조회중 에러가 발생했습니다. 관리자에게 문의하세요');
                        },

                    }
                );
            });
        })
    </script>
</head>
<body>

<div id="wrap">
    <header>
        <nav id="top_menu">
            <div id="logo">
                <a href="/"><img src="/images/logo.png" width="180" height="100"></a>
            </div>
            <div class="gnb">
                <c:choose>
                    <c:when test="${empty loginUser}">
                        <a href="loginForm">LOGIN</a>
                        <a href="contract">JOIN</a>
                    </c:when>
                    <c:otherwise>
                        <a href="#">${loginUser.name}(${loginUser.userid})</a>
                        <a href="logout">LOGOUT</a>
                    </c:otherwise>
                </c:choose>

                <a href="cartList">CART</a>
                <a href="mypage">MYPAGE</a>
                <a href="customer">CUSTOMER</a>
            </div>
        </nav>
        <nav id="category_menu">
            <a href="category?category=1">Heels</a>
            <a href="category?category=2">Boots</a>
            <a href="category?category=3">Sandals</a>
            <a href="category?category=4">Sneakers</a>
            <a href="category?category=5">Sleepers</a>
        </nav>
    </header>