package tests;
import static org.junit.Assert.*;
import org.junit.Test;
import ratings.Playlist;
import ratings.Rating;
import ratings.Song;
import ratings.datastructures.LinkedListNode;
import ratings.datastructures.SongBayesianRatingComparator;
import ratings.datastructures.SongTitleComparator;
import ratings.datastructures.BinaryTreeNode;
public class TestDataStructures2 {
    public boolean compareSongTrees(BinaryTreeNode<Song> BTN1, BinaryTreeNode<Song> BTN2) {
        TestClasses1 obj = new TestClasses1();
        if (BTN1 == null && BTN2 == null) {
            return true;
        }
        if (BTN1 == null || BTN2 == null) {
            return false;
        }
        if (!obj.compareSongs(BTN1.getValue(), BTN2.getValue())) {
            return false;
        }
        LinkedListNode<Rating> rat1 = BTN1.getValue().getRatings();
        LinkedListNode<Rating> rat2 = BTN2.getValue().getRatings();
        while (rat1 != null && rat2 != null) {
            if (!obj.compareRatings(rat1.getValue(), rat2.getValue())) {
                return false;
            }
            rat1 = rat1.getNext();
            rat2 = rat2.getNext();
        }
        return rat1 == null && rat2 == null && compareSongTrees(BTN1.getLeft(), BTN2.getLeft()) && compareSongTrees(BTN1.getRight(), BTN2.getRight());
    }


    @Test
    public void test2() { //getSongTree null
        Playlist Future = new Playlist(new SongTitleComparator());
        assertEquals(null, Future.getSongTree());
    }

    @Test
    public void test3() { //Testing compareSongTrees with different title/artist/id

        Playlist lilWayneSongs = new Playlist(new SongTitleComparator());
        Playlist drakeSongs = new Playlist(new SongTitleComparator());
        lilWayneSongs.addSong(new Song("YM Wasted", "Lil Wayne", "No Ceilings"));
        lilWayneSongs.addSong(new Song("President Carter", "Lil Wayne", "The Carter IV"));
        lilWayneSongs.addSong(new Song("Banned", "Lil Wayne", "No Ceilings"));
        drakeSongs.addSong(new Song("The Motto", "Drake", "Take Care"));
        drakeSongs.addSong(new Song("You & The 6", "Drake", "IYRTTL"));
        drakeSongs.addSong(new Song("Passionfruit", "Drake", "More Life"));

        assertEquals(false, compareSongTrees(drakeSongs.getSongTree(), lilWayneSongs.getSongTree()));
    }

    @Test
    public void test4() { //Testing compareSongTrees with different title/artist/id

        Playlist lilWayneSongs = new Playlist(new SongTitleComparator());
        Playlist drakeSongs = new Playlist(new SongTitleComparator());
        drakeSongs.addSong(new Song("The Motto", "Drake", "Take Care"));
        drakeSongs.addSong(new Song("HYFR", "Kendrick", "Takeoff"));
        drakeSongs.addSong(new Song("No", "Way", "Jose"));
        lilWayneSongs.addSong(new Song("Mr.Carter", "Lil Wayne", "tc3"));
        lilWayneSongs.addSong(new Song("Mrs.Officer", "Jay-Z", "tha carter 3"));
        lilWayneSongs.addSong(new Song("John", "Rick Ross", "tha carter 4"));

        assertEquals(false, compareSongTrees(drakeSongs.getSongTree(), lilWayneSongs.getSongTree()));
    }

    @Test
    public void test32() { //Testing compareSongTrees with different ratings
        Playlist running = new Playlist(new SongTitleComparator());
        Playlist running2 = new Playlist(new SongTitleComparator());
        Song song1 = new Song("Get Your Grind On", "Notorious B.I.G", "Paul");
        Song song2 = new Song("Get Your Grind On", "Notorious B.I.G", "Paul");
        song1.addRating(new Rating("Sean", 5));
        song2.addRating(new Rating("Sean", 3));


        running.addSong(song1);
        running2.addSong(song2);

        assertEquals(false, compareSongTrees(running.getSongTree(), running2.getSongTree()));
    }

