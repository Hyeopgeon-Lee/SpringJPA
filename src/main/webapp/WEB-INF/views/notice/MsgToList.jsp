<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%
//전달받은 메시지
String msg = CmmUtil.nvl((String)request.getAttribute("msg"));
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
<title>처리페이지</title>
<script type="text/javascript">

	alert("<%=msg%>");
	top.location.href="/notice/noticeList";
		
</script>
</head>
<body>

</body>
</html>

