package iseng.cafe.pos.controllers;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import iseng.cafe.pos.entities.SnackType;
import iseng.cafe.pos.models.PagedList;
import iseng.cafe.pos.models.ResponseMessage;
import iseng.cafe.pos.models.snackType.SnackTypeRequest;
import iseng.cafe.pos.models.snackType.SnackTypeResponse;
import iseng.cafe.pos.models.snackType.SnackTypeSearch;
import iseng.cafe.pos.services.SnackTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/snack-types")
@RestController
public class SnackTypeController {
    @Autowired
    private SnackTypeService snackTypeService;

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
    public ResponseMessage<SnackTypeResponse> add(
            @RequestBody @Valid SnackTypeRequest model){
        SnackType entity = modelMapper.map(model, SnackType.class);

        entity = snackTypeService.save(entity);

        SnackTypeResponse data = modelMapper.map(entity, SnackTypeResponse.class);
        return ResponseMessage.success(data);
    }

    @PutMapping("/{id}")
    public ResponseMessage<SnackTypeResponse> edit(
            @PathVariable Integer id,
            @RequestBody SnackTypeRequest model){

        SnackType entity = snackTypeService.findById(id);
        if(entity == null){
            throw new EntityNotFoundException();
        }

        modelMapper.map(model, entity);
        entity = snackTypeService.save(entity);

        SnackTypeResponse data = modelMapper.map(entity, SnackTypeResponse.class);
        return ResponseMessage.success(data);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<SnackTypeResponse> removeById(@PathVariable Integer id){

        SnackType entity = snackTypeService.removeById(id);

        if(entity == null){
            throw new EntityNotFoundException();
        }
        SnackTypeResponse data = modelMapper.map(entity, SnackTypeResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping("/{id}")
    public ResponseMessage<SnackTypeResponse> findById(@PathVariable Integer id){
        SnackType entity = snackTypeService.findById(id);
        if(entity == null){
            throw new EntityNotFoundException();
        }
        SnackTypeResponse data = modelMapper.map(entity, SnackTypeResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping
    public ResponseMessage<PagedList<SnackTypeResponse>> findAll(
            @Valid SnackTypeSearch model
    ){
        SnackType search = modelMapper.map(model, SnackType.class);

        Page<SnackType> entityPage = snackTypeService.findAll(search,
                model.getPage(), model.getSize(), model.getSort());

        List<SnackType> entities = entityPage.toList();

        List<SnackTypeResponse> models = entities.stream()
                .map(e -> modelMapper.map(e, SnackTypeResponse.class))
                .collect(Collectors.toList());

        PagedList<SnackTypeResponse> data = new PagedList(models,
                entityPage.getNumber(),
                entityPage.getSize(),
                entityPage.getTotalElements());

        return ResponseMessage.success(data);
    }
}
