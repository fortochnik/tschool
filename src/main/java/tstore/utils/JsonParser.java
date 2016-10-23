package tstore.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tstore.model.OrderEntity;
import tstore.model.ProductListEntity;
import tstore.service.impl.OrderServiceImpl;
import tstore.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by mipan on 17.10.2016.
 */
public class JsonParser {

    public static Map<Integer, Integer> getBasket(String json, HttpServletRequest request) throws ParseException {
        Map<Integer, Integer> mapJson =null;

            JSONParser parser = new JSONParser();
            JSONArray list = new JSONArray();

            Object obj = parser.parse(json);

            JSONObject jsonObject = (JSONObject) obj;
            JSONArray basket = (JSONArray) jsonObject.get("basket");
            Iterator i = basket.iterator();

            mapJson= new HashMap<Integer, Integer>();
            while (i.hasNext()) {
                JSONObject slide = (JSONObject) i.next();
                Integer product = Integer.valueOf((String)slide.get("product"));
                Integer count = Integer.valueOf((String)slide.get("count"));
                mapJson.put(product, count);
            }

        return mapJson;
    }
}
