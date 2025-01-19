package App.AssociationManagement;
import App.AssociationMember.Member;
import others.Tree;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;


public class Visit {


    private final double cout;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate date;

    private final Tree tree;

    @JsonFormat(shape = JsonFormat.Shape.ARRAY) // Stocke la liste comme un tableau JSON
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

