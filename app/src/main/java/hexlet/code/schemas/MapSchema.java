package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<Map<String, ?>> {
    private Integer size = null;
    // Используем wildcard для хранения
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

    // Принимаем любую BaseSchema<?> (и BaseSchema<String>, и сырой BaseSchema)
    @SuppressWarnings("unchecked")
    public MapSchema shape(Map<String, ? extends BaseSchema<?>> schemas) {
        // Приводим к нужному типу для сохранения в поле
        this.schemas = (Map<String, BaseSchema<?>>) schemas;
        return this;
    }

    @Override
    protected boolean isValidNonNull(Map<String, ?> value) {
        if (size != null && value.size() != size) {
            return false;
        }

        if (schemas != null) {
            for (Map.Entry<String, BaseSchema<?>> entry : schemas.entrySet()) {
                String key = entry.getKey();

                // Используем сырой BaseSchema для передачи Object в isValid
                @SuppressWarnings("rawtypes")
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