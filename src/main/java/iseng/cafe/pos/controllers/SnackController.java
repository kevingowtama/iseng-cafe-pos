package iseng.cafe.pos.controllers;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import iseng.cafe.pos.entities.SnackCategory;
import iseng.cafe.pos.entities.SnackType;
import iseng.cafe.pos.entities.Snack;
import iseng.cafe.pos.models.PagedList;
import iseng.cafe.pos.models.ResponseMessage;
import iseng.cafe.pos.models.snack.SnackRequest;
import iseng.cafe.pos.models.snack.SnackResponse;
import iseng.cafe.pos.models.snack.SnackSearch;
import iseng.cafe.pos.services.SnackCategoryService;
import iseng.cafe.pos.services.SnackTypeService;
import iseng.cafe.pos.services.SnackService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/snacks")
@RestController
public class SnackController {
    @Autowired
    private SnackService snackService;

    @Autowired
    private SnackTypeService snackTypeService;

    @Autowired
    private SnackCategoryService snackCategoryService;

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
    public ResponseMessage<SnackResponse> add(
            @RequestBody @Valid SnackRequest model){
        Snack entity = modelMapper.map(model, Snack.class);

        SnackType snackType = snackTypeService.findById(model.getSnackTypeId());
        entity.setSnackType(snackType);

        SnackCategory snackCategory = snackCategoryService.findById(model.getSnackCategoryId());
        entity.setSnackCategory(snackCategory);

        entity = snackService.save(entity);

        SnackResponse data = modelMapper.map(entity, SnackResponse.class);
        return ResponseMessage.success(data);
    }

    @PutMapping("/{id}")
    public ResponseMessage<SnackResponse> edit(
            @PathVariable Integer id,
            @RequestBody SnackRequest model){

        Snack entity = snackService.findById(id);
        if(entity == null){
            throw new EntityNotFoundException();
        }
        SnackType snackType = snackTypeService.findById(model.getSnackTypeId());
        entity.setSnackType(snackType);

        SnackCategory snackCategory = snackCategoryService.findById(model.getSnackCategoryId());
        entity.setSnackCategory(snackCategory);

        modelMapper.map(model, entity);
        entity = snackService.save(entity);

        SnackResponse data = modelMapper.map(entity, SnackResponse.class);
        return ResponseMessage.success(data);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<SnackResponse> removeById(@PathVariable Integer id){

        Snack entity = snackService.removeById(id);

        if(entity == null){
            throw new EntityNotFoundException();
        }
        SnackResponse data = modelMapper.map(entity, SnackResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping("/{id}")
    public ResponseMessage<SnackResponse> findById(@PathVariable Integer id){
        Snack entity = snackService.findById(id);
        if(entity == null){
            throw new EntityNotFoundException();
        }
        SnackResponse data = modelMapper.map(entity, SnackResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping
    public ResponseMessage<PagedList<SnackResponse>> findAll(
            @Valid SnackSearch model
    ){
        Snack search = modelMapper.map(model, Snack.class);

        Page<Snack> entityPage = snackService.findAll(search,
                model.getPage(), model.getSize(), model.getSort());

        List<Snack> entities = entityPage.toList();

        List<SnackResponse> models = entities.stream()
                .map(e -> modelMapper.map(e, SnackResponse.class))
                .collect(Collectors.toList());

        PagedList<SnackResponse> data = new PagedList(models,
                entityPage.getNumber(),
                entityPage.getSize(),
                entityPage.getTotalElements());

        return ResponseMessage.success(data);
    }


}
