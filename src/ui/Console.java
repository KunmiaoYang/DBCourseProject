package ui;

import common.InfoProcess;
import common.Maintainance;
import common.Report;
import db.Database;
import model.Customer;
import model.Hotel;
import model.Model;
import sun.applet.Main;

import java.io.*;
import java.time.LocalDate;

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
    public void menu(String[] args) {
        out.println(PROMPT_MENU);
    }

    public void create(String[] args) {
        if(args.length < 2) out.println(ERROR_CONSOLE_INVALID_PARAMETER);
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
            String[] parameters = br.readLine().split(",");
            InfoProcess.createHotel(parameters[0].trim(), parameters[1].trim(), parameters[2].trim(), parameters[3].trim());
            out.println(PROMPT_STATUS_SUCCESS);
        } catch (Exception e) {
            out.println(e.getMessage());
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
            String[] parameters = br.readLine().split(",");
            InfoProcess.createRoom(Hotel.getById(Integer.parseInt(parameters[0].trim()))
                    , Integer.parseInt(parameters[1].trim())
                    , parameters[2].trim()
                    , true);
            out.println(PROMPT_STATUS_SUCCESS);
        } catch (Exception e) {
            out.println(e.getMessage());
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
            String[] parameters = br.readLine().split(",");
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
            out.println(e.getMessage());
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
            String[] parameters = br.readLine().split(",");
            InfoProcess.createCustomer(Integer.parseInt(parameters[0].trim())   // Customer ID
                    , parameters[1].trim()                                      // Name
                    , parameters[2].trim()                                      // Phone number
                    , parameters[3].trim()                                      // E-mail
                    , LocalDate.parse(parameters[4].trim()));                   // Birth date
            out.println(PROMPT_STATUS_SUCCESS);
        } catch (Exception e) {
            out.println(e.getMessage());
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
            String[] parameters = br.readLine().split(",");
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
            out.println(e.getMessage());
            out.println(ERROR_CONSOLE_INVALID_PARAMETER);
            out.println(PROMPT_STATUS_FAIL);
        }
    }

    public void read(String[] args) {
        // TODO: Print parameter detail

        // TODO: accept further parameter and execute
    }

    public void update(String[] args) {
        // TODO: Print parameter detail

        // TODO: accept further parameter and execute
    }

    public void delete(String[] args) {
        // TODO: Print parameter detail

        // TODO: accept further parameter and execute
    }

    public void launch() throws IOException {
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
