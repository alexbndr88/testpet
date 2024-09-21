package entities;

import lombok.Data;

@Data
public class Product {
    private String product_title;
    private int product_id;
    private int count_of_products;
    private int product_price;
    private int service_type_id;
    private int category_id;
    private String product_description;

}
