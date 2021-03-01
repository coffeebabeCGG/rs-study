package com.cgg.redisstudy.controller;

import com.cgg.redisstudy.dto.RestResult;
import com.cgg.redisstudy.service.IRsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author cgg
 */

@RestController
@Api(tags = "resource interface")
public class RsController {

    @Resource
    private IRsService iRsService;

    @PostMapping(value = "/resource")
    @ApiOperation("add resource")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "status", paramType = "save", required = true),
            @ApiImplicitParam(name = "acTime", value = "acTime", paramType = "save", required = true),
            @ApiImplicitParam(name = "name", value = "name", paramType = "save", required = true)

    })
    public RestResult<Object> setResource(@RequestParam(value = "status") String status,
                              @RequestParam(value = "acTime") Long acTime,
                              @RequestParam(value = "name") String name) {
        return iRsService.setResource(status, acTime, name);
    }

    @GetMapping(value = "/resource")
    @ApiOperation("search resource")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "status", paramType = "query", required = false)
    })
    public RestResult<Object> getResource(@RequestParam(value = "status") String status) {
        return iRsService.getResource(status);
    }

    @PatchMapping(value = "/resource")
    @ApiOperation("update resource")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "status", value = "status", paramType = "update", required = true),
            @ApiImplicitParam(name = "acTime", value = "acTime", paramType = "update", required = false),
            @ApiImplicitParam(name = "name", value = "name", paramType = "update", required = false)
    })
    public RestResult<Object> updateResource(@RequestParam(value = "status") String status,
                                             @RequestParam(value = "acTime") Long acTime,
                                             @RequestParam(value = "name") String name) {
        return iRsService.updateResource(status, acTime, name);
    }

}
