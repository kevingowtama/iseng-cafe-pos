package iseng.cafe.pos.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import iseng.cafe.pos.entities.AdminType;
import iseng.cafe.pos.models.PagedList;
import iseng.cafe.pos.models.ResponseMessage;
import iseng.cafe.pos.models.adminType.AdminTypeRequest;
import iseng.cafe.pos.models.adminType.AdminTypeResponse;
import iseng.cafe.pos.models.adminType.AdminTypeSearch;
import iseng.cafe.pos.services.AdminTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.media.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/admin-types")
@RestController
public class AdminTypeController {

    @Autowired
    private AdminTypeService adminTypeService;

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
    public ResponseMessage<AdminTypeResponse> add(
            @RequestBody @Valid AdminTypeRequest model){
        AdminType entity = modelMapper.map(model, AdminType.class);

        entity = adminTypeService.save(entity);

        AdminTypeResponse data = modelMapper.map(entity, AdminTypeResponse.class);
        return ResponseMessage.success(data);
    }

    @PutMapping("/{id}")
    public ResponseMessage<AdminTypeResponse> edit(
            @PathVariable Integer id,
            @RequestBody AdminTypeRequest model){

        AdminType entity = adminTypeService.findById(id);
        if(entity == null){
            throw new EntityNotFoundException();
        }

        modelMapper.map(model, entity);
        entity = adminTypeService.save(entity);

        AdminTypeResponse data = modelMapper.map(entity, AdminTypeResponse.class);
        return ResponseMessage.success(data);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<AdminTypeResponse> removeById(@PathVariable Integer id){

        AdminType entity = adminTypeService.removeById(id);

        if(entity == null){
            throw new EntityNotFoundException();
        }
        AdminTypeResponse data = modelMapper.map(entity, AdminTypeResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping("/{id}")
    public ResponseMessage<AdminTypeResponse> findById(@PathVariable Integer id){
        AdminType entity = adminTypeService.findById(id);
        if(entity == null){
            throw new EntityNotFoundException();
        }
        AdminTypeResponse data = modelMapper.map(entity, AdminTypeResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping
    public ResponseMessage<PagedList<AdminTypeResponse>> findAll(
            @Valid AdminTypeSearch model
    ){
        AdminType search = modelMapper.map(model, AdminType.class);

        Page<AdminType> entityPage = adminTypeService.findAll(search,
                model.getPage(), model.getSize(), model.getSort());

        List<AdminType> entities = entityPage.toList();

        List<AdminTypeResponse> models = entities.stream()
                .map(e -> modelMapper.map(e, AdminTypeResponse.class))
                .collect(Collectors.toList());

        PagedList<AdminTypeResponse> data = new PagedList(models,
                entityPage.getNumber(),
                entityPage.getSize(),
                entityPage.getTotalElements());

        return ResponseMessage.success(data);
    }

}