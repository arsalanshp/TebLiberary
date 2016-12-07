package library.tebyan.com.teblibrary.model;

/**
 * Created by root on 12/6/16.
 */
public class MyRefrence {

    private int Type;
    private boolean IsMessage;
    private String Message;
    private int Data;
    private int MessageCode;

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public boolean isMessage() {
        return IsMessage;
    }

    public void setMessage(boolean message) {
        IsMessage = message;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public int getData() {
        return Data;
    }

    public void setData(int data) {
        Data = data;
    }

    public int getMessageCode() {
        return MessageCode;
    }

    public void setMessageCode(int messageCode) {
        MessageCode = messageCode;
    }
}


//{
//        "d": {
//        "Type": 0,
//        "IsMessage": true,
//        "Message": "اطلاعات با موفقیت به ثبت رسید",
//        "Data": 10832,
//        "MessageCode": 0
//        }
//        }
