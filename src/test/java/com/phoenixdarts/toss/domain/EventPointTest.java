package com.phoenixdarts.toss.backend.domain;

import static com.phoenixdarts.toss.backend.domain.DivisionTestSamples.*;
import static com.phoenixdarts.toss.backend.domain.EventPointTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.backend.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventPointTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventPoint.class);
        EventPoint eventPoint1 = getEventPointSample1();
        EventPoint eventPoint2 = new EventPoint();
        assertThat(eventPoint1).isNotEqualTo(eventPoint2);

        eventPoint2.setId(eventPoint1.getId());
        assertThat(eventPoint1).isEqualTo(eventPoint2);

        eventPoint2 = getEventPointSample2();
        assertThat(eventPoint1).isNotEqualTo(eventPoint2);
    }

    @Test
    void divisionTest() throws Exception {
        EventPoint eventPoint = getEventPointRandomSampleGenerator();
        Division divisionBack = getDivisionRandomSampleGenerator();

        eventPoint.setDivision(divisionBack);
        assertThat(eventPoint.getDivision()).isEqualTo(divisionBack);

        eventPoint.division(null);
        assertThat(eventPoint.getDivision()).isNull();
    }
}
