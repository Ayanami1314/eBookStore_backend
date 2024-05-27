package com.example.ebookstorebackend.repo;

import com.example.ebookstorebackend.entity.UserAuthEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserAuthRepo extends CrudRepository<UserAuthEntity, Long> {
    // HINT: 使用内置查询避免将隐私实体通过网络传输，只送回True/False, 不允许有方法返回UserAuthEntity
    boolean existsByUsernameAndPassword(String username, String password);


    @Modifying
    @Query("UPDATE UserAuthEntity u SET u.password = ?3 WHERE u.username = ?1 AND u.password = ?2")
    void updatePasswordByUserName(String username, String oldpassword, String password);

    void deleteUserByUsername(String username);

    void deleteUserById(Long id);

    @Modifying
    @Query("UPDATE UserAuthEntity u SET u.username = ?2 WHERE u.id = ?1")
    void updateUserNameById(Long id, String username);

}
