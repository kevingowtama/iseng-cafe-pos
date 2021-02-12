package iseng.cafe.pos.services.impl;

import iseng.cafe.pos.entities.PaymentMethod;
import iseng.cafe.pos.repositories.PaymentMethodRepository;
import iseng.cafe.pos.services.PaymentMethodService;
import org.springframework.stereotype.Service;

@Service
public class PaymentMethodServiceImpl extends CommonServiceImpl<PaymentMethod, Integer> implements PaymentMethodService {
    public PaymentMethodServiceImpl(PaymentMethodRepository repository) {
        super(repository);
    }

}
