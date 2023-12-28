package HashMap;

class HashNode {
    private final String Name;
    private Player Player;
    private HashNode next;

    public HashNode(String name, Player player, HashNode next) {
        Name = name;
        Player = player;
        this.next = next;
    }

    public String getName() {
        return Name;
    }

    public Player getPlayer() {
        return Player;
    }

    public void setPlayer(Player player) {
        Player = player;
    }

    public HashNode getNext() {
        return next;
    }

    public void setNext(HashNode next) {
        this.next = next;
    }
}
