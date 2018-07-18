package com.cloud.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 微信appid和secret对象
 * @author liugh 53182347@qq.com
 */
@Getter
@Setter
@ToString
public class WechatInfo implements Serializable {

    private static final long serialVersionUID = 3511834512371404079L;

    private String appid;
    private String secret;
}
