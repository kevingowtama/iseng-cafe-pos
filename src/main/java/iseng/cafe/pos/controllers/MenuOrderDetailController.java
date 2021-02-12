package iseng.cafe.pos.controllers;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import iseng.cafe.pos.entities.*;
import iseng.cafe.pos.models.PagedList;
import iseng.cafe.pos.models.ResponseMessage;
import iseng.cafe.pos.models.menuOrder.MenuOrderRequest;
import iseng.cafe.pos.models.menuOrderDetail.MenuOrderDetailRequest;
import iseng.cafe.pos.models.menuOrderDetail.MenuOrderDetailResponse;
import iseng.cafe.pos.models.menuOrderDetail.MenuOrderDetailSearch;
import iseng.cafe.pos.services.SnackService;
import iseng.cafe.pos.services.MenuOrderDetailService;
import iseng.cafe.pos.services.MenuOrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/menu-order-details")
@RestController
public class MenuOrderDetailController {
    @Autowired
    private MenuOrderDetailService menuOrderDetailService;

    @Autowired
    private MenuOrderService menuOrderService;

    @Autowired
    private SnackService snackService;

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
    public ResponseMessage<MenuOrderDetailResponse> add(
            @RequestBody @Valid MenuOrderDetailRequest model){
        MenuOrderDetail entity = modelMapper.map(model, MenuOrderDetail.class);

        MenuOrder menuOrder = menuOrderService.findById(model.getMenuOrderId());
        entity.setMenuOrder(menuOrder);

        Snack snack = snackService.findById(model.getSnackId());
        entity.setSnack(snack);

        entity = menuOrderDetailService.save(entity);

        MenuOrderDetailResponse data = modelMapper.map(entity, MenuOrderDetailResponse.class);
        return ResponseMessage.success(data);
    }

    @PutMapping("/{id}")
    public ResponseMessage<MenuOrderDetailResponse> edit(
            @PathVariable Integer id,
            @RequestBody MenuOrderDetailRequest model){

        MenuOrderDetail entity = menuOrderDetailService.findById(id);
        if(entity == null){
            throw new EntityNotFoundException();
        }

        MenuOrder menuOrder = menuOrderService.findById(model.getMenuOrderId());
        entity.setMenuOrder(menuOrder);

        Snack snack = snackService.findById(model.getSnackId());
        entity.setSnack(snack);

        modelMapper.map(model, entity);
        entity = menuOrderDetailService.save(entity);

        MenuOrderDetailResponse data = modelMapper.map(entity, MenuOrderDetailResponse.class);
        return ResponseMessage.success(data);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<MenuOrderDetailResponse> removeById(@PathVariable Integer id){

        MenuOrderDetail entity = menuOrderDetailService.removeById(id);

        if(entity == null){
            throw new EntityNotFoundException();
        }
        MenuOrderDetailResponse data = modelMapper.map(entity, MenuOrderDetailResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping("/{id}")
    public ResponseMessage<MenuOrderDetailResponse> findById(@PathVariable Integer id){
        MenuOrderDetail entity = menuOrderDetailService.findById(id);
        if(entity == null){
            throw new EntityNotFoundException();
        }
        MenuOrderDetailResponse data = modelMapper.map(entity, MenuOrderDetailResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping
    public ResponseMessage<PagedList<MenuOrderDetailResponse>> findAll(
            @Valid MenuOrderDetailSearch model
    ){
        MenuOrderDetail search = modelMapper.map(model, MenuOrderDetail.class);

        Page<MenuOrderDetail> entityPage = menuOrderDetailService.findAll(search,
                model.getPage(), model.getSize(), model.getSort());

        List<MenuOrderDetail> entities = entityPage.toList();

        List<MenuOrderDetailResponse> models = entities.stream()
                .map(e -> modelMapper.map(e, MenuOrderDetailResponse.class))
                .collect(Collectors.toList());

        PagedList<MenuOrderDetailResponse> data = new PagedList(models,
                entityPage.getNumber(),
                entityPage.getSize(),
                entityPage.getTotalElements());

        return ResponseMessage.success(data);
    }
}
