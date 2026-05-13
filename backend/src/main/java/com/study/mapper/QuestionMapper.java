package com.study.mapper;

import com.study.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionMapper extends JpaRepository<Question, Long> {

    List<Question> findByCategory(String category);

    List<Question> findByCategoryAndSubCategory(String category, String subCategory);

    List<Question> findBySubCategory(String subCategory);

    Question findByPid(String pid);
}