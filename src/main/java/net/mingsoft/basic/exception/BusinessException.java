package net.mingsoft.basic.exception;

public class BusinessException  extends RuntimeException {

    public BusinessException(Object Obj) {
        super(Obj.toString());
    }

}
