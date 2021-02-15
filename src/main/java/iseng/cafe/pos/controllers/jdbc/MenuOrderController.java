package iseng.cafe.pos.controllers.jdbc;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import iseng.cafe.pos.entities.Admin;
import iseng.cafe.pos.entities.MenuOrder;
import iseng.cafe.pos.entities.Customer;
import iseng.cafe.pos.exceptions.EntityNotFoundException;
import iseng.cafe.pos.models.ResponseMessage;
import iseng.cafe.pos.models.menuOrder.MenuOrderRequest;
import iseng.cafe.pos.services.jdbc.AdminService;
import iseng.cafe.pos.services.jdbc.MenuOrderService;
import iseng.cafe.pos.services.jdbc.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/menu-orders")
public class MenuOrderController {
    @Autowired
    private MenuOrderService menuOrderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AdminService adminService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal server error."),
            @ApiResponse(
                    responseCode = "200",
                    description = "Success.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseMessage.class))))
    })

    @PostMapping
    public ResponseMessage<MenuOrder> save(@RequestBody @Valid MenuOrderRequest model){
        MenuOrder menuOrder = new MenuOrder();

        menuOrder.setTotalCost(model.getTotalCost());
        menuOrder.setDescription(model.getDescription());

        Admin admin = adminService.findById(model.getAdminId());
        menuOrder.setAdmin(admin);

        Customer customer = customerService.findById(model.getCustomerId());
        menuOrder.setCustomer(customer);

        MenuOrder data = menuOrderService.save(menuOrder);
        return ResponseMessage.success("New data has been added", data);
    }

    @PutMapping("/{id}")
    public ResponseMessage<MenuOrder> update(@RequestBody @Valid MenuOrderRequest model, @PathVariable Integer id){
        MenuOrder menuOrder = menuOrderService.findById(id);

        if(menuOrder.getId() == null){
            throw new EntityNotFoundException();
        }

        menuOrder.setTotalCost(model.getTotalCost());
        menuOrder.setDescription(model.getDescription());

        Admin admin = adminService.findById(model.getAdminId());
        menuOrder.setAdmin(admin);

        Customer customer = customerService.findById(model.getCustomerId());
        menuOrder.setCustomer(customer);

        MenuOrder data = menuOrderService.save(menuOrder);
        return ResponseMessage.success("New data has been added", data);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<Boolean> remove(@PathVariable Integer id){
        Boolean data = menuOrderService.remove(id);

        return ResponseMessage.success("Data has been deleted", data);
    }

    @GetMapping("/{id}")
    public ResponseMessage<MenuOrder> findById(@PathVariable @Valid Integer id){
        MenuOrder data = menuOrderService.findById(id);

        return ResponseMessage.success(data);
    }

    @GetMapping
    public ResponseMessage<List<MenuOrder>> findAll(){
        List<MenuOrder> data = menuOrderService.findAll();

        return ResponseMessage.success(data);
    }

}
