package sf.travel.errors;
import lombok.Getter;

@Getter
public enum ErrorCode {
    EMAIL_ADDRESS_CANNOT_BE_DUPLICATED("401", "Email address cannot be duplicated"),
    NEW_NOT_FOUND("404", "New not found"),
    TRAVEL_NOT_FOUND("404", "Travel not found"),
    USER_NOT_FOUND("404", "User not found"),
    ORDER_NOT_FOUND("404", "Order not found");
    private final String code;
    private final String message;
    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
