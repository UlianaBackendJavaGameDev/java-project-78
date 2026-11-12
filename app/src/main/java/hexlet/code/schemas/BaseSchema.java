package hexlet.code.schemas;

public abstract class BaseSchema<T, S extends BaseSchema<T, S>> {
    protected boolean required = false;

    @SuppressWarnings("unchecked")
    public S required() {
        this.required = true;
        return (S) this; // Возвращаем правильный тип наследника
    }

    public boolean isValid(T value) {
        if (value == null) {
            return !required;
        }
        return isValidNonNull(value);
    }

    protected abstract boolean isValidNonNull(T value);
}