import org.easymock.EasyMock;

import static org.easymock.EasyMock.createMock;

public class Test {

    public Test() {
        this.newMethod();
        newMethod();
        com.apackage.EasyMock.createMock(Test.class);
        EasyMock.createMock(Test.class);
        createMock(Test.class)
    }

    public void newMethod() {

    }
}