    @Test
    public void test35() { //Testing compareSongTrees with different tree structures
        Playlist tree = new Playlist(new SongTitleComparator());
        Playlist tree2 = new Playlist(new SongTitleComparator());
        Song s1 = new Song("The Blacker the Berry", "Kendrick Lamar", "Paul");
        Song s2 = new Song("King Kunta", "Kendrick Lamar", "Paul");
        Song s3 = new Song("These Walls", "Kendrick Lamar", "Paul");

        tree.addSong(s1);
        tree.addSong(s3);
        tree.addSong(s2);
        tree2.addSong(s2);
        tree2.addSong(s3);


        assertEquals(false, compareSongTrees(tree.getSongTree(), tree2.getSongTree()));

    }


    @Test
    public void testCompareSongTreesWithIdenticalTrees() {
        Song song1 = new Song("Song1", "Artist1", "ID1");
        Song song2 = new Song("Song2", "Artist2", "ID2");

        song1.addRating(new Rating("Reviewer1", 5));
        song1.addRating(new Rating("Reviewer2", 4));

        song2.addRating(new Rating("Reviewer1", 3));
        song2.addRating(new Rating("Reviewer2", 2));

        BinaryTreeNode<Song> root1 = new BinaryTreeNode<>(song1, null, null);
        root1.setRight(new BinaryTreeNode<>(song2, null, null));

        BinaryTreeNode<Song> root2 = new BinaryTreeNode<>(song1, null, null);
        root2.setRight(new BinaryTreeNode<>(song2, null, null));

        assertTrue(compareSongTrees(root1, root2));
    }

    @Test
    public void testCompareSongTreesWithDifferentTrees() {
        Song song1 = new Song("Teenage Rager", "Ken Carson", "Sean");
        Song song2 = new Song("Yale", "Ken Carson", "Paul");

        song1.addRating(new Rating("Johnson", 5));
        song1.addRating(new Rating("Raul", 4));

        BinaryTreeNode<Song> root1 = new BinaryTreeNode<>(song1, null, null);
        root1.setRight(new BinaryTreeNode<>(song2, null, null));

        Song song3 = new Song("Eye For Eye", "GUNIT", "50");
        Song song4 = new Song("Stunt101", "GUNIT", "LLoyd");

        song3.addRating(new Rating("Jessie", 5));
        song3.addRating(new Rating("Nasrin", 4));

        BinaryTreeNode<Song> root2 = new BinaryTreeNode<>(song3, null, null);
        root2.setRight(new BinaryTreeNode<>(song4, null, null));

        TestDataStructures2 tester = new TestDataStructures2();
        assertFalse(tester.compareSongTrees(root1, root2));
    }


