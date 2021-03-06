package com.hqu.modules.common.service;


import com.github.abel533.echarts.json.GsonOption;
import com.hqu.modules.basedata.specialitymanage.entity.SpecialityManage;
import com.hqu.modules.basedata.university.entity.University;
import com.hqu.modules.common.mapper.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 公共SERVICE方法
 * @author hzb
 * @version 2018-08-29
 */
@Service
@Transactional(readOnly = true)
public class CommonService {

    @Autowired
    private CommonMapper commonMapper;

    /**
     * 检查唯一性
     * @param tableName 表名
     * @param filedName 字段名
     * @param filedValue  字段的值
     * @return
     */
    public Boolean checkOnly(String tableName,String filedName,String filedValue){
        Map<String,Object> statement = new HashMap<>();
        statement.put("tableName",tableName);
        statement.put("filedName",filedName);
        statement.put("filedValue","'"+filedValue+"'");

        return Integer.parseInt(commonMapper.checkOnly(statement))>0;
    }

/*    //////////////////////////////////////wy*/
    public Boolean checkOnlyt(String tableName, String filedName1, String filedName2, University filedValue1, SpecialityManage filedValue2){
        Map<String,Object> statement = new HashMap<>();
        statement.put("tableName",tableName);
        statement.put("filedName1",filedName1);
        statement.put("filedValue1","'"+filedValue1+"'");
        statement.put("filedName2",filedName2);
        statement.put("filedValue2","'"+filedValue2+"'");

        return Integer.parseInt(commonMapper.checkOnly(statement))>0;
    }

    /**
     * 获取一段时间内某字段的记录数
     * @param option
     * @param beginDate
     * @param endDate
     * @param tableName 表名
     * @param filedName 要筛选的字段名
     * @return
     */
    public int getNumByDate(String beginDate, String endDate, String tableName, String filedName){
        Map<String,Object> objectMap = new HashMap<>();
        objectMap.put("tableName",tableName);
        objectMap.put("filedName",filedName);
        objectMap.put("beginDate","'"+beginDate+"'");
        objectMap.put("endDate","'"+endDate+"'");

        int number = commonMapper.getNumByDate(objectMap);
        return number;
    }
}
