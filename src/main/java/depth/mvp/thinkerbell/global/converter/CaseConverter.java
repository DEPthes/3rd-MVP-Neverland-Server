package depth.mvp.thinkerbell.global.converter;

public class CaseConverter {

    /**
     * PascalCase 문자열을 snake_case 문자열로 변환합니다.
     *
     * @param pascalCaseString 변환할 PascalCase 문자열
     * @return 변환된 snake_case 문자열
     */
    public static String pascalToSnake(String pascalCaseString) {
        StringBuilder snakeCaseString = new StringBuilder();

        for (char c : pascalCaseString.toCharArray()) {
            if (Character.isUpperCase(c)) {
                if (snakeCaseString.length() > 0) {
                    snakeCaseString.append('_');
                }
                snakeCaseString.append(Character.toLowerCase(c));
            } else {
                snakeCaseString.append(c);
            }
        }

        return snakeCaseString.toString();
    }

    /**
     * snake_case 문자열을 PascalCase 문자열로 변환합니다.
     *
     * @param snakeCaseString 변환할 snake_case 문자열
     * @return 변환된 PascalCase 문자열
     */
    public static String snakeToPascal(String snakeCaseString) {
        StringBuilder pascalCaseString = new StringBuilder();
        boolean toUpperCase = true;  // 첫 문자를 대문자로 변환하기 위해 true로 설정

        for (char c : snakeCaseString.toCharArray()) {
            if (c == '_') {
                // 언더스코어 다음 문자를 대문자로 변환하도록 설정합니다.
                toUpperCase = true;
            } else {
                if (toUpperCase) {
                    // 대문자로 변환 후 설정을 초기화합니다.
                    pascalCaseString.append(Character.toUpperCase(c));
                    toUpperCase = false;
                } else {
                    // 소문자로 추가합니다.
                    pascalCaseString.append(c);
                }
            }
        }

        return pascalCaseString.toString();
    }

}
