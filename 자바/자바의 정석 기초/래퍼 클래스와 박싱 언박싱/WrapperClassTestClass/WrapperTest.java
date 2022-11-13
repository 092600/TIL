package WrapperClassTestClass;

public class WrapperTest {
    public static void main(String[] args){
        int primitiveIntValue = 3;
        float primitiveFloatValue = 3.14f;

        Integer wrappedIntValue = new Integer(primitiveIntValue);
        Float wrappedFloatValue = new Float(primitiveFloatValue);

        System.out.println("wrappedIntValue : " + wrappedIntValue);
        System.out.println("wrappedFloatValue : " + wrappedFloatValue);

        int returnToPrimitiveInt = wrappedIntValue.intValue();
        float returnToPrimitiveFloat = wrappedFloatValue.floatValue();

        System.out.println("returnToPrimitiveInt : " + returnToPrimitiveInt);
        System.out.println("returnToPrimitiveFloat : " + returnToPrimitiveFloat);

    }
}
