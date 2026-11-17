package hexlet.code.schemas;

public class StringSchema extends BaseSchema<String>{
    private int minLength  = 0;
    private String contains = null;

    @Override
    public StringSchema required(){
        super.required();
        return this;
    }

    public StringSchema minLength(int length) {
        this.minLength = length;
        return this;
    }
    public StringSchema contains(String substring){
        this.contains = substring;
        return this;
    }
    @Override
    protected boolean isValidNonNull(String value){
        if(required && value.isEmpty()){
            return false;
        }
        if(minLength > 0 && value.length() < minLength){
            return false;
        }
        if(contains != null && !value.contains(contains)){
            return false;
        }
        return true;
    }
}