package com.example.csc105.account;

import com.example.csc105.DTO.PlaceDTO;
import com.example.csc105.DTO.RegisterDTO;
import com.example.csc105.database.MySQLConnector;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class Place {
    @GetMapping(path = "/places")
    public Map<String, Object> Place() {
        Map<String, Object> res = new HashMap<>();
        try {
            Connection connection = MySQLConnector.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Place " );
            ResultSet rs = pstm.executeQuery();
            ArrayList<Object> places = new ArrayList<>();
            while (rs.next()){
                Map<String, Object> list = new HashMap<>();
                list.put("Place_ID",rs.getInt("Place_ID"));
                list.put("Name",rs.getString("Name"));
                list.put("Location",rs.getString("Location"));
                list.put("Price",rs.getString("Price"));
                places.add(list);
            }

            res.put("places", places);
            res.put("isPlace", true);
            res.put("text","Add Place successfull");
        } catch (Exception e) {
            res.put("isPlace", false);

                e.printStackTrace();
                res.put("text", "MySQL error :(");

        }
        return res;
    }
}
