package com.zcky.learn.with.teacher.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.zcky.learn.with.teacher.constant.Constant;
import com.zcky.learn.with.teacher.mongoDb.model.Users;
import com.zcky.learn.with.teacher.util.MailUtility;

@Service
public class AsyncService {
	private static final Logger logger = LoggerFactory.getLogger(AsyncService.class);

	@Async
    public void sendSuccessSignUpMailService(Users user) throws InterruptedException {
		logger.info("start sending mail");
		MailUtility mUtil = new MailUtility();
		String template = mUtil.getEmailTemplate(Constant.MAIL_TEMPLATE_PATH);
		String messageBody = mUtil.getVerificationMailMessageBody(template, user);
		mUtil.sendMail(messageBody, "", user.getEmail(), Constant.VERIFICATION_SIGN_UP_MAIL_SUBJECT);
		logger.info("end sending mail");
    }
	
}