package wang.zehui.self.cook.book.common.utils;

import lombok.extern.slf4j.Slf4j;
import wang.zehui.self.cook.book.common.properties.EmailServerProperties;
import wang.zehui.self.cook.book.domain.request.EmailSendCodeRequest;
import wang.zehui.self.cook.book.domain.response.CaptchaResponse;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @Author wangzehui
 * @Date 2026/6/5 12:12
 */
@Slf4j
public class SendUtil {

    private static final String VALID_CODE_MESSAGE = "您的验证码为: %s 请在%d秒内使用，过期请重新获取";

    /**
     * @Description: 发送邮箱验证码
     * @param request 发送验证码请求
     * @Return: void
     * @Author: wangzehui
     * @Date: 2026/6/5 12:13
     */
    public static void sendEmailValidCode(EmailSendCodeRequest request) {
        EmailServerProperties properties = request.getProperties();
        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", properties.getHost());
        props.put("mail.smtp.port", 587);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getUser(), properties.getPassword());
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(properties.getUser()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(request.getEmailAddress()));
            message.setSubject("专属菜单登录验证码");
            CaptchaResponse captcha = request.getCaptcha();
            message.setText(String.format(VALID_CODE_MESSAGE, captcha.getCaptchaCode(), captcha.getExpireSeconds()));

            Transport.send(message);
        } catch (MessagingException e) {
            log.error("发送邮箱验证码失败", e);
            e.printStackTrace();
        }
    }
}
