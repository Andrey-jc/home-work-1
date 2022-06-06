package lesson1;

public enum UtilRegx {
    FIO("[А-я]+\\s+[А-я]+\\s+[А-я]*"), NUMBER("\\d+"), EMAIL("\\w*@\\w*.\\w*");

    /**
     * Регулярка для поиска совпадения
     */
    private String regx;

    UtilRegx(String regx) {
        this.regx = regx;
    }

    public String getRegx() {
        return regx;
    }
}
