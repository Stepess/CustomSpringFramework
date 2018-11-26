package org.springframework.beans.factory;

public interface ApplicationListener<E> {
    void onApplicationEvent(E event);
}
