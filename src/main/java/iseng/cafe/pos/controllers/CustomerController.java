package iseng.cafe.pos.controllers;


import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import iseng.cafe.pos.entities.Customer;
import iseng.cafe.pos.models.PagedList;
import iseng.cafe.pos.models.ResponseMessage;
import iseng.cafe.pos.models.customer.CustomerRequest;
import iseng.cafe.pos.models.customer.CustomerResponse;
import iseng.cafe.pos.models.customer.CustomerSearch;
import iseng.cafe.pos.services.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/customers")
@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

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
    public ResponseMessage<CustomerResponse> add(
            @RequestBody @Valid CustomerRequest model){
        Customer entity = modelMapper.map(model, Customer.class);

        entity = customerService.save(entity);

        CustomerResponse data = modelMapper.map(entity, CustomerResponse.class);
        return ResponseMessage.success(data);
    }

    @PutMapping("/{id}")
    public ResponseMessage<CustomerResponse> edit(
            @PathVariable Integer id,
            @RequestBody CustomerRequest model){

        Customer entity = customerService.findById(id);
        if(entity == null){
            throw new EntityNotFoundException();
        }

        modelMapper.map(model, entity);
        entity = customerService.save(entity);

        CustomerResponse data = modelMapper.map(entity, CustomerResponse.class);
        return ResponseMessage.success(data);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<CustomerResponse> removeById(@PathVariable Integer id){

        Customer entity = customerService.removeById(id);

        if(entity == null){
            throw new EntityNotFoundException();
        }
        CustomerResponse data = modelMapper.map(entity, CustomerResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping("/{id}")
    public ResponseMessage<CustomerResponse> findById(@PathVariable Integer id){
        Customer entity = customerService.findById(id);
        if(entity == null){
            throw new EntityNotFoundException();
        }
        CustomerResponse data = modelMapper.map(entity, CustomerResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping
    public ResponseMessage<PagedList<CustomerResponse>> findAll(
            @Valid CustomerSearch model
    ){
        Customer search = modelMapper.map(model, Customer.class);

        Page<Customer> entityPage = customerService.findAll(search,
                model.getPage(), model.getSize(), model.getSort());

        List<Customer> entities = entityPage.toList();

        List<CustomerResponse> models = entities.stream()
                .map(e -> modelMapper.map(e, CustomerResponse.class))
                .collect(Collectors.toList());

        PagedList<CustomerResponse> data = new PagedList(models,
                entityPage.getNumber(),
                entityPage.getSize(),
                entityPage.getTotalElements());

        return ResponseMessage.success(data);
    }

}
