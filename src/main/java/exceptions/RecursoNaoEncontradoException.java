package exceptions;

public class RecursoNaoEncontradoException extends Exception {

    public RecursoNaoEncontradoException() {
        super("Id não pode ser encontrado.");
    }
}
