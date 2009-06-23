package viz;
import java.util.*;
import Interpreter.*;

public class QuestionFactory implements UpdateReasons
{

	private HashMap<String, Question> endQuestions;
	public QuestionFactory()
	{
		endQuestions = new HashMap<String, Question>();
	}
	
	public void addQuestion(int lineNumber, int reason)
	{
		switch (reason)
		{
			case UPDATE_REASON_BEGIN:
				addBeginQuestion();
				break;
			case UPDATE_REASON_CALL:
				addCallQuestion();
				addAnswers(lineNumber, reason);
				break;
			case UPDATE_REASON_STATEMENT:
				addStatementQuestion();
				addAnswers(lineNumber, reason);
				break;
			case UPDATE_REASON_END:
				addEndQuestion();
				addAnswers(lineNumber, reason);
				break;
			default:
				System.out.println("Unknown reason for question");
		}
	}
	
	public void addBeginQuestion()
	{
		String var = getGlobalVar();
		FIBQuestion question = new FIBQuestion("What will the value of " + var + " when the program has finished executing?");
		endQuestions.put(var, question);
	}
	
	
	public void addCallQuestion()
	{
		
	}
	
	public void addStatementQuestion()
	{
	
	}
	
	public void addEndQuestion()
	{
		
	}
	
	private void addAnswers(int lineNumber, int reason)
	{
		switch (reason)
		{
			case UPDATE_REASON_END:
			
				for (String key : endQuestions.keySet())
				{
					Question q = endQuestions.get(key);
					if (q.getClass().getName().equals("viz.FIBQuestion"))
					{
					
						int answer = Global.getCurrentSymbolTable().get(key);
						((FIBQuestion)q).addAnswer(answer + "");
					}
				}
				break;
			default:
				System.out.println("No answers to add for this reason");
		}		
	}
	
	private String getGlobalVar()
	{
		HashSet<String> varNames = Global.getSymbolTable().getCurrentVarNames();
		return getRandomMember(varNames);
	}
	
	private String getRandomMember(HashSet<String> varNames)
	{
		Random r = new Random();
		return (String)varNames.toArray()[r.nextInt(varNames.size())];
	}
	
		
}
