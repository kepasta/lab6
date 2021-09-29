package server.commands;

import common.data.*;
import common.exceptions.CollectionIsEmptyException;
import common.exceptions.MovieNotFoundException;
import common.exceptions.WrongAmountOfElementsException;
import common.interaction.MovieRaw;
import server.manager.CollectionManager;
import server.manager.ResponseOutputer;

import java.time.LocalDateTime;

/**
 * Command 'update'. Updates the information about selected marine.
 */
public class UpdateCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public UpdateCommand(CollectionManager collectionManager) {
        super("update", "<ID> {element}", "обновить значение элемента коллекции по ID");
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
            if (stringArgument.isEmpty() || objectArgument == null) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();

            Long id = Long.parseLong(stringArgument);
            if (id <= 0) throw new NumberFormatException();
            Movie oldMovie = collectionManager.getById(id);
            if (oldMovie == null) throw new MovieNotFoundException();

            MovieRaw movieRaw = (MovieRaw) objectArgument;
            String name = movieRaw.getName() == null ? oldMovie.getName() : movieRaw.getName();
            Coordinates coordinates = movieRaw.getCoordinates() == null ? oldMovie.getCoordinates() : movieRaw.getCoordinates();
            LocalDateTime creationDate = oldMovie.getCreationDate();
            Long oscars = movieRaw.getOscarCount() == -1 ? oldMovie.getOscarCount() : movieRaw.getOscarCount();
            Long usaBoxOffice = movieRaw.getUsaBoxOffice() == null ? oldMovie.getUsaBoxOffice() : movieRaw.getUsaBoxOffice();
            MovieGenre movieGenre = movieRaw.getGenre() == null ? oldMovie.getGenre() : movieRaw.getGenre();
            MpaaRating mpaaRating = movieRaw.getMpaaRating() == null ? oldMovie.getMpaaRating() : movieRaw.getMpaaRating();
            Person operator = movieRaw.getOperator() == null ? oldMovie.getOperator() : movieRaw.getOperator();

            collectionManager.removeFromCollection(oldMovie);
            collectionManager.addToCollection(new Movie(
                    id,
                    name,
                    coordinates,
                    creationDate,
                    oscars,
                    usaBoxOffice,
                    movieGenre,
                    mpaaRating,
                    operator
            ));
            ResponseOutputer.appendln("Фильм успешно изменен!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Использование: '" + getName() + " " + getUsage() + "'");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appenderror("Коллекция пуста!");
        } catch (NumberFormatException exception) {
            ResponseOutputer.appenderror("ID должен быть представлен положительным числом!");
        } catch (MovieNotFoundException exception) {
            ResponseOutputer.appenderror("Фильма с таким ID в коллекции нет!");
        } catch (ClassCastException exception) {
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        }
        return false;
    }
}
