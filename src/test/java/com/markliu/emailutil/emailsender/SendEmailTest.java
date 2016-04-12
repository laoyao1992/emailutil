package com.markliu.emailutil.emailsender;

import java.io.File;
import java.io.FileNotFoundException;

import javax.mail.Session;

import org.junit.Test;

import com.markliu.emailutil.emailsender.entities.EmailInfo;
import com.markliu.emailutil.emailsender.entities.EmailServerHostAndPort;
import com.markliu.emailutil.emailsender.entities.EmailServerInfo;
import com.markliu.emailutil.emailsender.service.EmailServerService;
import com.markliu.emailutil.emailsender.util.SendEmailTemplate;

/**
 * 
 * 
 * @auther SunnyMarkLiu
 * @time Apr 12, 2016 3:42:02 PM
 */
public class SendEmailTest {

	@Test
	public void testSendEmailTemplate() throws FileNotFoundException {
		EmailServerInfo emailServerInfo = new EmailServerInfo();
		emailServerInfo.setMailServerHost(EmailServerHostAndPort.NetEase163_SMTP_SERVER);
		emailServerInfo.setMailServerPort(EmailServerHostAndPort.SMTP_PORT);
		emailServerInfo.setValidate(true);
		emailServerInfo.setUserName("SunnyMarkLiu@163.com");
		emailServerInfo.setPassword("sqmmjlqwd992101"); // 注意使用的是开通 SMTP 协议的授权码
		emailServerInfo.setMyEmailAddress("SunnyMarkLiu@163.com");		
		
		// 构建邮件 email
		EmailInfo email = new EmailInfo();
		String[] toes = {"1291833546@qq.com", "2051459265@qq.com"};
		email.setToAddress(toes).setSubject("test 主题2")
				.setContent("this is content内容!");
		
		// 设置上传的附件
		File file1 = new File("E:\\Photos\\bob.jpeg");
		File file2 = new File("E:\\heartocat.png");
		File file3 = new File("E:\\Music\\Alison Krauss - When You Say Nothing At All.mp3");
		email.getAttachmentFiles().add(file1);
		email.getAttachmentFiles().add(file2);
		email.getAttachmentFiles().add(file3);
		
		SendEmailTemplate.sendEmail(emailServerInfo, email);
	}
	
	@Test
	public void testSendEmail() {

		EmailServerInfo emailServerInfo = new EmailServerInfo();
		emailServerInfo.setMailServerHost(EmailServerHostAndPort.NetEase163_SMTP_SERVER);
		emailServerInfo.setMailServerPort(EmailServerHostAndPort.SMTP_PORT);
		emailServerInfo.setValidate(true);
		emailServerInfo.setUserName("SunnyMarkLiu@163.com");
		emailServerInfo.setPassword(""); // 注意使用的是开通 SMTP 协议的授权码
		emailServerInfo.setMyEmailAddress("SunnyMarkLiu@163.com");

		EmailServerService emailServerService = new EmailServerService();

		// 如果登陆成功，则进行发送邮件
		Session sendMailSession = emailServerService.loginEmailServer(emailServerInfo);
		if (sendMailSession != null) {
			System.out.println(emailServerInfo.getMailServerHost() + " 登陆成功！");
			// 构建邮件 email
			EmailInfo email = new EmailInfo();
			String[] toes = {"1291833546@qq.com", "2051459265@qq.com"};
			email.setToAddress(toes).setSubject("test 主题2")
					.setContent("this is content内容!");
			boolean result = emailServerService.sendEmail(sendMailSession, emailServerInfo, email);
			if (result) {
				System.out.println("发送成功！");
			} else {
				System.out.println("发送失败！");
			}
		} else {
			System.out.println(emailServerInfo.getMailServerHost() + " 登陆失败！");
		}
	}

}