package ui;

import common.InfoProcess;
import common.Report;
import db.Database;
import model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.List;

import static common.Constants.*;

public class Console {
    InputStream in;
    BufferedReader br;
    PrintStream out;

    public Console(InputStream in, PrintStream out) {
        this.in = in;
        this.out = out;
        this.br = new BufferedReader(new InputStreamReader(this.in));
    }

    /**
     * Print menu
     * @param args
     */
    private void menu(String[] args) {
        out.println(PROMPT_MENU);
    }

    //region Create
    public void create(String[] args) {
        if(args.length < 2) {
            out.println(ERROR_CONSOLE_INVALID_PARAMETER);
            return;
        }
        out.println(PROMPT_CREATE);
        switch (args[1].toLowerCase()) {
            case CMD_OBJECT_HOTEL: createHotel(args); return;
            case CMD_OBJECT_ROOM: createRoom(args); return;
            case CMD_OBJECT_STAFF: createStaff(args); return;
            case CMD_OBJECT_CUSTOMER: createCustomer(args); return;
            case CMD_OBJECT_ACCOUNT: createAccount(args); return;
            default: out.println(ERROR_CONSOLE_INVALID_PARAMETER);
        }
    }

    private void createHotel(String[] args) {
        // Print parameter detail
        out.println(PROMPT_PARAMETER_HOTEL);
        out.print(CONSOLE_MARKER_PARAMETER);

        // Accept parameter and execute
        try {
            String[] parameters = br.readLine().split(",", 4);
            InfoProcess.createHotel(parameters[0].trim(), parameters[1].trim(), parameters[2].trim(), parameters[3].trim());
            out.println(PROMPT_STATUS_SUCCESS);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            out.println(ERROR_CONSOLE_INVALID_PARAMETER);
            out.println(PROMPT_STATUS_FAIL);
        }
    }

    private void createRoom(String[] args) {
        // Print parameter detail
        out.println(PROMPT_PARAMETER_ROOM);
        out.print(CONSOLE_MARKER_PARAMETER);

        // Accept parameter and execute
        try {
            String[] parameters = br.readLine().split(",", 3);
            InfoProcess.createRoom(Hotel.getById(Integer.parseInt(parameters[0].trim()))
                    , Integer.parseInt(parameters[1].trim())
                    , parameters[2].trim()
                    , true);
            out.println(PROMPT_STATUS_SUCCESS);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            out.println(ERROR_CONSOLE_INVALID_PARAMETER);
            out.println(PROMPT_STATUS_FAIL);
        }
    }

    private void createStaff(String[] args) {
        // Print parameter detail
        out.println(PROMPT_PARAMETER_STAFF);
        out.print(CONSOLE_MARKER_PARAMETER);

        // Accept parameter and execute
        try {
            String[] parameters = br.readLine().split(",", 8);
            InfoProcess.createStaff(Integer.parseInt(parameters[1].trim())      // Staff ID
                    , Integer.parseInt(parameters[2].trim())                    // Age
                    , parameters[3].trim()                                      // Name
                    , parameters[4].trim()                                      // Role
                    , parameters[5].trim()                                      // Department
                    , parameters[6].trim()                                      // Phone number
                    , parameters[7].trim()                                      // Address
                    , Hotel.getById(Integer.parseInt(parameters[0].trim())));   // Hotel
            out.println(PROMPT_STATUS_SUCCESS);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            out.println(ERROR_CONSOLE_INVALID_PARAMETER);
            out.println(PROMPT_STATUS_FAIL);
        }
    }

    private void createCustomer(String[] args) {
        // Print parameter detail
        out.println(PROMPT_PARAMETER_CUSTOMER);
        out.print(CONSOLE_MARKER_PARAMETER);

        // Accept parameter and execute
        try {
            String[] parameters = br.readLine().split(",", 5);
            InfoProcess.createCustomer(Integer.parseInt(parameters[0].trim())   // Customer ID
                    , parameters[1].trim()                                      // Name
                    , parameters[2].trim()                                      // Phone number
                    , parameters[3].trim()                                      // E-mail
                    , LocalDate.parse(parameters[4].trim()));                   // Birth date
            out.println(PROMPT_STATUS_SUCCESS);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            out.println(ERROR_CONSOLE_INVALID_PARAMETER);
            out.println(PROMPT_STATUS_FAIL);
        }
    }

