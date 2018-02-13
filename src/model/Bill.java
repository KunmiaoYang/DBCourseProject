package model;

/**
 *
 * Created by Kunmiao Yang on 2/12/2018.
 */
public class Bill {
    int id;
    Account account;
    CheckIn checkIn;
    float amount;

    public Bill(Account account, CheckIn checkIn, float amount) {
        this.account = account;
        this.checkIn = checkIn;
        this.amount = amount;
        // TODO: create tuple in database
    }

    public static Bill getById(int id) {
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public CheckIn getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(CheckIn checkIn) {
        this.checkIn = checkIn;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
