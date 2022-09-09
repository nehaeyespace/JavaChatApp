package registrationForm;

public interface Broadcast {
    void broadcast(String message);
    void notiLeftUser(ServerReceiver receiver);
    int getUserCount();
}
