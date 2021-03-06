package iseng.cafe.pos.controllers;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import iseng.cafe.pos.entities.PaymentMethod;
import iseng.cafe.pos.models.PagedList;
import iseng.cafe.pos.models.ResponseMessage;
import iseng.cafe.pos.models.paymentMethod.PaymentMethodRequest;
import iseng.cafe.pos.models.paymentMethod.PaymentMethodResponse;
import iseng.cafe.pos.models.paymentMethod.PaymentMethodSearch;
import iseng.cafe.pos.services.PaymentMethodService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/payment-methods")
@RestController
public class PaymentMethodController {
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
    public ResponseMessage<PaymentMethodResponse> add(
            @RequestBody @Valid PaymentMethodRequest model){
        PaymentMethod entity = modelMapper.map(model, PaymentMethod.class);

        entity = paymentMethodService.save(entity);

        PaymentMethodResponse data = modelMapper.map(entity, PaymentMethodResponse.class);
        return ResponseMessage.success(data);
    }

    @PutMapping("/{id}")
    public ResponseMessage<PaymentMethodResponse> edit(
            @PathVariable Integer id,
            @RequestBody PaymentMethodRequest model){

        PaymentMethod entity = paymentMethodService.findById(id);
        if(entity == null){
            throw new EntityNotFoundException();
        }

        modelMapper.map(model, entity);
        entity = paymentMethodService.save(entity);

        PaymentMethodResponse data = modelMapper.map(entity, PaymentMethodResponse.class);
        return ResponseMessage.success(data);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<PaymentMethodResponse> removeById(@PathVariable Integer id){

        PaymentMethod entity = paymentMethodService.removeById(id);

        if(entity == null){
            throw new EntityNotFoundException();
        }
        PaymentMethodResponse data = modelMapper.map(entity, PaymentMethodResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping("/{id}")
    public ResponseMessage<PaymentMethodResponse> findById(@PathVariable Integer id){
        PaymentMethod entity = paymentMethodService.findById(id);
        if(entity == null){
            throw new EntityNotFoundException();
        }
        PaymentMethodResponse data = modelMapper.map(entity, PaymentMethodResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping
    public ResponseMessage<PagedList<PaymentMethodResponse>> findAll(
            @Valid PaymentMethodSearch model
    ){
        PaymentMethod search = modelMapper.map(model, PaymentMethod.class);

        Page<PaymentMethod> entityPage = paymentMethodService.findAll(search,
                model.getPage(), model.getSize(), model.getSort());

        List<PaymentMethod> entities = entityPage.toList();

        List<PaymentMethodResponse> models = entities.stream()
                .map(e -> modelMapper.map(e, PaymentMethodResponse.class))
                .collect(Collectors.toList());

        PagedList<PaymentMethodResponse> data = new PagedList(models,
                entityPage.getNumber(),
                entityPage.getSize(),
                entityPage.getTotalElements());

        return ResponseMessage.success(data);
    }
}
