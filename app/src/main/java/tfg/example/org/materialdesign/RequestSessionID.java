package tfg.example.org.materialdesign;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Iván on 16/04/2018.
 */

public class RequestSessionID {

    @SerializedName("success")
    public boolean success;
    @SerializedName("session_id")
    public String session_id;

    public RequestSessionID(boolean success, String session_id) {
        this.success = success;
        this.session_id = session_id;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }
}
