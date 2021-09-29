package server.commands;

import common.exceptions.WrongAmountOfElementsException;
import server.manager.ResponseOutputer;
import server.manager.CollectionManager;
import common.exceptions.CollectionIsEmptyException;

/**
 * Command 'CountLessThanOscarsCount'. It's here just for logical structure.
 */
public class CountLessThanOscarsCountCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public CountLessThanOscarsCountCommand(CollectionManager collectionManager) {
        super("count_oscars", "<number>", "вывести количество элементов, значение поля oscarsCount которых меньше заданного");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            Long oscar_count = 0L;
            Long oscar_asked = Long.parseLong(stringArgument);
            int movie_count = 0;
            for (int i = 0; i <= collectionManager.collectionSize(); i++) {
                Long j = 0L;
                oscar_count = collectionManager.getById(j).getOscarCount();
                if (oscar_count < oscar_asked) movie_count++;
                j++;
            }
            if (oscar_count == 0) throw new CollectionIsEmptyException();
            ResponseOutputer.appendln("Количество фильмов с меньше" + oscar_asked + "оскаров: " + movie_count);
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Использование: '" + getName() + " " + getUsage() + "'");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appenderror("Коллекция пуста!");
        }
        return false;
    }
}
