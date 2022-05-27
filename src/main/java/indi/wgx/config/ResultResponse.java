package indi.wgx.config;

import lombok.Data;

@Data
public class ResultResponse<T> {
    private Boolean success;

    private Integer code;

    private String message;

    private T data;

    private ResultResponse(){}

    public static <T> ResultResponse<T> ok(){
        ResultResponse<T> r = new ResultResponse<T>();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }

    public static <T> ResultResponse<T> error(){
        ResultResponse<T> r = new ResultResponse<T>();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }

    public ResultResponse<T> success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public ResultResponse<T> message(String message){
        this.setMessage(message);
        return this;
    }

    public ResultResponse<T> code(Integer code){
        this.setCode(code);
        return this;
    }

    public ResultResponse<T> data(T data){
        this.data = data;
        return this;
    }

}
