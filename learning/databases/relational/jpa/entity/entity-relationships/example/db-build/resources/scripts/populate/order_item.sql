CREATE TABLE shop_schema.Order_Item (
    order_id_fk INT NOT NULL,
    item_id_fk INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    PRIMARY KEY (order_id_fk, item_id_fk),
    FOREIGN KEY (order_id_fk) REFERENCES shop_schema.Order(order_id) ON DELETE CASCADE,
    FOREIGN KEY (item_id_fk) REFERENCES shop_schema.Item(item_id) ON DELETE CASCADE
);