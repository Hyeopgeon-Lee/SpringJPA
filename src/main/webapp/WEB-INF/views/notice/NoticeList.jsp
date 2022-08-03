<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="kopo.poly.dto.NoticeDTO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%
    session.setAttribute("SESSION_USER_ID", "USER01"); //세션 강제 적용, 로그인된 상태로 보여주기 위함

    List<NoticeDTO> rList = (List<NoticeDTO>) request.getAttribute("rList");

//게시판 조회 결과 보여주기
    if (rList == null) {
        rList = new ArrayList<>();

    }

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>공지 리스트</title>
    <script type="text/javascript">

        //상세보기 이동
        function doDetail(seq) {
            location.href = "/notice/noticeInfo?nSeq=" + seq;
        }

    </script>
</head>
<body>
<h2>공지사항</h2>
<hr/>
<br/>

<table border="1" width="600px">
    <tr>
        <td width="100" align="center">순번</td>
        <td width="200" align="center">제목</td>
        <td width="100" align="center">조회수</td>
        <td width="100" align="center">등록자</td>
        <td width="100" align="center">등록일</td>
    </tr>
    <%
        for (NoticeDTO rDTO : rList) {
            if (rDTO == null) {
                rDTO = new NoticeDTO();
            }

    %>
    <tr>
        <td align="center">
            <%
                String html = "";
                //공지글이라면, [공지]표시
                if (CmmUtil.nvl(rDTO.getNoticeYn()).equals("1")) {
                    html += ("<b>[공지]</b>");

                    //공지글이 아니라면, 글번호 보여주기
                } else {
                    html += (rDTO.getNoticeSeq());

                }
            %><%= html%>
        </td>
        <td align="center">
            <a href="javascript:doDetail('<%=rDTO.getNoticeSeq()%>');">
                <%=CmmUtil.nvl(rDTO.getTitle()) %>
            </a>
        </td>
        <td align="center"><%=CmmUtil.nvl(rDTO.getReadCnt()) %>
        </td>
        <td align="center"><%=CmmUtil.nvl(rDTO.getUserName()) %>
        </td>
        <td align="center"><%=CmmUtil.nvl(rDTO.getRegDt()) %>
        </td>
    </tr>
    <%
        }
    %>
</table>
<a href="/notice/noticeReg">[글쓰기]</a>
</body>
</html>