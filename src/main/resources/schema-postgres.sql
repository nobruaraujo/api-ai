-- Criação do tipo ENUM
CREATE TYPE chat_type AS ENUM ('USER', 'ASSISTANT', 'SYSTEM', 'TOOL');

CREATE TABLE IF NOT EXISTS spring_ai_chat_memory (
    conversation_id UUID NOT NULL,
    content TEXT NOT NULL,
    type chat_type NOT NULL,
    timestamp TIMESTAMP NOT NULL
);

CREATE INDEX IF NOT EXISTS spring_ai_chat_memory_conversation_id_timestamp_idx
    ON spring_ai_chat_memory (conversation_id, timestamp);

CREATE TABLE IF NOT EXISTS chat_memory (
    conversation_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id VARCHAR(50) NOT NULL
);
