package com.example.poc.legacrm;

import java.time.LocalDate;
import java.util.Optional;

import com.example.poc.legacrm.customer.AuthLoginBuilder;
import com.example.poc.legacrm.customer.CreditTypeBuilder;
import com.example.poc.legacrm.customer.Gender;
import com.example.poc.legacrm.customer.GetMemberRequest;
import com.example.poc.legacrm.customer.GetMemberResponse;
import com.example.poc.legacrm.customer.Member;
import com.example.poc.legacrm.customer.MemberBuilder;
import com.example.poc.legacrm.customer.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ws.client.core.WebServiceTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class LegacrmApplicationTests {
	@Autowired
	private WebServiceTemplate webServiceTemplate;
	@MockBean
	MemberRepository memberRepository;

	@Test
	public void getMember() {
		this.setUpMock();
		GetMemberRequest request = new GetMemberRequest("0000000002");
		GetMemberResponse response = (GetMemberResponse) this.webServiceTemplate
				.marshalSendAndReceive(request);
		assertThat(response).isNotNull();
		Member member = response.getMember();
		assertThat(member).isNotNull();
		assertThat(member.getAuthLogin()).isNotNull();
		assertThat(member.getCreditType()).isNotNull();
		assertThat(member.getGender()).isEqualTo(Gender.M);
		assertThat(member.getBirthday()).isEqualTo(LocalDate.of(2000, 1, 2));
	}

	private void setUpMock() {
		given(this.memberRepository.findOne("0000000002"))
				.willReturn(Optional.of(new MemberBuilder() //
						.setMembershipNumber("0000000002") //
						.setKanjiFamilyName("山田").setKanjiGivenName("太郎") //
						.setKanaFamilyName("ヤマダ").setKanaGivenName("タロウ") //
						.setBirthday(LocalDate.of(2000, 1, 2)) //
						.setGender(Gender.M) //
						.setTel("111-1111-1111") //
						.setZipCode("111-1111") //
						.setAddress("東京都港区港南Ｘ－Ｘ－Ｘ") //
						.setMail("foo@example.com") //
						.setCreditType(new CreditTypeBuilder() //
								.setCreditTypeCd("VIS") //
								.setCreditFirm("VISA") //
								.createCreditType())
						.setCreditNo("1111111111111111").setCreditTerm("0120") //
						.setCurrentPassword("demodemo") //
						.setAuthLogin(new AuthLoginBuilder() //
								.setMembershipNumber("1111111111111111") //
								.setPassword("demodemo") //
								.setLastPassword("demodemo") //
								.setLoginDateTime(LocalDate.of(2000, 1, 1)) //
								.setLoginFlg(true) //
								.createAuthLogin()) //
						.createMember()));
	}

}
