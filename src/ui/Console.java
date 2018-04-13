package ui;

import common.InfoProcess;
import common.Maintainance;
import common.Report;
import db.Database;
import model.Model;
import sun.applet.Main;

import java.io.*;

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
            default: out.println(ERROR_CONSOLE_INVALID_PARAMETER);
        }
    }

    public void createHotel(String[] args) {
        // Print parameter detail
        out.println(PROMPT_PARAMETER_HOTEL);
        out.print(CONSOLE_MARKER_PARAMETER);

        // Accept parameter and execute
        String[] parameters;
        try {
            parameters = br.readLine().split(",");
            InfoProcess.createHotel(parameters[0].trim(), parameters[1].trim(), parameters[2].trim(), parameters[3].trim());
        } catch (Exception e) {
            out.println(ERROR_CONSOLE_INVALID_PARAMETER);
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