    private void createAccount(String[] args) {
        // Print parameter detail
        out.println(PROMPT_PARAMETER_ACCOUNT);
        out.print(CONSOLE_MARKER_PARAMETER);

        // Accept parameter and execute
        try {
            String[] parameters = br.readLine().split(",", 5);
            String payMethod = parameters[2].trim().toLowerCase();
            InfoProcess.createAccount(
                    Customer.getById(Integer.parseInt(parameters[0].trim()))    // Customer ID
                    , parameters[1].trim()                                      // Address
                    , payMethod                                                 // Pay method
                    , "cash".equals(payMethod)? null: Integer.parseInt(parameters[3].trim())
                                                                                // Card number
                    , parameters[4].trim());                                    // SSN
            out.println(PROMPT_STATUS_SUCCESS);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            out.println(ERROR_CONSOLE_INVALID_PARAMETER);
            out.println(PROMPT_STATUS_FAIL);
        }
    }
    //endregion

    //region Read
    private void read(String[] args) {
        if(args.length < 2) {
            out.println(ERROR_CONSOLE_INVALID_PARAMETER);
            return;
        }
        out.println(PROMPT_CREATE);
        switch (args[1].toLowerCase()) {
            case CMD_OBJECT_ROOM: readRoom(args); return;
            default: out.println(ERROR_CONSOLE_INVALID_PARAMETER);
        }
    }

    private void readRoom(String[] args) {
        // Print parameter detail
        out.println(PROMPT_CONDITION_ROOM);
        out.print(CONSOLE_MARKER_PARAMETER);

        // Accept further parameter and execute
        try {
            String[] parameters = br.readLine().split(",", 3);
            Hotel hotel = "".equals(parameters[1].trim())?
                    null : Hotel.getById(Integer.parseInt(parameters[1].trim()));
            String type = "".equals(parameters[2].trim())?
                    null : parameters[2].trim();
            List<Room> rooms = InfoProcess.getAvailableRooms(
                    Integer.parseInt(parameters[0].trim()), hotel, type);
            out.println(PROMPT_TABLE_HEADER_ROOM);
            for(Room room: rooms) {
                hotel = room.getHotel();
                out.println(String.format(PROMPT_TABLE_FORMAT_ROOM,
                        hotel.getId(), hotel.getName(), room.getNumber(),
                        room.getType(), room.getMaxOccupy(), room.getNightlyRate()));
            }
            out.println(PROMPT_STATUS_SUCCESS);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            out.println(ERROR_CONSOLE_INVALID_PARAMETER);
            out.println(PROMPT_STATUS_FAIL);
        }

    }
    //endregion

    //region Update
    public void update(String[] args) {
        if(args.length < 2) {
            out.println(ERROR_CONSOLE_INVALID_PARAMETER);
            return;
        }
        out.println(PROMPT_UPDATE);
        switch (args[1].toLowerCase()) {
            case CMD_OBJECT_HOTEL: updateHotel(args); return;
            case CMD_OBJECT_ROOM: updateRoom(args); return;
            case CMD_OBJECT_STAFF: updateStaff(args); return;
            case CMD_OBJECT_CUSTOMER: updateCustomer(args); return;
            case CMD_OBJECT_ACCOUNT: updateAccount(args); return;
            default: out.println(ERROR_CONSOLE_INVALID_PARAMETER);
        }
    }

