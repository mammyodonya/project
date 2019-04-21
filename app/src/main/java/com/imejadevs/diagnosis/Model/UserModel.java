package com.imejadevs.diagnosis.Model;

public class UserModel {
  String firstname,lastname,phone,location,email,profile, diagnoses,reserves,messages,height,weight;
  byte [] aByte;

  public UserModel() {
  }

  public byte[] getaByte() {
    return aByte;
  }

  public void setaByte(byte[] aByte) {
    this.aByte = aByte;
  }

  public String getHeight() {
    return height;
  }

  public void setHeight(String height) {
    this.height = height;
  }

  public String getWeight() {
    return weight;
  }

  public void setWeight(String weight) {
    this.weight = weight;
  }

  public String getDiagnoses() {
    return diagnoses;
  }

  public void setDiagnoses(String diagnoses) {
    this.diagnoses = diagnoses;
  }

  public String getReserves() {
    return reserves;
  }

  public void setReserves(String reserves) {
    this.reserves = reserves;
  }

  public String getMessages() {
    return messages;
  }

  public void setMessages(String messages) {
    this.messages = messages;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getProfile() {
    return profile;
  }

  public void setProfile(String profile) {
    this.profile = profile;
  }
}
