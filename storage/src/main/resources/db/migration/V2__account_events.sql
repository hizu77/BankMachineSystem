CREATE TABLE account_events (
    id SERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    event_type VARCHAR(50) NOT NULL,
    changes JSONB,
    date TIMESTAMP NOT NULL
);