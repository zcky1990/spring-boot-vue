package app.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import app.constants.Constant;
import app.mongo.model.Users;
import app.util.MailUtility;

@Service
public class AsyncService {
	private static final Logger logger = LoggerFactory.getLogger(AsyncService.class);

	@Async
    public void sendSuccessSignUpMailService(Users user, String env) throws InterruptedException {
		logger.info("start sending mail");
		MailUtility mUtil = new MailUtility(env);
		String template = mUtil.getEmailTemplate(Constant.MAIL_TEMPLATE_PATH);
		String messageBody = mUtil.getVerificationMailMessageBody(template, user);
		mUtil.sendMail(messageBody, "", user.getEmail(), Constant.VERIFICATION_SIGN_UP_MAIL_SUBJECT);
		logger.info("end sending mail");
    }
	
}
