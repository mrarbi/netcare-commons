/*
 * Copyright (c) 2012, Callista Enterprise AB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.callistasoftware.sendmail;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Command tool for sending e-mails, optionally with attachments. <p>
 * 
 */
public class SendMail {
	private Properties properties;
	private String[] attachmentFiles;

	/**
	 * Initializes.
	 * 
	 * @param properties the properties.
	 * @param attachmentFiles any attachment files, or an empty array if none.
	 */
	public SendMail(Properties properties, String[] attachmentFiles) {
		this.properties = new Properties(properties);
		this.attachmentFiles = attachmentFiles;
		properties.put("mail.smtp.host", getProperty("host"));
		properties.put("mail.debug", getProperty("debug"));
		
		if (Boolean.valueOf(getProperty("secure"))) {
			properties.put("mail.smtp.port", "465");
			properties.put("mail.smtp.socketFactory.port", "465");
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			properties.put("mail.smtp.socketFactory.fallback", "false");
		}

		if (properties.containsKey("user")) {
			properties.put("mail.smtp.auth", "true");	
		}
	}

	/**
	 * Sends the email.
	 * 
	 * @throws Exception on any kind of error.
	 */
	public void send() throws Exception {

		Session session = Session.getDefaultInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(getProperty("user"), getProperty("pwd"));
			}
		});

		// Create a default MimeMessage object.
		MimeMessage message = new MimeMessage(session);

		// Set From: header field of the header.
		message.setFrom(new InternetAddress(getProperty("from")));

		// Set To: header field of the header.
		String[] recipients = getProperty("to").split(",");
		for (String recipient : recipients) {
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient.trim()));
		}

		// Set Subject: header field
		message.setSubject(getProperty("subject"), "ISO-8859-1");

		Multipart multipart = new MimeMultipart(); 

		// text
		MimeBodyPart messageBodyPart = new MimeBodyPart(); 
		messageBodyPart.setText(getProperty("text"), "ISO-8859-1");
		messageBodyPart.setHeader("Content-Type", "text/plain");
		multipart.addBodyPart(messageBodyPart); 

		// attachments
		for (String attachmentFile : attachmentFiles) {
			messageBodyPart = new MimeBodyPart(); 
			DataSource source = new FileDataSource(attachmentFile); 
			messageBodyPart.setDataHandler(new DataHandler(source)); 
			messageBodyPart.setFileName(attachmentFile);
			messageBodyPart.setHeader("Content-Type", getProperty("attachment-type"));			
			multipart.addBodyPart(messageBodyPart);
		}

		message.setContent(multipart); 

		// Send message
		Transport.send(message);
	}

	//
	private String getProperty(String name) {
		String value = properties.getProperty(name);
		if (value == null) {
			throw new IllegalArgumentException("Missing property: " + name);
		}
		return value;
	}

	//
	private static boolean exists(String fileName) {
		File f = new File(fileName);
		return f.exists();
	}

	/**
	 * Command-line tool, main function. <p>
	 * 
	 * <pre>
	 * usage: java -jar sendmail.jar &lt;config-file&gt; [attachments...]
	 * 
	 *  The config file is an ordinary property file like:
	 * 
	 * # to: comma separated list of email addresses
	 * to = advptr@gmail.com, another@gmail.com
	 * 
	 * # from email address
	 * from = peter.larsson@callistaenterprise.se
	 * 
	 * # body text
	 * text = Any text!
	 * 
	 * # subject
	 * subject = Test
	 * 
	 * # content-type of attachments
	 * attachment-type = text/csv
	 * 
	 * # secure smtp connection
	 * secure = true
	 * 
	 * # host to connect to
	 * host = smtp.gmail.com
	 * 
	 * # debug flag, enables trace on stdout
	 * debug = true
	 * 
	 * # user and pwd if smtp servere requires authentication, comment out if not
	 * # user = callista.push@gmail.com
	 * # pwd = some_password
	 * 
	 * </pre>
	 *
	 * Exit code 1 on error, otherwise exit code 0.
	 * 
	 * @param args the configuration file, and optional files to attach, all input files must exist.
	 */
	public static void main(String[] args) {

		if (args.length < 1) {
			System.out.printf("usage: java -jar sendmail.jar <config-file> [attachment1, ..., attachmentN]\n");
			System.exit(1);
		}

		if (!exists(args[0])) {
			System.out.printf("error: no such config file: %s\n", args[0]);
			System.exit(1);			
		}

		String[] files = new String[args.length-1];
		System.arraycopy(args, 1, files, 0, files.length);

		for (String file : files) {
			if (!exists(file)) {
				System.out.printf("error: no such attachment file: %s\n", file);
				System.exit(1);			
			}			
		}

		try {
			Properties properties = new Properties();
			FileInputStream in = new FileInputStream(args[0]);
			properties.load(in);
			in.close();
			SendMail sendMail = new SendMail(properties, files);
			sendMail.send();
			System.out.printf("sendmail: Message successfully sent\n");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}