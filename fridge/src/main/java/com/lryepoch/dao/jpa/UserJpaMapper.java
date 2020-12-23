package com.lryepoch.dao.jpa;

import com.lryepoch.entity.usermanage.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * @author lryepoch
 * @date 2020/9/29 16:01
 * @description TODO
 */
public interface UserJpaMapper extends JpaRepository<User, Integer> {

    User findUserByAccountAndDeleted(Integer account, int i);

    List<User> findAllByDeleted(int i);
}
