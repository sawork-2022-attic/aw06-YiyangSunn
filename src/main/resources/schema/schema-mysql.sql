CREATE TABLE IF NOT EXISTS product (
    asin CHAR(10) PRIMARY KEY ,
    main_cat VARCHAR(255),
    title VARCHAR(255),
    also_view VARCHAR(1022),
    image_url_high_res VARCHAR(1022)
) ENGINE InnoDB DEFAULT CHARSET utf8mb4;
