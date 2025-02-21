package com.eci.Calculator.Server;

import com.eci.Calculator.Rest.Request;
import com.eci.Calculator.Rest.Response;
import java.util.List;

public class ReflexCalculator {

    public static List calculate(List list) {
        return list;
    }

    public static String calcular(Request req, Response resp) {
        // String ListStr = req.getValues("list");
        // if (ListStr != null) {
        //    try {
        //        List list = Double.parseDouble(list);
        //        List result = calculate(list);
        //        resp.setBody("{\"List\": " + list + ", \"result\": " + result + "}");
        //               return resp.getBody();
        //         } catch (NumberFormatException e) {
        //             resp.setBody("Error.");
        //             return resp.getBody();
        //         } catch (IllegalArgumentException e) {
        //             resp.setBody("Error: " + e.getMessage());
        //             return resp.getBody();
        //         }
        //     }
        return null;
    }
}
