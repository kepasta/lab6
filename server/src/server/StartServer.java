package server;

import server.commands.*;
import server.manager.FileManager;
import server.manager.CollectionManager;
import server.manager.CommandManager;
import server.manager.RequestHandler;

/**
 * Main server class. Creates all server instances.
 *
 * @author Pozdnyakov Egor.
 */
public class StartServer {
    public static final int PORT = 1821;
    public static final int CONNECTION_TIMEOUT = 60 * 1000;
    public static final String ENV_VARIABLE = "LABA";


    public static void main(String[] args) {
        FileManager collectionFileManager = new FileManager(ENV_VARIABLE);
        CollectionManager collectionManager = new CollectionManager(collectionFileManager);
        CommandManager commandManager = new CommandManager(
                new HelpCommand(),
                new InfoCommand(collectionManager),
                new ShowCommand(collectionManager),
                new AddCommand(collectionManager),
                new UpdateCommand(collectionManager),
                new RemoveByIdCommand(collectionManager),
                new ExitCommand(),
                new ExecuteScriptCommand(),
                new RemoveGreaterCommand(collectionManager),
                new HistoryCommand(),
                new CountLessThanOscarsCountCommand(collectionManager),
                new SaveCommand(collectionManager),
                new ServerExitCommand(),
                new ClearCommand(collectionManager)
        );
        RequestHandler requestHandler = new RequestHandler(commandManager);
        Server server = new Server(PORT, CONNECTION_TIMEOUT, requestHandler);
        server.run();
    }
}