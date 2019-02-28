package com.example.poc.legacrm.customer;

import java.util.Optional;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class MemberEndpoint {
	public static final String NAMESPACE_URI = "http://customer.legacrm.poc.example.com";

	private final MemberRepository memberRepository;

	public MemberEndpoint(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMemberRequest")
	@ResponsePayload
	public GetMemberResponse getMember(@RequestPayload GetMemberRequest request) {
		Optional<Member> member = this.memberRepository
				.findOne(request.getMembershipNumber());
		return member.map(GetMemberResponse::new).get();
	}
}
