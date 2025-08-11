CREATE TABLE rides (
    id BIGSERIAL PRIMARY KEY,
    origin VARCHAR(255) NOT NULL,
    destination VARCHAR(255) NOT NULL,
    departure_time TIMESTAMP NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    available_seats INT NOT NULL,
    driver_id BIGINT NOT NULL,
    CONSTRAINT fk_driver FOREIGN KEY (driver_id) REFERENCES users(id)
);
