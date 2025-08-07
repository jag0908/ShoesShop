function go_cart(){
    if(document.formm.quantity.value==""){
        alert("수량을 입력하세요");
        return;
    }
    document.formm.action = "cartInsert";
    document.formm.submit();
}

function go_cart_delete(){
    // 체크박스가 한개인지 또는 두개 이상인지 점검
    var count = 0;
    if(document.cartFrm.cseq.length == undefined){
        if(document.cartFrm.cseq.checked == true){
            count++;
        }
    }else{
        for(var i=0; i<document.cartFrm.cseq.length; i++){
            if(document.cartFrm.cseq[i].checked == true){
                count++;
            }
        }
    }
    // 한개도 체크가 안된 상태인지 점검
    if(count == 0){
        alert("삭제할 항목을 선택하세요");
        return;
    }else{
        // 체크박스 value 로 카트 테이블의 해당 레코드를 삭제
        if(confirm("선택한 항목을 삭제하시겠습니까?")){
            document.cartFrm.action = 'deleteCart';
            document.cartFrm.submit();
            }
        }
    // 모든 삭제를 마치고 cartList 로 다시 되돌아옴
}

function go_order_insert(){
    document.cartFrm.action = 'orderForm';
    document.cartFrm.submit();
}

function loadAddress(zip_num, address1, address2, address3) {
    if(document.cartFrm.address.checked==true) {
        document.cartFrm.zip_num.value = zip_num;
        document.cartFrm.address1.value = address1;
        document.cartFrm.address2.value = address2;
        document.cartFrm.address3.value = address3;
    }else{
        document.cartFrm.zip_num.value = "";
        document.cartFrm.address1.value = "";
        document.cartFrm.address2.value = "";
        document.cartFrm.address3.value = "";
    }
}

function go_order() {
    if(document.cartFrm.zip_num.value=="" || document.cartFrm.address1.value=="" || document.cartFrm.address2.value==""){
        alert("우편번호와 주소를 확인하세요");
    }else{
        document.cartFrm.action="orderInsert";
        document.cartFrm.submit();
    }

}

function go_orderOne() {
    if (document.formm.quantity.value == ""){
        alert("수량을 입력하세요");
        return;
    }
    document.formm.action = "orderFormOne";
    document.formm.submit();
}

function deleteMember() {
    if(confirm('구매 내역과 회원 정보가 모두 사라집니다. 정말로 탈퇴하시겠습니까?')){
        location.href="deleteMember";
    }
}

function qnaViewWithPass(qseq){
    var inputPass = prompt('비밀번호를 입력하세요', '')
    location.href="comfirmPass?qseq=" + qseq + "&pass=" + inputPass;
}

function qnaView(qseq){
    location.href="qnaView?qseq=" + qseq;
}