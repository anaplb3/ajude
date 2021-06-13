package exceptions;

public class DeadlineInvalidoException extends Exception {

    public DeadlineInvalidoException() {
        super("Data do deadline deve ser no futuro!");
    }
}
