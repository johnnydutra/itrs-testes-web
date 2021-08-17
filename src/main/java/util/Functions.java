package util;

public class Functions {

    public static Double removeCurrencySignAndCastToDouble(String text) {
        text = text.replace("$", "");
        return Double.parseDouble(text);
    }

    public static int removeItemsStringAndCastToInt(String text) {
        text = text.replace(" items", "");
        return Integer.parseInt(text);
    }
}
