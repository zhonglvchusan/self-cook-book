package wang.zehui.self.cook.book.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.zehui.self.cook.book.common.consts.RedisKeyConst;
import wang.zehui.self.cook.book.common.domain.BusinessException;
import wang.zehui.self.cook.book.common.domain.SystemEnvironment;
import wang.zehui.self.cook.book.common.enums.SystemEnvironmentEnum;
import wang.zehui.self.cook.book.common.utils.RedisUtil;
import wang.zehui.self.cook.book.domain.request.CaptchaRequest;
import wang.zehui.self.cook.book.domain.response.CaptchaResponse;
import wang.zehui.self.cook.book.service.ICaptchaService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

/**
 * @Author wangzehui
 * @Date 2025/11/6 14:05
 */
@Service
@Slf4j
public class CaptchaServiceImpl implements ICaptchaService {

    private static final char[] CAPTCHA_CHAR = "abcdefghjkmnprstuvwxyzABCDEFGHJKMNPRSTUVWXYZ2345678".toCharArray();

    private static final Integer LENGTH = 4;

    private static final Integer WIDTH = 125;

    private static final Integer HEIGHT = 43;

    private static final Integer EXPIRE_SECONDS = 60;

    @Autowired
    private SystemEnvironment systemEnvironment;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public CaptchaResponse generateCaptcha() {
        // 1.定义验证码的图片的宽、高、类型
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        // 2.获得Graphics对象
        Graphics graphics = image.getGraphics();
        // 3.设置背景颜色
        Color color = new Color(249, 250, 253);
        // 设置画笔颜色
        graphics.setColor(color);
        // 5.画框
        graphics.fillRect(0, 0, WIDTH, HEIGHT);

        // 6.设置字体
        graphics.setFont(new Font("Arial", Font.BOLD, 24));
        StringBuilder captcha = new StringBuilder(LENGTH);

        // 绘制验证码
        for (int i = 0; i < LENGTH; i++) {
            char c = CAPTCHA_CHAR[RandomUtils.nextInt(0, CAPTCHA_CHAR.length)];
            captcha.append(c);
            graphics.setColor(new Color(RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255)));
            graphics.drawString(String.valueOf(c), i * 25 + 10, HEIGHT - 15);
        }

        log.info("验证码：{}", captcha);

        // 绘制干扰线
        for (int i = 0; i < 20; i++) {
            graphics.setColor(new Color(RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255), RandomUtils.nextInt(0, 255)));
            graphics.drawLine(RandomUtils.nextInt(0, WIDTH), RandomUtils.nextInt(0, HEIGHT), RandomUtils.nextInt(0, WIDTH), RandomUtils.nextInt(0, HEIGHT));
        }
        // 释放资源
        graphics.dispose();
        String captchaBase64Image = "";
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", outputStream);
            captchaBase64Image = "data:image/jpg;base64," + Base64.getEncoder().encodeToString(outputStream.toByteArray());
            outputStream.close();
        } catch (IOException e) {
            log.error("验证码图片转换为Base64失败", e);
        }

        CaptchaResponse response = new CaptchaResponse();
        response.setCaptchaId(UUID.randomUUID().toString().replaceAll("-", ""));
        response.setCaptchaBase64Image(captchaBase64Image);
        response.setExpireSeconds(EXPIRE_SECONDS);
        // 如果是测试开发环境，返回结果
        if (systemEnvironment.getCurrentEnvironment().equalsValue(SystemEnvironmentEnum.SystemEnvironmentNameConst.TEST) ||
            systemEnvironment.getCurrentEnvironment().equalsValue(SystemEnvironmentEnum.SystemEnvironmentNameConst.DEV)) {
            response.setCaptchaCode(captcha.toString());
        }

        // 将结果放入redis中，并设置过期时间
        String redisKey = redisUtil.generateRedisKey(RedisKeyConst.CAPTCHA, response.getCaptchaId());
        redisUtil.set(redisKey, captcha.toString(), EXPIRE_SECONDS);
        return response;
    }

    @Override
    public Boolean checkCaptcha(CaptchaRequest captchaRequest) {
        if (StringUtils.isAllBlank(captchaRequest.getCaptchaId(), captchaRequest.getCaptchaCode())) {
            throw new BusinessException("请正确输入验证码");
        }

        // 开发环境不进行校验
        if (Objects.equals(SystemEnvironmentEnum.DEV, systemEnvironment.getCurrentEnvironment()) || Objects.equals(SystemEnvironmentEnum.TEST, systemEnvironment.getCurrentEnvironment())) {
            return true;
        }

        // 从redis中取出验证码，判断验证码是否正确
        String redisKey = redisUtil.generateRedisKey(RedisKeyConst.CAPTCHA, captchaRequest.getCaptchaId());
        String code = redisUtil.get(redisKey, String.class);

        if (StringUtils.isBlank(code)) {
            throw new BusinessException("验证码已过期，请刷新重试");
        }

        if (!code.equalsIgnoreCase(captchaRequest.getCaptchaCode())) {
            throw new BusinessException("验证码错误，请输入正确的验证码");
        }

        // 验证完毕后删除redis数据
        redisUtil.del(redisKey);
        return true;
    }

    public static void main(String[] args) {
        System.out.println(new CaptchaServiceImpl().generateCaptcha().getCaptchaBase64Image());
    }
}
