package tstore.utils;

import junit.framework.TestCase;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mipan on 24.10.2016.
 */

public class JsonParserTest {
    private static final String JSON_VALID_ARRAY = "{\"basket\":[{\"product\":\"4\",\"count\":\"2\"},{\"product\":\"3\",\"count\":\"5\"}]}";
    private static final String JSON_VALID_ONE_VALUE = "{\"basket\":[{\"product\":\"4\",\"count\":\"15\"}]}";
    private static final String JSON_INVALID_NUMBER_IN_PRODUCT = "{\"basket\":[{\"product\":\"wrong\",\"count\":\"2\"},{\"product\":\"3\",\"count\":\"5\"}]}";
    private static final String JSON_INVALID_NUMBER_IN_COUNT = "{\"basket\":[{\"product\":\"5\",\"count\":\"#$\"},{\"product\":\"3\",\"count\":\"5\"}]}";
    private static final String JSON_ARRAY_WITHOUT_ROOT = "{{\"product\":\"5\",\"count\":\"#$\"},{\"product\":\"3\",\"count\":\"5\"}}";

    private HttpServletRequest request = null;
    @Test
    public void jsonArrayValidValue() throws ParseException {
        Map<Integer, Integer> resultParsMap = new HashMap<Integer, Integer>();
        resultParsMap.put(4, 2);
        resultParsMap.put(3, 5);

        assertEquals(resultParsMap, JsonParser.getBasket(JSON_VALID_ARRAY, request));

    }@Test
    public void jsonSingleValidValue() throws ParseException {
        Map<Integer, Integer> resultParsMap = new HashMap<Integer, Integer>();
        resultParsMap.put(4, 15);

        assertEquals(resultParsMap, JsonParser.getBasket(JSON_VALID_ONE_VALUE, request));
    }

    @Test(expected = ParseException.class )
    public void jsonParsRootException() throws ParseException {
        Map<Integer, Integer> resultParsMap = new HashMap<Integer, Integer>();
        JsonParser.getBasket(JSON_ARRAY_WITHOUT_ROOT, request);
    }
    @Test(expected = NumberFormatException.class )
    public void jsonParsNumberFormatExceptionForProduct() throws ParseException {
        Map<Integer, Integer> resultParsMap = new HashMap<Integer, Integer>();
        JsonParser.getBasket(JSON_INVALID_NUMBER_IN_PRODUCT, request);
    }
    @Test(expected = NumberFormatException.class )
    public void jsonParsNumberFormatExceptionForCount() throws ParseException {
        Map<Integer, Integer> resultParsMap = new HashMap<Integer, Integer>();
        JsonParser.getBasket(JSON_INVALID_NUMBER_IN_COUNT, request);
    }
}
