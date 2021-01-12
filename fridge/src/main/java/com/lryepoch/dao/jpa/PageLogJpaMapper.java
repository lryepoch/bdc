package com.lryepoch.dao.jpa;

import com.lryepoch.config.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lryepoch
 * @date 2021/1/12 16:55
 * @description TODO 向数据库写入页面访问日志
 */
public interface PageLogJpaMapper extends JpaRepository<LogEntity, Integer> {
}
