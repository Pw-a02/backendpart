package com.example.csc105.account;

import com.example.csc105.DTO.PlaceDTO;
import com.example.csc105.DTO.RegisterDTO;
import com.example.csc105.database.MySQLConnector;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class Place {
    @PostMapping(path = "/place")
    public Map<String, Object> Place(@RequestBody PlaceDTO place) {
        Map<String, Object> res = new HashMap<>();
        try {
            Connection connection = MySQLConnector.getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO Place ( Place_ID, Name,Location,Price )\n" +
                    "VALUES (?, ?, ?,?)");
            pstm.setInt(1, place.getPlaceID());
            pstm.setString(2, place.getName());
            pstm.setString(3, place.getLocation());
            pstm.setDouble(4, place.getPrice());
            pstm.execute();

            res.put("isAddPlace", true);
            res.put("text","Add Place successfull");
        } catch (Exception e) {
            res.put("isAddPlace", false);
            if (e instanceof SQLIntegrityConstraintViolationException) {
                if (e.getMessage().contains("PRIMARY")) {
                    res.put("text", "This place is already add :(");
                } else if(e.getMessage().contains("User_Email_uindex")) {
                    res.put("text", "This place is already add :(");
                }
            } else {
                e.printStackTrace();
                res.put("text", "MySQL error :(");
            }
        }
        return res;
    }
}
