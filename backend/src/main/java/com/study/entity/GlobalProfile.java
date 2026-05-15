package com.study.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "global_profile")
public class GlobalProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "age")
    private Integer age;

    @Column(name = "student_id", length = 50)
    private String studentId;

    @Column(name = "school", length = 100)
    private String school;

    @Column(name = "major", length = 100)
    private String major;

    @Column(name = "grade", length = 20)
    private String grade;

    @Column(name = "education_level", length = 20)
    private String educationLevel;

    @Column(name = "enrollment_date", length = 20)
    private String enrollmentDate;

    @Column(name = "expected_graduation", length = 20)
    private String expectedGraduation;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "preferred_period", length = 50)
    private String preferredPeriod;

    @Column(name = "period_detail", length = 50)
    private String periodDetail;

    @Column(name = "learning_frequency", length = 50)
    private String learningFrequency;

    @Column(name = "focus_duration", length = 20)
    private String focusDuration;

    @Column(name = "focus_minutes")
    private Integer focusMinutes;

    @Column(name = "learning_pattern", length = 50)
    private String learningPattern;

    @Column(name = "environment_type", length = 50)
    private String environmentType;

    @Column(name = "location", length = 100)
    private String location;

    @Column(name = "device_preference", length = 50)
    private String devicePreference;

    @Column(name = "learning_strategy", length = 50)
    private String learningStrategy;

    @Column(name = "note_taking", length = 50)
    private String noteTaking;

    @Column(name = "note_tool", length = 100)
    private String noteTool;

    @Column(name = "review_habit", length = 50)
    private String reviewHabit;

    @Column(name = "content_ranking", length = 500)
    private String contentRanking;

    @Column(name = "content_style", length = 50)
    private String contentStyle;

    @Column(name = "question_style", length = 50)
    private String questionStyle;

    @Column(name = "feedback_preference", length = 50)
    private String feedbackPreference;

    @Column(name = "learning_pace", length = 50)
    private String learningPace;

    @Column(name = "completeness", length = 50)
    private String completeness;

    @Column(name = "personality_type", length = 50)
    private String personalityType;

    @Column(name = "cognitive_style", length = 50)
    private String cognitiveStyle;

    @Column(name = "motivation", length = 500)
    private String motivation;

    @Column(name = "programming_level")
    private Integer programmingLevel;

    @Column(name = "math_level")
    private Integer mathLevel;

    @Column(name = "english_level")
    private Integer englishLevel;

    @Column(name = "learning_ability")
    private Integer learningAbility;

    @Column(name = "problem_solving_level")
    private Integer problemSolvingLevel;

    @Column(name = "is_completed", nullable = false)
    private Boolean isCompleted = false;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
        updateTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDateTime.now();
    }
}
