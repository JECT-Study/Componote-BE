package ject.componote.infra.file.dto.move.request;

public record MoveRequest(String tempObjectKey, String permanentObjectKey) {
    public static MoveRequest of(final String tempObjectKey, final String permanentObjectKey) {
        return new MoveRequest(tempObjectKey, permanentObjectKey);
    }
}
