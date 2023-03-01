package model.element.value;

public class LiteralElement implements ValueElement {

    private final int value;

    public LiteralElement(final int value) {
        this.value = value;
    }


    @Override
    public int getValue() {
        return value;
    }

}
