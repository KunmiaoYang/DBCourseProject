package common;

import model.*;

import java.util.Date;
import java.util.Map;

/**
 * 
 * Created by Kunmiao Yang on 2/13/2018.
 */
public class Maintainance {
    public static Service getServiceById(int id) {
        return Service.getById(id);
    }
    public static Bill getBillById(int id) {
        return Bill.getById(id);
    }
    public static CheckIn getCheckInById(int id) {
        return CheckIn.getById(id);
    }
    public static Service createService(float price, String serviceName, CheckIn checkIn, Staff staff) {
        return new Service(price, serviceName, checkIn, staff);
    }
    public static Bill createBill(Customer customer, Account account, CheckIn checkIn, float amount) {
        return new Bill(account, checkIn, amount);
    }
    public static boolean updateService(Service service, Map<String, String> data) {
        // TODO: update data
        return false;
    }
    public static boolean updateBill(Bill bill, Map<String, String> data) {
        // TODO: update data
        return false;
    }
    public static CheckIn checkIn(Date checkInTime, Customer customer, Room room) {
        return new CheckIn(checkInTime, customer, room);
    }
    public static Bill checkOut(CheckIn checkIn, Account account) {
        return checkIn.checkOut(account);
    }
    public static boolean removeService(Service service) {
        return service.remove();
    }
    public static boolean removeBill(Bill bill) {
        return bill.remove();
    }
    public static boolean removeCheckIn(CheckIn checkIn) {
        return checkIn.remove();
    }
}
