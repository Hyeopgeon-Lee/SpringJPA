<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>게시판 글쓰기</title>
    <link rel="stylesheet" href="/css/table.css"/>
    <script type="text/javascript">

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
            for (let i = 0; i < strCnt; i++) {
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
<h2>공지사항 등록하기</h2>
<hr/>
<br/>
<form name="f" method="post" action="/notice/noticeInsert" onsubmit="return doSubmit(this);">
    <div class="divTable minimalistBlack">
        <div class="divTableBody">
            <div class="divTableRow">
                <div class="divTableCell">제목
                </div>
                <div class="divTableCell"><input type="text" name="title" maxlength="100"
                                                 style="width: 95%"/>
                </div>
            </div>
            <div class="divTableRow">
                <div class="divTableCell">공지글 여부
                </div>
                <div class="divTableCell">
                    예<input type="radio" name="noticeYn" value="Y"/>
                    아니오<input type="radio" name="noticeYn" value="N"/>
                </div>
            </div>
            <div class="divTableRow">
                <div class="divTableCell">조회수
                </div>
                <div class="divTableCell">
                </div>
            </div>
            <div class="divTableRow">
                <div class="divTableCell">내용
                </div>
                <div class="divTableCell">
                    <textarea name="contents"
                              style="width: 95%; height: 400px"></textarea>
                </div>
            </div>
        </div>
    </div>
    <div>
        <input type="submit" value="등록"/>
        <input type="reset" value="다시 작성"/>
    </div>
</form>
<iframe name="ifrPrc" style="display:none"></iframe>

<!-- 프로세스 처리용 iframe / form 태그에서 target을 iframe으로 한다. -->
<iframe name="ifrPrc" style="display:none"></iframe>
</body>
</html>