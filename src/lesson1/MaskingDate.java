package lesson1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс для маскирования персональных данных
 */
public class MaskingDate {

    /**
     * Номер телефеона пользователя исходном виде
     */
    private String number;

    /**
     * Фио пользователя в исходном виде
     */
    private String fio;

    /**
     * Почта пользователя в исходном виде
     */
    private String email;

    private final String TAG_DATA = "<data>";
    private final String END_MESSAGE = "</data></client>";

    /**
     * Обработка полученых данных
     * @param data данные для обработки
     */
    public void input(String data) {
        String startJson = Arrays.stream(data.split(TAG_DATA)).findFirst().get();
        data = data.replaceAll("^[A-Z0-9+_.-]*\\W\\w*\\W\\w*.\\w*\\W\\w*\\W*\\w*>", "");
        data = data.replaceAll(END_MESSAGE, "");
        List<String> userData = Arrays.asList(data.split(";"));
        List<String> maskingData = new ArrayList<>();
        masking(userData, maskingData);
        printData(maskingData, startJson);
    }

    private void printData(List<String> maskingData, String startJson) {
        StringBuilder builder = new StringBuilder();
        builder.append(startJson);
        builder.append(TAG_DATA);
        maskingData.forEach(data -> builder.append(data).append(";"));
        if (!maskingData.isEmpty()) {
            builder.deleteCharAt(builder.length() - 1);
        }
        builder.append(END_MESSAGE);
        System.out.println(builder);
    }

    /**
     * Маскирование данных
     */
    private void masking(List<String> collect, List<String> soutInfo) {
        collect.forEach(text -> {
            if (text.matches(UtilRegx.NUMBER.getRegx())) {
                number = text;
                soutInfo.add(maskingNumber(text));
            } else if (text.matches(UtilRegx.FIO.getRegx())) {
                fio = text;
                soutInfo.add(maskingName(text));
            } else if (text.matches(UtilRegx.EMAIL.getRegx())) {
                email = text;
                soutInfo.add(maskingEmail(text));
            }
        });
    }

    /**
     * Маскирование номера телефона
     * @param number номер телефона
     * @return маскированный номер пользователя для вывода
     */
    private String maskingNumber(String number) {
       return number.substring(0, 4).concat("***").concat(number.substring(7, 11));
    }

    /**
     * Маскирование почты
     * @param email почта пользователя
     * @return масикированая почта пользователя для вывода
     */
    private String maskingEmail(String email) {

        List<String> collect = Arrays.asList(email.split("\\."));

        String domainFirstLevel = collect.get(1);
        List<String> emailNameAndDomainName = Arrays.asList(collect.get(0).split("@"));

        String emailName = emailNameAndDomainName.get(0);
        String domainName = emailNameAndDomainName.get(1);

        String substring = emailName.substring(0, 3);
        email = substring.concat("*".repeat(emailName.length() - substring.length()))
                .concat("@")
                .concat(domainName.replace(domainName, ("*").repeat(domainName.length())))
                .concat(".")
                .concat(domainFirstLevel);
        return email;
    }

    /**
     * Маскирование имени пользователя
     * @param fio Фио Пользователя
     * @return маскированые данные для вывода
     */
    private String maskingName(String fio) {
        List<String> collect = Arrays.stream(fio.split("\\s")).collect(Collectors.toList());
        String secondName = collect.get(0);
        secondName = secondName.replace(secondName.substring(1, secondName.length() - 1), "*".repeat(secondName.length() - 2));
        String firstName = collect.get(1);
        String patronymic = collect.get(2);
        patronymic = patronymic.charAt(0) + ".";
        return secondName.concat(" ").concat(firstName).concat(" ").concat(patronymic);
    }

    public MaskingDate() {
    }

    @Override
    public String toString() {
        return "MaskingDate{" +
                "number='" + number + '\'' +
                ", fio='" + fio + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
