package br.com.brunadelmouro.cursospringboot.domain.enums;

public enum CustomerType {
    PESSOAFISICA(1, "Pessoa Física"),
    PESSOAJURIDICA(2, "Pessoa Jurídica");

    private int cod;
    private String description;

    CustomerType(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public String getDescription() {
        return description;
    }

    //return object - enum
    public static CustomerType toEnum(Integer cod){

        if(cod == null) return null;

        for (CustomerType ct :CustomerType.values()) {
            if(cod.equals(ct.getCod()))
                return ct;
        }
        throw new IllegalArgumentException("Invalid id " + cod);
    }
}
