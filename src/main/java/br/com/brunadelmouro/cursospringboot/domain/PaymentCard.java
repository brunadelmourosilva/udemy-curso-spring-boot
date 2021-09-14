package br.com.brunadelmouro.cursospringboot.domain;

import br.com.brunadelmouro.cursospringboot.domain.enums.StatusPayment;

import java.io.Serializable;

public class PaymentCard extends Payment implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer installmentsNumber;

    public PaymentCard() {
    }

    public PaymentCard(Integer id, StatusPayment status, Request request, Integer installmentsNumber) {
        super(id, status, request);
        this.installmentsNumber = installmentsNumber;
    }

    public Integer getInstallmentsNumber() {
        return installmentsNumber;
    }

    public void setInstallmentsNumber(Integer installmentsNumber) {
        this.installmentsNumber = installmentsNumber;
    }

}
