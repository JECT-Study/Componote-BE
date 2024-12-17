package ject.componote.domain.auth.model;

import ject.componote.domain.auth.error.InvalidEmailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailTest {
    @DisplayName("정상 이메일 형식인 경우")
    @ParameterizedTest
    @ValueSource(strings = {"rlarla677@gmail.com", "rlarla677@naver.com", "kmw89891@sju.ac.kr"})
    public void validValue(final String value) throws Exception {
        assertThatCode(() -> Email.from(value))
                .doesNotThrowAnyException();
    }

    @DisplayName("이메일 형식이 잘못된 경우")
    @ParameterizedTest
    @ValueSource(strings = {"hello", "hello@gmail", "hello@", "@gmail.com", "@gmail.co"})
    public void invalidValue(final String value) throws Exception {
        assertThatThrownBy(() -> Email.from(value))
                .isInstanceOf(InvalidEmailException.class);
    }
}