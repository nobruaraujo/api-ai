package com.nobru.api_ai.memory;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryChatRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemoryChatRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String generateChatId(String userId) {
        final String insertSql = "INSERT INTO CHAT_MEMORY (user_id) VALUES (?)";
        jdbcTemplate.update(insertSql, userId);

        final String selectSql = "SELECT conversation_id FROM CHAT_MEMORY WHERE user_id = ? ORDER BY conversation_id DESC LIMIT 1";
        return jdbcTemplate.queryForObject(selectSql, String.class, userId);
    }
}
