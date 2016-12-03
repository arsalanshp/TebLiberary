package library.tebyan.com.teblibrary.model;

/**
 * Created by root on 10/23/16.
 */
public class BookerQuestion {
    private String Question;
    private String Answer;
    private int QuestionID;

    public String getQuestion() {
        return Question;
    }
    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public int getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(int questionID) {
        QuestionID = questionID;
    }
}
