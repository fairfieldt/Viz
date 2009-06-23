package viz;
import java.util.*;
import Interpreter.*;

public class QuestionFactory implements QuestionTypes
{
	public QuestionFactory()
	{

	}
	
	public Question getQuestion(int reason)
	{
		switch (reason)
		{
			case QUESTION_REASON_BEGIN:
				return getBeginQuestion();
			case QUESTION_REASON_CALL:
				return getCallQuestion();
			case QUESTION_REASON_STATEMENT:
				return getStatementQuestion();
			case QUESTION_REASON_END:
				return getEndQuestion();
			default:
				System.out.println("Unknown reason for question");
				return null;
		}
	}
	
	public Question getBeginQuestion()
	{
		return null;
	}
	
	public Question getCallQuestion()
	{
		return null;
	}
	
	public Question getStatementQuestion()
	{
		return null;
	}
	
	public Question getEndQuestion()
	{
		return null;
	}
		
}
