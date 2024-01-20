package org.telegram.mybot.message;


public abstract class Handler<T> {

    public final Sender  sender;

    public Handler(Sender sender) {
        this.sender = sender;
    }

    public abstract void resolve(T msg);

}
