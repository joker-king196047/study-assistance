package com.study.mapper;

import com.study.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionMapper extends JpaRepository<Question, Long> {
    List<Question> findByBankId(Integer bankId);
    long countByBankId(Integer bankId);
}
