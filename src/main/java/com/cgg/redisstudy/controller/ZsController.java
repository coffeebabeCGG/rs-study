package com.cgg.redisstudy.controller;

import com.cgg.redisstudy.dto.RestResult;
import com.cgg.redisstudy.service.IRsService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/sets")
public class ZsController {

    @Resource
    private IRsService iRsService;

    @PostMapping(value = "/resource")
    @ApiOperation("add set resource")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "status", paramType = "save", required = true)
    })
    public RestResult<Object> setSetResource(@RequestParam(value = "status") String status) {
        return iRsService.getSetResource(status);
    }
}
