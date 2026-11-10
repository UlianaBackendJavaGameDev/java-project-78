package hexlet.code;

import hexlet.code.Validator;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringSchemaTest {

    @Test
    void testStringSchemaWithoutRequired() {
        Validator v = new Validator();
        StringSchema schema = v.string();

        assertTrue(schema.isValid(""));
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid("some value"));
    }

    @Test
    void testStringSchemaWithRequired() {
        Validator v = new Validator();
        StringSchema schema = v.string().required();

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));
        assertTrue(schema.isValid("hexlet"));
    }

    @Test
    void testStringSchemaWithMinLength() {
        Validator v = new Validator();
        StringSchema schema = v.string().minLength(5);

        assertTrue(schema.isValid("hexlet"));
        assertFalse(schema.isValid("code"));
    }

    @Test
    void testStringSchemaWithContains() {
        Validator v = new Validator();
        StringSchema schema = v.string().contains("what");

        assertTrue(schema.isValid("what does the fox say"));
        assertFalse(schema.isValid("where does the fox say"));
    }

    @Test
    void testStringSchemaChainAndReplacement() {
        Validator v = new Validator();

        StringSchema schema = v.string().required().contains("what");
        assertTrue(schema.isValid("what does the fox say"));

        schema.contains("whatthe");
        assertFalse(schema.isValid("what does the fox say"));

        StringSchema schema1 = v.string();
        assertTrue(schema1.minLength(10).minLength(4).isValid("Hexlet"));
    }
}