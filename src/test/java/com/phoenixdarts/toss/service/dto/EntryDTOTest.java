package com.phoenixdarts.toss.backend.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.backend.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EntryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntryDTO.class);
        EntryDTO entryDTO1 = new EntryDTO();
        entryDTO1.setId("id1");
        EntryDTO entryDTO2 = new EntryDTO();
        assertThat(entryDTO1).isNotEqualTo(entryDTO2);
        entryDTO2.setId(entryDTO1.getId());
        assertThat(entryDTO1).isEqualTo(entryDTO2);
        entryDTO2.setId("id2");
        assertThat(entryDTO1).isNotEqualTo(entryDTO2);
        entryDTO1.setId(null);
        assertThat(entryDTO1).isNotEqualTo(entryDTO2);
    }
}
