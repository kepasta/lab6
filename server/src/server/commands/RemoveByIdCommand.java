package server.commands;

import common.data.Movie;
import common.exceptions.CollectionIsEmptyException;
import common.exceptions.MovieNotFoundException;
import common.exceptions.WrongAmountOfElementsException;
import server.manager.CollectionManager;
import server.manager.ResponseOutputer;

/**
 * Command 'remove_by_id'. Removes the element by its ID.
 */
public class RemoveByIdCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("remove_by_id", "<ID>", "удалить элемент из коллекции по ID");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     *
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            Long id = Long.parseLong(stringArgument);
            Movie movieToRemove = collectionManager.getById(id);
            if (movieToRemove == null) throw new MovieNotFoundException();
            collectionManager.removeFromCollection(movieToRemove);
            ResponseOutputer.appendln("Фильм успешно удален!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Использование: '" + getName() + " " + getUsage() + "'");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appenderror("Коллекция пуста!");
        } catch (NumberFormatException exception) {
            ResponseOutputer.appenderror("ID должен быть представлен числом!");
        } catch (MovieNotFoundException exception) {
            ResponseOutputer.appenderror("Фильма с таким ID в коллекции нет!");
        }
        return false;
    }
}
