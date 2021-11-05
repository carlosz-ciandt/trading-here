package com.trading.tradinghere.gateway.database.repository;

import com.trading.tradinghere.domain.Greeting;
import com.trading.tradinghere.gateway.database.entity.GreetingEntity;
import org.simpleflatmapper.jdbc.SqlTypeColumnProperty;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.jdbc.spring.ResultSetExtractorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.Types;
import java.util.List;
import java.util.UUID;

@Repository
public class GreetingRepository {

    private final NamedParameterJdbcOperations jdbcTemplate;

    @Autowired
    public GreetingRepository(NamedParameterJdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public GreetingEntity save(GreetingEntity greetingEntity) {
        var parameterSourceFactory = JdbcTemplateMapperFactory.newInstance()
                .addColumnProperty("uuid", SqlTypeColumnProperty.of(Types.OTHER))
                .newSqlParameterSourceFactory(GreetingEntity.class);

        var keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update("INSERT INTO hello(uuid, phrase) "
                .concat("VALUES(:uuid, :phrase) "),
                parameterSourceFactory.newSqlParameterSource(greetingEntity),
                keyHolder,
                new String[] { "id" }
        );

        Long id = keyHolder.getKey().longValue();

        GreetingEntity savedGreeting = greetingEntity.toBuilder()
                .id(id)
                .build();

        return savedGreeting;
    }

    public GreetingEntity findByUuid(UUID uuid) {
        ResultSetExtractorImpl<GreetingEntity> resultSetExtractor = JdbcTemplateMapperFactory.newInstance()
                .addColumnProperty("uuid", SqlTypeColumnProperty.of(Types.OTHER))
                .newResultSetExtractor(GreetingEntity.class);

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("uuid", uuid);


        List<GreetingEntity> greetingEntities = this.jdbcTemplate.query("SELECT id, "
                        .concat("uuid, ")
                        .concat("phrase ")
                        .concat("FROM hello ")
                        .concat("WHERE uuid = :uuid "),
                params,
                resultSetExtractor);

        return greetingEntities != null ? greetingEntities.get(0) : null;
    }
}
