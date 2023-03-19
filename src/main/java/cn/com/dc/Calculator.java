package cn.com.dc;

import org.springframework.util.Assert;

import java.util.Stack;

/**
 * @Description
 * @Author yingqiang.Cen
 * @Date 2023/3/19
 * @Version 1.0.0
 */
public class Calculator {

    public static final Integer ERROR_NUM = -999;

    private Stack<Double> stack;
    private Stack<Command> undoStack;
    private Stack<Command> redoStack;

    public Calculator() {
        stack = new Stack();
        undoStack = new Stack();
        redoStack = new Stack();
    }

    /**
     * 将数据压入栈
     */
    public void push(Double i) {
        checkIsNotNull(i);
        stack.push(i);
    }

    public Double pop(){
        return stack.pop();
    }

    /**
     * 数据判断
     */
    public void checkIsNotNull(Double i) {
        checkIsNotNull(i, "数据不能为空");
    }

    public void checkIsNotNull(Double i, String message) {
        Assert.notNull(i, message);
    }

    /**
     * 撤销上一步
     */
    public void undo() {
        if (!undoStack.isEmpty()) {
            Command com = undoStack.pop();
            com.undo();
            redoStack.push(com);
        }

    }

    /**
     * 重做上一步
     */
    public void redo() {
        if (!redoStack.empty()) {
            Command command = redoStack.pop();
            command.execute();
            undoStack.push(command);
        }
    }

    /**
     * 加
     */
    public void add() {
        Double n1 = stack.pop();
        Double n2 = stack.pop();
        if (n1 == null || n2 == null) {
            throw new NullPointerException("数据缺失");
        }
        Double result = n1 + n2;
        stack.push(result);
        undoStack.push(new AddCommand(n1,n2));
    }

    /**
     * 减
     */
    public void subtract() {
        Double n1 = stack.pop();
        Double n2 = stack.pop();
        if (n1 == null || n2 == null) {
            throw new NullPointerException("数据缺失");
        }
        Double result = n2 - n1;
        stack.push(result);
        undoStack.push(new SubtractCommand(n1,n2));
    }

    /**
     * 乘
     */
    public void multiply() {
        Double n1 = stack.pop();
        Double n2 = stack.pop();
        if (n1 == null || n2 == null) {
            throw new NullPointerException("数据缺失");
        }
        Double result = n1 * n2;
        stack.push(result);
        undoStack.push(new MultiplyCommand(n1,n2));
    }

    /**
     * 除
     */
    public void divide() {
        Double n1 = stack.pop();
        Double n2 = stack.pop();
        if (n1 == null || n2 == null) {
            throw new NullPointerException("数据缺失");
        }
        if(n2.equals(0d)){
            throw new IllegalArgumentException("除数不能为0");
        }
        Double result = n1 / n2;
        stack.push(result);
        undoStack.push(new DivideCommand(n1,n2));
    }

    private class AddCommand implements Command {
        private Double a;
        private Double b;
        public AddCommand(Double a,Double b){
            this.a = a;
            this.b = b;
        }

        public void execute() {
            add();
        }

        public void undo() {
            stack.push(a);
            stack.push(b);
        }
    }

    private class SubtractCommand implements Command{
        private Double a;
        private Double b;
        public SubtractCommand(Double a,Double b){
            this.a = a;
            this.b = b;
        }
        public void execute() {
            subtract();
        }

        public void undo() {
            stack.push(a);
            stack.push(b);
        }
    }

    private class MultiplyCommand implements Command{
        private Double a;
        private Double b;
        public MultiplyCommand(Double a,Double b){
            this.a = a;
            this.b = b;
        }
        public void execute() {
            multiply();
        }

        public void undo() {
            stack.push(a);
            stack.push(b);
        }
    }

    private class DivideCommand implements Command{
        private Double a;
        private Double b;
        public DivideCommand(Double a,Double b){
            this.a = a;
            this.b = b;
        }
        public void execute() {
            divide();
        }

        public void undo() {
            stack.push(a);
            stack.push(b);
        }
    }

    public static void main(String[] args) {
        Calculator cal = new Calculator();
        cal.push(2d);
        cal.push(3d);
        cal.add();
        System.out.println(cal.pop());
        cal.undo();
        cal.redo();
        System.out.println(cal.pop());

        cal.push(2d);
        cal.push(3d);
        cal.subtract();
        System.out.println(cal.pop());

        cal.push(2d);
        cal.push(3d);
        cal.multiply();
        System.out.println(cal.pop());


        cal.push(2d);
        cal.push(3d);
        cal.divide();
        System.out.println(cal.pop());
    }
}
