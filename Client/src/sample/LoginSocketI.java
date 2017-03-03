package sample;

/**
 * Created by ibrar on 2/23/17.
 */
public interface LoginSocketI extends MainSocketI {
    public void RegisterUSer(String userID,String password);
    public void LoginUser(String userID,String password);
    public void setLoginCallback(LoginCallback callback);
}
