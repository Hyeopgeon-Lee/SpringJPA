<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page import="kopo.poly.dto.NoticeDTO" %>
<%
    NoticeDTO rDTO = (NoticeDTO) request.getAttribute("rDTO");

    //공지글 정보를 못불러왔다면, 객체 생성
    if (rDTO == null) {
        rDTO = new NoticeDTO();

    }

    int access = 1; //(작성자 : 2 / 다른 사용자: 1)

    if (CmmUtil.nvl((String) session.getAttribute("SESSION_USER_ID")).equals(
            CmmUtil.nvl(rDTO.getUserId()))) {
        access = 2;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>게시판 글쓰기</title>
    <script type="text/javascript">

        //작성자 여부체크
        function doOnload() {

            if ("<%=access%>" === "1") {
                alert("작성자만 수정할 수 있습니다.");
                location.href = "/notice/NoticeList";

            }
        }

        //전송시 유효성 체크
        function doSubmit(f) {
            if (f.title.value === "") {
                alert("제목을 입력하시기 바랍니다.");
                f.title.focus();
                return false;
            }

            if (calBytes(f.title.value) > 200) {
                alert("최대 200Bytes까지 입력 가능합니다.");
                f.title.focus();
                return false;
            }

            let noticeCheck = false; //체크 여부 확인 변수
            for (let i = 0; i < f.noticeYn.length; i++) {
                if (f.noticeYn[i].checked) {
                    noticeCheck = true;
                }
            }

            if (noticeCheck === false) {
                alert("공지글 여부를 선택하시기 바랍니다.");
                f.noticeYn[0].focus();
                return false;
            }

            if (f.contents.value === "") {
                alert("내용을 입력하시기 바랍니다.");
                f.contents.focus();
                return false;
            }

            if (calBytes(f.contents.value) > 4000) {
                alert("최대 4000Bytes까지 입력 가능합니다.");
                f.contents.focus();
                return false;
            }


        }

        //글자 길이 바이트 단위로 체크하기(바이트값 전달)
        function calBytes(str) {

            let tcount = 0;
            let tmpStr = String(str);
            let strCnt = tmpStr.length;

            let onechar;
            for (var i = 0; i < strCnt; i++) {
                onechar = tmpStr.charAt(i);

                if (escape(onechar).length > 4) {
                    tcount += 2;
                } else {
                    tcount += 1;
                }
            }

            return tcount;
        }

    </script>
</head>
<body onload="doOnload();">
<h2>글 수정!</h2>
<form name="f" method="post" action="/notice/noticeUpdate" onsubmit="return doSubmit(this);">
    <input type="hidden" name="nSeq" value="<%=CmmUtil.nvl(request.getParameter("nSeq")) %>"/>
    <table border="1">
        <col width="100px"/>
        <col width="500px"/>
        <tr>
            <td align="center">제목</td>
            <td>
                <input type="text" name="title" maxlength="100"
                       value="<%=CmmUtil.nvl(rDTO.getTitle()) %>" style="width: 450px"/>
            </td>
        </tr>
        <tr>
            <td align="center">공지글 여부</td>
            <td>
                예<input type="radio" name="noticeYn" value="1"
                    <%=CmmUtil.checked(CmmUtil.nvl(rDTO.getNoticeYn()), "1") %>    />
                아니오<input type="radio" name="noticeYn" value="2"
                    <%=CmmUtil.checked(CmmUtil.nvl(rDTO.getNoticeYn()), "2") %>    />
            </td>
        </tr>
        <tr>
            <td colspan="2">
				<textarea
                        name="contents"
                        style="width: 550px; height: 400px"><%=CmmUtil.nvl(rDTO.getContents()) %></textarea>
            </td>
        </tr>
        <tr>
            <td align="center" colspan="2">
                <input type="submit" value="수정"/>
                <input type="reset" value="다시 작성"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>