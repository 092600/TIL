package TestHashTablePackage;

import java.util.LinkedList;

public class HashTable {
    class Node {
        String key;
        String value;

        Node(String key, String value){
            this.key = key;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value){
            this.value = value;
        }

    }

    LinkedList<Node>[] data;

    HashTable(int capacity){
        data = new LinkedList[capacity];
    }

    int getIndex(int hashcode) {
        return hashcode % data.length;
    }
    Node searchKey(LinkedList<Node> list, String key){
        if (list == null) return null;
        for (Node node : list) {
            if (node.key.equals(key)) {
                return node;
            }
        }
        return null;
    }

    void put(String key, String value) {
        int hashcode = key.hashCode();
        int index = hashcode % data.length;

        LinkedList<Node> list = data[index];

        if (list == null) {
            list = new LinkedList<Node>();
            data[index] = list;
        }

        Node node = searchKey(list, key);
        if (node == null){
            list.addLast(new Node(key, value));
        } else {
            node.value = value;
        }
    }

    String get(String key) {
        int hashcode = key.hashCode();
        int index = getIndex(hashcode);

        System.out.println("key : "+key+", hashcode : "+hashcode+", index : "+index);
        LinkedList<Node> list = data[index];
        Node node = searchKey(list, key);
        if (node == null) {
            return "Not Found";
        } else {
            return node.getValue();
        }
    }
}
