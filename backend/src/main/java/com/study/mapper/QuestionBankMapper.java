package com.study.mapper;

import com.study.entity.QuestionBank;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionBankMapper extends JpaRepository<QuestionBank, Integer> {
    List<QuestionBank> findByCategory(String category);
    long count();
}
