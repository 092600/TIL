package TestHashTablePackage;

public class Test {
    public static void main(String[] args){
        HashTable h = new HashTable(5);
        h.put("sung", "sung is pretty");
        h.put("messi", "messi is cute");
        h.put("hee", "hee is ugly");
        h.put("sim", "sim is reader");
        h.put("kim", "kim is follower");

        System.out.println(h.get("sung"));
        System.out.println(h.get("messi"));
        System.out.println(h.get("hee"));
        System.out.println(h.get("sim"));
        System.out.println(h.get("kim"));
    }
}
