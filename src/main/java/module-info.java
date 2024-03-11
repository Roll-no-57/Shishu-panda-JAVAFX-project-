module tastytreat.tastytreat {
    requires javafx.controls;
    requires javafx.fxml;

    exports tastytreat.tastytreat.Client;
    opens tastytreat.tastytreat.Client to javafx.fxml;

    exports tastytreat.tastytreat.classes;
    opens tastytreat.tastytreat.classes to javafx.fxml;

//    exports tastytreat.tastytreat.classes;
//    opens tastytreat.tastytreat.RestaurantManager to javafx.fxml;

    exports tastytreat.tastytreat.server;
    opens tastytreat.tastytreat.server to javafx.fxml;

    exports tastytreat.tastytreat.util;
    opens tastytreat.tastytreat.util to javafx.fxml;
}