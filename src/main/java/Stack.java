import java.util.*;

//Reversed stack structure;
//Pop and push elements from top of list
public class Stack<T>
{

    private ArrayList<Object> list;

    public Stack()
    {
        this.list = new ArrayList<>();
    }

    public int size() {
        return list.size();
    }

    public T pop()
    {
        if (size() == 0) { return null; }
        T element = peek();
        list.remove(0);
        return element;
    }

    public void push(T val)
    {
        list.add(0, val);
    }

    public T peek()
    {
        int size = this.size();
        if (size == 0) { return null; }
        return (T) list.get(0);
    }

    static boolean checkParentheses(String s) {
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            Character currentChar = s.charAt(i);
            if (stack.size() > 0 && currentChar != stack.peek()) {
                stack.pop();
            } else {
                stack.push(currentChar);
            }
        }
        return stack.size() == 0;
    }

}