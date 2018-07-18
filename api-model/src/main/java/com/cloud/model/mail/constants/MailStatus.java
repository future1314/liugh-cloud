package com.cloud.model.mail.constants;

/**
 * 邮件状态
 * @author liugh 53182347@qq.com
 */
public interface MailStatus {
    /**
     * 草稿
     */
    int DRAFT = 0;
    /**
     * 发送成功
     */
    int SUCCESS = 1;
    /**
     * 发送失败
     */
    int ERROR = 2;
}
