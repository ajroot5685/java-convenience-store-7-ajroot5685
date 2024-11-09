package store.constant;

public class ExceptionMessage {

    public static final String FILE_COLUMN_SIZE_WRONG = "파일의 컬럼 개수가 잘못되었습니다.";
    public static final String FILE_PARSING_ERROR = "파일 파싱 중 오류가 발생했습니다.";
    public static final String FILE_PRICE_ERROR = "상품의 정상가격과 프로모션 가격은 같아야 합니다.";
    public static final String FILE_PROMOTION_PRODUCT_DUPLICATE = "같은 상품의 프로모션 정보가 여러개 들어왔습니다. 하나의 상품 정보로 통합해주세요.";
    public static final String FILE_NORMAL_PRODUCT_DUPLICATE = "같은 상품 정보가 여러개 들어왔습니다. 하나의 상품 정보로 통합해주세요.";

    public static final String INPUT_CANT_EMPTY = "공백은 입력할 수 없습니다.";
    public static final String PRODUCT_START_WITH_LEFT_SQUARE_BRACKET = "개별상품은 '['로 시작해야 합니다.";
    public static final String PRODUCT_END_WITH_RIGHT_SQUARE_BRACKET = "개별상품은 ']'로 끝나야 합니다.";
    public static final String PRODUCT_SEPARATOR_HYPHEN = "개별상품은 '-'를 기준으로 상품명, 수량을 입력해야 합니다.";
    public static final String PRODUCT_NAME_KOREAN = "상품명은 한글로만 구성되어 있습니다.";
    public static final String QUANTITY_POSITIVE_INTEGER = "수량은 Int 범위의 양수만 입력할 수 있습니다.";

    public static final String PRODUCT_NOT_FOUND = "존재하지 않는 상품입니다. 다시 입력해 주세요.";
    public static final String PRODUCT_QUANTITY_INSUFFICIENT = "재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.";
}
