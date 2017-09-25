
package com.denkensol.universaladmission.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.amazonaws.Request;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.model.EmailAddressGrantee;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.RawMessage;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;
import com.denkensol.universaladmission.constant.CredentialsConstants;
import com.denkensol.universaladmission.template.AmazonS3Template;

/**
 * @author Indrajit UtilClass For Sending EMail Using AWS SES
 */
@Service
public class EMailUtil {
	private static String EMAIL_FROM = "noreply@skoolville.com";
	private static Regions AWS_REGION = Regions.US_WEST_2;
	private static String EMAIL_SUBJECT = "Amazon SES email test";
	private static String EMAIL_BODY_TEXT = "This Test email was sent through Amazon SES .";
	private static String EMAIL_BODY_RESET_PASSWORD_TEXT = "skoolville.com/resetPassword?";
	private static String RESET_PASSWORD_SUBJECT = "Reset your password";

	@Autowired
	AmazonS3Template amazonS3Template;

	public DefaultAWSCredentialsProviderChain getCredentials() {

		return new DefaultAWSCredentialsProviderChain();

	}

	@Transactional
	@Async
	public void sendMail(final String to, final String filePath) {

		Session session = Session.getDefaultInstance(new Properties());
		MimeMessage message = new MimeMessage(session);
		try {
			message.setSubject(EMAIL_SUBJECT, "UTF-8");
			message.setFrom(new InternetAddress(EMAIL_FROM));
			message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress
					.parse("kiran.alapati123@gmail.com,indrajit@denkensolutions.com,raj@denkensolutions.com"));

		} catch (MessagingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		// Cover wrap
		MimeBodyPart wrap = new MimeBodyPart();
		MimeMultipart cover = new MimeMultipart("alternative");
		MimeBodyPart html = new MimeBodyPart();
		try {
			cover.addBodyPart(html);
			wrap.setContent(cover);
		} catch (MessagingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String id = UUID.randomUUID().toString();
		MimeBodyPart attachment = new MimeBodyPart();
		DataSource dataSource = new FileDataSource(filePath);
		try {
			MimeMultipart content = new MimeMultipart("related");
			message.setContent(content);
			content.addBodyPart(wrap);
			attachment.setDataHandler(new DataHandler(dataSource));
			attachment.setHeader("Content-ID", "<" + id + ">");
			attachment.setFileName("Application.pdf");
			content.addBodyPart(attachment);
			html.setContent(EMAIL_BODY_TEXT, "text/html");

		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceClient(getCredentials());
			Region REGION = Region.getRegion(AWS_REGION);
			client.setRegion(REGION);
			// Send the email.
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			message.writeTo(outputStream);
			RawMessage rawMessage = new RawMessage(ByteBuffer.wrap(outputStream.toByteArray()));
			SendRawEmailRequest rawEmailRequest = new SendRawEmailRequest(rawMessage);
			client.sendRawEmail(rawEmailRequest);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void sendResetPasswordLinkEmail(String toAddresses, String resetPasswordHashCode) {
		String htmlBody = null;
		SendEmailRequest emailRequest = null;
		Message message = null;
		Content subjectContent = null;
		Destination dest = null;
		Content htmlContent = null;
		Body body = null;
		AmazonSimpleEmailServiceClient client = null;
		/*
		 * Create Email request to add Body ,Subject,BCC,CC
		 */
		emailRequest = new SendEmailRequest().withSource(EMAIL_FROM);

		/*
		 * Include Subject In Message Create Message to Insert text/html
		 */

		subjectContent = new Content().withData(RESET_PASSWORD_SUBJECT);
		message = new Message().withSubject(subjectContent);

		/*
		 * To Set Destination
		 * 
		 */
		dest = new Destination().withToAddresses(toAddresses);
		emailRequest.setDestination(dest);

		htmlBody = "<html><body style='color: #555555;font-size: 12px;line-height: 20px;'><p>We recently received a request to reset the password to your account:</p><p><b style=' text-decoration: none;color: #555555;font-weight: bold;'>"
				+ toAddresses
				+ "</b></p><br></br></br>To reset your password, click on this link (or copy and paste it into your browser):<br></br><br></br>"
				+ EMAIL_BODY_RESET_PASSWORD_TEXT + "id=" + resetPasswordHashCode
				+ "<br></br></br><p>If you did not request to reset your password, simply disregard this email. No changes will be made to your account.</p><br></br></br>Time of Request: "
				+ new Date() + "</body></html>";
		// Include a body in HTML formats.
		htmlContent = new Content().withData(htmlBody);
		body = new Body().withHtml(htmlContent);
		message.setBody(body);
		try {
			client = new AmazonSimpleEmailServiceClient(getCredentials());
			Region REGION = Region.getRegion(AWS_REGION);
			client.setRegion(REGION);
			// set Message To Request
			emailRequest.setMessage(message);
			// Send the email.
			client.sendEmail(emailRequest);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
