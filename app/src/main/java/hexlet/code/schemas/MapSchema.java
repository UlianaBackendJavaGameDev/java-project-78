package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<Map<String, ?>> {
    private Integer size = null;

    // Убираем <?> здесь, чтобы тесты могли передать сюда Map<String, BaseSchema>
    private Map<String, BaseSchema> schemas = null;

    @Override
    public MapSchema required(){
        super.required();
        return this;
    }

    public MapSchema sizeof(int size) {
        this.size = size;
        return this;
    }

    // Убираем <?> здесь
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

                // Сырой тип, чтобы метод isValid принимал Object
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