    private void updateHotel(String[] args) {
        // TODO: Print hotels

        // Print parameter detail
        out.println(PROMPT_PARAMETER_KEY_HOTEL + ", " + PROMPT_PARAMETER_HOTEL);
        out.print(CONSOLE_MARKER_PARAMETER);

        // Accept parameter and execute
        try {
            String[] parameters = br.readLine().split(",", 5);
            Hotel hotel = Hotel.getById(Integer.parseInt(parameters[0]));
            if(null == hotel) throw new Exception(ERROR_CONSOLE_INVALID_KEY);
            if(!"".equals(parameters[1].trim())) hotel.setName(parameters[1].trim());
            if(!"".equals(parameters[2].trim())) hotel.setCity(parameters[2].trim());
            if(!"".equals(parameters[3].trim())) hotel.setAddress(parameters[3].trim());
            if(!"".equals(parameters[4].trim())) hotel.setPhoneNumber(parameters[4].trim());
            InfoProcess.update(hotel);
            out.println(PROMPT_STATUS_SUCCESS);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            out.println(ERROR_CONSOLE_INVALID_PARAMETER);
            out.println(PROMPT_STATUS_FAIL);
        }
    }

    private void updateRoom(String[] args) {
        // TODO: Print rooms

        // Print parameter detail
        out.println(PROMPT_PARAMETER_ROOM);
        out.print(CONSOLE_MARKER_PARAMETER);

        // Accept parameter and execute
        try {
            String[] parameters = br.readLine().split(",", 3);
            Room room = Room.getById(Integer.parseInt(parameters[0]), Integer.parseInt(parameters[1]));
            if(null == room) throw new Exception(ERROR_CONSOLE_INVALID_KEY);
            if(!"".equals(parameters[2].trim())) room.setType(parameters[2].trim());
            InfoProcess.update(room);
            out.println(PROMPT_STATUS_SUCCESS);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            out.println(ERROR_CONSOLE_INVALID_PARAMETER);
            out.println(PROMPT_STATUS_FAIL);
        }
    }

    private void updateStaff(String[] args) {
        // TODO: Print staffs

        // Print parameter detail
        out.println(PROMPT_PARAMETER_STAFF);
        out.print(CONSOLE_MARKER_PARAMETER);

        // Accept parameter and execute
        try {
            String[] parameters = br.readLine().split(",", 8);
            Staff staff = Staff.getById(Integer.parseInt(parameters[1]));
            if(null == staff) throw new Exception(ERROR_CONSOLE_INVALID_KEY);
            if(!"".equals(parameters[0].trim())) staff.setHotel(Hotel.getById(Integer.parseInt(parameters[0].trim())));
            if(!"".equals(parameters[2].trim())) staff.setAge(Integer.parseInt(parameters[2].trim()));
            if(!"".equals(parameters[3].trim())) staff.setName(parameters[3].trim());
            if(!"".equals(parameters[4].trim())) staff.setTitle(parameters[4].trim());
            if(!"".equals(parameters[5].trim())) staff.setDepartment(parameters[5].trim());
            if(!"".equals(parameters[6].trim())) staff.setPhoneNum(parameters[6].trim());
            if(!"".equals(parameters[7].trim())) staff.setAddress(parameters[7].trim());
            InfoProcess.update(staff);
            out.println(PROMPT_STATUS_SUCCESS);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            out.println(ERROR_CONSOLE_INVALID_PARAMETER);
            out.println(PROMPT_STATUS_FAIL);
        }
    }

    private void updateCustomer(String[] args) {
        // TODO: Print customers

        // Print parameter detail
        out.println(PROMPT_PARAMETER_CUSTOMER);
        out.print(CONSOLE_MARKER_PARAMETER);

        // Accept parameter and execute
        try {
            String[] parameters = br.readLine().split(",", 5);
            Customer customer = Customer.getById(Integer.parseInt(parameters[0]));
            if(null == customer) throw new Exception(ERROR_CONSOLE_INVALID_KEY);
            if(!"".equals(parameters[1].trim())) customer.setName(parameters[1].trim());
            if(!"".equals(parameters[2].trim())) customer.setPhone(parameters[2].trim());
            if(!"".equals(parameters[3].trim())) customer.setEmail(parameters[3].trim());
            if(!"".equals(parameters[4].trim())) customer.setBirth(LocalDate.parse(parameters[4].trim()));
            InfoProcess.update(customer);
            out.println(PROMPT_STATUS_SUCCESS);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            out.println(ERROR_CONSOLE_INVALID_PARAMETER);
            out.println(PROMPT_STATUS_FAIL);
        }
    }

