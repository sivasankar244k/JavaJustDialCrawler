package com.justdial.crawler;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GmailSendingJobDone {

	public static void main(String[] args) {

		final String username = "kuppalasiva344@gmail.com";
		final String password = "Stuart544.7799548";

		Properties props = new Properties();
		props.put("mail.smtps.auth", "true");
		props.put("mail.smtps.starttls.enable", "true");
		props.put("mail.smtps.host", "smtps.gmail.com");
		props.put("mail.smtps.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("kuppalasiva344@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("siva244k@gmail.com"));
			message.setSubject("Justdial web scrapping done ");
			message.setText("Dear Siva,"
					+ "\n webscrapping has been done please update your excel sheet wiht new urls !");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}