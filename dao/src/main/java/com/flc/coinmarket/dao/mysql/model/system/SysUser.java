package com.flc.coinmarket.dao.mysql.model.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel("系统用户信息")
public class SysUser implements Serializable {

    @ApiModelProperty(name = "id", value = "ID", example = "1")
    private Integer id;

    @ApiModelProperty(name = "username", value = "用户名称", example = "1")
    private String username;

    @ApiModelProperty(name = "password", value = "登陆密码", example = "1")
    private String password;

    @ApiModelProperty(name = "phoneNo", value = "电话号码", example = "1")
    private String phoneNo;

    @ApiModelProperty(name = "account", value = "登陆账号", example = "1")
    private String account;

    @ApiModelProperty(name = "createTime", value = "创建时间", example = "1")
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo == null ? null : phoneNo.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}