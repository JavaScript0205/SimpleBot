package pdpBot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Deque;
import java.util.LinkedList;

public class SimpleBot extends TelegramLongPollingBot {

    Deque<String> deque;

    public SimpleBot() {
        deque = new LinkedList<>();
    }

    @Override
    public void onUpdateReceived(Update update) {

        Message msg = update.getMessage();

        User usr = msg.getFrom();

        String txt = msg.getText();

        SendMessage smsg = new SendMessage();

        String[] msgs = txt.split(" ");

        if (txt.equals("/start")) {
            smsg.setText("""
                    **********Foydalanuchi qo`llanmasi:**********
                    ____      - Biror so`z kiritasiz;
                    /start    - Botga kirish;
                    /user     - Foydalanuvchi turi;
                    /show     - Array(Massiv, List)ga qo`shilgan elementlarni ko`rish;
                    /size     - Arrayga qo`shilgan elementlar soni;
                    
                    /add ____      - Arrayga element qo`shish;
                    /addFirst ____ - Array boshidan element qo`shish;
                    /addLast ____  - Array oxiridan element qo`shish;
                    
                    /poll      - Array oxiridan 1 ta element qirqish;
                    /pollFirst - Array boshidan 1 ta element qirqish;
                    /pollLast  - Array oxiridan 1 ta element qirqish;
                    
                    """);
        } else if (txt.equals("/user")) {
            smsg.setText(usr.getId().toString().equals("1618991665")
                    ? usr.getFirstName() + usr.getLastName() + " - foydalanuvchi turi: Admin"
                    : usr.getFirstName() + usr.getLastName() + " - foydalanuvchi turi: User");

        } else if (txt.startsWith("/addLast")) {

            deque.addLast(msgs[1]);

            smsg.setText("Success!");

        } else if (txt.startsWith("/addFirst")) {

            deque.addFirst(msgs[1]);

            smsg.setText("Success!");

        } else if (txt.startsWith("/add")) {

            deque.add(msgs[1]);

            smsg.setText("Success!");

        } else if (txt.equals("/show")) {

            smsg.setText(deque.toString());

        } else if (txt.startsWith("/pollLast")) {

            smsg.setText(deque.pollLast());

        } else if (txt.startsWith("/pollFirst")) {

            smsg.setText(deque.pollFirst());

        } else if (txt.startsWith("/poll")) {

            smsg.setText(deque.poll());

        } else if (txt.startsWith("/size")) {

            smsg.setText("Arrayda " + deque.size() + " ta element mavjud...");

        }

        smsg.setChatId(msg.getChatId());

        try {
            execute(smsg);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String getBotUsername() {
        return "pdpu_simple_bot";
    }

    @Override
    public String getBotToken() {
        return "6502751914:AAEVM5WEJyOwcrdlN_BiCvDNFVzIwbvFMTw";
    }
}
