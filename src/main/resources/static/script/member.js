function goNext(){
    if(document.contract.okon[1].checked == true){
        alert('회원 약관에 동의하지 않으면 회원가입이 불가능합니다')
    }else{
        document.contract.submit();
        // location.href='joinForm';
    }
}

function confirmCode(){
    document.findid.action = "confirmCode";
    document.findid.submit();
}

function confirmCodeForPwd(){
    document.findpwd.action = "confirmCodeForPwd";
    document.findpwd.submit();
}