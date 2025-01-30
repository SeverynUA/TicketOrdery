CREATE TABLE IF NOT EXISTS Event (
id BIGSERIAL PRIMARY KEY,
event_date DATE NOT NULL,
name VARCHAR(100) NOT NULL,
place_id BIGINT NOT NULL,
CONSTRAINT fk_event_place FOREIGN KEY (place_id) REFERENCES Place(id) ON DELETE CASCADE
);