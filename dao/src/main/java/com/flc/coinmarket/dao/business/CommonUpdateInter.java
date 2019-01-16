package com.flc.coinmarket.dao.business;

import com.flc.coinmarket.dao.mysql.mapper.system.SysDictionaryMapper;
import com.flc.coinmarket.dao.mysql.model.system.SysDictionary;
import com.flc.coinmarket.dao.mysql.model.system.SysDictionaryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CommonUpdateInter {
    @Autowired
    private SysDictionaryMapper sysDictionaryMapper;
    /**
     * 更新内部账余额
     */
    public  synchronized BigDecimal updateInterBalance(BigDecimal changeAmt) throws RuntimeException {
        //查询内部账余额
        SysDictionaryExample sysDictionaryExample = new SysDictionaryExample();
        sysDictionaryExample.createCriteria().andDicCodeEqualTo("inter_balance");
        List<SysDictionary> sysDictionaries = sysDictionaryMapper.selectByExample(sysDictionaryExample);
        if (sysDictionaries.size() == 0 || sysDictionaries.get(0) == null) {
            throw new RuntimeException("内部账余额数据不存在");
        }
        SysDictionary sysDictionary = sysDictionaries.get(0);
        String dicValue = sysDictionary.getDicValue();
        BigDecimal interBalance = new BigDecimal(Double.parseDouble(dicValue));
        //更新内部账余额
        BigDecimal newInterBalance = BigDecimal.ZERO;
        newInterBalance = interBalance.add(changeAmt).setScale(8,BigDecimal.ROUND_HALF_UP);
        sysDictionary.setDicValue(newInterBalance + "");
        sysDictionaryMapper.updateByPrimaryKey(sysDictionary);
        //返回更新后余额
        return newInterBalance.setScale(8,BigDecimal.ROUND_HALF_UP);
    }


}
