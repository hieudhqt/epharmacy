package com.hieu.swd.epharmacy.app.category;

import com.hieu.swd.epharmacy.app.PageResult;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Api(tags = "Category API")
@Slf4j
@Validated
public class CategoryRestController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get All Category", tags = "Category API")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @ApiImplicitParams( value = {
            @ApiImplicitParam(name = "page", value = "Page number start from 1", dataType = "integer", examples = @Example(@ExampleProperty("1")), paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Maximum items in a page", dataType = "integer", examples = @Example(@ExampleProperty("100")), paramType = "query")
    })
    public PageResult findAll(@RequestParam(name = "page", defaultValue = "1") int pageNumber,
                              @RequestParam(name = "size", defaultValue = "100") int pageSize) throws Exception {
        return categoryService.findAll(pageNumber, pageSize);
    }
}
