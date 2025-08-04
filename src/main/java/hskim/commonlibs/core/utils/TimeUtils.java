/*
 * Copyright 2025 Kim HyeonSu (rebri99@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package hskim.commonlibs.core.utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * 시간 데이터 관련 유틸
 * 
 * @author Kim HyeonSu (rebri99@gmail.com)
 * @date 2025. 6. 26.
 */
public class TimeUtils {

    /** 한국 표준시(KST) 타임존 */
    private static final ZoneId KOREA_ZONE = ZoneId.of("Asia/Seoul");

    /** 하루의 끝 시간 나노초 (23:59:59.999999999) */
    private static final int END_OF_DAY_NANO = 999_999_999;

    /** 변환할 수 있는 String 포맷 목록 */
    private static final DateTimeFormatter[] SUPPORTED_FORMATTERS = {
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"), //
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"), //
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"), //
            DateTimeFormatter.ofPattern("yyyy-MM-dd"), //
            DateTimeFormatter.ofPattern("yyyy/MM/dd"), //
            DateTimeFormatter.ofPattern("yyyy.MM.dd") //
    };

    /**
     * 유효한 문자열인지 검증 <br>
     *
     * <pre>
     *  
     * [개정이력]
     *      날짜      | 작성자 |       내용 
     * ------------------------------------------
     * 2025. 8. 1.    김현수   최초작성
     * </pre>
     *
     * @param time 검증할 문자열
     * @return 유효하면 true, 그렇지 않으면 false
     */
    private static boolean isValidTimeString(String time) {
        return time != null && !time.trim().isEmpty();
    }

    /**
     * 해당 날짜의 끝 시간을 Timestamp로 반환한다.<br>
     * ex) yyyy-MM-dd 23:59:59.999<br>
     *
     * <pre>
     *  
     * [개정이력]
     *      날짜      | 작성자 |       내용 
     * ------------------------------------------
     * 2025. 6. 26.    김현수   최초작성
     * 2025. 8. 1.     김현수   타임존 명시, 나노초 수정, 유효성 검증 추가
     * </pre>
     *
     * @param timeMillis Long 타입 변환할 시간
     * @return 해당 날짜의 끝 시간
     */
    public static Timestamp getEndOfDay(Long timeMillis) {
        if (timeMillis == null) {
            throw new IllegalArgumentException("timeMillis parameter cannot be null.");
        }

        try {
            Instant instant = Instant.ofEpochMilli(timeMillis.longValue());
            LocalDate date = instant.atZone(KOREA_ZONE).toLocalDate();
            LocalDateTime endOfDay = date.atTime(LocalTime.of(23, 59, 59, END_OF_DAY_NANO));
            Timestamp result = Timestamp.valueOf(endOfDay);
            result.setNanos(END_OF_DAY_NANO);

            return result;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid timestamp value: " + timeMillis, e);
        }
    }

    /**
     * 해당 날짜의 끝 시간을 Timestamp로 변환한다.<br>
     * ex) yyyy-MM-dd 23:59:59.999<br>
     * <ul>
     * <b>변환 가능한 format 목록</b>
     * <li>yyyy-MM-dd HH:mm:ss</li>
     * <li>yyyy-MM-dd'T'HH:mm:ss</li>
     * <li>yyyy-MM-dd'T'HH:mm:ss.SSS</li>
     * <li>yyyy/MM/dd</li>
     * <li>yyyy.MM.dd</li>
     * </ul>
     *
     * <pre>
     *  
     * [개정이력]
     *      날짜      | 작성자 |       내용 
     * ------------------------------------------
     * 2025. 6. 26.    김현수   최초작성
     * 2025. 8. 1.     김현수   타임존 명시, 나노초 수정, 유효성 검증 추가
     * </pre>
     *
     * @param timeString String 타입 변환할 시간
     * @return 해당 날짜의 끝 시간
     */
    public static Timestamp getEndOfDay(String timeString) throws DateTimeParseException {

        if (!isValidTimeString(timeString)) {
            throw new IllegalArgumentException("timeString parameter cannot be null or empty.");
        }

        String trimmedTime = timeString.trim();

        for (DateTimeFormatter formatter : SUPPORTED_FORMATTERS) {
            try {
                LocalDate date = parseToLocalDate(trimmedTime, formatter);
                LocalDateTime endOfDay = date.atTime(LocalTime.of(23, 59, 59, END_OF_DAY_NANO));
                Timestamp result = Timestamp.valueOf(endOfDay);
                result.setNanos(END_OF_DAY_NANO);

                return result;
            } catch (DateTimeParseException ignored) {
                // 무시하고 다음 로직 처리
            }
        }

        // 전부 실패 시 예외 처리
        throw new IllegalArgumentException("Unsupported date format. [input: " + trimmedTime
                + ", supported formats: yyyy-MM-dd, yyyy-MM-dd HH:mm:ss, yyyy-MM-dd'T'HH:mm:ss, etc.]");
    }

