package com.example.snowball.service;

import com.example.snowball.common.code.ErrorCode;
import com.example.snowball.dto.PagingResponseDto;
import com.example.snowball.dto.ResponseRootDto;
import com.example.snowball.exception.ServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.springframework.data.domain.Page;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BaseApiService {

  public static final String ERROR = "error";
  public static final String ERROR_CODE = "errorCode";
  public static final String ERROR_MESSAGE = "errorMessage";
  public static final String RESULT = "result";


  public static HashMap<String, Object> makeErrorResult(ErrorCode errorCode, Object data) {
    HashMap<String, Object> result = new HashMap<>();
    result.put(ERROR, true);
    result.put(ERROR_CODE, errorCode.getCode());
    result.put(ERROR_MESSAGE, errorCode.getDescription());
    result.put(RESULT, data);
    return result;
  }

  public static HashMap<String, Object> makeErrorResult(ErrorCode errorCode) {
    //    result.put(ERROR, true);
    //    result.put(ERROR_CODE, errorCode.getCode());
    //    result.put(ERROR_MESSAGE, errorCode.getDescription());
    //    result.put(RESULT, null);
    return makeErrorResult(errorCode, null);
  }

  public static HashMap<String, Object> makeResult(Object data) {
    HashMap<String, Object> result = new HashMap<>();
    result.put(ERROR, false);
    result.put(ERROR_CODE, "");
    result.put(ERROR_MESSAGE, "");
    result.put(RESULT, data);
    return result;
  }

  public static <T> ResponseEntity<ResponseRootDto<T>> response(HttpStatus httpStatus, T result) {
    return response(httpStatus, null, null, result, true);
  }

  public static <T> ResponseEntity<ResponseRootDto<T>> response(
      HttpStatus httpStatus, ErrorCode errorCode, String errorMessage, T result) {
    return response(httpStatus, errorCode, errorMessage, result, true);
  }

  public static <T> ResponseEntity<ResponseRootDto<T>> response(
      HttpStatus httpStatus,
      ErrorCode errorCode,
      String errorMessage,
      T result,
      boolean isNoStore) {
    ResponseRootDto responseRootDto;
    if (httpStatus.value() >= 400) {
      responseRootDto =
          new ResponseRootDto(
              httpStatus.toString(),
              true,
              errorCode.getCode(),
              errorMessage.isEmpty() ? errorCode.getDescription() : errorMessage,
              result);
    } else {
      responseRootDto = new ResponseRootDto(httpStatus.toString(), false, "", "", result);
    }
    ResponseEntity.BodyBuilder body = ResponseEntity.status(httpStatus);
    if (isNoStore) {
      body.cacheControl(
          CacheControl.noStore().mustRevalidate().sMaxAge(0, TimeUnit.SECONDS).cachePrivate());
    }
    return body.body(responseRootDto);
  }

  public static <T> Map<String, Object> applySerializer(
      T object, Class<? extends T> type, JsonSerializer<T> ser)
      throws JsonProcessingException, IOException {
    Map<DeserializationFeature, Boolean> configure = new HashMap<>();
    configure.put(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return applySerializer(object, type, ser, configure);
  }

  public static <T> Map<String, Object> applySerializer(
      T object,
      Class<? extends T> type,
      JsonSerializer<T> ser,
      Map<DeserializationFeature, Boolean> configure)
      throws JsonProcessingException, IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    SimpleModule simpleModule = new SimpleModule();
    simpleModule.addSerializer(type, ser);
    objectMapper.registerModule(simpleModule);
    for (Map.Entry<DeserializationFeature, Boolean> entry : configure.entrySet()) {
      objectMapper.configure(entry.getKey(), entry.getValue());
    }

    String jsonString = objectMapper.writeValueAsString(object);
    Map<String, Object> resultMap =
        objectMapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {});
    return resultMap;
  }

  public <T> HashMap<String, Object> makePageResult(
      Page<T> pageContents, String dataRootIndex, Class<? extends T> type, JsonSerializer<T> ser) {
    HashMap<String, Object> result = new HashMap<>();
    result.put("total", pageContents.getTotalElements());
    result.put("totalPages", pageContents.getTotalPages());
    result.put("page", pageContents.getNumber() + 1);
    result.put("pageSize", pageContents.getSize());
    result.put("hasNext", pageContents.hasNext());
    result.put("hasPrevious", pageContents.hasPrevious());
    result.put(dataRootIndex, applySerializeList(pageContents.getContent(), type, ser));
    return result;
  }

  public <T> PagingResponseDto<T> makePageResult(
      Page<? extends Object> pageContents, List<T> content) {
    return new PagingResponseDto(
        pageContents.getTotalElements(),
        pageContents.getTotalPages(),
        pageContents.getSize(),
        pageContents.hasPrevious(),
        pageContents.hasNext(),
        pageContents.getNumber() + 1,
        content);
  }

  public <T> PagingResponseDto<T> makePageResult(Page<T> pageContents) throws ServiceException {
    if (pageContents.getTotalElements() != 0
        && pageContents.getNumber() + 1 > pageContents.getTotalPages()) {
      throw new ServiceException(HttpStatus.BAD_REQUEST, null, "전체 페이지를 초과 하였습니다.");
    }
    return new PagingResponseDto(
        pageContents.getTotalElements(),
        pageContents.getTotalPages(),
        pageContents.getSize(),
        pageContents.hasPrevious(),
        pageContents.hasNext(),
        pageContents.getNumber() + 1,
        pageContents.getContent());
    //    return PagingResponseDto.builder()
    //      .total(pageContents.getTotalElements())
    //      .totalPages(pageContents.getTotalPages())
    //      .page(pageContents.getNumber()+1)
    //      .pageSize(pageContents.getSize())
    //      .hasNext(pageContents.hasNext())
    //      .hasPrevious(pageContents.hasPrevious())
    //      .contents(pageContents.getContent())
    //      .build();
    //    HashMap<String, Object> result = new HashMap<>();
    //    result.put("total", pageContents.getTotalElements());
    //    result.put("totalPages", pageContents.getTotalPages());
    //    result.put("page", pageContents.getNumber()+1);
    //    result.put("pageSize", pageContents.getSize());
    //    result.put("hasNext", c);
    //    result.put("hasPrevious", pageContents.hasPrevious());
    //    result.put(dataRootIndex, applySerializeList(pageContents.getContent(), type, ser));
    //    return result;
  }

  private <T> List<Map> applySerializeList(
      List<T> list, Class<? extends T> type, JsonSerializer<T> ser) {
    try {
      List<Map> result = new ArrayList<>();
      for (T row : list) {
        Map<String, Object> resultMap = applySerializer(row, type, ser);
        result.add(resultMap);
      }
      return result;
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

}
