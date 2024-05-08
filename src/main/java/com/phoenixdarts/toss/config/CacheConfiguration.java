package com.phoenixdarts.toss.backend.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(
                Object.class,
                Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries())
            )
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build()
        );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.phoenixdarts.toss.backend.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.phoenixdarts.toss.backend.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.phoenixdarts.toss.backend.domain.User.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.Authority.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.User.class.getName() + ".authorities");
            createCache(cm, com.phoenixdarts.toss.backend.domain.PersistentToken.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.User.class.getName() + ".persistentTokens");
            createCache(cm, com.phoenixdarts.toss.backend.domain.Currency.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.Currency.class.getName() + ".entryFees");
            createCache(cm, com.phoenixdarts.toss.backend.domain.FileInfo.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.FileInfo.class.getName() + ".affiliatedInfos");
            createCache(cm, com.phoenixdarts.toss.backend.domain.Country.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.Country.class.getName() + ".competitions");
            createCache(cm, com.phoenixdarts.toss.backend.domain.Country.class.getName() + ".regions");
            createCache(cm, com.phoenixdarts.toss.backend.domain.Region.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.Region.class.getName() + ".operators");
            createCache(cm, com.phoenixdarts.toss.backend.domain.Role.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.OperatorRole.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.OperatorRole.class.getName() + ".roles");
            createCache(cm, com.phoenixdarts.toss.backend.domain.OperatorRole.class.getName() + ".operators");
            createCache(cm, com.phoenixdarts.toss.backend.domain.Operator.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.Operator.class.getName() + ".competitions");
            createCache(cm, com.phoenixdarts.toss.backend.domain.Language.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.EntryFee.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.EntryFee.class.getName() + ".tournaments");
            createCache(cm, com.phoenixdarts.toss.backend.domain.EntryFee.class.getName() + ".competitions");
            createCache(cm, com.phoenixdarts.toss.backend.domain.Competition.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.Competition.class.getName() + ".tournaments");
            createCache(cm, com.phoenixdarts.toss.backend.domain.Competition.class.getName() + ".machineAreas");
            createCache(cm, com.phoenixdarts.toss.backend.domain.Tournament.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.Tournament.class.getName() + ".divisions");
            createCache(cm, com.phoenixdarts.toss.backend.domain.Division.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.Division.class.getName() + ".matchFormats");
            createCache(cm, com.phoenixdarts.toss.backend.domain.Division.class.getName() + ".eventPoints");
            createCache(cm, com.phoenixdarts.toss.backend.domain.Division.class.getName() + ".teams");
            createCache(cm, com.phoenixdarts.toss.backend.domain.Division.class.getName() + ".matches");
            createCache(cm, com.phoenixdarts.toss.backend.domain.Game.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.Game.class.getName() + ".matchFormatGames");
            createCache(cm, com.phoenixdarts.toss.backend.domain.MatchFormat.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.MatchFormat.class.getName() + ".matchFormatGames");
            createCache(cm, com.phoenixdarts.toss.backend.domain.MatchFormat.class.getName() + ".matchFormatOptions");
            createCache(cm, com.phoenixdarts.toss.backend.domain.MatchFormat.class.getName() + ".matchFormatSets");
            createCache(cm, com.phoenixdarts.toss.backend.domain.MatchFormatGame.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.MatchFormatOption.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.MatchFormatSet.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.MatchFormatSet.class.getName() + ".matchFormatLegs");
            createCache(cm, com.phoenixdarts.toss.backend.domain.MatchFormatLeg.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.EventPoint.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.AffiliatedInfo.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.AffiliatedInfo.class.getName() + ".teams");
            createCache(cm, com.phoenixdarts.toss.backend.domain.PaymentInfo.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.PaymentInfo.class.getName() + ".teams");
            createCache(cm, com.phoenixdarts.toss.backend.domain.Team.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.Team.class.getName() + ".entries");
            createCache(cm, com.phoenixdarts.toss.backend.domain.Team.class.getName() + ".matchTeams");
            createCache(cm, com.phoenixdarts.toss.backend.domain.Entry.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.Entry.class.getName() + ".matchAttendances");
            createCache(cm, com.phoenixdarts.toss.backend.domain.Match.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.Match.class.getName() + ".matchScores");
            createCache(cm, com.phoenixdarts.toss.backend.domain.Match.class.getName() + ".machines");
            createCache(cm, com.phoenixdarts.toss.backend.domain.MatchTeam.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.MatchTeam.class.getName() + ".matchAttendances");
            createCache(cm, com.phoenixdarts.toss.backend.domain.MatchTeam.class.getName() + ".matchCalls");
            createCache(cm, com.phoenixdarts.toss.backend.domain.MatchScore.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.MatchAttendance.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.MatchCall.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.MachineArea.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.MachineArea.class.getName() + ".machines");
            createCache(cm, com.phoenixdarts.toss.backend.domain.Machine.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.Reward.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.Reward.class.getName() + ".rewardDetails");
            createCache(cm, com.phoenixdarts.toss.backend.domain.Reward.class.getName() + ".competitions");
            createCache(cm, com.phoenixdarts.toss.backend.domain.RewardDetail.class.getName());
            createCache(cm, com.phoenixdarts.toss.backend.domain.RewardDetail.class.getName() + ".rewardItems");
            createCache(cm, com.phoenixdarts.toss.backend.domain.RewardItem.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
