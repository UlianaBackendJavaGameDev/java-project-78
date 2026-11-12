package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<Map<String, ?>, MapSchema> {
    private Integer size = null;
    private Map<String, BaseSchema> schemas = null;

    public MapSchema sizeof(int size) {
        this.size = size;
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema> schemas) {
        this.schemas = schemas;
        return this;
    }

    @Override
    protected boolean isValidNonNull(Map<String, ?> value) {
        if (size != null && value.size() != size) {
            return false;
        }

        if (schemas != null) {
            for (Map.Entry<String, BaseSchema> entry : schemas.entrySet()) {
                String key = entry.getKey();
                BaseSchema schema = entry.getValue();
                Object fieldValue = value.get(key);

                if (!schema.isValid(fieldValue)) {
                    return false;
                }
            }
        }
        return true;
    }
}