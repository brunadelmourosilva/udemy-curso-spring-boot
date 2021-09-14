package br.com.brunadelmouro.cursospringboot.domain;

import br.com.brunadelmouro.cursospringboot.domain.enums.StatePayment;

import java.io.Serializable;

public class PaymentCard extends Payment implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer installmentsNumber;

    public PaymentCard() {
    }

    public PaymentCard(Integer id, StatePayment state, Request request, Integer installmentsNumber) {
        super(id, state, request);
        this.installmentsNumber = installmentsNumber;
    }

    public Integer getInstallmentsNumber() {
        return installmentsNumber;
    }

    public void setInstallmentsNumber(Integer installmentsNumber) {
        this.installmentsNumber = installmentsNumber;
    }

}
