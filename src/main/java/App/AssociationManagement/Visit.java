package App.AssociationManagement;
import App.AssociationMember.Member;
import App.AssociationMember.Member;
import others.Tree;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.util.List;


public class Visit {

    private final double cout;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate date;

    private Tree tree;

    @JsonIgnoreProperties({"identifiant","password","cotisationPayee","dateInscription","cotisationsPayees"})
    private List<Member> visitors;


    public Visit(double cout, LocalDate date, Tree tree, List<Member> visitors){
        this.cout = cout;
        this.date = date;
        this.tree = tree;
        this.visitors = visitors;
    }

    Visit(){
        this.cout = 0;
        this.date = LocalDate.now();
        this.tree = new Tree();
        this.visitors = null;
    }

    public void addVisitor(Member p){
        visitors.add(p);
    }

    //getters
    public double getCout(){
        return cout;
    }
    public LocalDate getDate(){
        return date;
    }
    public Tree getTree(){
        return tree;
    }
    public List<Member> getVisitors(){
        return visitors;
    }

    //setters
    public void setTree(Tree tree){
        this.tree = tree;
    }
    public void setVisitors(List<Member> visitors){
        this.visitors = visitors;
    }



}

