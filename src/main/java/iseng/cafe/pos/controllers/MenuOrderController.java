package iseng.cafe.pos.controllers;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import iseng.cafe.pos.entities.Admin;
import iseng.cafe.pos.entities.AdminType;
import iseng.cafe.pos.entities.Customer;
import iseng.cafe.pos.entities.MenuOrder;
import iseng.cafe.pos.models.PagedList;
import iseng.cafe.pos.models.ResponseMessage;
import iseng.cafe.pos.models.menuOrder.MenuOrderRequest;
import iseng.cafe.pos.models.menuOrder.MenuOrderResponse;
import iseng.cafe.pos.models.menuOrder.MenuOrderSearch;
import iseng.cafe.pos.services.AdminService;
import iseng.cafe.pos.services.CustomerService;
import iseng.cafe.pos.services.MenuOrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/menu-orders")
@RestController
public class MenuOrderController {
    @Autowired
    private MenuOrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AdminService adminService;

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
    public ResponseMessage<MenuOrderResponse> add(
            @RequestBody @Valid MenuOrderRequest model){
        MenuOrder entity = modelMapper.map(model, MenuOrder.class);

        Customer customer = customerService.findById(model.getCustomerId());
        entity.setCustomer(customer);

        Admin admin = adminService.findById(model.getAdminId());
        entity.setAdmin(admin);

        entity = orderService.save(entity);

        MenuOrderResponse data = modelMapper.map(entity, MenuOrderResponse.class);
        return ResponseMessage.success(data);
    }

    @PutMapping("/{id}")
    public ResponseMessage<MenuOrderResponse> edit(
            @PathVariable Integer id,
            @RequestBody MenuOrderRequest model){

        MenuOrder entity = orderService.findById(id);
        if(entity == null){
            throw new EntityNotFoundException();
        }
        Customer customer = customerService.findById(model.getCustomerId());
        entity.setCustomer(customer);

        Admin admin = adminService.findById(model.getAdminId());
        entity.setAdmin(admin);

        modelMapper.map(model, entity);
        entity = orderService.save(entity);

        MenuOrderResponse data = modelMapper.map(entity, MenuOrderResponse.class);
        return ResponseMessage.success(data);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<MenuOrderResponse> removeById(@PathVariable Integer id){

        MenuOrder entity = orderService.removeById(id);

        if(entity == null){
            throw new EntityNotFoundException();
        }
        MenuOrderResponse data = modelMapper.map(entity, MenuOrderResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping("/{id}")
    public ResponseMessage<MenuOrderResponse> findById(@PathVariable Integer id){
        MenuOrder entity = orderService.findById(id);
        if(entity == null){
            throw new EntityNotFoundException();
        }
        MenuOrderResponse data = modelMapper.map(entity, MenuOrderResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping
    public ResponseMessage<PagedList<MenuOrderResponse>> findAll(
            @Valid MenuOrderSearch model
    ){
        MenuOrder search = modelMapper.map(model, MenuOrder.class);

        Page<MenuOrder> entityPage = orderService.findAll(search,
                model.getPage(), model.getSize(), model.getSort());

        List<MenuOrder> entities = entityPage.toList();

        List<MenuOrderResponse> models = entities.stream()
                .map(e -> modelMapper.map(e, MenuOrderResponse.class))
                .collect(Collectors.toList());

        PagedList<MenuOrderResponse> data = new PagedList(models,
                entityPage.getNumber(),
                entityPage.getSize(),
                entityPage.getTotalElements());

        return ResponseMessage.success(data);
    }

}
