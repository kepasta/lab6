package server.commands;

import java.time.LocalDateTime;

import common.data.Movie;
import common.exceptions.CollectionIsEmptyException;
import common.exceptions.WrongAmountOfElementsException;
import common.exceptions.MovieNotFoundException;
import server.manager.CollectionManager;
import common.interaction.MovieRaw;
import server.manager.ResponseOutputer;


/**
 * Command 'remove_greater'. Removes elements greater than user entered.
 */
public class RemoveGreaterCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public RemoveGreaterCommand(CollectionManager collectionManager) {
        super("remove_greater", "{element}", "удалить из коллекции все элементы, превышающие заданный");
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
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            MovieRaw movieRaw = (MovieRaw) objectArgument;
            Movie movieToFind = new Movie(
                    collectionManager.generateNextId(),
                    movieRaw.getName(),
                    movieRaw.getCoordinates(),
                    LocalDateTime.now(),
                    movieRaw.getOscarCount(),
                    movieRaw.getUsaBoxOffice(),
                    movieRaw.getGenre(),
                    movieRaw.getMpaaRating(),
                    movieRaw.getOperator()
            );
            Movie movieFromCollection = collectionManager.getByValue(movieToFind);
            if (movieFromCollection == null) throw new MovieNotFoundException();
            collectionManager.removeGreater(movieFromCollection);
            ResponseOutputer.appendln("Фильмы успешно удалены!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Использование: '" + getName() + " " + getUsage() + "'");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appenderror("Коллекция пуста!");
        } catch (MovieNotFoundException exception) {
            ResponseOutputer.appenderror("Фильмы с такими характеристиками в коллекции нет!");
        } catch (ClassCastException exception) {
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        }
        return false;
    }
}
