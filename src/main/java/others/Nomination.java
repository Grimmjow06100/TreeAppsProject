package others;


import App.AssociationMember.Member;

import java.util.LinkedList;
import java.util.List;

public class Nomination extends Tree {
    private int nombreVotes;

    public Nomination(){
        super();
    }

    public Nomination(Tree tree, int nombreVotes){
        super(tree);
        this.nombreVotes = nombreVotes;
    }

    public int getNombreVotes(){
        return nombreVotes;
    }
    public void addVote(){
        nombreVotes++;
    }



}
