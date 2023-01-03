package com.trading.day.common.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

/************
 * @info : 이메일 전송을 위한 공통 컴포넌트
 * @name : EmailService
 * @date : 2022/12/02 5:48 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;

    /**
     * @info    : 지정된 주소로 이메일 전송 - (지원 거절 메시지)
     * @name    : sendMail
     * @date    : 2022/12/02 5:59 PM
     * @author  : SeokJun Kang(swings134@gmail.com)
     * @version : 1.0.0
     * @param   :
     * @return  :
     * @Description : 이메일주소, 내용, 제목이 필요함.
     */
    @Async
    public boolean sendMailReject(EmailDTO inDTO) throws Exception{
        boolean msg = false;

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(inDTO.getTargetMail());
        simpleMailMessage.setSubject("[트레이딩 데이] 지원하신 프로젝트에 대한 답변.");
        simpleMailMessage.setFrom("email.test.only134@gmail.com");
        simpleMailMessage.setText("귀하가 지원하신 글번호: " + inDTO.getBoardId() +" 에 대한 지원이 거절되었습니다.");

        try {
            javaMailSender.send(simpleMailMessage);
            msg = true;
        } catch (Exception e) {
            e.printStackTrace();
            return msg;
        }

        return msg;
    } //reject email


    /**
     * @info    : mime 타입으로 이메일 전송.
     * @name    : sendMailMime
     * @date    : 2022/12/04 12:53 AM
     * @author  : SeokJun Kang(swings134@gmail.com)
     * @version : 1.0.0
     * @param   :
     * @return  :
     * @Description :
     */
    @Async
    public boolean sendMailMime(EmailDTO inDTO) {
        boolean msg = false;

        MimeMessage message = javaMailSender.createMimeMessage();

        String info = "<h3 style='color:blue;'>트레이딩 데이 지원하신 프로젝트 게시물:" + inDTO.getBoardIdNo() +
                      " 에 대한 답변입니다.<h3/><br/>" + "<p>"+ inDTO.getContent() +"</p>";

        try {
            message.setSubject("[트레이딩 데이 지원서 답변] " + inDTO.getTitle()); // 제목
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(inDTO.getTargetMail(), "", "UTF-8")); // TO
            message.setText(info, "UTF-8", "html");
//            message.setContent(info, "text/html; charset=euc-kr"); // 위의 방법이 안될떄 사용.

            javaMailSender.send(message);
        }catch (Exception e){
            e.printStackTrace();
            log.warn("Mail Sending Error={}", "이메일을 발송 도중 문제가 발생했습니다!");
            return msg;
        }
//        catch (UnsupportedEncodingException e){
//            e.printStackTrace();
//            return msg;
//        }
        return msg = true;
    }// 지원 수락.


}//class
