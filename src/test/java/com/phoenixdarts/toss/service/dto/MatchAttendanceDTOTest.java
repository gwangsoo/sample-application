package com.phoenixdarts.toss.backend.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.backend.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MatchAttendanceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchAttendanceDTO.class);
        MatchAttendanceDTO matchAttendanceDTO1 = new MatchAttendanceDTO();
        matchAttendanceDTO1.setId("id1");
        MatchAttendanceDTO matchAttendanceDTO2 = new MatchAttendanceDTO();
        assertThat(matchAttendanceDTO1).isNotEqualTo(matchAttendanceDTO2);
        matchAttendanceDTO2.setId(matchAttendanceDTO1.getId());
        assertThat(matchAttendanceDTO1).isEqualTo(matchAttendanceDTO2);
        matchAttendanceDTO2.setId("id2");
        assertThat(matchAttendanceDTO1).isNotEqualTo(matchAttendanceDTO2);
        matchAttendanceDTO1.setId(null);
        assertThat(matchAttendanceDTO1).isNotEqualTo(matchAttendanceDTO2);
    }
}
