package com.example.poc.legacrm.customer;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MemberRepository {
	private final JdbcTemplate jdbcTemplate;

	private final RowMapper<Member> memberRowMapper = (rs, i) -> new MemberBuilder()
			.setMembershipNumber(String.format("%010d", rs.getInt("CUSTOMER_NO"))) //
			.setKanjiFamilyName(rs.getString("KANJI_FAMILY_NAME")) //
			.setKanjiGivenName(rs.getString("KANJI_GIVEN_NAME")) //
			.setKanaGivenName(rs.getString("KANA_FAMILY_NAME")) //
			.setKanaFamilyName(rs.getString("KANA_FAMILY_NAME")) //
			.setBirthday(rs.getDate("BIRTHDAY").toLocalDate()) //
			.setGender(Gender.valueOf(rs.getString("GENDER"))) //
			.setTel(rs.getString("TEL")) //
			.setZipCode(rs.getString("ZIP_CODE")) //
			.setAddress(rs.getString("ADDRESS")) //
			.setMail(rs.getString("MAIL")) //
			.setCreditNo(rs.getString("CREDIT_NO")) //
			.setCreditType(new CreditTypeBuilder() //
					.setCreditTypeCd(rs.getString("CREDIT_TYPE_CD")) //
					.setCreditFirm(rs.getString("CREDIT_TERM")) //
					.createCreditType()) //
			.setCurrentPassword(rs.getString("PASSWORD")) //
			.setAuthLogin(new AuthLoginBuilder() //
					.setPassword(rs.getString("PASSWORD")) //
					.setLastPassword(rs.getString("LAST_PASSWORD")) //
					.setLoginDateTime(rs.getDate("LOGIN_DATE_TIME").toLocalDate()) //
					.setLoginFlg(rs.getBoolean("LOGIN_FLG")) //
					.createAuthLogin()) //
			.createMember();

	public MemberRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Member> findAll() {
		return this.jdbcTemplate.query(
				"select m.CUSTOMER_NO, KANJI_FAMILY_NAME, KANJI_GIVEN_NAME, KANA_FAMILY_NAME, KANA_GIVEN_NAME, BIRTHDAY, GENDER, TEL, ZIP_CODE, ADDRESS, MAIL, CREDIT_NO, CREDIT_TYPE_CD, CREDIT_TERM, PASSWORD, LAST_PASSWORD, LOGIN_DATE_TIME, LOGIN_FLG from MEMBER m, MEMBER_LOGIN l WHERE m.CUSTOMER_NO = l.CUSTOMER_NO",
				this.memberRowMapper);
	}

	public Optional<Member> findOne(String membershipNumber) {
		int number = Integer.parseInt(membershipNumber);
		try {
			return Optional.of(this.jdbcTemplate.queryForObject(
					"select m.CUSTOMER_NO, KANJI_FAMILY_NAME, KANJI_GIVEN_NAME, KANA_FAMILY_NAME, KANA_GIVEN_NAME, BIRTHDAY, GENDER, TEL, ZIP_CODE, ADDRESS, MAIL, CREDIT_NO, CREDIT_TYPE_CD, CREDIT_TERM, PASSWORD, LAST_PASSWORD, LOGIN_DATE_TIME, LOGIN_FLG from MEMBER m, MEMBER_LOGIN l WHERE m.CUSTOMER_NO = ? AND m.CUSTOMER_NO = l.CUSTOMER_NO",
					this.memberRowMapper, number));
		}
		catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Transactional
	public Member insert(Member member) {
		this.jdbcTemplate.update(
				"INSERT INTO MEMBER (KANJI_FAMILY_NAME, KANJI_GIVEN_NAME, KANA_FAMILY_NAME, KANA_GIVEN_NAME, BIRTHDAY, GENDER, TEL, ZIP_CODE, ADDRESS, MAIL, CREDIT_NO, CREDIT_TYPE_CD, CREDIT_TERM) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)",
				member.getKanjiFamilyName(), member.getKanjiGivenName(),
				member.getKanaFamilyName(), member.getKanaGivenName(),
				member.getBirthday(), member.getGender(), member.getTel(),
				member.getZipCode(), member.getMail(), member.getCreditNo(),
				member.getCreditType().getCreditTypeCd(), member.getCreditTerm());
		Integer id = this.jdbcTemplate
				.queryForObject("SELECT last_insert_id() FROM MEMBER", Integer.class);
		AuthLogin authLogin = member.getAuthLogin();
		this.jdbcTemplate.update(
				"INSERT INTO MEMBER_LOGIN (CUSTOMER_NO, PASSWORD, LAST_PASSWORD, LOGIN_DATE_TIME, LOGIN_FLG) VALUES (?, ?, ?, ?, ?)",
				id, authLogin.getPassword(), authLogin.getLastPassword(),
				authLogin.getLoginDateTime(), authLogin.isLoginFlg());
		member.setMembershipNumber(String.format("%10d", id));
		return member;
	}
}
