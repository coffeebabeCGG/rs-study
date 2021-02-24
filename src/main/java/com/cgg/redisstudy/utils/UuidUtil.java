package com.cgg.redisstudy.utils;

import java.util.UUID;

/**
 * @author cgg
 */
public class UuidUtil {

    public static Long uuid() {
        return IdSnowflakeWorker.getGenerateId();
    }

    public static void main(String[] args) {
        System.out.println(IdSnowflakeWorker.getGenerateId());
    }
}
