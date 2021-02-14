package iseng.cafe.pos.controllers.jdbc;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import iseng.cafe.pos.entities.Customer;
import iseng.cafe.pos.exceptions.EntityNotFoundException;
import iseng.cafe.pos.models.ResponseMessage;
import iseng.cafe.pos.models.customer.CustomerRequest;
import iseng.cafe.pos.services.jdbc.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/customers")
@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal server error."),
            @ApiResponse(
                    responseCode = "200",
                    description = "Success.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseMessage.class))))
    })

    @PostMapping
    public ResponseMessage<Customer> save(@RequestBody @Valid CustomerRequest model){
        Customer customer = new Customer();

        customer.setFirstName(model.getFirstName());
        customer.setLastName(model.getLastName());
        customer.setEmail(model.getEmail());
        customer.setPhone(model.getPhone());

        Customer data = customerService.save(customer);
        return ResponseMessage.success("New data has been added", data);
    }

    @PutMapping("/{id}")
    public ResponseMessage<Customer> update(@RequestBody @Valid CustomerRequest model, @PathVariable Integer id){
        Customer customer = customerService.findById(id);
        System.out.println(customer.getId());
        if(customer.getId() == null){
            throw new EntityNotFoundException();
        }

        customer.setFirstName(model.getFirstName());
        customer.setLastName(model.getLastName());
        customer.setEmail(model.getEmail());
        customer.setPhone(model.getPhone());

        Customer data = customerService.save(customer);
        return ResponseMessage.success("Data has been updated", data);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<Boolean> remove(@PathVariable Integer id){
        Boolean data = customerService.remove(id);

        return ResponseMessage.success("Data has been deleted", data);
    }

    @GetMapping("/{id}")
    public ResponseMessage<Customer> findById(@PathVariable @Valid Integer id){
        Customer data = customerService.findById(id);

        return ResponseMessage.success("Get data success", data);
    }

    @GetMapping
    public ResponseMessage<List<Customer>> findAll(){
        List<Customer> data = customerService.findAll();

        return ResponseMessage.success("Get list of data success", data);
    }
}
