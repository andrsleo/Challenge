package com.service.general.country.repositories;

import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.service.general.country.dto.ResultInfoDTO;

@Repository
public class ResultInfoRepository implements RedisRepository {
	
	private static final String KEY = "ResultInfoDTO";
	
	private RedisTemplate<String, ResultInfoDTO> redisTemplate;
    private HashOperations hashOperations;

    public ResultInfoRepository(RedisTemplate<String, ResultInfoDTO> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
	
	 @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public Map<String, ResultInfoDTO> findAll() {
        return hashOperations.entries(KEY);
    }

    @Override
    public ResultInfoDTO findById(String id) {
        return (ResultInfoDTO) hashOperations.get(KEY, id);
    }

    @Override
    public void save(ResultInfoDTO resultInfoDTO) {
        hashOperations.put(KEY, UUID.randomUUID().toString(), resultInfoDTO);
    }

    @Override
    public void delete(String id) {
        hashOperations.delete(KEY, id);
    }

}
