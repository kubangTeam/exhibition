package cn.edu.hqu.cst.kubang.exhibition.pub;

/**
 * @author: 邢佳成
 * @Date: 2020.02.18 01:06
 * @Description:
 */

public class ParamException extends RuntimeException {

    private static final long serialVersionUID = -8475947045204262508L;

    public ParamException() {
        super("参数不能为空");
    }

    public ParamException(String message) {
        super(message);
    }
}
