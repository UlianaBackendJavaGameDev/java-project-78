package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<Map<String, ?>> {
    private Integer size = null;

    // Делаем поле обобщенным, чтобы хранить любые схемы
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

    // Используем дженерик <T> в методе, чтобы принять ЛЮБУЮ BaseSchema
    @SuppressWarnings("unchecked")
    public <T> MapSchema shape(Map<String, BaseSchema<T>> schemas) {
        this.schemas = (Map<String, BaseSchema<?>>) (Map<?, ?>) schemas;
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

                // Извлекаем как сырой тип, чтобы вызвать isValid
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