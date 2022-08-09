<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>로그인 화면</title>
    <link rel="stylesheet" href="/css/table.css"/>
    <script type="text/javascript">

        //로그인을 위한 입력정보의 유효성 체크하기
        function doLoginUserCheck(f) {

            if (f.user_id.value === "") {
                alert("아이디를 입력하세요.");
                f.user_id.focus();
                return false;
            }

            if (f.password.value === "") {
                alert("비밀번호를 입력하세요.");
                f.password.focus();
                return false;
            }

        }
    </script>
</head>
<body>
<h2>로그인하기</h2>
<hr/>
<br/>
<form name="f" method="post" action="/user/getUserLoginCheck" onsubmit="return doLoginUserCheck(this);">
    <div class="divTable minimalistBlack">
        <div class="divTableBody">
            <div class="divTableRow">
                <div class="divTableCell">아이디
                </div>
                <div class="divTableCell">
                    <input type="text" name="user_id" style="width:95%"/>
                </div>
            </div>
            <div class="divTableRow">
                <div class="divTableCell">비밀번호
                </div>
                <div class="divTableCell">
                    <input type="password" name="password" style="width:95%"/>
                </div>
            </div>
        </div>
    </div>
    <div><input type="submit" value="로그인"/></div>
</form>
</body>
</html>