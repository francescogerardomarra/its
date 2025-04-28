CREATE TABLE shop_schema.Order (
    order_id SERIAL PRIMARY KEY,
    user_id_fk INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) CHECK (status IN ('pending', 'shipped', 'delivered', 'canceled')),
    FOREIGN KEY (user_id_fk) REFERENCES shop_schema.User(user_id) ON DELETE CASCADE
);