package algonquin.cst2335.labslarysachampagne;public class ChatMessage {
    private final String message;
    private final String timeSent;
    private final boolean isSentButton;


    public ChatMessage(String m, String t, boolean sent) {
        message = m;
        timeSent = t;
        isSentButton = sent;
    }


    public String getMessage() {
        return message;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public boolean isSentButton() {
        return isSentButton;
    }

}
