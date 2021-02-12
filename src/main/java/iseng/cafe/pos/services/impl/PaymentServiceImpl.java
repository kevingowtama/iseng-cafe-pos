package iseng.cafe.pos.services.impl;

import iseng.cafe.pos.entities.Payment;
import iseng.cafe.pos.repositories.PaymentRepository;
import iseng.cafe.pos.services.CustomerService;
import iseng.cafe.pos.services.MenuOrderService;
import iseng.cafe.pos.services.PaymentMethodService;
import iseng.cafe.pos.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl extends CommonServiceImpl<Payment, Integer> implements PaymentService {
    @Autowired
    private MenuOrderService menuOrderService;

    @Autowired
    private PaymentMethodService paymentMethodService;

    @Autowired
    private CustomerService customerService;

    public PaymentServiceImpl(PaymentRepository repository) {
        super(repository);
    }

}
