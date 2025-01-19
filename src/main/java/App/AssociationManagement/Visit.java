package App.AssociationManagement;
import App.AssociationMember.Member;

import java.time.LocalDate;
import java.util.List;


public class Visit {


    private final double cout;
    private final LocalDate date;
    private final Tree tree;
    private List<Member> visitors;


    Visit(double cout, LocalDate date, Tree tree, List<Member> visitors){
        this.cout = cout;
        this.date = date;
        this.tree = tree;
        this.visitors = visitors;
    }

    public void addVisitor(Member member){
        visitors.add(member);
    }

    //getters
    public double getCout(){
        return cout;
    }
    public LocalDate getDate(){
        return date;
    }
    public Tree getArbre(){
        return tree;
    }
    public List<Member> getVisitors(){
        return visitors;
    }


}

