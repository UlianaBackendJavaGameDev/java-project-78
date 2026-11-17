package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<Map<String, ?>> {
    private Integer size = null;

    // Оставляем <?> здесь
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

    public MapSchema shape(Map<String, BaseSchema<?>> schemas) {
        this.schemas = schemas;
        return this;
    }

    @Override
    protected boolean isValidNonNull(Map<String, ?> value) {
        if (size != null && value.size() != size) {
            return false;
        }

        if (schemas != null) {
            // Убираем <?> из цикла, используем сырой тип BaseSchema
            for (Map.Entry<String, BaseSchema<?>> entry : schemas.entrySet()) {
                String key = entry.getKey();

                // Если IDEA подчеркивает BaseSchema, просто добавь подавление предупреждения
                // или оставь как есть — на компиляцию это не влияет
                @SuppressWarnings("rawtypes")
                BaseSchema schema = entry.getValue();

                Object fieldValue = value.get(key);

                // Теперь сюда можно передать Object без ошибки
                if (!schema.isValid(fieldValue)) {
                    return false;
                }
            }
        }
        return true;
    }
}