package ratings;

import ratings.datastructures.BinaryTreeNode;
import ratings.datastructures.Comparator;
import static org.junit.Assert.*;
import org.junit.Test;

import ratings.datastructures.LinkedListNode;
import ratings.datastructures.SongTitleComparator;
import tests.TestClasses1;

import java.util.Objects;

public class Playlist {
    Comparator<Song> comparator;
    BinaryTreeNode<Song> root;
    public Playlist(Comparator<Song>comparator){
        this.root=null;
        this.comparator=comparator;

    }
    public void addSong(Song song) {
        if (this.root == null) {
            this.root = new BinaryTreeNode<>(song, null, null);
        } else {
            this.addSongHelper(this.root, song);
        }
    }


    public void addSongHelper(BinaryTreeNode<Song> node, Song value){
        if (comparator.compare(value, node.getValue())) {
            if (node.getLeft() == null) {
                node.setLeft(new BinaryTreeNode<>(value, null, null));
            } else {
                addSongHelper(node.getLeft(), value);
            }
        } else {
            if (node.getRight() == null) {
                node.setRight(new BinaryTreeNode<>(value, null, null));
            } else {
                addSongHelper(node.getRight(), value);
            }
        }
    }





    public LinkedListNode<Song> getSongList(BinaryTreeNode<Song> node) {
        if (node == null) {
            return null;
        }
        LinkedListNode<Song> left = getSongList(node.getLeft());
        LinkedListNode<Song> right = getSongList(node.getRight());

        LinkedListNode<Song> thisNode = new LinkedListNode<>(node.getValue(), null);
        if (left != null) {
            LinkedListNode<Song> listNode = left;
            while (listNode.getNext() != null) {
                listNode = listNode.getNext();
            }
            listNode.setNext(thisNode);
            thisNode = left;
        }
        if (right != null) {
            LinkedListNode<Song> listNode2 = thisNode;
            while (listNode2.getNext() != null) {
                listNode2 = listNode2.getNext();
            }
            listNode2.setNext(right);
        }
        return thisNode;
    }

    public LinkedListNode<Song> getSongList(){
        return getSongList(root);
    }
    public BinaryTreeNode<Song> getSongTree() {
        return root;
    }


}


