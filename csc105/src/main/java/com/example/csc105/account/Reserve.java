package com.example.csc105.account;

import com.example.csc105.DTO.FavouriteDTO;
import com.example.csc105.DTO.ReserveDTO;
import com.example.csc105.database.MySQLConnector;
import com.example.csc105.utils.JwtUtils;
import io.jsonwebtoken.JwtException;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class Reserve {
    @PostMapping(path = "/reserve")
    public Map<String, Object> Reserve(@RequestBody ReserveDTO reserve, @CookieValue String token) {
        Map<String, Object> res = new HashMap<>();
        try {
            String username = JwtUtils.parseToken(token);
            Connection connection = MySQLConnector.getConnection();
            PreparedStatement check = connection.prepareStatement("SELECT Username FROM User WHERE Username = ?");
            check.setString(1, username);
            ResultSet rs = check.executeQuery();
            if (rs.next()) {
                PreparedStatement pstm = connection.prepareStatement("INSERT INTO Reserve (Reserve_ID ,Place_ID, Username )\n" +
                        "VALUES (?, ?,?)") ;
                pstm.setInt(1, reserve.getReserveId());
                pstm.setInt(2, reserve.getPlaceId());
                pstm.setString(3, username);
                pstm.execute();
                res.put("Text", "success");
                res.put("success", true);
                return res;
            }
            res.put("success", false);
            res.put("text", "something wrong");
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


