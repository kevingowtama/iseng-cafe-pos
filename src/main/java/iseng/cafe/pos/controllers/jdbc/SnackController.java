package iseng.cafe.pos.controllers.jdbc;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import iseng.cafe.pos.entities.Snack;
import iseng.cafe.pos.entities.SnackCategory;
import iseng.cafe.pos.entities.SnackType;
import iseng.cafe.pos.exceptions.EntityNotFoundException;
import iseng.cafe.pos.models.ResponseMessage;
import iseng.cafe.pos.models.snack.SnackRequest;
import iseng.cafe.pos.services.jdbc.SnackService;
import iseng.cafe.pos.services.jdbc.SnackCategoryService;
import iseng.cafe.pos.services.jdbc.SnackTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/snacks")
public class SnackController {
    @Autowired
    private SnackService snackService;

    @Autowired
    private SnackTypeService snackTypeService;

    @Autowired
    private SnackCategoryService snackCategoryService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal server error."),
            @ApiResponse(
                    responseCode = "200",
                    description = "Success.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseMessage.class))))
    })

    @PostMapping
    public ResponseMessage<Snack> save(@RequestBody @Valid SnackRequest model){
        Snack snack = new Snack();

        snack.setName(model.getName());
        snack.setPrice(model.getPrice());
        snack.setDescription(model.getDescription());

        SnackType snackType = snackTypeService.findById(model.getSnackTypeId());
        snack.setSnackType(snackType);

        SnackCategory snackCategory = snackCategoryService.findById(model.getSnackCategoryId());
        snack.setSnackCategory(snackCategory);

        Snack data = snackService.save(snack);
        return ResponseMessage.success("New data has been added", data);
    }

    @PutMapping("/{id}")
    public ResponseMessage<Snack> update(@RequestBody @Valid SnackRequest model, @PathVariable Integer id){
        Snack snack = snackService.findById(id);

        if(snack.getId() == null){
            throw new EntityNotFoundException();
        }

        snack.setName(model.getName());
        snack.setPrice(model.getPrice());
        snack.setDescription(model.getDescription());

        SnackType snackType = snackTypeService.findById(model.getSnackTypeId());
        snack.setSnackType(snackType);

        SnackCategory snackCategory = snackCategoryService.findById(model.getSnackCategoryId());
        snack.setSnackCategory(snackCategory);

        Snack data = snackService.save(snack);
        return ResponseMessage.success("New data has been added", data);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<Boolean> remove(@PathVariable Integer id){
        Boolean data = snackService.remove(id);

        return ResponseMessage.success("Data has been deleted", data);
    }

    @GetMapping("/{id}")
    public ResponseMessage<Snack> findById(@PathVariable @Valid Integer id){
        Snack data = snackService.findById(id);

        return ResponseMessage.success(data);
    }

    @GetMapping
    public ResponseMessage<List<Snack>> findAll(){
        List<Snack> data = snackService.findAll();

        return ResponseMessage.success(data);
    }
}
