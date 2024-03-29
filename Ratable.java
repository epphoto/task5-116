package ratings;

import ratings.datastructures.LinkedListNode;

import java.util.ArrayList;

public class Ratable {
    private String title;
    private LinkedListNode<Rating> ratings;
    public Ratable() {
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void addRating(Rating r1) {
        if (this.ratings == null) {
            this.ratings = new LinkedListNode<>(r1, null);
        } else {
            LinkedListNode<Rating> thisNode = this.ratings;
            while (thisNode.getNext() != null) {
                thisNode = thisNode.getNext();
            }
            thisNode.setNext(new LinkedListNode<>(r1, null));
        }
    }
    public LinkedListNode<Rating> getRatings() {
        if (ratings == null) {
            return null;
        } else {
            return ratings;
        }
    }

    public void setRatings(LinkedListNode<Rating> lln) {
        this.ratings = lln;
    }
    public void removeRatingByReviewer(Reviewer rev1){
        LinkedListNode<Rating> now = ratings;
        String rev12 = rev1.getReviewerID();
        if(ratings==null){
            return;
        }
        if(ratings.getValue().getReviewerID().equals(rev12)){
            ratings=ratings.getNext();
            return;
        }
        while(now.getNext()!=null){
            if(now.getNext().getValue().getReviewerID().equals(rev12)){
                now.setNext(now.getNext().getNext());
                return;
            }
            now=now.getNext();
        }
    }

    public double averageRating() {
        LinkedListNode<Rating> newNode=ratings;
        int counter = 0;
        double sum = 0.0;
        if (ratings == null) {
            return 0.0;
        }
        while (newNode != null) {
            int var = newNode.getValue().getRating();
            if (var != -1) {
                sum += var;
                counter++;
            }
            newNode = newNode.getNext();
        }
        return sum / counter;

    }

    public double bayesianAverageRating(int amount, int extras) {
        if (amount < 0 || extras < 1 || extras > 5) {return 0.0;}
        int sum = 0;
        int count = 0;
        LinkedListNode<Rating> node = this.getRatings();
        while (node != null) {
            int rat = node.getValue().getRating();
            if (rat >= 1 && rat <= 5) {
                sum += rat;
                count++;
            }
            node = node.getNext();
        }
        count += amount;
        sum += amount * extras;
        double avg = 0.0;
        if (count > 0) {avg = (double) sum / count;}
        return avg;
    }




}
