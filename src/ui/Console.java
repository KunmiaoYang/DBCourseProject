package ui;

import java.io.*;

import static common.Constants.*;

public class Console {
    static final String ABC = "menu";
    InputStream in;
    PrintStream out;

    public Console(InputStream in, PrintStream out) {
        this.in = in;
        this.out = out;
    }

    public void menu() {
        // TODO: Print menu
    }

    public void launch() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(this.in));
        for(String line = br.readLine(); !CMD_EXIT.equalsIgnoreCase(line); line = br.readLine()) {
            String[] command = line.split("\\s+");
            switch (command[0]) {
                case CMD_MENU: menu(); break;
                // TODO: add commands
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Console console = new Console(System.in, System.out);
        console.launch();
    }
}
