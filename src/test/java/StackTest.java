import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StackTest {

    @Test
    void size() {
        Stack<Object> stack = createSUT();
        assertEquals(0, stack.size());
        stack.push(1);
        assertEquals(1, stack.size());
        stack.push(2);
        assertEquals(2, stack.size());
        stack.push(3);
        assertEquals(3, stack.size());
    }

    @Test
    void pop() {
        Stack<Object> stack = createSUT();
        stack.pop();
        stack.push(1);

        assertEquals(1, stack.size());
        assertEquals(1, stack.pop());
        assertEquals(0, stack.size());

        stack.push("3");
        assertEquals("3", stack.pop());
    }

    @Test
    void push() {
        Stack<Object> stack = createSUT();
        stack.push(1);
        assertEquals(1, stack.peek());
        stack.push(2);
        assertEquals(2, stack.peek());
        stack.push(3);
        assertEquals(3, stack.peek());
        assertEquals(3, stack.size());
    }

    @Test
    void peek() {
        Stack<Object> stack = createSUT();
        stack.peek();
        stack.push(1);

        assertEquals(1, stack.size());
        assertEquals(1, stack.peek());
        assertEquals(1, stack.size());

        stack.push(2);
        assertEquals(2, stack.size());
        assertEquals(2, stack.peek());
        assertEquals(2, stack.size());
    }

    @Test
    void test_checkParentheses() {
//        assertFalse(Stack.checkParentheses("(()"));
//        assertTrue(Stack.checkParentheses("(())"));
//        assertFalse(Stack.checkParentheses("()())"));
//        assertTrue(Stack.checkParentheses("(()())"));
//        assertTrue(Stack.checkParentheses("((((()))))"));
//        assertFalse(Stack.checkParentheses("((((())))))"));
    }

    @Test
    void test_calculatePostfix() {
        assertEquals(0, StackBonusTask.calculatePostfixExpression(""));
        assertEquals(3, StackBonusTask.calculatePostfixExpression("1 2 +"));
        assertEquals(12, StackBonusTask.calculatePostfixExpression("1 2 + 4 *"));
        assertEquals(2, StackBonusTask.calculatePostfixExpression("2 4 /"));
        assertEquals(59, StackBonusTask.calculatePostfixExpression("8 2 + 5 * 9 +"));
    }

    private Stack<Object> createSUT() {
        return new Stack<>();
    }

}