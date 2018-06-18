package tfg.example.org.materialdesign;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Iván on 19/04/2018.
 */

public class SuccessResponse {

    @SerializedName("status_code")
    public int status_code;
    @SerializedName("status_message")
    public String status_message;

    public SuccessResponse(int status_code, String status_message) {
        this.status_code = status_code;
        this.status_message = status_message;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }
}
