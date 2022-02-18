package presentation;

import application.OperationalSystem;

public class Startup {
    public static void main(String[] args) {
        var so = new OperationalSystem("Linux", "4.0");

        so.initialize();
    }
}
