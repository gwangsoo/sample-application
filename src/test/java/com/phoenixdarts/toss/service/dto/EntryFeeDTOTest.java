package com.phoenixdarts.toss.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EntryFeeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntryFeeDTO.class);
        EntryFeeDTO entryFeeDTO1 = new EntryFeeDTO();
        entryFeeDTO1.setId("id1");
        EntryFeeDTO entryFeeDTO2 = new EntryFeeDTO();
        assertThat(entryFeeDTO1).isNotEqualTo(entryFeeDTO2);
        entryFeeDTO2.setId(entryFeeDTO1.getId());
        assertThat(entryFeeDTO1).isEqualTo(entryFeeDTO2);
        entryFeeDTO2.setId("id2");
        assertThat(entryFeeDTO1).isNotEqualTo(entryFeeDTO2);
        entryFeeDTO1.setId(null);
        assertThat(entryFeeDTO1).isNotEqualTo(entryFeeDTO2);
    }
}
