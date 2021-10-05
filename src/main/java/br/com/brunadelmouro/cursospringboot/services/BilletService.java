package br.com.brunadelmouro.cursospringboot.services;

import br.com.brunadelmouro.cursospringboot.domain.PaymentBillet;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class BilletService {

    //7 days after the date
    public void fillPaymentWithBillet(PaymentBillet paymentBillet, Date requestTime){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(requestTime);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        paymentBillet.setDueDate(calendar.getTime());
    }
}
