package server.commands;

import java.time.LocalDateTime;

import common.data.Movie;
import common.interaction.MovieRaw;
import common.exceptions.WrongAmountOfElementsException;
import server.manager.CollectionManager;
import server.manager.ResponseOutputer;

/**
 * Command 'add'. Adds a new element to collection.
 */
public class AddCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager) {
        super("add", "{element}", "добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument == null) throw new WrongAmountOfElementsException();
            MovieRaw movieRaw = (MovieRaw) objectArgument;
            collectionManager.addToCollection(new Movie(
                    collectionManager.generateNextId(),
                    movieRaw.getName(),
                    movieRaw.getCoordinates(),
                    LocalDateTime.now(),
                    movieRaw.getOscarCount(),
                    movieRaw.getUsaBoxOffice(),
                    movieRaw.getGenre(),
                    movieRaw.getMpaaRating(),
                    movieRaw.getOperator()
            ));
            ResponseOutputer.appendln("Фильм успешно добавлен!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Использование: '" + getName() + " " + getUsage() + "'");
        } catch (ClassCastException exception) {
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        }
        return false;
    }
}