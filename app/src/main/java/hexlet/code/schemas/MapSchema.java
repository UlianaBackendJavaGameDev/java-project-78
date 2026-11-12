package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<Map<String, Object>, MapSchema> {
    private Integer size = null;
    private Map<String, BaseSchema> schemas = null; // Храним схемы для ключей

    public MapSchema sizeof(int size) {
        this.size = size;
        return this;
    }

    // Новый метод для вложенной валидации
    public MapSchema shape(Map<String, BaseSchema> schemas) {
        this.schemas = schemas;
        return this;
    }

    @Override
    protected boolean isValidNonNull(Map<String, Object> value) {
        // 1. Проверка размера (если задана)
        if (size != null && value.size() != size) {
            return false;
        }

        // 2. Проверка вложенных схем (если заданы)
        if (schemas != null) {
            for (Map.Entry<String, BaseSchema> entry : schemas.entrySet()) {
                String key = entry.getKey();
                BaseSchema schema = entry.getValue();
                Object fieldValue = value.get(key);

                // Если хотя бы одно поле не проходит валидацию — вся мапа невалидна
                if (!schema.isValid(fieldValue)) {
                    return false;
                }
            }
        }

        return true;
    }
}