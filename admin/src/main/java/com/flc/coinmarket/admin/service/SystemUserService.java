package com.flc.coinmarket.admin.service;

import com.flc.coinmarket.dao.mysql.mapper.system.SysUserMapper;
import com.flc.coinmarket.dao.mysql.model.system.SysUser;
import com.flc.coinmarket.dao.mysql.model.system.SysUserExample;
import com.flc.coinmarket.core.base.BaseResponse;
import com.flc.coinmarket.core.base.ResponseCode;
import com.flc.coinmarket.core.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 管理员登录
     * @param sysUser
     * @return
     */
    public BaseResponse<SysUser> login(SysUser sysUser) {
        BaseResponse<SysUser> baseResponse=new BaseResponse();
        SysUserExample example=new SysUserExample();
        example.createCriteria().andAccountEqualTo(sysUser.getAccount());
        List<SysUser> users = sysUserMapper.selectByExample(example);

        if(users.size()!=0&&!users.isEmpty()&& PasswordUtil.checkPassword(sysUser.getPassword(),users.get(0).getPassword())){
            baseResponse.setData(users.get(0));
            baseResponse.setResponseMsg(ResponseCode.OK.getMessage());
            baseResponse.setResponseCode(ResponseCode.OK.getCode());
            return baseResponse;
        }
        baseResponse.setResponseMsg(ResponseCode.USERNAME_OR_PWD_WRONG.getMessage());
        baseResponse.setResponseCode(ResponseCode.USERNAME_OR_PWD_WRONG.getCode());
        return baseResponse;
    }

    /**
     * 新增管理员
     * @param account
     * @param password
     * @return
     */
    public BaseResponse<SysUser> add(String account, String password) {
        BaseResponse<SysUser> response=new BaseResponse<>();
        SysUser sysUser=new SysUser();
        sysUser.setAccount(account);
        String encode= PasswordUtil.hashPassword(password);
        sysUser.setPassword(encode);
        sysUserMapper.insertSelective(sysUser);

        response.setResponseMsg(ResponseCode.OK.getMessage());
        response.setResponseCode(ResponseCode.OK.getCode());
        return response;
    }
}
