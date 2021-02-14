package iseng.cafe.pos.controllers.jdbc;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import iseng.cafe.pos.entities.Admin;
import iseng.cafe.pos.entities.AdminType;
import iseng.cafe.pos.exceptions.EntityNotFoundException;
import iseng.cafe.pos.models.ResponseMessage;
import iseng.cafe.pos.models.adminType.AdminTypeRequest;
import iseng.cafe.pos.services.jdbc.AdminTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/jdbc/admin-types")
@RestController
public class AdminTypeController {

    @Autowired
    private AdminTypeService adminTypeService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal server error."),
            @ApiResponse(
                    responseCode = "200",
                    description = "Success.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseMessage.class))))
    })

    @PostMapping
    public ResponseMessage<AdminType> save(@RequestBody @Valid AdminTypeRequest model){
        AdminType adminType = new AdminType();

        adminType.setName(model.getName());

        AdminType data = adminTypeService.save(adminType);
        return ResponseMessage.success("New data has been added", data);
    }

    @PutMapping("/{id}")
    public ResponseMessage<AdminType> update(@RequestBody @Valid AdminTypeRequest model, @PathVariable Integer id){
        AdminType adminType = adminTypeService.findById(id);
        System.out.println(adminType.getId());
        if(adminType.getId() == null){
            throw new EntityNotFoundException();
        }
        adminType.setName(model.getName());

        AdminType data = adminTypeService.save(adminType);
        return ResponseMessage.success("Data has been updated", data);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<Boolean> remove(@PathVariable Integer id){
        Boolean data = adminTypeService.remove(id);

        return ResponseMessage.success("Data has been deleted", data);
    }

    @GetMapping("/{id}")
    public ResponseMessage<AdminType> findById(@PathVariable @Valid Integer id){
        AdminType data = adminTypeService.findById(id);

        return ResponseMessage.success("Get data success", data);
    }

    @GetMapping
    public ResponseMessage<List<AdminType>> findAll(){
        List<AdminType> data = adminTypeService.findAll();

        return ResponseMessage.success("Get list of data success", data);
    }

}