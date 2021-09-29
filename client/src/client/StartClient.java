package client;

import client.manager.UserHandler;

import java.util.Scanner;

/**
 * Main client class. Creates all client instances.
 *
 * @author Pozdnyakov Egor.
 */
public class StartClient {
    public static final int port = 1821;
    public static final String PS1 = "$ ";
    public static final String PS2 = "> ";

    private static final int RECONNECTION_TIMEOUT = 5 * 1000;
    private static final int MAX_RECONNECTION_ATTEMPTS = 5;




    public static void main(String[] args) {
        Scanner userScanner = new Scanner(System.in);
        UserHandler userHandler = new UserHandler(userScanner);
        Client client = new Client("127.0.0.1", port, RECONNECTION_TIMEOUT, MAX_RECONNECTION_ATTEMPTS, userHandler);
        client.run();
        userScanner.close();
    }
}