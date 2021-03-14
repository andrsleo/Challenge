package com.service.general.country.repositories;

import java.util.Map;

import com.service.general.country.dto.ResultInfoDTO;

public interface RedisRepository {
    Map<String, ResultInfoDTO> findAll();
    ResultInfoDTO findById(String id);
    void save(ResultInfoDTO student);
    void delete(String id);
}
