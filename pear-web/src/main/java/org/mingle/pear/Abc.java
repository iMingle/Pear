package org.mingle.pear;

import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author mingle
 */
public class Abc {
    private static class Node<E> {
        E item;
        Node<E> next;

        public Node(E value, Node<E> next) {
            this.item = value;
            this.next = next;
        }

        @Override public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(item + "->");
            while (next != null) {
                sb.append(next.item + "->");
                next = next.next;
            }
            sb.append("\n");

            return sb.toString();
        }
    }

    public static <E> Node<E> reverse(Node<E> head) {
        Node<E> prev = head;
        Node<E> next = prev.next;
        while (next != null) {
            next.next = prev;
            prev = next;
            next = next.next;
        }

        return prev;
    }

    public static void main(String[] args) {
        Node<Integer> node = new Node<>(1, null);
        node.next = new Node<>(2, null);
        System.out.println(node);
        System.out.println(Abc.reverse(node));

        WebClient client = WebClient.builder()
                .defaultHeader("", "")
                .filter(ExchangeFilterFunctions.limitResponseSize(800))
                .build();
    }
}
