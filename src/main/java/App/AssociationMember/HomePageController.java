package App.AssociationMember;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXML;

public class HomePageController {

    @FXML
    private JFXDrawer JFXDrawerStack;

    @FXML
    private JFXHamburger JFXHamburger;

    @FXML
    public void  initialize() {
        System.out.println("HomePageController initialized");
        HamburgerBackArrowBasicTransition burgerTask2 = new HamburgerBackArrowBasicTransition(JFXHamburger);
        burgerTask2.setRate(-1);
        JFXHamburger.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_PRESSED, (e) -> {
            burgerTask2.setRate(burgerTask2.getRate() * -1);
            burgerTask2.play();
            if (JFXDrawerStack.isOpened()) {
                JFXDrawerStack.close();
            } else {
                JFXDrawerStack.open();
            }
        });
    }
}
