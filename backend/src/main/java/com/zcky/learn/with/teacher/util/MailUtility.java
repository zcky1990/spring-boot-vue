package com.zcky.learn.with.teacher.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;

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

import com.google.gson.Gson;
import com.zcky.learn.with.teacher.constant.Constant;
import com.zcky.learn.with.teacher.environtment.Environtment;
import com.zcky.learn.with.teacher.mongoDb.model.Users;

import config.Mail;
import config.MailConfig;
import config.MailEnvirontment;

public class MailUtility {
	private Util util = new Util();
	private Environtment env = new Environtment();
	private MailConfig config;
	private Gson gson = new Gson();

	public MailUtility() {
		this.getMailConfig();
	}

	public void getMailConfig() {
		String mailConfig = util.getJsonFile(Constant.MAIL_CONFIG_PATH);
		Mail mailconfig = gson.fromJson(mailConfig, Mail.class);
		ArrayList<MailEnvirontment> mailEnvirontment = mailconfig.getMailconfig();
		for(int i = 0; i < mailEnvirontment.size(); i++) {
			MailEnvirontment mail = mailEnvirontment.get(i);
			if(mail.getEnv().equals(env.getEnvirontment())) {
				config = mail.getConfig();
				break;
			}
		}
	} 

	public MailConfig getConfig() {
		return config;
	}


	public Properties getProperties() {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", config.getHost());
		properties.put("mail.smtp.port", config.getPort());
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		return properties;
	}

	public void sendMail(String messageBody, String pathAttachment, String to, String subject) {
		Properties prop = getProperties();
		/*Session session = Session.getInstance( prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(config.getUsername(), config.getPassword());
            }
        }); */

		Session session = Session.getDefaultInstance(prop);
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(config.getUsername()));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			Multipart multipart = new MimeMultipart();

			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(messageBody, "text/html");

			if(!pathAttachment.equals("")) {
				MimeBodyPart attachmentBodyPart = new MimeBodyPart();
				attachmentBodyPart.attachFile(new File(pathAttachment));
				multipart.addBodyPart(attachmentBodyPart);
			}
			multipart.addBodyPart(mimeBodyPart);
			message.setContent(multipart);

			//Transport.send(message);
			System.out.println("sendmail ");
			Transport t = session.getTransport("smtp");
			t.connect(config.getUsername(), config.getPassword());
			t.sendMessage(message, message.getAllRecipients());
			t.close();
			System.out.println("success-send-mail ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getEmailTemplate(String url) {
		String mailBody = util.getJsonFile(url);
		return mailBody;
	}

	public String getVerificationMailMessageBody(String template, Users users) {
		template = template.replace("{{username}}", users.getUsername());
		String verificationUrl = getBaseUrl()+"api/users/validate/"+ users.getStringId();
		template = template.replace("{{url}}", verificationUrl);
		return template;
	}

	public String getBaseUrl() {
		if(env.getEnvirontment().equals(Constant.DEV_ENV)){
			return Constant.BASE_URL_DEV;
		}else {
			return Constant.BASE_URL_PROD;
		}
	}
}
