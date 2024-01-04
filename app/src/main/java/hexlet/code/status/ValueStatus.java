package hexlet.code.status;

public final class ValueStatus {

    public ValueStatus(AcceptableStatusValue acceptableValue, Object prevValue, Object updatedValue) {
        this.status = acceptableValue;
        this.value1 = prevValue;
        this.value2 = updatedValue;

    }

    private final AcceptableStatusValue status;

    private final Object value1;

    private final Object value2;

    public AcceptableStatusValue getStatus() {
        return status;
    }

    public Object getValue1() {
        return value1;
    }

    public Object getValue2() {
        return value2;
    }
}
