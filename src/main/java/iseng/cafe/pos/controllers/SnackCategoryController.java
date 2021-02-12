package iseng.cafe.pos.controllers;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import iseng.cafe.pos.entities.SnackCategory;
import iseng.cafe.pos.models.PagedList;
import iseng.cafe.pos.models.ResponseMessage;
import iseng.cafe.pos.models.snackCategory.SnackCategoryRequest;
import iseng.cafe.pos.models.snackCategory.SnackCategoryResponse;
import iseng.cafe.pos.models.snackCategory.SnackCategorySearch;
import iseng.cafe.pos.services.SnackCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/snack-categories")
@RestController
public class SnackCategoryController {
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
    public ResponseMessage<SnackCategoryResponse> add(
            @RequestBody @Valid SnackCategoryRequest model){
        SnackCategory entity = modelMapper.map(model, SnackCategory.class);

        entity = snackCategoryService.save(entity);

        SnackCategoryResponse data = modelMapper.map(entity, SnackCategoryResponse.class);
        return ResponseMessage.success(data);
    }

    @PutMapping("/{id}")
    public ResponseMessage<SnackCategoryResponse> edit(
            @PathVariable Integer id,
            @RequestBody SnackCategoryRequest model){

        SnackCategory entity = snackCategoryService.findById(id);
        if(entity == null){
            throw new EntityNotFoundException();
        }

        modelMapper.map(model, entity);
        entity = snackCategoryService.save(entity);

        SnackCategoryResponse data = modelMapper.map(entity, SnackCategoryResponse.class);
        return ResponseMessage.success(data);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<SnackCategoryResponse> removeById(@PathVariable Integer id){

        SnackCategory entity = snackCategoryService.removeById(id);

        if(entity == null){
            throw new EntityNotFoundException();
        }
        SnackCategoryResponse data = modelMapper.map(entity, SnackCategoryResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping("/{id}")
    public ResponseMessage<SnackCategoryResponse> findById(@PathVariable Integer id){
        SnackCategory entity = snackCategoryService.findById(id);
        if(entity == null){
            throw new EntityNotFoundException();
        }
        SnackCategoryResponse data = modelMapper.map(entity, SnackCategoryResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping
    public ResponseMessage<PagedList<SnackCategoryResponse>> findAll(
            @Valid SnackCategorySearch model
    ){
        SnackCategory search = modelMapper.map(model, SnackCategory.class);

        Page<SnackCategory> entityPage = snackCategoryService.findAll(search,
                model.getPage(), model.getSize(), model.getSort());

        List<SnackCategory> entities = entityPage.toList();

        List<SnackCategoryResponse> models = entities.stream()
                .map(e -> modelMapper.map(e, SnackCategoryResponse.class))
                .collect(Collectors.toList());

        PagedList<SnackCategoryResponse> data = new PagedList(models,
                entityPage.getNumber(),
                entityPage.getSize(),
                entityPage.getTotalElements());

        return ResponseMessage.success(data);
    }
}
