package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MapSchemaTest {

    @Test
    public void testMapSchema() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        assertTrue(schema.isValid(null));

        schema.required();
        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(new HashMap<>()));

        Map<String, Object> data = new HashMap<>(); // Явно указываем Object
        data.put("key1", "value1");
        assertTrue(schema.isValid(data));

        schema.sizeof(2);
        assertFalse(schema.isValid(data));

        data.put("key2", "value2");
        assertTrue(schema.isValid(data));
    }
    @Test
    public void testMapSchemaShape() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        // Создаем схемы для проверки
        Map<String, BaseSchema> schemas = new HashMap<>();
        schemas.put("firstName", v.string().required());
        schemas.put("lastName", v.string().required().minLength(2));

        schema.shape(schemas);

        // Валидный объект
        Map<String, Object> human1 = new HashMap<>();
        human1.put("firstName", "John");
        human1.put("lastName", "Smith");
        assertTrue(schema.isValid(human1));

        // Невалидный (lastName = null)
        Map<String, Object> human2 = new HashMap<>();
        human2.put("firstName", "John");
        human2.put("lastName", null);
        assertFalse(schema.isValid(human2));

        // Невалидный (lastName слишком короткий)
        Map<String, Object> human3 = new HashMap<>();
        human3.put("firstName", "Anna");
        human3.put("lastName", "B");
        assertFalse(schema.isValid(human3));
    }
}