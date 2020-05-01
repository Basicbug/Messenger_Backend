package com.basicbug.messenger.service;

import com.basicbug.messenger.model.response.CommonResponse;
import com.basicbug.messenger.model.response.ListResponse;
import com.basicbug.messenger.model.response.SingleResponse;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author JaewonChoi
 */

@Service
public class ResponseService {

    public enum CommonResult {
        SUCCESS(0, "성공"),
        FAIL(-1, "실패");

        int code;
        String message;

        CommonResult(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return this.code;
        }

        public String getMessage() {
            return this.message;
        }
    }

    public <T> SingleResponse<T> getSingleResponse(T data) {
        SingleResponse<T> response = new SingleResponse<>();
        response.setData(data);
        setSuccessfulResult(response);

        return response;
    }

    public <T> ListResponse<T> getListResponse(List<T> dataList) {
        ListResponse<T> response = new ListResponse<>();
        response.setDataList(dataList);
        setSuccessfulResult(response);

        return response;
    }

    public CommonResponse getSuccessResponse() {
        CommonResponse response = new CommonResponse();
        setSuccessfulResult(response);
        return response;
    }

    public CommonResponse getFailResponse(int code, String message) {
        CommonResponse response = new CommonResponse();
        response.setSuccess(false);
        response.setCode(code);
        response.setMessage(message);
        return response;
    }

    private void setFailResult(CommonResponse response) {
        response.setSuccess(false);
        response.setCode(CommonResult.FAIL.getCode());
        response.setMessage(CommonResult.FAIL.getMessage());
    }

    private void setSuccessfulResult(CommonResponse response) {
        response.setSuccess(true);
        response.setCode(CommonResult.SUCCESS.getCode());
        response.setMessage(CommonResult.SUCCESS.getMessage());
    }
}
