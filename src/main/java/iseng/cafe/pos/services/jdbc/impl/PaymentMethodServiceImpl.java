package iseng.cafe.pos.services.jdbc.impl;

import iseng.cafe.pos.entities.PaymentMethod;
import iseng.cafe.pos.repositories.jdbc.PaymentMethodRepository;
import iseng.cafe.pos.services.jdbc.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Override
    public PaymentMethod save(PaymentMethod entity) {
        return paymentMethodRepository.save(entity);
    }

    @Override
    public Boolean remove(Integer id) {
        return paymentMethodRepository.remove(id);
    }

    @Override
    public PaymentMethod findById(Integer id) {
        return paymentMethodRepository.findById(id);
    }

    @Override
    public List<PaymentMethod> findAll() {
        return paymentMethodRepository.findAll();
    }
}
