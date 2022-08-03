<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="kopo.poly.dto.NoticeDTO" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%
    NoticeDTO rDTO = (NoticeDTO) request.getAttribute("rDTO");

//공지글 정보를 못불러왔다면, 객체 생성
    if (rDTO == null) {
        rDTO = new NoticeDTO();

    }

    String ss_user_id = CmmUtil.nvl((String) session.getAttribute("SESSION_USER_ID"));

//본인이 작성한 글만 수정 가능하도록 하기(1:작성자 아님 / 2: 본인이 작성한 글 / 3: 로그인안함)
    int edit = 1;

//로그인 안했다면....
    if (ss_user_id.equals("")) {
        edit = 3;

//본인이 작성한 글이면 2가 되도록 변경
    } else if (ss_user_id.equals(CmmUtil.nvl(rDTO.getUserId()))) {
        edit = 2;

    }

    System.out.println("user_id : " + CmmUtil.nvl(rDTO.getUserId()));
    System.out.println("ss_user_id : " + ss_user_id);

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>게시판 글보기</title>
    <script type="text/javascript">

        //수정하기
        function doEdit() {
            if ("<%=edit%>" === "2") {
                location.href = "/notice/noticeEditInfo?nSeq=<%=rDTO.getNoticeSeq()%>";

            } else if ("<%=edit%>" === "3") {
                alert("로그인 하시길 바랍니다.");

            } else {
                alert("본인이 작성한 글만 수정 가능합니다.");

            }
        }


        //삭제하기
        function doDelete() {
            if ("<%=edit%>" === "2") {
                if (confirm("작성한 글을 삭제하시겠습니까?")) {
                    location.href = "/notice/noticeDelete?nSeq=<%=rDTO.getNoticeSeq()%>";

                }

            } else if ("<%=edit%>" === "3") {
                alert("로그인 하시길 바랍니다.");

            } else {
                alert("본인이 작성한 글만 삭제 가능합니다.");

            }
        }

        //목록으로 이동
        function doList() {
            location.href = "/notice/noticeList";

        }

    </script>
</head>
<body>
<table border="1">
    <col width="100px"/>
    <col width="200px"/>
    <col width="100px"/>
    <col width="200px"/>
    <tr>
        <td align="center">제목</td>
        <td colspan="3"><%=CmmUtil.nvl(rDTO.getTitle())%>
        </td>
    </tr>
    <tr>
        <td align="center">공지글 여부</td>
        <td colspan="3">예<input type="radio" name="noticeYn" value="1"
                <%=CmmUtil.checked(CmmUtil.nvl(rDTO.getNoticeYn()), "1") %>/>
            아니오<input type="radio" name="noticeYn" value="2"
                    <%=CmmUtil.checked(CmmUtil.nvl(rDTO.getNoticeYn()), "2") %>/>
        </td>
    </tr>
    <tr>
        <td align="center">작성일</td>
        <td><%=CmmUtil.nvl(rDTO.getRegDt())%>
        </td>
        <td align="center">조회수</td>
        <td><%=CmmUtil.nvl(rDTO.getReadCnt())%>
        </td>
    </tr>
    <tr>
        <td colspan="4" height="300px" valign="top">
            <%=CmmUtil.nvl(rDTO.getContents()).replaceAll("\r\n", "<br/>") %>
        </td>
    </tr>
    <tr>
        <td align="center" colspan="4">
            <a href="javascript:doEdit();">[수정]</a>
            <a href="javascript:doDelete();">[삭제]</a>
            <a href="javascript:doList();">[목록]</a>
        </td>
    </tr>
</table>
</body>
</html>