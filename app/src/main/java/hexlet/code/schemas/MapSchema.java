package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<Map<String, ?>> {
    private Integer size = null;
    private Map<String, BaseSchema<?>> schemas = null;

    @Override
    public MapSchema required(){
        super.required();
        return this;
    }

    public MapSchema sizeof(int size) {
        this.size = size;
        return this;
    }

    // ИСПОЛЬЗУЕМ СЫРОЙ ТИП ЗДЕСЬ. Это примет ЛЮБУЮ мапу.
    @SuppressWarnings({"unchecked", "rawtypes"})
    public MapSchema shape(Map<String, ? extends BaseSchema> schemas) {
        this.schemas = (Map<String, BaseSchema<?>>) (Map) schemas;
        return this;
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected boolean isValidNonNull(Map<String, ?> value) {
        if (size != null && value.size() != size) {
            return false;
        }

        if (schemas != null) {
            for (Map.Entry<String, BaseSchema<?>> entry : schemas.entrySet()) {
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