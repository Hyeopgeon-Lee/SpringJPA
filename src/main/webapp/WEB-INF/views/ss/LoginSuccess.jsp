<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%
    //Controller에 저장된 세션으로 로그인할때 생성됨
    String SS_USER_ID = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
    String userName = CmmUtil.nvl((String) request.getAttribute("userName"));
    String userId = CmmUtil.nvl((String) session.getAttribute("userId"));

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>로그인 성공</title>
</head>
<body>
<%=userName%> 로그인 성공
</body>
</html>
