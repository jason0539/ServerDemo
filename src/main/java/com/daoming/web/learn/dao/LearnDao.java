package com.daoming.web.learn.dao;

import com.daoming.web.learn.domain.LearnResource;
import com.daoming.web.tools.Page;

import java.util.Map;

public interface LearnDao {
    int add(LearnResource learnResource);

    int update(LearnResource learnResource);

    int deleteById(String id);

    LearnResource queryLearnResourceById(Long id);

    Page queryLearnResourceList(Map<String, Object> params);
}
