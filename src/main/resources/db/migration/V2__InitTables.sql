ALTER TABLE product ADD category varchar;
ALTER TABLE product ADD historic integer references product(id);
