package com.dacs2_be.service;

import com.dacs2_be.dto.MailDTO;
import jakarta.mail.MessagingException;

public interface MailService {
    /**
     * Gửi email
     *
     * @param mail thông tin email
     * @throws MessagingException lỗi gửi email
     */
    void send(MailDTO mail) throws MessagingException;

    /**
     * Gửi email đơn giản
     *
     * @param to      email người nhận
     * @param subject tiêu đề email
     * @param body    nội dung email
     * @throws MessagingException lỗi gửi email
     */
    void send(String to, String subject, String body) throws MessagingException;

    /**
     * xếp mail vào hàng đợi
     *
     * @param mail thông tin email
     */
    void queue(MailDTO mail);

    /**
     * Tạo MailInfo và xếp vào hàng đợi
     *
     * @param to      email người nhận
     * @param subject tiêu đề email
     * @param body    nội dung email
     */
    void queue(String to, String subject, String body);

}