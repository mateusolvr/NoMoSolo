package com.example.nomosoloapp;

public class User {
    private String fn, ln, bio, instrument, skillLevel, genre1, genre2, seekingInstrument, seekingSkill, seekingGenre;

    public User(String bio, String instrument, String skillLevel, String genre1, String genre2, String seekingInstrument, String seekingSkill, String seekingGenre) {
        this.bio = bio;
        this.instrument = instrument;
        this.skillLevel = skillLevel;
        this.genre1 = genre1;
        this.genre2 = genre2;
        this.seekingInstrument = seekingInstrument;
        this.seekingSkill = seekingSkill;
        this.seekingGenre = seekingGenre;
    }

    public String getFn() {
        return fn;
    }

    public void setFn(String fn) {
        this.fn = fn;
    }

    public String getLn() {
        return ln;
    }

    public void setLn(String ln) {
        this.ln = ln;
    }


    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

    public String getGenre1() {
        return genre1;
    }

    public void setGenre1(String genre1) {
        this.genre1 = genre1;
    }

    public String getGenre2() {
        return genre2;
    }

    public void setGenre2(String genre2) {
        this.genre2 = genre2;
    }

    public String getSeekingInstrument() {
        return seekingInstrument;
    }

    public void setSeekingInstrument(String seekingInstrument) {
        this.seekingInstrument = seekingInstrument;
    }

    public String getSeekingSkill() {
        return seekingSkill;
    }

    public void setSeekingSkill(String seekingSkill) {
        this.seekingSkill = seekingSkill;
    }

    public String getSeekingGenre() {
        return seekingGenre;
    }

    public void setSeekingGenre(String seekingGenre) {
        this.seekingGenre = seekingGenre;
    }
}
