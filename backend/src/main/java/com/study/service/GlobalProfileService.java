package com.study.service;

import com.study.entity.GlobalProfile;
import com.study.mapper.GlobalProfileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GlobalProfileService {

    @Autowired
    private GlobalProfileMapper globalProfileMapper;

    public GlobalProfile getProfileByUserId(Long userId) {
        Optional<GlobalProfile> profile = globalProfileMapper.findByUserId(userId);
        return profile.orElse(null);
    }

    public GlobalProfile createOrUpdateProfile(GlobalProfile profile) {
        Optional<GlobalProfile> existingProfile = globalProfileMapper.findByUserId(profile.getUserId());

        if (existingProfile.isPresent()) {
            GlobalProfile existing = existingProfile.get();
            updateProfileFields(existing, profile);
            return globalProfileMapper.save(existing);
        } else {
            profile.setIsCompleted(true);
            return globalProfileMapper.save(profile);
        }
    }

    public boolean isProfileCompleted(Long userId) {
        Optional<GlobalProfile> profile = globalProfileMapper.findByUserId(userId);
        return profile.map(GlobalProfile::getIsCompleted).orElse(false);
    }

    private void updateProfileFields(GlobalProfile existing, GlobalProfile newProfile) {
        if (newProfile.getName() != null) existing.setName(newProfile.getName());
        if (newProfile.getGender() != null) existing.setGender(newProfile.getGender());
        if (newProfile.getAge() != null) existing.setAge(newProfile.getAge());
        if (newProfile.getStudentId() != null) existing.setStudentId(newProfile.getStudentId());
        if (newProfile.getSchool() != null) existing.setSchool(newProfile.getSchool());
        if (newProfile.getMajor() != null) existing.setMajor(newProfile.getMajor());
        if (newProfile.getGrade() != null) existing.setGrade(newProfile.getGrade());
        if (newProfile.getEducationLevel() != null) existing.setEducationLevel(newProfile.getEducationLevel());
        if (newProfile.getEnrollmentDate() != null) existing.setEnrollmentDate(newProfile.getEnrollmentDate());
        if (newProfile.getExpectedGraduation() != null) existing.setExpectedGraduation(newProfile.getExpectedGraduation());
        if (newProfile.getEmail() != null) existing.setEmail(newProfile.getEmail());
        if (newProfile.getPhone() != null) existing.setPhone(newProfile.getPhone());
        if (newProfile.getPreferredPeriod() != null) existing.setPreferredPeriod(newProfile.getPreferredPeriod());
        if (newProfile.getPeriodDetail() != null) existing.setPeriodDetail(newProfile.getPeriodDetail());
        if (newProfile.getLearningFrequency() != null) existing.setLearningFrequency(newProfile.getLearningFrequency());
        if (newProfile.getFocusDuration() != null) existing.setFocusDuration(newProfile.getFocusDuration());
        if (newProfile.getFocusMinutes() != null) existing.setFocusMinutes(newProfile.getFocusMinutes());
        if (newProfile.getLearningPattern() != null) existing.setLearningPattern(newProfile.getLearningPattern());
        if (newProfile.getEnvironmentType() != null) existing.setEnvironmentType(newProfile.getEnvironmentType());
        if (newProfile.getLocation() != null) existing.setLocation(newProfile.getLocation());
        if (newProfile.getDevicePreference() != null) existing.setDevicePreference(newProfile.getDevicePreference());
        if (newProfile.getLearningStrategy() != null) existing.setLearningStrategy(newProfile.getLearningStrategy());
        if (newProfile.getNoteTaking() != null) existing.setNoteTaking(newProfile.getNoteTaking());
        if (newProfile.getNoteTool() != null) existing.setNoteTool(newProfile.getNoteTool());
        if (newProfile.getReviewHabit() != null) existing.setReviewHabit(newProfile.getReviewHabit());
        if (newProfile.getContentRanking() != null) existing.setContentRanking(newProfile.getContentRanking());
        if (newProfile.getContentStyle() != null) existing.setContentStyle(newProfile.getContentStyle());
        if (newProfile.getQuestionStyle() != null) existing.setQuestionStyle(newProfile.getQuestionStyle());
        if (newProfile.getFeedbackPreference() != null) existing.setFeedbackPreference(newProfile.getFeedbackPreference());
        if (newProfile.getLearningPace() != null) existing.setLearningPace(newProfile.getLearningPace());
        if (newProfile.getCompleteness() != null) existing.setCompleteness(newProfile.getCompleteness());
        if (newProfile.getPersonalityType() != null) existing.setPersonalityType(newProfile.getPersonalityType());
        if (newProfile.getCognitiveStyle() != null) existing.setCognitiveStyle(newProfile.getCognitiveStyle());
        if (newProfile.getMotivation() != null) existing.setMotivation(newProfile.getMotivation());
        if (newProfile.getProgrammingLevel() != null) existing.setProgrammingLevel(newProfile.getProgrammingLevel());
        if (newProfile.getMathLevel() != null) existing.setMathLevel(newProfile.getMathLevel());
        if (newProfile.getEnglishLevel() != null) existing.setEnglishLevel(newProfile.getEnglishLevel());
        if (newProfile.getLearningAbility() != null) existing.setLearningAbility(newProfile.getLearningAbility());
        if (newProfile.getProblemSolvingLevel() != null) existing.setProblemSolvingLevel(newProfile.getProblemSolvingLevel());

        existing.setIsCompleted(true);
    }
}
