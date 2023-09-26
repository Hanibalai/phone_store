CREATE TABLE brand (
    id   bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    name VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE phone (
    id         bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    brand_id   BIGINT           NOT NULL,
    model      VARCHAR(32)      NOT NULL,
    color      VARCHAR(32),
    price      DOUBLE PRECISION NOT NULL,
    created_at timestamptz DEFAULT current_timestamp,
    updated_at timestamptz DEFAULT current_timestamp,
    CONSTRAINT fk_phone_brand FOREIGN KEY (brand_id) REFERENCES Brand (id)
);