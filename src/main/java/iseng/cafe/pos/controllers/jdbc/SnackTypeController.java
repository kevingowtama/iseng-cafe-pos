package iseng.cafe.pos.controllers.jdbc;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import iseng.cafe.pos.entities.SnackType;
import iseng.cafe.pos.exceptions.EntityNotFoundException;
import iseng.cafe.pos.models.ResponseMessage;
import iseng.cafe.pos.models.snackType.SnackTypeRequest;
import iseng.cafe.pos.services.jdbc.SnackTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/snack-types")
public class SnackTypeController {
    @Autowired
    private SnackTypeService snackTypeService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal server error."),
            @ApiResponse(
                    responseCode = "200",
                    description = "Success.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseMessage.class))))
    })

    @PostMapping
    public ResponseMessage<SnackType> save(@RequestBody @Valid SnackTypeRequest model){
        SnackType snackType = new SnackType();

        snackType.setName(model.getName());

        SnackType data = snackTypeService.save(snackType);
        return ResponseMessage.success("New data has been added", data);
    }

    @PutMapping("/{id}")
    public ResponseMessage<SnackType> update(@RequestBody @Valid SnackTypeRequest model, @PathVariable Integer id){
        SnackType snackType = snackTypeService.findById(id);

        if(snackType.getId() == null){
            throw new EntityNotFoundException();
        }
        snackType.setName(model.getName());

        SnackType data = snackTypeService.save(snackType);
        return ResponseMessage.success("Data has been updated", data);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<Boolean> remove(@PathVariable Integer id){
        Boolean data = snackTypeService.remove(id);

        return ResponseMessage.success("Data has been deleted", data);
    }

    @GetMapping("/{id}")
    public ResponseMessage<SnackType> findById(@PathVariable @Valid Integer id){
        SnackType data = snackTypeService.findById(id);

        return ResponseMessage.success("Get data success", data);
    }

    @GetMapping
    public ResponseMessage<List<SnackType>> findAll(){
        List<SnackType> data = snackTypeService.findAll();

        return ResponseMessage.success("Get list of data success", data);
    }
}