    private void updateAccount(String[] args) {
        // TODO: Print accounts

        // Print parameter detail
        out.println(PROMPT_PARAMETER_KEY_ACCOUNT + ", " + PROMPT_PARAMETER_ACCOUNT);
        out.print(CONSOLE_MARKER_PARAMETER);

        // Accept parameter and execute
        try {
            String[] parameters = br.readLine().split(",", 6);
            Account account = Account.getById(Integer.parseInt(parameters[0]));
            if(null == account) throw new Exception(ERROR_CONSOLE_INVALID_KEY);
            String payMethod = parameters[3].trim().toLowerCase();
            if(!"".equals(parameters[1].trim())) account.setCustomer(Customer.getById(Integer.parseInt(parameters[1].trim())));
            if(!"".equals(parameters[2].trim())) account.setAddress(parameters[2].trim());
            if(!"".equals(payMethod)) account.setPayMethod(payMethod);
            if(!"".equals(parameters[4].trim())) {
                if("cash".equals(payMethod)) account.setCardNumber(null);
                else account.setCardNumber(Integer.parseInt(parameters[4].trim()));
            }
            if(!"".equals(parameters[5].trim())) account.setSsn(parameters[5].trim());
            InfoProcess.update(account);
            out.println(PROMPT_STATUS_SUCCESS);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            out.println(ERROR_CONSOLE_INVALID_PARAMETER);
            out.println(PROMPT_STATUS_FAIL);
        }
    }
    //endregion

    //region Delete
    private void delete(String[] args) {
        if(args.length < 2) {
            out.println(ERROR_CONSOLE_INVALID_PARAMETER);
            return;
        }
        out.println(PROMPT_DELETE);
        switch (args[1].toLowerCase()) {
            case CMD_OBJECT_HOTEL: deleteHotel(args); return;
            case CMD_OBJECT_ROOM: deleteRoom(args); return;
            case CMD_OBJECT_STAFF: deleteStaff(args); return;
            case CMD_OBJECT_CUSTOMER: deleteCustomer(args); return;
            case CMD_OBJECT_ACCOUNT: deleteAccount(args); return;
            default: out.println(ERROR_CONSOLE_INVALID_PARAMETER);
        }
    }

    private void deleteHotel(String[] args) {
        // TODO: Print hotels

        // Print parameter detail
        out.println(PROMPT_PARAMETER_KEY_HOTEL);

        // Accept further parameter and execute
        try {
            String[] parameters = br.readLine().split(",");
            Hotel hotel = Hotel.getById(Integer.parseInt(parameters[0]));
            if(null == hotel) throw new Exception(ERROR_CONSOLE_INVALID_KEY);
            InfoProcess.remove(hotel);
            out.println(PROMPT_STATUS_SUCCESS);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            out.println(ERROR_CONSOLE_INVALID_PARAMETER);
            out.println(PROMPT_STATUS_FAIL);
        }
    }

    private void deleteRoom(String[] args) {
        // TODO: Print hotels

        // Print parameter detail
        out.println(PROMPT_PARAMETER_KEY_ROOM);

        // Accept further parameter and execute
        try {
            String[] parameters = br.readLine().split(",", 2);
            Room room = Room.getById(Integer.parseInt(parameters[0]), Integer.parseInt(parameters[1]));
            if(null == room) throw new Exception(ERROR_CONSOLE_INVALID_KEY);
            InfoProcess.remove(room);
            out.println(PROMPT_STATUS_SUCCESS);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            out.println(ERROR_CONSOLE_INVALID_PARAMETER);
            out.println(PROMPT_STATUS_FAIL);
        }
    }

