package model;

import java.util.Date;

/**
 *
 * Created by Kunmiao Yang on 2/12/2018.
 */
public class Customer extends Person {
    String email;

    public Customer(int ssn, String name, String phoneNum, Date birth, String email) {
        super(ssn, name, phoneNum, birth);
        this.email = email;
        // TODO: create tuple in database
    }

    public static Customer getBySSN(int ssn) {
        // TODO: get instance from database
        return null;
    }

    @Override
    public boolean remove() {
        if(!super.remove()) return false;
        // TODO: remove from DB
        return false;
    }

    @Override
    public boolean update() {
        if(!super.update()) return false;
        // TODO: update attributes to DB
        return false;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
