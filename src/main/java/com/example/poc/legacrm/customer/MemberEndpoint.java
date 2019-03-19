package com.example.poc.legacrm.customer;

import java.time.Clock;
import java.time.Instant;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class MemberEndpoint {
	public static final String NAMESPACE_URI = "http://customer.legacrm.poc.example.com";

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	private final Clock clock;

	public MemberEndpoint(MemberRepository memberRepository, Clock clock) {
		this.memberRepository = memberRepository;
		this.clock = clock;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMemberRequest")
	@ResponsePayload
	public GetMemberResponse getMember(@RequestPayload GetMemberRequest request) {
		Optional<Member> member = this.memberRepository
				.findOne(request.getMembershipNumber());
		return member.map(GetMemberResponse::new).get();
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createMemberRequest")
	@ResponsePayload
	public CreateMemberResponse createMember(
			@RequestPayload CreateMemberRequest request) {
		Member member = request.getMember();
		String password = this.passwordEncoder.encode(request.getRawPassword());
		member.setCurrentPassword(password);
		AuthLogin authLogin = member.getAuthLogin();
		authLogin.setPassword(password);
		authLogin.setLastPassword(password);
		authLogin.setLoginDateTime(Instant.now(this.clock));
		authLogin.setLoginFlg(false);
		Member created = this.memberRepository.insert(member);
		return new CreateMemberResponse(created);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateMemberRequest")
	@ResponsePayload
	public UpdateMemberResponse updateMember(
			@RequestPayload UpdateMemberRequest request) {
		Member member = request.getMember();
		String membershipNumber = member.getMembershipNumber();
		Member current = this.memberRepository.findOne(membershipNumber).orElseThrow(
				() -> new IllegalArgumentException(membershipNumber + " is not found."));
		String currentPassword = current.getCurrentPassword();
		if (!StringUtils.isEmpty(request.getCurrentPassword())) {
			if (!this.passwordEncoder.matches(request.getCurrentPassword(),
					currentPassword)) {
				throw new IllegalArgumentException("Current password is wrong.");
			}
			String password = this.passwordEncoder.encode(request.getRawPassword());
			member.setCurrentPassword(password);
			AuthLogin authLogin = current.getAuthLogin();
			authLogin.setPassword(password);
			authLogin.setLastPassword(currentPassword);
			member.setAuthLogin(authLogin);
		}
		Member updated = this.memberRepository.update(member);
		return new UpdateMemberResponse(updated);
	}
}
