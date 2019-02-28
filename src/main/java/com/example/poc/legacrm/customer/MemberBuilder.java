package com.example.poc.legacrm.customer;

import java.time.LocalDate;

public class MemberBuilder {

    private String address;

    private AuthLogin authLogin;

    private LocalDate birthday;

    private String creditNo;

    private String creditTerm;

    private CreditType creditType;

    private String currentPassword;

    private Gender gender;

    private String kanaFamilyName;

    private String kanaGivenName;

    private String kanjiFamilyName;

    private String kanjiGivenName;

    private String mail;

    private String membershipNumber;

    private String tel;

    private String zipCode;

    public Member createMember() {
        return new Member(address, authLogin, birthday, creditNo, creditTerm, creditType, currentPassword, gender, kanaFamilyName, kanaGivenName, kanjiFamilyName, kanjiGivenName, mail,
            membershipNumber, tel, zipCode);
    }

    public MemberBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public MemberBuilder setAuthLogin(AuthLogin authLogin) {
        this.authLogin = authLogin;
        return this;
    }

    public MemberBuilder setBirthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    public MemberBuilder setCreditNo(String creditNo) {
        this.creditNo = creditNo;
        return this;
    }

    public MemberBuilder setCreditTerm(String creditTerm) {
        this.creditTerm = creditTerm;
        return this;
    }

    public MemberBuilder setCreditType(CreditType creditType) {
        this.creditType = creditType;
        return this;
    }

    public MemberBuilder setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
        return this;
    }

    public MemberBuilder setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public MemberBuilder setKanaFamilyName(String kanaFamilyName) {
        this.kanaFamilyName = kanaFamilyName;
        return this;
    }

    public MemberBuilder setKanaGivenName(String kanaGivenName) {
        this.kanaGivenName = kanaGivenName;
        return this;
    }

    public MemberBuilder setKanjiFamilyName(String kanjiFamilyName) {
        this.kanjiFamilyName = kanjiFamilyName;
        return this;
    }

    public MemberBuilder setKanjiGivenName(String kanjiGivenName) {
        this.kanjiGivenName = kanjiGivenName;
        return this;
    }

    public MemberBuilder setMail(String mail) {
        this.mail = mail;
        return this;
    }

    public MemberBuilder setMembershipNumber(String membershipNumber) {
        this.membershipNumber = membershipNumber;
        return this;
    }

    public MemberBuilder setTel(String tel) {
        this.tel = tel;
        return this;
    }

    public MemberBuilder setZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }
}