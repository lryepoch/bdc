package com.lryepoch.config;

import com.lryepoch.dao.ReminderMapper;
import com.lryepoch.entity.product.ProductReminder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author lryepoch
 * @date 2020/11/11 8:46
 * @description TODO 提醒缓存
 */
@Component
public class ReminderCache {
    @Autowired
    private ReminderMapper reminderMapper;

    public static ConcurrentHashMap<String, HashSet<Integer>> map = new ConcurrentHashMap<>();

    @PostConstruct
    private void init(){
        map.put("reminderSet", reminderMapper.getReminder().stream().map(ProductReminder::getPid).collect(Collectors.toCollection(HashSet::new)));
    }

    public static void removeId(String userName, Integer id){
        Set<Integer> set = map.get(userName);
        if (set!=null){
            if (set.size()>0){
                set.remove(id);
            }
            if (set.size()==0){
                map.remove(userName);
            }
        }
    }
}
