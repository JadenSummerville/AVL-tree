public class AvlTest {
    private final static int itterations = 1000;
    public static void main(String[] args){
        testCreateAVL();
        testInsert();
        testGet();
        testFindEmpty();
        testFindNotPresent();

        Avl a = new Avl();
        a.insert("Cata", "a");
        a.insert("Cate", "e");
        a.insert("Catc", "c");
        a.insert("Cat", "");
        a.insert("Catd", "d");
        a.insert("Catb", "b");
        a.insert("Catf", "f");
        a.display();
    }
    public static void testCreateAVL(){
        Avl a = new Avl();
    }
    public static void testInsert(){
        Avl a = new Avl();
        for(Integer i = 0; i != itterations; i++){
            a.insert(""+i,i*i);
        }
    }
    public static void testGet(){
        Avl a = new Avl();
        for(Integer i = 0; i != itterations; i++){
            a.insert(i*i, ""+i);
        }
        for(Integer i = 0; i != itterations; i++){
            assertTrue((Integer)a.get(""+i) == i*i,"Wrong value accosiated with key on itteration "+i+".");
        }
    }
    public static void testFindEmpty(){
        Avl a = new Avl();
        assertTrue(a.get("a") == null, "Empty Avl should be null, was"+a.get("a")+".");
    }
    public static void testFindNotPresent(){
        Avl a = new Avl();
        for(Integer i = 0; i != itterations; i++){
            a.insert(i*i, ""+i);
        }
        for(Integer i = 0; i != itterations; i++){
            assertTrue((Integer)a.get("a"+i) == null,"Wrong value accosiated with key on itteration "+i+", should be null");
        }
    }
    private static void assertTrue(boolean trueStatement, String errorMessage){
        if(trueStatement){
            return;
        }
        throw new IllegalArgumentException(errorMessage);
    }
}