    /**
     * 해당 날짜의 시작 시간을 Timestamp로 반환한다.<br>
     * ex) yyyy-MM-dd 00:00:00.000<br>
     *
     * <pre>
     *  
     * [개정이력]
     *      날짜      | 작성자 |       내용 
     * ------------------------------------------
     * 2025. 6. 26.    김현수   최초작성
     * 2025. 8. 1.     김현수   타임존 명시, 유효성 검증 추가, 변수명 통일
     * </pre>
     *
     * @param timeMillis Long 타입 변환할 시간
     * @return 해당 날짜의 시작 시간
     */
    public static Timestamp getStartOfDay(Long timeMillis) {

        if (timeMillis == null) {
            throw new IllegalArgumentException("timeMillis parameter cannot be null.");
        }

        try {
            Instant instant = Instant.ofEpochMilli(timeMillis.longValue());
            LocalDate date = instant.atZone(KOREA_ZONE).toLocalDate();
            LocalDateTime startOfDay = date.atStartOfDay();

            return Timestamp.valueOf(startOfDay);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid timestamp value: " + timeMillis, e);
        }
    }

    /**
     * 해당 날짜의 시작 시간을 Timestamp로 변환한다.<br>
     * ex) yyyy-MM-dd 00:00:00.000<br>
     * <ul>
     * <b>변환 가능한 format 목록</b>
     * <li>yyyy-MM-dd HH:mm:ss</li>
     * <li>yyyy-MM-dd'T'HH:mm:ss</li>
     * <li>yyyy-MM-dd'T'HH:mm:ss.SSS</li>
     * <li>yyyy/MM/dd</li>
     * <li>yyyy.MM.dd</li>
     * </ul>
     *
     * <pre>
     *  
     * [개정이력]
     *      날짜      | 작성자 |       내용 
     * ------------------------------------------
     * 2025. 6. 26.    김현수   최초작성
     * 2025. 8. 1.     김현수   타임존 명시, 유효성 검증 추가, 변수명 통일
     * </pre>
     *
     * @param timeString String 타입 변환할 시간 (yyyy-MM-dd)
     * @return 해당 날짜의 시작 시간
     */
    public static Timestamp getStartOfDay(String timeString) {

        if (!isValidTimeString(timeString)) {
            throw new IllegalArgumentException("timeString parameter cannot be null or empty.");
        }

        String trimmedTime = timeString.trim();

        for (DateTimeFormatter formatter : SUPPORTED_FORMATTERS) {
            try {
                LocalDate date = parseToLocalDate(trimmedTime, formatter);
                LocalDateTime startOfDay = date.atStartOfDay();

                return Timestamp.valueOf(startOfDay);
            } catch (DateTimeParseException ignored) {
                // 무시하고 다음 로직 처리
            }
        }

        // 전부 실패 시 예외 처리
        throw new IllegalArgumentException("Unsupported date format. [input: " + trimmedTime
                + ", supported formats: yyyy-MM-dd, yyyy-MM-dd HH:mm:ss, yyyy-MM-dd'T'HH:mm:ss, etc.]");
    }

    /**
     * 주어진 formatter를 사용하여 문자열을 LocalDate로 변환 <br>
     *
     * <pre>
     *  
     * [개정이력]
     *      날짜      | 작성자 |       내용 
     * ------------------------------------------
     * 2025. 8. 1.    김현수   최초작성
     * </pre>
     *
     * @param timeString 변환
     * @param formatter
     * @return
     */
    private static LocalDate parseToLocalDate(String timeString, DateTimeFormatter formatter)
            throws DateTimeParseException {
        // 시간 정보가 포함된 포맷인지 확인
        if (formatter.toString().contains("H")) {
            LocalDateTime dateTime = LocalDateTime.parse(timeString, formatter);
            return dateTime.atZone(KOREA_ZONE).toLocalDate();
        } else {
            return LocalDate.parse(timeString, formatter);
        }
    }
}
