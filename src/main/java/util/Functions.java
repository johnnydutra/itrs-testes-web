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

    public static String removeSubstring(String str, String subStr) {
        str = str.replace(subStr, "");
        return str;
    }
}
