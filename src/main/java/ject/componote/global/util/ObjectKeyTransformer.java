package ject.componote.global.util;

public class ObjectKeyTransformer {
    private ObjectKeyTransformer() {}

    /**
     * Temp 경로를 Permanent 경로로 변환합니다.
     * @param tempObjectKey Temp 경로
     * @return Permanent 경로
     * @throws IllegalArgumentException 유효하지 않은 키일 경우
     */
    public static String toPermanentKey(final String tempObjectKey) {
        validateKey(tempObjectKey);
        return tempObjectKey.replaceFirst("temp", "permanent");
    }

    /**
     * Permanent 경로를 Temp 경로로 변환합니다.
     * @param permanentObjectKey Permanent 경로
     * @return Temp 경로
     * @throws IllegalArgumentException 유효하지 않은 키일 경우
     */
    public static String toTempKey(final String permanentObjectKey) {
        validateKey(permanentObjectKey);
        return permanentObjectKey.replaceFirst("permanent", "temp");
    }

    /**
     * 객체 키의 유효성을 검사합니다.
     * @param key 검사할 키
     * @throws IllegalArgumentException 키가 null 또는 비어있을 경우
     */
    private static void validateKey(final String key) {
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("Object key cannot be null or empty");
        }
    }
}
