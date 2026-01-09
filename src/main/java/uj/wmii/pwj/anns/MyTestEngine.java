package uj.wmii.pwj.anns;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

public class MyTestEngine {


    private static Object[] parseParameters(String paramString, Class<?>[] paramTypes) {
        String[] parts = paramString.split(" ", paramTypes.length);
        Object[] parsedParams = new Object[paramTypes.length];

        for (int i = 0; i < paramTypes.length; i++) {
            String value = (i < parts.length) ? parts[i] : "";

            if (paramTypes[i] == String.class) {
                parsedParams[i] = value;
            } else if (paramTypes[i] == int.class || paramTypes[i] == Integer.class) {
                parsedParams[i] = Integer.parseInt(value);
            } else if (paramTypes[i] == double.class || paramTypes[i] == Double.class) {
                parsedParams[i] = Double.parseDouble(value);
            } else if (paramTypes[i] == boolean.class || paramTypes[i] == Boolean.class) {
                parsedParams[i] = Boolean.parseBoolean(value);
            } else if (paramTypes[i] == long.class || paramTypes[i] == Long.class) {
                parsedParams[i] = Long.parseLong(value);
            } else if (paramTypes[i] == float.class || paramTypes[i] == Float.class) {
                parsedParams[i] = Float.parseFloat(value);
            } else if (paramTypes[i] == char.class || paramTypes[i] == Character.class) {
                parsedParams[i] = value.isEmpty() ? ' ' : value.charAt(0);
            } else if (paramTypes[i].isEnum()) {
                parsedParams[i] = Enum.valueOf((Class<Enum>) paramTypes[i], value);
            } else {
                throw new IllegalArgumentException("Unsupported parameter type: " + paramTypes[i]);
            }
        }

        return parsedParams;
    }

    private final String className;
    static OutputManager outputManager = new OutputManager();
    public static void main(String[] args) {
        outputManager.displayHeader();

        if (args.length < 1) {
            System.out.println("Please specify test class name");
            System.exit(-1);
        }
        String className = args[0].trim();

        outputManager.displayTestInfo(className);

        outputManager.printTestResults("white", "bold",  ("Testing class: "+ className));
        outputManager.printDivide();
        MyTestEngine engine = new MyTestEngine(className);
        engine.runTests();
    }

    public MyTestEngine(String className) {
        this.className = className;
    }

    public void runTests() {
        final Object unit = getObject(className);
        List<Method> testMethods = getTestMethods(unit);
        int successCount = 0;
        int failCount = 0;
        int errorCount = 0;
        for (Method m: testMethods) {
            TestResult result = launchSingleMethod(m, unit);
            if (result == TestResult.SUCCESS) successCount++;
            else if (result == TestResult.FAIL) failCount++;
            else errorCount++;
        }
        int runtests = testMethods.size() - errorCount;
        outputManager.printDivide();
        if (errorCount == 0)
            outputManager.printTestResults("green", "bold", "Engine launched all" + testMethods.size() + "tests " );
        else
            outputManager.printTestResults("red", "bold", "Engine launched "+runtests+" tests ("+errorCount+" ended with and error), " );
        if (failCount == 0)
            outputManager.printTestResults("green", "bold", "all of them have been successful");
        else

            outputManager.printTestResults("red", "bold", successCount + " of them have been successful and "+ failCount+ " failed");
    }

    private TestResult launchSingleMethod(Method m, Object unit) {
        try {
            String[] params = m.getAnnotation(MyTest.class).params();
            String[] expectedResults = m.getAnnotation(MyTest.class).expectedResults();
            Map<Integer,String> failedTests = new HashMap<>();
            boolean failed = false;

            try {
                if (params.length == 0) {

                        m.invoke(unit);
                } else {
                    for (int i = 0; i < params.length; i++) {
                        Class<?>[] paramTypes = m.getParameterTypes();
                        Object[] parsedParams = parseParameters(params[i], paramTypes);
                        String result = m.invoke(unit, parsedParams).toString();
                        if (!result.equals(expectedResults[i])) {
                            failedTests.put(i, result);
                            failed = true;
                        }
                    }
                }
                if (!failed) {
                    outputManager.printTestResults("green","","Tested method: " + m.getName() + " test successful.");
                    System.out.println();
                    return TestResult.SUCCESS;
                } else {
                    outputManager.printTestResults("red","","Tested method: " + m.getName() + " test failed:");
                    for (Integer key : failedTests.keySet()){
                        String string = String.format("     for parameters:  \"%s\",  result should be:  \"%s\"  ,but was: \"%s\"\n", params[key], expectedResults[key], failedTests.get(key));
                        outputManager.printTestResults("white","",string);}
                    System.out.println();
                    return TestResult.FAIL;
                }
            }catch (IllegalArgumentException e) {
                outputManager.printTestResults("red","","Test Error: " + e.getMessage());
                System.out.println();
                return TestResult.ERROR;
            }
        } catch (ReflectiveOperationException e) {
            outputManager.printTestResults("red","","Test Error: " + e.getMessage());
            System.out.println();
            return TestResult.ERROR;
        }
    }

    private static List<Method> getTestMethods(Object unit) {
        Method[] methods = unit.getClass().getDeclaredMethods();
        return Arrays.stream(methods).filter(
                m -> m.getAnnotation(MyTest.class) != null).collect(Collectors.toList());
    }

    private static Object getObject(String className) {
        try {
            Class<?> unitClass = Class.forName(className);
            return unitClass.getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return new Object();
        }
    }
}
