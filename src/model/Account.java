package model;

/**
 *
 * Created by Kunmiao Yang on 2/12/2018.
 */
public class Account {
    int id;
    Customer customer;
    String address, payMethod, cardNumber;

    public Account(Customer customer, String address, String payMethod, String cardNumber) {
        this.customer = customer;
        this.address = address;
        this.payMethod = payMethod;
        this.cardNumber = cardNumber;
        // TODO: create tuple in database
    }

    public static Account getById(int id) {
        // TODO: get instance from database
        return null;
    }

    public boolean remove() {
        // TODO: remove from DB
        return false;
    }

    public boolean update() {
        // TODO: update attributes to DB
        return false;
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
