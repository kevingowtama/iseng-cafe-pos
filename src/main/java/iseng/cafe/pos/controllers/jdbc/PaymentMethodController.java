package iseng.cafe.pos.controllers.jdbc;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import iseng.cafe.pos.entities.PaymentMethod;
import iseng.cafe.pos.exceptions.EntityNotFoundException;
import iseng.cafe.pos.models.ResponseMessage;
import iseng.cafe.pos.models.paymentMethod.PaymentMethodRequest;
import iseng.cafe.pos.services.jdbc.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/payment-methods")
public class PaymentMethodController {
    @Autowired
    private PaymentMethodService paymentMethodService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal server error."),
            @ApiResponse(
                    responseCode = "200",
                    description = "Success.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseMessage.class))))
    })

    @PostMapping
    public ResponseMessage<PaymentMethod> save(@RequestBody @Valid PaymentMethodRequest model){
        PaymentMethod paymentMethod = new PaymentMethod();

        paymentMethod.setName(model.getName());

        PaymentMethod data = paymentMethodService.save(paymentMethod);
        return ResponseMessage.success("New data has been added", data);
    }

    @PutMapping("/{id}")
    public ResponseMessage<PaymentMethod> update(@RequestBody @Valid PaymentMethodRequest model, @PathVariable Integer id){
        PaymentMethod paymentMethod = paymentMethodService.findById(id);

        if(paymentMethod.getId() == null){
            throw new EntityNotFoundException();
        }
        paymentMethod.setName(model.getName());

        PaymentMethod data = paymentMethodService.save(paymentMethod);
        return ResponseMessage.success("Data has been updated", data);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<Boolean> remove(@PathVariable Integer id){
        Boolean data = paymentMethodService.remove(id);

        return ResponseMessage.success("Data has been deleted", data);
    }

    @GetMapping("/{id}")
    public ResponseMessage<PaymentMethod> findById(@PathVariable @Valid Integer id){
        PaymentMethod data = paymentMethodService.findById(id);

        return ResponseMessage.success("Get data success", data);
    }

    @GetMapping
    public ResponseMessage<List<PaymentMethod>> findAll(){
        List<PaymentMethod> data = paymentMethodService.findAll();

        return ResponseMessage.success("Get list of data success", data);
    }
}
