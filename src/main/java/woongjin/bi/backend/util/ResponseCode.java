package woongjin.bi.backend.util;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    /* 200 OK : 요청 성공 */
    SIGNIN_SUCCESS(OK, "로그인 성공"),
    REISSUE_TOKEN_SUCCESS(OK, "토큰 재발급 성공"),
    SEARCH_DASHBOARD_SUCCESS(CREATED, "대시보드 조회 성공"),
    MODIFY_DASHBOARD_SUCCESS(CREATED, "대시보드 수정 성공"),
    DELETE_DASHBOARD_SUCCESS(CREATED, "대시보드 삭제 성공"),


    /* 201 CREATED : 요청 성공, 자원 생성 */
    CREATE_DASHBOARD_SUCCESS(CREATED, "대시보드 생성 성공"),


    /* 400 BAD_REQUEST : 잘못된 요청 */
    MAIL_SEND_FAIL(BAD_REQUEST, "메일 전송 실패"),
    AUTH_NUMBER_INCORRECT(BAD_REQUEST, "인증 번호가 틀렸습니다"),
    CREATE_DASHBOARD_FAIL(BAD_REQUEST,"대시보드 생성 실패"),
    MODIFY_DASHBOARD_FAIL(BAD_REQUEST,"대시보드 수정 실패"),
    DELETE_DASHBOARD_FAIL(BAD_REQUEST,"대시보드 삭제 실패"),


    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "권한 정보가 없는 토큰입니다"),
    UNKNOWN_AUTHENTICATION_ERROR(UNAUTHORIZED, "알 수 없는 이유로 로그인에 실패했습니다"),


    /* 403 FORBIDDEN : 권한이 없는 사용자 */
    INVALID_REFRESH_TOKEN(FORBIDDEN, "잘못된 REFRESH 토큰입니다"),
    SIGNOUT_FAIL_REFRESH_TOKEN(FORBIDDEN, "본인의 REFRESH 토큰으로만 로그아웃할 수 있습니다"),
    DASHBOARD_MODIFY_FORBIDDEN_NOT_OWNER(FORBIDDEN, "대시보드 수정 권한이 없습니다"),
    DASHBOARD_DELETE_FORBIDDEN_NOT_OWNER(FORBIDDEN, "대시보드 삭제 권한이 없습니다"),



    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    ACCOUNT_NOT_FOUND(NOT_FOUND, "계정 정보를 찾을 수 없습니다"),
    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "REFRESH 토큰 정보를 찾을 수 없습니다"),
    DASHBOARD_SEARCH_FAIL(NOT_FOUND, "대시보드를 찾을 수 없습니다"),


    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    EMAIL_DUPLICATION(CONFLICT, "이미 사용 중인 이메일입니다")

    ;


    private final HttpStatus httpStatus;
    private final String detail;


}