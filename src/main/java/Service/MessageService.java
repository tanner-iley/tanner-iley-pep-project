package Service;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;

import java.util.List;

public class MessageService {
    
    MessageDAO messageDAO;
    AccountDAO accountDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO messageDAO, AccountDAO accountDAO) {
        this.messageDAO = messageDAO;
        this.accountDAO = accountDAO;
    }

    public Message createMessage(Message message) {
        if (message.getMessage_text() == null || message.getMessage_text().isBlank()) {
            return null;
        }
        if (message.getMessage_text().length() > 255) {
            return null;
        }
        if (accountDAO.getAccountById(message.getPosted_by()) == null) {
            return null;
        }
        return messageDAO.insertMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int messageId) {
        return messageDAO.getMessageById(messageId);
    }

    public Message deleteMessageById(int messageId) {
        return messageDAO.deleteMessageById(messageId);
    }

    public Message updateMessage(int messageId, String newText) {
        if (newText == null || newText.isBlank() || newText.length() > 255) {
            return null;
        }
        return messageDAO.updateMessage(messageId, newText);
    }

    public List<Message> getMessagesByAccountId( int accountId) {
        return messageDAO.getMessagesByAccountId(accountId);
    }
}
