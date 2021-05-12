package com.example.csc105.account;

import com.example.csc105.DTO.LoginDTO;
import com.example.csc105.DTO.RegisterDTO;
import com.example.csc105.database.MySQLConnector;
import com.example.csc105.utils.JwtUtils;
import io.jsonwebtoken.JwtException;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class Register {
    @PostMapping(path = "/register")
    public Map<String, Object> _login(@RequestBody RegisterDTO user) {
        Map<String, Object> res = new HashMap<>();
        try {
            Connection connection = MySQLConnector.getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO User ( Email, Password,Username )\n" +
                    "VALUES (?, ?, ?)");
            pstm.setString(1, user.getEmail());
            pstm.setString(2, user.getPassword());
            pstm.setString(3, user.getUsername());
             pstm.execute();

            res.put("isSignup", true);
            res.put("text","Login successfully");
        } catch (Exception e) {
            res.put("isSignup", false);
            if (e instanceof SQLIntegrityConstraintViolationException) {
                if (e.getMessage().contains("PRIMARY")) {
                    res.put("text", "This username is already registered :(");
                } else if(e.getMessage().contains("User_Email_uindex")) {
                    res.put("text", "This email is already registered :(");
                }
            } else {
                e.printStackTrace();
                res.put("text", "MySQL error :(");
            }
        }
        return res;
    }
}
