package com.test.service;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

import org.springframework.stereotype.Service;

import com.test.entity.UserEntity;


@Service
public class ReadEmail implements IReadEmail {

	@Override
	public void readEmail(String username,String password) {

		// 1. Setup properties for the mail session.
		Properties props = new Properties();
		props.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.pop3.socketFactory.fallback", "false");
		props.put("mail.pop3.socketFactory.port", "995");
		props.put("mail.pop3.port", "995");
		props.put("mail.pop3.host", "pop.gmail.com");
		props.put("mail.pop3.user", username);
		props.put("mail.store.protocol", "pop3");
		props.put("mail.pop3.ssl.protocols", "TLSv1.2");

		// 2. Creates a javax.mail.Authenticator object.
		Authenticator auth = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username,password);
			}
		};

		// 3. Creating mail session.
		Session session = Session.getDefaultInstance(props, auth);

		// 4. Get the POP3 store provider and connect to the store.
		Store store = null;
		try {
			store = session.getStore("pop3");
		} catch (NoSuchProviderException e1) {
			
			e1.printStackTrace();
		}

		try {
			store.connect("pop.gmail.com", username, password);
		} catch (MessagingException e) {
			
			e.printStackTrace();
		}

		// 5. Get folder and open the INBOX folder in the store.
		Folder inbox = null;
		try {
			inbox = store.getFolder("INBOX");
		} catch (MessagingException e) {
			
			e.printStackTrace();
		}
		try {
			inbox.open(Folder.READ_ONLY);
		} catch (MessagingException e) {
			
			e.printStackTrace();
		}

		// 6. Retrieve the messages from the folder.
		Message[] messages = null;
		try {
			messages = inbox.getMessages();
		} catch (MessagingException e) {
			
			e.printStackTrace();
		}
		for (Message message : messages) {
			try {
				message.writeTo(System.out);
				//List<String> mailmsg=(List<String>) message;
			} catch (IOException | MessagingException e) {
				
				e.printStackTrace();
			}
		}

		// 7. Close folder and close store.
		try {
			inbox.close(false);
		} catch (MessagingException e) {
			
			e.printStackTrace();
		}
		try {
			store.close();
		} catch (MessagingException e) {
			
			e.printStackTrace();
		}

	}

}
