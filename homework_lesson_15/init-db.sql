CREATE TABLE IF NOT EXISTS notice (
    id SERIAL PRIMARY KEY,
    message TEXT,
    type TEXT,
    processed BOOLEAN
);
