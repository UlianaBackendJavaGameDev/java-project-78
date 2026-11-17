package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<Map<String, ?>> {
    private Integer size = null;

    // Оставляем сырой тип для хранения
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

    // Используем <? super BaseSchema>, чтобы принимать и сырые, и параметризованные типы
    @SuppressWarnings("unchecked")
    public MapSchema shape(Map<String, ? extends BaseSchema<?>> schemas) {
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

                // Используем wildcard, чтобы Java не ругалась на Object в методе isValid
                BaseSchema<?> schema = entry.getValue();
                Object fieldValue = value.get(key);

                // Если Java продолжит ругаться на типы (Object), используем сырой тип
                if (!isValidField(schema, fieldValue)) {
                    return false;
                }
            }
        }
        return true;
    }

    // Вспомогательный метод с сырым типом, чтобы обойти проверку типов
    @SuppressWarnings({"unchecked", "rawtypes"})
    private boolean isValidField(BaseSchema schema, Object value) {
        return schema.isValid(value);
    }
}