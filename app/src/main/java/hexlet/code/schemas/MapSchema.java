package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<Map<String, Object>, MapSchema> {
    private Integer size = null;

    public MapSchema sizeof(int size) {
        this.size = size;
        return this;
    }

    @Override
    protected boolean isValidNonNull(Map<String, Object> value) {
        if (size != null && value.size() != size) {
            return false;
        }
        return true;
    }
}