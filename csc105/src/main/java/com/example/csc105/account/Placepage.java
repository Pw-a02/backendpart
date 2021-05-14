package com.example.csc105.account;


import com.example.csc105.database.MySQLConnector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class Placepage {
    @GetMapping (path = "/Placepage/{placeId}")
    public Map<String, Object> Placepage(@PathVariable int placeId) {
        Map<String, Object> res = new HashMap<>();

        try {
            Connection connection = MySQLConnector.getConnection();
            PreparedStatement pstm = connection.prepareStatement(
                    "SELECT * FROM Place WHERE Place_ID = ?");
            pstm.setInt(1, placeId);



            ResultSet rs = pstm.executeQuery();


            if(rs.next()) {
                Map<String, Object> place = new HashMap<>();


                place.put("location", rs.getString("location"));
                place.put("price", rs.getString("price"));
                place.put("Name", rs.getString("Name"));
                res.put("place", place);
            }




            res.put("success", true);
        } catch (SQLException e) {
            res.put("success", false);
            e.printStackTrace();
        }
        return res;
    }
}

