package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<Map<String, ?>> {

    public MapSchema required() {
        this.required = true;
        return this;
    }

    public MapSchema sizeof(int size) {
        addCheck("sizeof", value -> value.size() == size);
        return this;
    }

    // Теперь shape принимает мапу без жестких дженерик-ограничений!
    public MapSchema shape(Map<String, BaseSchema<?>> schemas) {
        addCheck("shape", value -> {
            for (Map.Entry<String, BaseSchema<?>> entry : schemas.entrySet()) {
                String key = entry.getKey();
                BaseSchema<?> schema = entry.getValue();
                if (!schema.isValid(value.get(key))) {
                    return false;
                }
            }
            return true;
        });
        return this;
    }
}