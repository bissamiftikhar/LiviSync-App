package com.livisync.app;

public class UserProfile {
    private String uid;
    private String name;
    private String email;
    private String age;
    private String bio;
    private String gender;
    private String photoUrl;

    // Empty constructor required for Firestore
    public UserProfile() {}

    public UserProfile(String uid, String name, String email, String age, String bio, String gender, String photoUrl) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.age = age;
        this.bio = bio;
        this.gender = gender;
        this.photoUrl = photoUrl;
    }

    public String getUid() { return uid; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getAge() { return age; }
    public String getBio() { return bio; }
    public String getGender() { return gender; }
    public String getPhotoUrl() { return photoUrl; }

    public void setUid(String uid) { this.uid = uid; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setAge(String age) { this.age = age; }
    public void setBio(String bio) { this.bio = bio; }
    public void setGender(String gender) { this.gender = gender; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
}