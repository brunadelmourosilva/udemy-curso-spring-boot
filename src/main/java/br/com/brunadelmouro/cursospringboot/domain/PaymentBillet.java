package br.com.brunadelmouro.cursospringboot.domain;

import br.com.brunadelmouro.cursospringboot.domain.enums.StatusPayment;

import java.io.Serializable;
import java.util.Date;

public class PaymentBillet extends Payment implements Serializable {
    private static final long serialVersionUID = 1L;

    private Date dueDate;
    private Date payDate;

    public PaymentBillet(){
    }

    public PaymentBillet(Integer id, StatusPayment status, Request request, Date dueDate, Date payDate) {
        super(id, status, request);
        this.dueDate = dueDate;
        this.payDate = payDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }
}
