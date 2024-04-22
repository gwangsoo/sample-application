package com.phoenixdarts.toss.domain;

import static com.phoenixdarts.toss.domain.GameTestSamples.*;
import static com.phoenixdarts.toss.domain.MatchFormatGameTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.phoenixdarts.toss.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class GameTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Game.class);
        Game game1 = getGameSample1();
        Game game2 = new Game();
        assertThat(game1).isNotEqualTo(game2);

        game2.setId(game1.getId());
        assertThat(game1).isEqualTo(game2);

        game2 = getGameSample2();
        assertThat(game1).isNotEqualTo(game2);
    }

    @Test
    void matchFormatGameTest() throws Exception {
        Game game = getGameRandomSampleGenerator();
        MatchFormatGame matchFormatGameBack = getMatchFormatGameRandomSampleGenerator();

        game.addMatchFormatGame(matchFormatGameBack);
        assertThat(game.getMatchFormatGames()).containsOnly(matchFormatGameBack);
        assertThat(matchFormatGameBack.getGame()).isEqualTo(game);

        game.removeMatchFormatGame(matchFormatGameBack);
        assertThat(game.getMatchFormatGames()).doesNotContain(matchFormatGameBack);
        assertThat(matchFormatGameBack.getGame()).isNull();

        game.matchFormatGames(new HashSet<>(Set.of(matchFormatGameBack)));
        assertThat(game.getMatchFormatGames()).containsOnly(matchFormatGameBack);
        assertThat(matchFormatGameBack.getGame()).isEqualTo(game);

        game.setMatchFormatGames(new HashSet<>());
        assertThat(game.getMatchFormatGames()).doesNotContain(matchFormatGameBack);
        assertThat(matchFormatGameBack.getGame()).isNull();
    }
}
