package com.sample.oauthsample;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Ignore
public class AppTest {

	@Test
	public void testBCryptPasswordEncoder() {
		BCryptPasswordEncoder b = new BCryptPasswordEncoder();
		System.out.println(b.encode("demo"));
	}
}
