package br.com.brunadelmouro.cursospringboot.domain.enums;

public enum StatusPayment {
    PENDENTE(1, "Pendente"),
    QUITADO(2, "Quitado"),
    CANCELADO(3, "Cancelado");

    private int cod;
    private String description;

    StatusPayment(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public String getDescription() {
        return description;
    }

    //return object - enum(convert Integer to enum)
    public static StatusPayment toEnum(Integer cod){

        if(cod == null) return null;

        for (StatusPayment ct : StatusPayment.values()) {
            if(cod.equals(ct.getCod()))
                return ct;
        }
        throw new IllegalArgumentException("Invalid id " + cod);
    }
}
