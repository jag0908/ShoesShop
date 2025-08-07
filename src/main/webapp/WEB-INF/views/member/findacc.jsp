<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp"%>

<section>
    <%@ include file="sub_image_menu.jsp" %>
    <article>
        <form >
            <h2>아이디 / 비밀번호 찾기</h2><br />
            <table align="center" cellspacing="1" border="0" bgcolor="black" width="60%">
                <tr bgcolor="white" height="100">
                    <td valign="center" align="center">
                        아이디 찾기<br /><br />
                        <input type="button" value="Click" onclick="location.href='findId'"/>
                    </td>
                    <td valign="center" align="center">
                        비밀번호 찾기<br /><br />
                        <input type="button" value="Click" onclick="location.href='findpwd'"/>
                    </td>
                </tr>
            </table>
        </form>
    </article>
</section>


<%@ include file="../footer.jsp"%>