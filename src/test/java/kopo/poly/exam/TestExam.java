package kopo.poly.exam;

import kopo.poly.util.CmmUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class TestExam {

    @DisplayName("CmmUtil.nvl 함수의 널값 처리 테스트하기")
    @Test
    void nvlTest() {

        // 테스트하기 위해 값을 강제로 null 저장
        String testStr = null;

        // 정답 변수
        String correctStr = "";

        assertThat(CmmUtil.nvl(testStr)).isEqualTo(correctStr);

    }
}
