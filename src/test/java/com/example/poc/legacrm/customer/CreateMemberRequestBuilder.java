package com.example.poc.legacrm.customer;

public class CreateMemberRequestBuilder {

    private Member member;

    private String rawPassword;

    public CreateMemberRequest createCreateMemberRequest() {
        return new CreateMemberRequest(member, rawPassword);
    }

    public CreateMemberRequestBuilder setMember(Member member) {
        this.member = member;
        return this;
    }

    public CreateMemberRequestBuilder setRawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
        return this;
    }
}