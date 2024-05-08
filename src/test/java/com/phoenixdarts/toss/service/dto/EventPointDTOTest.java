package com.phoenixdarts.toss.backend.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.backend.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventPointDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventPointDTO.class);
        EventPointDTO eventPointDTO1 = new EventPointDTO();
        eventPointDTO1.setId("id1");
        EventPointDTO eventPointDTO2 = new EventPointDTO();
        assertThat(eventPointDTO1).isNotEqualTo(eventPointDTO2);
        eventPointDTO2.setId(eventPointDTO1.getId());
        assertThat(eventPointDTO1).isEqualTo(eventPointDTO2);
        eventPointDTO2.setId("id2");
        assertThat(eventPointDTO1).isNotEqualTo(eventPointDTO2);
        eventPointDTO1.setId(null);
        assertThat(eventPointDTO1).isNotEqualTo(eventPointDTO2);
    }
}
