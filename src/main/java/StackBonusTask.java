public class StackBonusTask {

    static int calculatePostfixExpression(String s) {
        if (s.length() == 0) { return 0; }

        Stack<String> expressionStack = new Stack<>();
        String[] components = s.split(" ");

        //Fill expression stack by iterating reversed expression string
        for (int i = components.length - 1; i >= 0; i--) {
            expressionStack.push(components[i]);
        }

        Stack<Integer> numberStack = new Stack<>();
        int currentResult = 0;

        while (expressionStack.size() > 0) {
            if (isNumber(expressionStack.peek())) {
                numberStack.push(Integer.valueOf(expressionStack.pop()));
            } else {
                int number1 = numberStack.pop();
                int number2 = numberStack.pop();
                switch (operation(expressionStack.pop())) {
                    case PLUS:
                        currentResult = number1 + number2; break;
                    case MINUS:
                        currentResult = number1 - number2; break;
                    case MULTIPLY:
                        currentResult = number1 * number2; break;
                    case DIVIDE:
                        currentResult = number1 / number2; break;
                }
                expressionStack.push(String.valueOf(currentResult));
            }
        }

        return currentResult;
    }

    private enum Operation {
        PLUS,
        MINUS,
        MULTIPLY,
        DIVIDE
    }

    private static boolean isNumber(String s) {
        return s.matches("[0-9.]+");
    }

    private static Operation operation(String s) {
        switch (s) {
            case "+": return Operation.PLUS;
            case "-": return Operation.MINUS;
            case "*": return Operation.MULTIPLY;
            case "/": return Operation.DIVIDE;
        }
        throw new RuntimeException(new Exception("Wrong input"));
    }

}
