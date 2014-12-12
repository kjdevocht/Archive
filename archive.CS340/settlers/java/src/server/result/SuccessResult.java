package server.result;

/**
 * The result of any request with  simple a success or failure result.
 */
public class SuccessResult implements IResult {

    private String status;

    public SuccessResult() {
        status = "";
    }

    @Override
    public Object getResult() {
        return status;
    }

    @Override
    public void setResult(Object status) {
        this.status = (String)status;
    }

}
