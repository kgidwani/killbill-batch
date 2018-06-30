package com.kpn.killbill;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kpn.killbill.processor.MailUtils;

public class TestCase {

	@RunWith(SpringRunner.class)
	@SpringBootTest
	public class KillbillBatchApplicationTests {

		@Test
		public void testMail() {

			MailUtils.sendMail(null);
		}

	}

}
