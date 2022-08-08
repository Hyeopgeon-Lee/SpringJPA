<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="kopo.poly.dto.NoticeDTO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%
    //세션 강제 적용, 로그인된 상태로 보여주기 위함
    session.setAttribute("SESSION_USER_ID", "USER01");

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
    <link rel="stylesheet" href="/css/table.css"/>
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

<div class="divTable minimalistBlack">
    <div class="divTableHeading">
        <div class="divTableRow">
            <div class="divTableHead">순번</div>
            <div class="divTableHead">제목</div>
            <div class="divTableHead">조회수</div>
            <div class="divTableHead">등록자</div>
            <div class="divTableHead">등록일</div>
        </div>
    </div>
    <div class="divTableBody">
        <%
            for (NoticeDTO rDTO : rList) {
                if (rDTO == null) {
                    rDTO = new NoticeDTO();
                }
        %>
        <div class="divTableRow">
            <div class="divTableCell">
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
            </div>
            <div class="divTableCell">
                <a href="javascript:doDetail('<%=rDTO.getNoticeSeq()%>');">
                    <%=CmmUtil.nvl(rDTO.getTitle()) %>
                </a>
            </div>
            <div class="divTableCell"><%=CmmUtil.nvl(rDTO.getReadCnt()) %>
            </div>
            <div class="divTableCell"><%=CmmUtil.nvl(rDTO.getUserName()) %>
            </div>
            <div class="divTableCell"><%=CmmUtil.nvl(rDTO.getRegDt()) %>
            </div>
        </div>
        <%
            }
        %>
    </div>
</div>
<a href="/notice/noticeReg">[글쓰기]</a>
</body>
</html>