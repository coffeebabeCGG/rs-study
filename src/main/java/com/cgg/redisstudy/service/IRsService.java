package com.cgg.redisstudy.service;

import com.cgg.redisstudy.dto.RestResult;

public interface IRsService {

    RestResult<Object> setResource(String status, Long acTime, String name);

    RestResult<Object> getResource(String status);
}
