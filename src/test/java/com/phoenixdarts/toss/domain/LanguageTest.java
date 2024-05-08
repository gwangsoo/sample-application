package com.phoenixdarts.toss.backend.domain;

import static com.phoenixdarts.toss.backend.domain.LanguageTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.backend.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LanguageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Language.class);
        Language language1 = getLanguageSample1();
        Language language2 = new Language();
        assertThat(language1).isNotEqualTo(language2);

        language2.setId(language1.getId());
        assertThat(language1).isEqualTo(language2);

        language2 = getLanguageSample2();
        assertThat(language1).isNotEqualTo(language2);
    }
}