    @Test
    public void testOrderOfSongs() {
        Playlist grind = new Playlist(new SongTitleComparator());

        grind.addSong(new Song("EPMD","Boldy James","Kung Fu Kenny"));
        grind.addSong(new Song("Double Hockey Sticks","Boldy James","Kendrick"));
        grind.addSong(new Song("Bye Baby","Nas","DOT"));




        LinkedListNode<Song> thisNode = grind.getSongList();


        assertEquals("Bye Baby",thisNode.getValue().getTitle());
        assertNotNull(thisNode.getNext());
        thisNode = thisNode.getNext();
        assertEquals("Double Hockey Sticks", thisNode.getValue().getTitle());
        assertNotNull(thisNode.getNext());
        thisNode=thisNode.getNext();
        assertFalse(thisNode.getValue().getTitle().equals("Bye Baby"));
        assertNull(thisNode.getNext());


    }
    @Test
    public void testOrderOfSongs2() { //decent number of songs
        Playlist grind = new Playlist(new SongTitleComparator());
        grind.addSong(new Song("a","Boldy James","Kung Fu Kenny"));
        grind.addSong(new Song("g","Boldy James","Kendrick"));
        grind.addSong(new Song("c","Nas","DOT"));
        grind.addSong(new Song("b","Nas","Paul"));
        grind.addSong(new Song("f","Nas","Carl"));
        grind.addSong(new Song("l","Nas","Jesse"));
        grind.addSong(new Song("e","Nas","Paul Dick"));
        grind.addSong(new Song("k","Kenny","Nasrin"));
        grind.addSong(new Song("m","Kendrick Lamar","Lil Wayne"));
        grind.addSong(new Song("n","Lamar Kendrick","files"));
        grind.addSong(new Song("p","Kendrick Lamar","a"));
        grind.addSong(new Song("h","Kung Fu Kenny","b"));
        grind.addSong(new Song("i","KDOT","c"));
        grind.addSong(new Song("o","Kendrick","d"));
        grind.addSong(new Song("j","Kendrick Lamar","e"));
        grind.addSong(new Song("q","Kendrick Lamar","e"));
        grind.addSong(new Song("r","Kendrick Lamar","e"));
        grind.addSong(new Song("d","Kendrick Lamar","e"));

        LinkedListNode<Song> thisNode = grind.getSongList();


        assertEquals("a",thisNode.getValue().getTitle());
        assertNotNull(thisNode.getNext());
        thisNode = thisNode.getNext();
        assertEquals("b", thisNode.getValue().getTitle());
        assertNotNull(thisNode.getNext());
        thisNode=thisNode.getNext();
        assertEquals("c", thisNode.getValue().getTitle());
        assertNotNull(thisNode.getNext());

        thisNode=thisNode.getNext();
        assertEquals("d",thisNode.getValue().getTitle());
        assertNotNull(thisNode.getNext());

        thisNode = thisNode.getNext();
        assertEquals("e", thisNode.getValue().getTitle());
        assertNotNull(thisNode.getNext());

        thisNode=thisNode.getNext();
        assertEquals("f", thisNode.getValue().getTitle());
        assertNotNull(thisNode.getNext());

        thisNode= thisNode.getNext();
        assertEquals("g",thisNode.getValue().getTitle());
        assertNotNull(thisNode.getNext());

        thisNode = thisNode.getNext();
        assertEquals("h", thisNode.getValue().getTitle());
        assertNotNull(thisNode.getNext());

        thisNode=thisNode.getNext();
        assertEquals("i", thisNode.getValue().getTitle());
        assertNotNull(thisNode.getNext());

        thisNode=thisNode.getNext();
        assertEquals("j",thisNode.getValue().getTitle());
        assertNotNull(thisNode.getNext());

        thisNode = thisNode.getNext();
        assertEquals("k", thisNode.getValue().getTitle());
        assertNotNull(thisNode.getNext());

        thisNode=thisNode.getNext();
        assertEquals("l", thisNode.getValue().getTitle());
        assertNotNull(thisNode.getNext());

        thisNode=thisNode.getNext();
        assertEquals("m",thisNode.getValue().getTitle());
        assertNotNull(thisNode.getNext());

        thisNode = thisNode.getNext();
        assertEquals("n", thisNode.getValue().getTitle());
        assertNotNull(thisNode.getNext());

        thisNode=thisNode.getNext();
        assertEquals("o", thisNode.getValue().getTitle());
        assertNotNull(thisNode.getNext());

        thisNode=thisNode.getNext();
        assertEquals("p",thisNode.getValue().getTitle());
        assertNotNull(thisNode.getNext());

        thisNode = thisNode.getNext();
        assertEquals("q", thisNode.getValue().getTitle());
        assertNotNull(thisNode.getNext());

        thisNode=thisNode.getNext();
        assertEquals("r", thisNode.getValue().getTitle());
        assertNull(thisNode.getNext());

    }
    @Test
    public void testGetSongListOrder() {
        Playlist playlist = new Playlist(new SongTitleComparator());

        playlist.addSong(new Song("Theta", "Kendrick Lamar", "Paul"));
        playlist.addSong(new Song("Alpha", "Kendrick Lamar", "Paul"));
        playlist.addSong(new Song("Beta", "Kendrick Lamar", "Paul"));

        LinkedListNode<Song> current = playlist.getSongList();

        assertEquals("Alpha", current.getValue().getTitle());
        assertNotNull(current.getNext());

        current = current.getNext();
        assertEquals("Beta", current.getValue().getTitle());
        assertNotNull(current.getNext());
        current = current.getNext();
        assertEquals("Theta", current.getValue().getTitle());
        assertNull(current.getNext());
    }
    @Test
    public void testEmptyTree() {
        Playlist playlist = new Playlist(new SongTitleComparator());
        assertNull(playlist.getSongList());
    }
    @Test
    public void test50(){
        Playlist playlist = new Playlist(new SongTitleComparator());
        playlist.addSong(new Song("P","Kenny","Jota"));
        playlist.addSong(new Song("C","Kenny","Jota"));
        playlist.addSong(new Song("A","Kenny","Jota"));
        assertNotEquals("P", playlist.getSongList().getValue().getTitle());
    }
  @Test
    public void test51(){
        Playlist playlist = new Playlist(new SongTitleComparator());
        assertEquals(null,playlist.getSongList());
    }
}














