package com.example.csc105.account;

import com.example.csc105.DTO.LoginDTO;
import com.example.csc105.database.MySQLConnector;
import com.example.csc105.utils.JwtUtils;
import io.jsonwebtoken.JwtException;
import org.springframework.web.bind.annotation.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
// import libraries
@RestController
@RequestMapping("/auth")
public class Login {
    @PostMapping(path = "/login")
    public Map<String, Object> _login(@RequestBody LoginDTO user) {
        Map<String, Object> res = new HashMap<>();
        try {
            Connection connection = MySQLConnector.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM User WHERE  Email = ? AND Password = ?");
            pstm.setString(1, user.getEmail());
            pstm.setString(2, user.getPassword());
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                String token = JwtUtils.generateToken(rs.getString("Username") + "");
                res.put("token", token);

                Map<String, Object> userDetail = new HashMap<>();
                Map<String, Object> bgColor = new HashMap<>();

                userDetail.put("Username", rs.getString("Username"));




                res.put("isLogin", true);
                res.put("userDetail", userDetail);
                res.put("text", "login successfully :)");
            } else {
                res.put("isLogin", false);
                res.put("text", "login fail :(");
            }
        } catch (SQLException e) {
            res.put("success", false);
            res.put("text", "Something Wrong :(");
            e.printStackTrace();
        } catch (JwtException e) {
            e.printStackTrace();
            res.put("success", false);
            res.put("text", "Token is incorrect :(");
        }
        return res;
    }
}
