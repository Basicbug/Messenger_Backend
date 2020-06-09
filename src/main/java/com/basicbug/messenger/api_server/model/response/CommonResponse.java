package com.basicbug.messenger.api_server.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author JaewonChoi
 */

@Getter
@Setter
public class CommonResponse {

    @ApiModelProperty(value = "응답 성공 여부 (true/false)")
    private boolean success;

    @ApiModelProperty(value = "응답 코드 (정상 >= 0, 비정상 < 0)")
    private int code;

    @ApiModelProperty(value = "응답 메세지")
    private String message;
}
