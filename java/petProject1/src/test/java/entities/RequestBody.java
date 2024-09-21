package entities;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class RequestBody {
    private String email;
    private String product_title;
private String invoice_title;
private int client_id;
private String date_of_creation;
public List<Product> products;
private int sum;
    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getDate_of_payment() {
        return date_of_payment;
    }

    public void setDate_of_payment(String date_of_payment) {
        this.date_of_payment = date_of_payment;
    }

    public String getDo_remind_every_month() {
        return do_remind_every_month;
    }

    public void setDo_remind_every_month(String do_remind_every_month) {
        this.do_remind_every_month = do_remind_every_month;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public int getService_type_id() {
        return service_type_id;
    }

    public void setService_type_id(int service_type_id) {
        this.service_type_id = service_type_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getRemind_before_day() {
        return remind_before_day;
    }

    public void setRemind_before_day(int remind_before_day) {
        this.remind_before_day = remind_before_day;
    }

    private String product_description;
    private String date_of_payment;
    private String do_remind_every_month;
    private int product_price;
    private int service_type_id;
    private int category_id;
    private int remind_before_day;

    public String getCategory_description() {
        return Category_description;
    }

    public void setCategory_description(String category_description) {
        Category_description = category_description;
    }

    public boolean isFlag() {
        return Flag;
    }

    public void setFlag(boolean flag) {
        Flag = flag;
    }

    private String password;
private String Category_description;
private boolean Flag;
    public String getCompany_name() {
        return Company_name;
    }

    public void setCompany_name(String company_name) {
        Company_name = company_name;
    }

    public String getSeller_name() {
        return Seller_name;
    }

    public void setSeller_name(String seller_name) {
        Seller_name = seller_name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    private String Company_name;
           private  String Seller_name;
    private  String Address;
    private  String Email;
    private  String Number;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCategory_title(String title) {
    }
}
