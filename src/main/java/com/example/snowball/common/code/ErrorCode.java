package com.example.snowball.common.code;

public enum ErrorCode {

  /** Success error code. */
  SUCCESS("0000", "Success"),
  /** Invalid parameter error code. */
  // 공통
  INVALID_PARAMETER("0100", "입력값이 형식에 맞지 않습니다."),
  /** Failed send email error code. */
  FAILED_SEND_EMAIL("0101", "이메일 발송이 실패했습니다."),
  /** Not loged in error code. */
  NOT_LOGED_IN("0102", "로그인 되어 있지 않습니다."),
  /** Unauthorized error code. */
  UNAUTHORIZED("0103", "해당 요청을 처리할 권한이 없습니다."),
  /** No exist data error code. */
  NO_EXIST_DATA("0104", "해당 데이터가 없습니다."),
  /** Not found error code. */
  NOT_FOUND("0105", "요청한 결과가 없습니다."),
  /** Internal server error error code. */
  INTERNAL_SERVER_ERROR("0106", "서버 에러가 발생했습니다."),
  /** Json parse error error code. */
  JSON_PARSE_ERROR("0107", "Data 오류입니다. 잠시후 다시 시도해 주세요."),
  /** Processing failed error code. */
  PROCESSING_FAILED("0108", "해당 요청을 처리할 수 없습니다."),
  MISSING_HEADER("0109", "필수 헤더값이 누락되었습니다."),
  ALREADY_RPOCESSED("0110", "동일한 요청이 이미 수행되었습니다."),

  // 사용자
  NO_EXIST_USER("0200", "사용자 정보가 없습니다."),
  INVALID_CREATOR("0201", "해당 프로젝트의 창작자와 일치하지 않습니다."),
  /** Invalid email error code. */
  // 로그인
  INVALID_EMAIL("0201", "아이디가 존재하지 않습니다"),
  /** Invalid password error code. */
  INVALID_PASSWORD("0202", "패스워드가 맞지 않습니다."),
  /** Exist email error code. */
  // 회원가입
  EXIST_EMAIL("0204", "이미 가입된 이메일입니다."),
  /** Invalid token error code. */
  // 이메일 변경시 토큰
  INVALID_TOKEN("0205", "사용할 수 없는 이메일 링크입니다."),
  /** No exist email error code. */
  NO_EXIST_EMAIL("0206", "이메일이 존재하지 않습니다."),
  NO_EXIST_NAME("0207", "이름이 존재하지 않습니다."),
  /** Already joined with social account fb error code. */
  // 소셜로그인
  ALREADY_JOINED_WITH_SOCIAL_ACCOUNT_FB("0207", "이미 페이스북 아이디와 연결되어 있습니다."),
  /** Already joined with social account naver error code. */
  ALREADY_JOINED_WITH_SOCIAL_ACCOUNT_NAVER("0208", "이미 네이버 아이디와 연결되어 있습니다."),
  /** Already used social account fb error code. */
  ALREADY_USED_SOCIAL_ACCOUNT_FB("0209", "다른 텀블벅 계정에 페이스북 아이디가 연결되어 있습니다."),
  /** Already used social account naver error code. */
  ALREADY_USED_SOCIAL_ACCOUNT_NAVER("0210", "다른 텀블벅 계정에 네이버 아이디가 연결되어 있습니다."),
  /** Not found social account info error code. */
  NOT_FOUND_SOCIAL_ACCOUNT_INFO("0211", "소셜 로그인 정보가 없습니다."),
  /** Failed login social account error code. */
  FAILED_LOGIN_SOCIAL_ACCOUNT("0212", "로그인을 실패해서 소셜 계정 연동을 할 수 없습니다."),
  /** Not found social connect error code. */
  NOT_FOUND_SOCIAL_CONNECT("0213", "요청한 외부계정 정보가 없습니다."),
  /** Not verified email social account error code. */
  NOT_VERIFIED_EMAIL_SOCIAL_ACCOUNT(
      "0214",
      "연동할 소셜 계정의 이메일이 이미 사용중입니다.\n연동을 원하시면 해당 이메일 인증 후 다시 시도해 주세요.\n비밀번호 찾기를 통해"
          + " 해당 이메일로 로그인 할 수 있습니다."),
  /** Find naver account email error code. */
  FIND_NAVER_ACCOUNT_EMAIL("0215", "이메일이 네이버 계정과 연결되어 있습니다. 네이버 아이디로 로그인 하세요."),
  /** Find fb account email error code. */
  FIND_FB_ACCOUNT_EMAIL("0216", "이메일이 페이스북 계정과 연결되어 있습니다. 페이스북 아이디로 로그인 하세요."),
  /** No exist password error code. */
  NO_EXIST_PASSWORD("0217", "해당 이메일은 비밀번호 변경 후 사용 가능합니다."),
  /** Already joined with social account naver error code. */
  ALREADY_JOINED_WITH_SOCIAL_ACCOUNT_APPLE("0218", "이미 애플 아이디와 연결되어 있습니다."),
  /** Already used social account naver error code. */
  ALREADY_USED_SOCIAL_ACCOUNT_APPLE("0219", "다른 텀블벅 계정에 애플 아이디가 연결되어 있습니다."),
  // 디바이스 등록 안 되어 있음.
  NO_EXIST_DEVICE("0220", "디바이스 정보가 없습니다."),
  // JWT관련
  EXPIRED_JWT("0290", "토큰이 만료되었습니다."),
  WRONG_JWT("0291", "잘못된 형식의 토큰입니다."),
  NO_EXIST_JWT("0292", "만료되었거나 등록되지 않은 Token입니다."),
  DIFF_MACHINE_ID("0293", "기기 ID 값이 다릅니다.");

  /** Unchargeable project warranty error code. */

  /** No exist shipping address error code. */

  private final String code;
  private final String description;
  ErrorCode(String code, String description) {
    this.code = code;
    this.description = description;
  }
  /**
   * Gets error code.
   *
   * @param code the code
   * @return the error code
   */
  public static ErrorCode getErrorCode(String code) {
    for (ErrorCode errorCode : ErrorCode.values()) {
      if (errorCode.code.equals(code)) {
        return errorCode;
      }
    }
    return null;
  }
  /**
   * Gets code.
   *
   * @return the code
   */
  public String getCode() {
    return code;
  }
  /**
   * Gets description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }
  public String toString() {
    return "[\"code\":\"" + getCode() + "\", \"description\":\"" + getDescription() + "\"]";
  }

}
