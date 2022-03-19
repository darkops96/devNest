package es.urjc.dad.devNestInternalService.Objets;

public class Email {
    private String destination;
    private String subject;
    private String content;

    public Email(String d, String s, String c) {
        destination = d;
        subject = s;
        content = c;
    }

    public String getContent() {
        return content;
    }

    public String getDestination() {
        return destination;
    }

    public String getSubject() {
        return subject;
    }
}
