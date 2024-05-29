package com.example.ebookstorebackend.dao;

import com.example.ebookstorebackend.entity.UserAuthEntity;
import com.example.ebookstorebackend.entity.UserEntity;
import com.example.ebookstorebackend.exception.UserNotFoundException;
import com.example.ebookstorebackend.repo.UserAuthRepo;
import com.example.ebookstorebackend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    // TODO: 对于Privacy实体，进行哈希加密处理
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserAuthRepo userAuthRepo;

    @Override
    public boolean isUserExist(String username) {
        return userRepo.findByUsername(username).orElse(null) != null;
    }

    @Override
    public boolean isVerified(String username, String password) {
        // FIXME
        return userAuthRepo.existsByUsernameAndPassword(username, password);
    }

    @Override
    public boolean isAdmin(String username) {
        var user = userRepo.findByUsername(username);
        return user.isPresent() && user.get().isAdministrator();
    }

    @Override
    public boolean isUser(String username) {
        var user = userRepo.findByUsername(username);
        return user.isPresent() && user.get().isUser();
    }


    public void addUser(String username, String password, UserEntity.Role role) {
        // TODO: 强hash加密,非明文密码
        UserEntity newUser = new UserEntity();
        newUser.setUsername(username);
        newUser.setRole(role);
        UserAuthEntity newUserAuth = new UserAuthEntity();
        newUserAuth.setUsername(username);
        newUserAuth.setPassword(password);
        newUserAuth.setUserEntity(newUser);
        userAuthRepo.save(newUserAuth);  // 级联保存user
    }

    @Override
    public boolean addUser(UserAuthEntity userAuthEntity) {
        if (userRepo.findByUsername(userAuthEntity.getUsername()).isPresent()) {
            return false;
        }
        userAuthRepo.save(userAuthEntity);
        return true;
    }

    @Override
    public UserEntity getUser(String username) {
        try {
            return userRepo.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void removeUser(String username) {
        userRepo.delete(userRepo.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username)));
    }

    @Override
    public void updateUser(UserEntity newUser, String username) {
        userRepo.findByUsername(username).map(userPublicEntity -> {
            userPublicEntity = newUser;
            return userRepo.save(userPublicEntity);
        }).orElseThrow(() -> new UserNotFoundException(username));
    }

    @Override
    public void setRole(String username, String role) {
        userRepo.findByUsername(username).map(userPublicEntity -> {
            userPublicEntity.setRole(UserEntity.Role.valueOf(role));
            return userRepo.save(userPublicEntity);
        }).orElseThrow(() -> new UserNotFoundException(username));
    }

    @Override
    public void changePassword(String username, String oldpassword, String password) throws Exception {
        boolean verified = isVerified(username, oldpassword);
        if (verified) {
            userAuthRepo.updatePasswordByUserName(username, oldpassword, password);
        } else {
            throw new Exception("Password is not correct.");
        }
    }

    @Override
    public List<UserEntity> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public List<UserEntity> searchUsers(String keyword) {
        return userRepo.findByUsernameContaining(keyword);
    }

    @Override
    public boolean changeUserStatus(String username, UserEntity.status status) {
        try {
            UserEntity userEntity = userRepo.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
            userEntity.setStatus(status);
            userRepo.save(userEntity);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public UserEntity getUser(Long id) {
        try {
            return userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id.toString()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
