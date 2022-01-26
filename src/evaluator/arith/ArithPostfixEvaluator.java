package evaluator.arith;

import language.Operand;
import language.Operator;
import parser.IllegalPostfixExpressionException;
import parser.PostfixParser.Type;
import parser.Token;
import parser.arith.ArithPostfixParser;
import stack.LinkedStack;
import stack.StackInterface;
import evaluator.PostfixEvaluator;

/** An {@link ArithPostfixEvaluator} is a postfix evaluator over simple arithmetic expressions. */
public class ArithPostfixEvaluator implements PostfixEvaluator<Integer> {

  private final StackInterface<Operand<Integer>> stack;

  /** Constructs an {@link ArithPostfixEvaluator} */
  public ArithPostfixEvaluator() {
    // TODO Initialize to your LinkedStack
    stack = new LinkedStack<>();
  }

  /** {@inheritDoc} */
  @Override
  public Integer evaluate(String expr) throws IllegalPostfixExpressionException {
    ArithPostfixParser parser = new ArithPostfixParser(expr);
    int countOperand = 0;
    int countOperator = 0;
    for (Token<Integer> token : parser) {
      Type type = token.getType();
      switch (type) {
        case OPERAND:
          // TODO What do we do when we see an operand?
          stack.push(token.getOperand());
          countOperand++;
          break;
        case OPERATOR:
          // TODO What do we do when we see an operator?
          Operator<Integer> operator = token.getOperator();
          if (operator.toString() != "!"){
            Operand<Integer> op1 = stack.pop();
            Operand<Integer> op0 = stack.pop();
            operator.setOperand(0, op0);
            operator.setOperand(1, op1);
          }
          else if (operator.toString() == "!") {
            Operand<Integer> op0 = stack.pop();
            operator.setOperand(0,op0);
          }
          Operand<Integer> result = operator.performOperation();
          stack.push(result);
          countOperator++;
          break;
        default:
          throw new IllegalPostfixExpressionException("Parser returned an invalid Type: " + type);
      }
    }

    if (countOperand > 1 && countOperator == 0) {
      throw new IllegalPostfixExpressionException();
    }
    // TODO What do we return?
    Integer result = stack.top().getValue();
    return result;
  }
}
