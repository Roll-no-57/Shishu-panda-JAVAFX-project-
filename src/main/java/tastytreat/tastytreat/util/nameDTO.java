package tastytreat.tastytreat.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class nameDTO implements Serializable {

        public List<String> names = new ArrayList<String>();


        public nameDTO(List<String> names) {
            this.names = names;
        }

        public List<String> getNames() {
            return names;
        }
        public void setNames(List<String> names) {
            this.names = names;
        }
        public void showNameList(){
            for(String name : names){
                System.out.println(name);
            }
        }


}
