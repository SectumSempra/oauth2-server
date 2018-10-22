package com.sample.oauthsample;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AppTest {

	@Test
	public void testBCryptPasswordEncoder() {
		BCryptPasswordEncoder b = new BCryptPasswordEncoder();
		System.out.println(b.encode("b"));
	}
}
