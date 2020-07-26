package client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Request;

import java.io.PrintStream;
import java.io.PrintWriter;

public class Sender {

    private PrintStream printWriter;
    private ObjectMapper objectMapper =new ObjectMapper();

    public Sender(PrintStream printWriter) {
        this.printWriter = printWriter;
    }

    public void send(Request request) {

        String message=convertRequest(request);
        printWriter.println(message);
        System.out.println("we send "+message);
    }

    private String convertRequest(Request request){
        try {
            String message = objectMapper.writeValueAsString(request);
            return message;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }


    public PrintStream getPrintWriter() {
        return printWriter;
    }

    public void setPrintWriter(PrintStream printWriter) {
        this.printWriter = printWriter;
    }
}
