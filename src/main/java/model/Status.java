package model;

public enum Status {
    ATIVA(1),
    ENCERRADA(2),
    VENCIDA(3),
    CONCLUIDA(4);

    private int id;

    Status(int id) {
        this.id = id;
    }


}
