package tfg.example.org.materialdesign;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Iván on 24/04/2018.
 */

public class GuestSession {

    @SerializedName("success")
    public boolean success;
    @SerializedName("guest_session_id")
    public String guest_session_id;
    @SerializedName("expires_at")
    public String expires_at;

    public GuestSession(boolean success, String sessiguest_session_idon_id, String expires_at) {
        this.success = success;
        this.guest_session_id = guest_session_id;
        this.expires_at = expires_at;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getSession_id() {
        return guest_session_id;
    }

    public void setSession_id(String guest_session_id) {
        this.guest_session_id = guest_session_id;
    }

    public String getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(String expires_at) {
        this.expires_at = expires_at;
    }
}
