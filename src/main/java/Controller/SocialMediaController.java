package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Service.AccountService;
import Service.MessageService;
import Model.Account;
import Model.Message;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.post("/register", this::registerAccountHandler);
        app.post("/login", this::loginAccountHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getMessageByAccountHandler);

        return app;
    }

    private void registerAccountHandler(Context context) {
        Account account = context.bodyAsClass(Account.class);
        Account created = accountService.registerAccount(account);
        if (created != null) {
            context.json(created);
        } else {
            context.status(400);
        }
    }

    private void loginAccountHandler(Context context) {
        Account account = context.bodyAsClass(Account.class);
        Account loggedIn = accountService.login(account);
        if (loggedIn != null) {
            context.json(loggedIn);
        } else {
            context.status(401);
        }
    }

    private void createMessageHandler(Context context) {
        Message message = context.bodyAsClass(Message.class);
        Message created = messageService.createMessage(message);
        if (created != null) {
            context.json(created);
        } else {
            context.status(400);
        }
    }

    private void getAllMessagesHandler(Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    private void getMessageByIdHandler(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
        if (message != null) {
            context.json(message);
        } else {
            context.status(400);
        }
    }

    private void deleteMessageHandler(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message deleted = messageService.deleteMessageById(messageId);
        if (deleted != null) {
            context.json(deleted);
        } else {
            context.status(400);
        }
    }

    private void updateMessageHandler(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        String newText = context.bodyAsClass(Message.class).getMessage_text();
        Message updated = messageService.updateMessage(messageId, newText);
        if (updated != null) {
            context.json(updated);
        } else {
            context.status(400);
        }
    }

    private void getMessageByAccountHandler(Context context) {
        int accountId = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByAccountId(accountId);
        context.json(messages);
    }
}