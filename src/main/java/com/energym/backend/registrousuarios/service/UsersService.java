package com.energym.backend.registrousuarios.service;

import com.energym.backend.registrousuarios.model.Users;
import com.energym.backend.registrousuarios.repository.UsersRepository;
import com.energym.backend.util.exception.EncryptException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

@Service
public class UsersService {

    private static String ENCRYPT_KEY = "energym-energym-";

    private final UsersRepository repository;

    public UsersService(UsersRepository repository) {
        this.repository = repository;
    }

    public String encrypt(String password) throws EncryptException {
        try {
            Key aesKey = new SecretKeySpec(ENCRYPT_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(password.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        }catch(Exception ex){
            throw new EncryptException(ex.getMessage());
        }
    }

    public Users newUser(Users user){
        try{
            user.setPassword(encrypt(user.getPassword()));
        }catch (Exception ex){
            return null;
        }
        return repository.save(user);
    }

    public Users findUserbyEmail(String email) {
        Users found = repository.findUserByEmail(email);
        return found;
    }

    public Optional<Users> findUserById(Integer id){
        return repository.findById(id);
    }

    public List<Users> getUsers(){
        return (List<Users>) repository.findAll();
    }

    public Users login(String email, String password) throws Exception {
        password = encrypt(password);
        Users user = repository.findUserByEmailAndPassword(email, password);
        if (user == null){
            throw new Exception("Usuario no encontrado");
        }
        return user;
    }

    public Users updateUser(Users u){
        repository.updateUser(u.getId(), u.getName(), u.getEmail(), u.getTelephone(), u.getRole());
        return repository.findById(u.getId()).get();
    }

    public Users updatePassword(Integer id, String password){
        try{
            password = encrypt(password);
        }catch (Exception ex){
            return null;
        }
        repository.updatePassword(id, password);
        return repository.findById(id).get();
    }
}
