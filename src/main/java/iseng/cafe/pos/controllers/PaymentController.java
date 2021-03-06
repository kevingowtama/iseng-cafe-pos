package iseng.cafe.pos.controllers;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import iseng.cafe.pos.entities.Customer;
import iseng.cafe.pos.entities.MenuOrder;
import iseng.cafe.pos.entities.Payment;
import iseng.cafe.pos.entities.PaymentMethod;
import iseng.cafe.pos.models.PagedList;
import iseng.cafe.pos.models.ResponseMessage;
import iseng.cafe.pos.models.payment.PaymentRequest;
import iseng.cafe.pos.models.payment.PaymentResponse;
import iseng.cafe.pos.models.payment.PaymentSearch;
import iseng.cafe.pos.services.CustomerService;
import iseng.cafe.pos.services.MenuOrderService;
import iseng.cafe.pos.services.PaymentMethodService;
import iseng.cafe.pos.services.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/payments")
@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MenuOrderService menuOrderService;

    @Autowired
    private PaymentMethodService paymentMethodService;

    @Autowired
    private ModelMapper modelMapper;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal server error."),
            @ApiResponse(
                    responseCode = "200",
                    description = "Success.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseMessage.class))))
    })

    @PostMapping
    public ResponseMessage<PaymentResponse> add(
            @RequestBody @Valid PaymentRequest model){
        Payment entity = modelMapper.map(model, Payment.class);

        Customer customer = customerService.findById(model.getCustomerId());
        entity.setCustomer(customer);

        MenuOrder menuOrder = menuOrderService.findById(model.getMenuOrderId());
        entity.setMenuOrder(menuOrder);

        PaymentMethod paymentMethod = paymentMethodService.findById(model.getPaymentMethodId());
        entity.setPaymentMethod(paymentMethod);

        entity = paymentService.save(entity);

        PaymentResponse data = modelMapper.map(entity, PaymentResponse.class);
        return ResponseMessage.success(data);
    }

    @PutMapping("/{id}")
    public ResponseMessage<PaymentResponse> edit(
            @PathVariable Integer id,
            @RequestBody PaymentRequest model){

        Payment entity = paymentService.findById(id);
        if(entity == null){
            throw new EntityNotFoundException();
        }
        Customer customer = customerService.findById(model.getCustomerId());
        entity.setCustomer(customer);

        MenuOrder menuOrder = menuOrderService.findById(model.getMenuOrderId());
        entity.setMenuOrder(menuOrder);

        PaymentMethod paymentMethod = paymentMethodService.findById(model.getPaymentMethodId());
        entity.setPaymentMethod(paymentMethod);

        modelMapper.map(model, entity);
        entity = paymentService.save(entity);

        PaymentResponse data = modelMapper.map(entity, PaymentResponse.class);
        return ResponseMessage.success(data);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<PaymentResponse> removeById(@PathVariable Integer id){

        Payment entity = paymentService.removeById(id);

        if(entity == null){
            throw new EntityNotFoundException();
        }
        PaymentResponse data = modelMapper.map(entity, PaymentResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping("/{id}")
    public ResponseMessage<PaymentResponse> findById(@PathVariable Integer id){
        Payment entity = paymentService.findById(id);
        if(entity == null){
            throw new EntityNotFoundException();
        }
        PaymentResponse data = modelMapper.map(entity, PaymentResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping
    public ResponseMessage<PagedList<PaymentResponse>> findAll(
            @Valid PaymentSearch model
    ){
        Payment search = modelMapper.map(model, Payment.class);

        Page<Payment> entityPage = paymentService.findAll(search,
                model.getPage(), model.getSize(), model.getSort());

        List<Payment> entities = entityPage.toList();

        List<PaymentResponse> models = entities.stream()
                .map(e -> modelMapper.map(e, PaymentResponse.class))
                .collect(Collectors.toList());

        PagedList<PaymentResponse> data = new PagedList(models,
                entityPage.getNumber(),
                entityPage.getSize(),
                entityPage.getTotalElements());

        return ResponseMessage.success(data);
    }
}
