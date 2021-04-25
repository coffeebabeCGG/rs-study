package com.cgg.redisstudy.service;

import com.cgg.redisstudy.dto.RestResult;

public interface IRsService {

    RestResult<Object> setResource(String status, Long acTime, String name);

    RestResult<Object> getResource(String status, Long userId);

    RestResult<Object> updateResource(String status, Long acTime, String name);

    RestResult<Object> getSetResource(String status);
}
