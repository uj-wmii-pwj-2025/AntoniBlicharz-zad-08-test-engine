package uj.wmii.pwj.anns;

public class MyBeautifulTestSuite {

    @MyTest
    public void testSomething() {
        System.out.println("I'm testing something!");
    }

    @MyTest(params = {"a param", "b param", "c param. Long, long D param."},
            expectedResults = {"a param", "b param", "c param. Long, long C param."})
    public String testWithParam(String param) {
        System.out.printf("I was invoked with parameter: %s\n", param);
        return param;
    }

    @MyTest
    public int testWithWrongNumOfParams(String param) {
        return Integer.parseInt(param);
    }


    @MyTest
    public void imFailure() {
        System.out.println("I AM EVIL.");
        throw new NullPointerException("Intentional failure");
    }

    @MyTest(expectedResults = {"42"})
    public int testReturnInt() {
        System.out.println("Returning integer 42");
        return 42;
    }

    @MyTest(params = {"10 true", "20 false", "0 true"},
            expectedResults = {"10", "20", "0"})
    public int testWithMultipleParams(int number, boolean flag) {
        System.out.printf("Number: %d, Flag: %b\n", number, flag);
        return flag ? number : number * 2;
    }

    @MyTest(params = {"true", "false"},
            expectedResults = {"true", "false"})
    public boolean testBoolean(boolean value) {
        System.out.println("Testing boolean: " + value);
        return value;
    }

    @MyTest(params = {"123", "-456", "0"},
            expectedResults = {"123", "-456", "0"})
    public int testInteger(int value) {
        System.out.println("Testing integer: " + value);
        return value;
    }

    @MyTest(params = {"3.14", "-2.71", "0.0"},
            expectedResults = {"3.14", "-2.71", "0.0"})
    public double testDouble(double value) {
        System.out.println("Testing double: " + value);
        return value;
    }

    @MyTest(params = {"1000000000", "-999999999"},
            expectedResults = {"1000000000", "-999999999"})
    public long testLong(long value) {
        System.out.println("Testing long: " + value);
        return value;
    }

    @MyTest(params = {"1.5", "-2.5"},
            expectedResults = {"1.5", "-2.5"})
    public float testFloat(float value) {
        System.out.println("Testing float: " + value);
        return value;
    }

    @MyTest(params = {"A", "b", " "},
            expectedResults = {"A", "b", " "})
    public char testChar(char value) {
        System.out.println("Testing char: '" + value + "'");
        return value;
    }

    @MyTest(params = {"1 hello true", "2 world false"},
            expectedResults = {"1-hello-true", "2-world-false"})
    public String testThreeParams(int num, String text, boolean flag) {
        String result = num + "-" + text + "-" + flag;
        System.out.println("Three params result: " + result);
        return result;
    }

    @MyTest(params = {"test"},
            expectedResults = {"wrong"})
    public String testShouldFail(String input) {
        System.out.println("Input: " + input);
        return input;
    }

    @MyTest(params = {}, expectedResults = {})
    public void testEmptyParams() {
        System.out.println("Test with empty params array");
    }

    @MyTest(expectedResults = {"null"})
    public Object testReturnNull() {
        System.out.println("Returning null");
        return null;
    }


    @MyTest
    private void privateTestMethod() {
        System.out.println("Private test method");
    }

    @MyTest(params = {"static"}, expectedResults = {"static"})
    public static String staticTestMethod(String input) {
        System.out.println("Static test: " + input);
        return input;
    }

    @MyTest(params = {"  spaced  ", "tab\tseparated", "new\nline"},
            expectedResults = {"  spaced  ", "tab\tseparated", "new\nline"})
    public String testWhitespace(String input) {
        System.out.println("Whitespace test: '" + input + "'");
        return input;
    }

    @MyTest(params = {"@#$%", "ünîcødé", "123!@#"},
            expectedResults = {"@#$%", "ünîcødé", "123!@#"})
    public String testSpecialChars(String input) {
        System.out.println("Special chars: " + input);
        return input;
    }

    private int counter = 0;

    @MyTest(params = {"1", "2", "3"}, expectedResults = {"1", "2", "3"})
    public int testStateful(int increment) {
        counter += increment;
        System.out.println("Counter: " + counter);
        return increment;
    }

    @MyTest
    public void testLongRunning() {
        System.out.println("Starting long running test...");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Long running test completed");
    }

    @MyTest(params = {"5"}, expectedResults = {"25"})
    public int testUsingHelper(int value) {
        return square(value);
    }

    private int square(int x) {
        return x * x;
    }

    @MyTest(params = {"valid", "error"}, expectedResults = {"VALID", "ERROR"})
    public String testWithConditionalError(String input) {
        if ("error".equals(input)) {
            throw new IllegalArgumentException("Test error case");
        }
        return input.toUpperCase();
    }


    @MyTest(params = {"SUCCESS", "FAIL"},
            expectedResults = {"SUCCESS", "FAILURE"})
    public TestResult testEnum(TestResult value) {
        System.out.println("Enum test: " + value);
        return value;
    }

    @MyTest
    public void testStdErr() {
        System.err.println("This goes to stderr");
        System.out.println("This goes to stdout");
    }


    public void notATest() {
        System.out.println("I'm not a test.");
    }


    public String helperMethod() {
        return "helper";
    }


    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
