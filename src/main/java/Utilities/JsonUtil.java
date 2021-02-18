package Utilities;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class JsonUtil {
    // Object Mapper kulanılarak De-SErialization yapma metodu

    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    // Metodumuzu olusturucaz-- Json Data sını  Java Object sine donusturucek.

    public static <T> T convertJsonToJava(String json, Class<T> cls) {
        T javaResult = null;

        try {
            javaResult = mapper.readValue(json, cls);
        } catch (IOException e) {
            System.err.println("Json Datasını Java'ya donusturemedi" + e.getMessage());
        }
        return javaResult;

    }
}


