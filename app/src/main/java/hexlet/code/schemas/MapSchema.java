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

  // Сырой тип BaseSchema в параметре - это ключ к успеху
  public MapSchema shape(Map<String, BaseSchema> schemas) {
    addCheck(
        "shape",
        value -> {
          for (Map.Entry<String, BaseSchema> entry : schemas.entrySet()) {
            String key = entry.getKey();

            // Здесь тоже сырой тип, чтобы компилятор не ругался на Object
            BaseSchema schema = entry.getValue();

            // Теперь передача Object в isValid пройдет без ошибок
            if (!schema.isValid(value.get(key))) {
              return false;
            }
          }
          return true;
        });
    return this;
  }
}
