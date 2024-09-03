CREATE TABLE product_shipping_infos (
    id UUID,
    product_id UUID NOT NULL,
    product_volume NUMERIC NOT NULL,
    CONSTRAINT pk_product_shipping_info PRIMARY KEY(id)
);

CREATE TABLE shipping_methods (
    id UUID,
    method_name VARCHAR(255) NOT NULL,
    price_unit_volume NUMERIC NOT NULL,
    is_default BOOLEAN DEFAULT FALSE,
    CONSTRAINT pk_shipping_method PRIMARY KEY(id)
);

CREATE TABLE shippings (
    id UUID,
    order_id UUID NOT NULL,
    shipping_method_id UUID,
    shipping_charge NUMERIC NOT NULL DEFAULT 0.0,
    status VARCHAR(255),
--    shipping_time TIMESTAMP,
--    is_free_ship BOOL DEFAULT FALSE,
    CONSTRAINT pk_shipping PRIMARY KEY(id),
    CONSTRAINT fk_shipping_shipping_method
        FOREIGN KEY(shipping_method_id)
        REFERENCES shippings(id)
        ON DELETE SET NULL
);
