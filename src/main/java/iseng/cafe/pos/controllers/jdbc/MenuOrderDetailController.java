package iseng.cafe.pos.controllers.jdbc;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import iseng.cafe.pos.entities.MenuOrderDetail;
import iseng.cafe.pos.entities.MenuOrder;
import iseng.cafe.pos.entities.Snack;
import iseng.cafe.pos.exceptions.EntityNotFoundException;
import iseng.cafe.pos.models.ResponseMessage;
import iseng.cafe.pos.models.menuOrderDetail.MenuOrderDetailRequest;
import iseng.cafe.pos.services.jdbc.MenuOrderDetailService;
import iseng.cafe.pos.services.jdbc.MenuOrderService;
import iseng.cafe.pos.services.jdbc.SnackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/menu-order-details")
public class MenuOrderDetailController {
    @Autowired
    private MenuOrderDetailService menuOrderDetailService;

    @Autowired
    private MenuOrderService menuOrderService;

    @Autowired
    private SnackService snackService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal server error."),
            @ApiResponse(
                    responseCode = "200",
                    description = "Success.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseMessage.class))))
    })

    @PostMapping
    public ResponseMessage<MenuOrderDetail> save(@RequestBody @Valid MenuOrderDetailRequest model){
        MenuOrderDetail menuOrderDetail = new MenuOrderDetail();

        menuOrderDetail.setQuantity(model.getQuantity());
        menuOrderDetail.setSubTotal(model.getSubTotal());
        menuOrderDetail.setNotes(model.getNotes());

        MenuOrder menuOrder = menuOrderService.findById(model.getMenuOrderId());
        menuOrderDetail.setMenuOrder(menuOrder);

        Snack snack = snackService.findById(model.getSnackId());
        menuOrderDetail.setSnack(snack);

        MenuOrderDetail data = menuOrderDetailService.save(menuOrderDetail);
        return ResponseMessage.success("New data has been added", data);
    }

    @PutMapping("/{id}")
    public ResponseMessage<MenuOrderDetail> update(@RequestBody @Valid MenuOrderDetailRequest model, @PathVariable Integer id){
        MenuOrderDetail menuOrderDetail = menuOrderDetailService.findById(id);

        if(menuOrderDetail.getId() == null){
            throw new EntityNotFoundException();
        }
        menuOrderDetail.setQuantity(model.getQuantity());
        menuOrderDetail.setSubTotal(model.getSubTotal());
        menuOrderDetail.setNotes(model.getNotes());

        MenuOrder menuOrder = menuOrderService.findById(model.getMenuOrderId());
        menuOrderDetail.setMenuOrder(menuOrder);

        Snack snack = snackService.findById(model.getSnackId());
        menuOrderDetail.setSnack(snack);

        MenuOrderDetail data = menuOrderDetailService.save(menuOrderDetail);
        return ResponseMessage.success("Data has been updated", data);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<Boolean> remove(@PathVariable Integer id){
        Boolean data = menuOrderDetailService.remove(id);

        return ResponseMessage.success("Data has been deleted", data);
    }

    @GetMapping("/{id}")
    public ResponseMessage<MenuOrderDetail> findById(@PathVariable @Valid Integer id){
        MenuOrderDetail data = menuOrderDetailService.findById(id);

        return ResponseMessage.success(data);
    }

    @GetMapping
    public ResponseMessage<List<MenuOrderDetail>> findAll(){
        List<MenuOrderDetail> data = menuOrderDetailService.findAll();

        return ResponseMessage.success(data);
    }
}
