package com.assorttech.myquizler.Model;

import java.io.Serializable;

public class StatModel {

    private int totalQuestionsAttempted;
    private int total_ques;
    private int score;
    private int silver;
    private int gold;
    private int platinum;
    private int xp;
    private String personName;
    private String jobProfile;
    private String user_id;

    public StatModel() {
    }

    public StatModel(int totalQuestionsAttempted, int total_ques, int score, int silver, int gold, int platinum, int xp, String personName, String jobProfile, String user_id) {
        this.totalQuestionsAttempted = totalQuestionsAttempted;
        this.total_ques = total_ques;
        this.score = score;
        this.silver = silver;
        this.gold = gold;
        this.platinum = platinum;
        this.xp = xp;
        this.personName = personName;
        this.jobProfile = jobProfile;
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getTotal_ques() {
        return total_ques;
    }

    public void setTotal_ques(int total_ques) {
        this.total_ques = total_ques;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getSilver() {
        return silver;
    }

    public void setSilver(int silver) {
        this.silver = silver;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getPlatinum() {
        return platinum;
    }

    public void setPlatinum(int platinum) {
        this.platinum = platinum;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getJobProfile() {
        return jobProfile;
    }

    public void setJobProfile(String jobProfile) {
        this.jobProfile = jobProfile;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getTotalQuestionsAttempted() {
        return totalQuestionsAttempted;
    }

    public void setTotalQuestionsAttempted(int totalQuestionsAttempted) {
        this.totalQuestionsAttempted = totalQuestionsAttempted;
    }
}
