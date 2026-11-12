package hexlet.code.schemas;

public abstract class BaseSchema<T> {
    protected boolean required = false;

    public BaseSchema<T> required() {
        this.required = true;
        return this;
    }

    public boolean isValid(T value) {
        if (value == null) {
            return !required;
        }
        return isValidNonNull(value);
    }

    protected abstract boolean isValidNonNull(T value);
}