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
	
	public Question addQuestion(int lineNumber, int reason)
	{
		switch (reason)
		{
			case UPDATE_REASON_BEGIN:
				return addBeginQuestion();
			case UPDATE_REASON_CALL:
				addCallQuestion();
				addAnswers(lineNumber, reason);
				break;
			case UPDATE_REASON_STATEMENT:
				addStatementQuestion();
				addAnswers(lineNumber, reason);
				break;
			case UPDATE_REASON_END:
				System.out.println("Updating at end");
				addEndQuestion();
				addAnswers(lineNumber, reason);
				break;
			default:
				System.out.println("Unknown reason for question");
		}
		
		return null;
	}
	
	public Question[] getQuestions()
	{
	
		return ((Question[])endQuestions.entrySet().toArray());
	}
	
	public Question addBeginQuestion()
	{
		String var = getGlobalVar();
		FIBQuestion question = new FIBQuestion("What will the value of " + var + " when the program has finished executing?");
		endQuestions.put(var, question);
		return question;
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
				System.out.println("Ok, we're updating at the end");
				for (String key : endQuestions.keySet())
				{
					Question q = endQuestions.get(key);
					if (q.getClass().getName().equals("viz.FIBQuestion"))
					{
					
						int answer = Global.getCurrentSymbolTable().get(key);
						((FIBQuestion)q).addAnswer(answer + "");
						System.out.println(q.getText() + "answer: " + answer);
					}
				}
				break;
			default:
				//System.out.println("No answers to add for this reason");
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
