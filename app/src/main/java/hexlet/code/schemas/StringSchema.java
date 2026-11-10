package hexlet.code.schemas;




public class StringSchema extends BaseSchema<String> {
    private int minLength = 0;
    private String contains = null;
@Override
    public StringSchema required() {
        super.required = true; // Исправлено!
        return this;
    }

    @Override
    public boolean isValue(Object value) {
        return false;
    }

    public StringSchema minLength(int length) {
        this.minLength = length;
        return this;
    }

    public StringSchema contains(String substring) {
        this.contains = substring;
        return this;
    }

    @Override
    public boolean isValid(Object value) {
        // 1. Проверка на null
        if (value == null) {
            return !required;
        }

        // 2. Проверка, что это строка
        if (!(value instanceof String)) {
            return false;
        }
        String str = (String) value;

        // 3. Проверка на пустую строку
        if (str.isEmpty()) {
            return !required;
        }

        // 4. Проверка минимальной длины
        if (minLength > 0 && str.length() < minLength) {
            return false;
        }

        // 5. Проверка contains
        if (contains != null && !str.contains(contains)) {
            return false;
        }

        return true;
    }
}