CREATE TABLE product_supplier (
    id SERIAL UNIQUE PRIMARY KEY,
    product_id INT NOT NULL,
    supplier_id INT NOT NULL
);

ALTER TABLE product_supplier ADD CONSTRAINT fk_product_supplier_product
FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE product_supplier ADD CONSTRAINT fk_product_supplier_supplier
FOREIGN KEY (supplier_id) REFERENCES supplier (id);