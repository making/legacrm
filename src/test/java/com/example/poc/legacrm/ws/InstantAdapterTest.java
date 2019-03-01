package com.example.poc.legacrm.ws;

import java.time.Instant;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InstantAdapterTest {

	@Test
	public void parse() {
		Instant fixed = Instant.now();
		Instant parsed = InstantAdapter.parse(fixed.toString());
		assertThat(parsed).isEqualTo(fixed);
	}

	@Test
	public void print() {
		Instant fixed = Instant.now();
		String printed = InstantAdapter.print(fixed);
		assertThat(printed).isEqualTo(fixed.toString());
	}
}