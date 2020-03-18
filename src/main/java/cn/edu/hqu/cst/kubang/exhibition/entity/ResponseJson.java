package cn.edu.hqu.cst.kubang.exhibition.entity;

import cn.edu.hqu.cst.kubang.exhibition.pub.enums.ResponseCodeEnums;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author: 邢佳成
 * @Date: 2020.02.18 18:36
 * @Description: 统一接口返回JSON数据的格式
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public final class ResponseJson<T> implements Serializable {
    private Boolean success;
    private String errCode;
    private String errMsg;
    private T data;

    public ResponseJson(Boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public ResponseJson(Boolean success, String errCode, String errMsg) {
        this.success = success;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public ResponseJson(Boolean success, ResponseCodeEnums enums) {
        this.success = success;
        this.errCode = enums.getCode();
        this.errMsg = enums.getMsg();
    }

    public ResponseJson(Boolean success, T data, ResponseCodeEnums enums) {
        this.success = success;
        this.data = data;
        this.errCode = enums.getCode();
        this.errMsg = enums.getMsg();
    }
}