    private void deleteStaff(String[] args) {
        // TODO: Print hotels

        // Print parameter detail
        out.println(PROMPT_PARAMETER_KEY_STAFF);

        // Accept further parameter and execute
        try {
            String[] parameters = br.readLine().split(",");
            Staff staff = Staff.getById(Integer.parseInt(parameters[0]));
            if(null == staff) throw new Exception(ERROR_CONSOLE_INVALID_KEY);
            InfoProcess.remove(staff);
            out.println(PROMPT_STATUS_SUCCESS);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            out.println(ERROR_CONSOLE_INVALID_PARAMETER);
            out.println(PROMPT_STATUS_FAIL);
        }
    }

    private void deleteCustomer(String[] args) {
        // TODO: Print hotels

        // Print parameter detail
        out.println(PROMPT_PARAMETER_KEY_CUSTOMER);

        // Accept further parameter and execute
        try {
            String[] parameters = br.readLine().split(",");
            Customer customer = Customer.getById(Integer.parseInt(parameters[0]));
            if(null == customer) throw new Exception(ERROR_CONSOLE_INVALID_KEY);
            InfoProcess.remove(customer);
            out.println(PROMPT_STATUS_SUCCESS);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            out.println(ERROR_CONSOLE_INVALID_PARAMETER);
            out.println(PROMPT_STATUS_FAIL);
        }
    }

    private void deleteAccount(String[] args) {
        // TODO: Print hotels

        // Print parameter detail
        out.println(PROMPT_PARAMETER_KEY_ACCOUNT);

        // Accept further parameter and execute
        try {
            String[] parameters = br.readLine().split(",");
            Account account = Account.getById(Integer.parseInt(parameters[0]));
            if(null == account) throw new Exception(ERROR_CONSOLE_INVALID_KEY);
            InfoProcess.remove(account);
            out.println(PROMPT_STATUS_SUCCESS);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            out.println(ERROR_CONSOLE_INVALID_PARAMETER);
            out.println(PROMPT_STATUS_FAIL);
        }
    }
    //endregion

    private void assignRoom(String[] args) {
        // Print parameter detail
        out.println(PROMPT_PARAMETER_ASSIGN);
        out.print(CONSOLE_MARKER_PARAMETER);

        // Accept further parameter and execute
        try {
            String[] parameters = br.readLine().split(",", 3);
            Room room = Room.getById(Integer.parseInt(parameters[0]), Integer.parseInt(parameters[1]));
            if(null == room) throw new Exception(ERROR_CONSOLE_INVALID_KEY);
            InfoProcess.assignRoom(room, Integer.parseInt(parameters[2]));
            out.println(PROMPT_STATUS_SUCCESS);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            out.println(ERROR_CONSOLE_INVALID_PARAMETER);
            out.println(PROMPT_STATUS_FAIL);
        }
    }

    private void releaseRoom(String[] args) {
        // Print parameter detail
        out.println(PROMPT_PARAMETER_KEY_ROOM);
        out.print(CONSOLE_MARKER_PARAMETER);

        // TODO: Accept further parameter and execute
    }

    private void launch() throws IOException {
        menu(new String[0]);
        out.print(CONSOLE_MARKER_COMMAND);
        for(String line = br.readLine(); !CMD_EXIT.equalsIgnoreCase(line); line = br.readLine()) {
            String[] command = line.split("\\s+");
            switch (command[0].toLowerCase()) {
                case CMD_MENU: menu(command); break;
                case CMD_CREATE: create(command); break;
                case CMD_READ: read(command); break;
                case CMD_UPDATE: update(command); break;
                case CMD_DELETE: delete(command); break;
                case CMD_ASSIGN: assignRoom(command); break;
                case CMD_RELEASE: releaseRoom(command); break;
                // TODO: add commands
                default: menu(new String[0]);
            }
            out.print(CONSOLE_MARKER_COMMAND);
        }
    }

    public static void main(String[] args) throws Exception {
        Database db = new Database(DB_DRIVER, DB_URL, DB_USER, DB_PASSWORD);
        try {
            Model.setDatabase(db);
            InfoProcess.setDatabase(db);
            Report.setDatabase(db);
            Console console = new Console(System.in, System.out);
            console.launch();
        } finally {
            db.close();
        }
    }
}
