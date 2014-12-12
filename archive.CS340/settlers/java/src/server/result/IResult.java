package server.result;

/**
 * IResult is an interface for the different types of results
 * that can be returned back to the client.
 */
public interface IResult {

    public Object getResult();

    public void setResult(Object object);

}
