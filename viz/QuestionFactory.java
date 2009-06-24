package viz;
import java.util.*;
import Interpreter.*;

public class QuestionFactory implements UpdateReasons
{

	private HashMap<String, Question> endQuestions;
	private HashMap<String, Question> callQuestions;
	public QuestionFactory()
	{
		endQuestions = new HashMap<String, Question>();
		callQuestions = new HashMap<String, Question>();
	}
	
	
	public Question[] getQuestions()
	{
	
		return ((Question[])endQuestions.entrySet().toArray());
	}
	
	public Question addBeginQuestion()
	{
		System.out.println("Adding a begin question");
		String var = getGlobalVar();
		FIBQuestion question = new FIBQuestion("What will the value of " + var + " when the program has finished executing?");
		endQuestions.put(var, question);
		return question;
	}
	
	
	public Question addCallQuestion(ArrayList<String> args, String funName)
	{
		String var = getRandomMember(args);
		TFQuestion question;
		if (Global.getSymbolTable().get(var) != - 255 && Global.getCurrentSymbolTable().get(var, true) == -255)
		{
			question = new TFQuestion("The value of " + var + " have changed when " + funName + " returns.");
			question.setAnswer(false);
		}
		else
		{
				question = new TFQuestion("If the evaluation strategy were call by reference instead of call by value, the value of " +
					var + " would have changed when " + funName + " returns");
				question.setExpectedValue(Global.getCurrentSymbolTable().get(var));
				question.setAnswer(false);
				callQuestions.put(var, question);
		}
		
		return question;
		
	}
	
	public Question addAssignmentQuestion(HashMap<String, String> paramArgMap, String leftHandVar)
	{
		String arg = paramArgMap.get(leftHandVar);
		TFQuestion question;
		if (arg != null)
		{
			question = new TFQuestion(arg + "'s value will have changed after the next line of code executes");
			question.setAnswer(false);
		}
		else if (Global.getSymbolTable().get(leftHandVar) != -255)
		{
			question = new TFQuestion("In the Global scope, " + arg + "'s value will have changed after the next line of code executes");
			if (Global.getCurrentSymbolTable().get(leftHandVar) != -255)
			{
				question.setAnswer(false);
			}
			else
			{
				question.setAnswer(true);
			}
		}
		else
		{
			question = new TFQuestion("If the evaluation strategy were pass by reference instead of pass by value, would the outcome of executing the next line of code be the same?");
			question.setAnswer(true);
		}
		return question;
	}
	
	public Question addEndQuestion()
	{
		return null;
	}
	
	public void addAnswers(int lineNumber, int reason)
	{
		switch (reason)
		{
			case UPDATE_REASON_END:
				System.out.println("Ok, we're updating at the end");
				for (String key : endQuestions.keySet())
				{
					System.out.println(endQuestions.size());
					Question q = endQuestions.get(key);
					if (q.getClass().getName().equals("viz.FIBQuestion"))
					{
					
						int answer = Global.getCurrentSymbolTable().get(key);
						((FIBQuestion)q).addAnswer(answer + "");
						System.out.println(q.getText() + "answer: " + answer);
					}
				}
				break;
			case UPDATE_REASON_LEAVEFUN:
				System.out.println("Leave func question updates");
				for (String key : callQuestions.keySet())
				{
					Question q = callQuestions.get(key);
					if (q.getClass().getName().equals("viz.TFQuestion"))
					{
						int answer = Global.getCurrentSymbolTable().get(key);
						if (((TFQuestion)q).getExpectedValue() != answer)
						{
							((TFQuestion)q).flipAnswer();
						}
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
	
	private void getArgVar()
	{
		
	}
	
	private String getRandomMember(HashSet<String> varNames)
	{
		Random r = new Random();
		return (String)varNames.toArray()[r.nextInt(varNames.size())];
	}
	
	private String getRandomMember(ArrayList<String> varNames)
	{
		Random r = new Random();
		return (String)varNames.toArray()[r.nextInt(varNames.size())];
	}
	
		
}
