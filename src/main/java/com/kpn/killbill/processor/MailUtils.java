package com.kpn.killbill.processor;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.kpn.killbill.model.Event;

public class MailUtils {

	public static void sendMail(Event event) {

		final String username = "youmail@gmail.com";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("yourmail@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(event.getEmail()));
			message.setSubject("Test Invoice for period Jun 2018");
			message.setText("Dear Customer," + "\n\n" + event.getChargeAmountIncl() + " Euro is  Amount Due for your OTT service "
					+ event.getServiceProv() + "\n\n click here to pay Online.  \"\\n\\n  Regards, \\\\n Team Technos : Kill Bill" );

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public static String password = "XXX";

}
