package com.oxymedical.component.rulesComponent;

import java.util.ArrayList;
import java.util.List;

//import org.apache.mahout.cf.taste.recommender.RecommendedItem;


import com.oxymedical.component.baseComponent.exception.ComponentException;
import com.oxymedical.component.rulesComponent.rule.elements.Consequence;
import com.oxymedical.core.commonData.Application;
import com.oxymedical.core.commonData.Data;
import com.oxymedical.core.commonData.FormPattern;
import com.oxymedical.core.commonData.HICData;
import com.oxymedical.core.commonData.IData;
import com.oxymedical.core.commonData.IFormPattern;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.commonData.IWorkflowPattern;
import com.oxymedical.core.commonData.WorkflowPattern;
import com.oxymedical.core.propertyUtil.PropertyUtil;
import com.oxymedical.core.userdata.IUserPattern;
import com.oxymedical.core.userdata.UserPattern;

public class RuleComponentHelper {

	RuleComponent ruleComponent = null;
	static StringBuilder result = new StringBuilder(); 
	/**
	 * @param args
	 */
	public static StringBuilder callRuleHelper(Iterable<Object> outputFromRecommender) 
	{
		try 
		{
			System.out.println("callRuleHelper called");
			RuleComponentHelper rulesHelper = new RuleComponentHelper();
			/*String obj = "dataObject";
			String nameObj;
			*/
			String value, id;
			 for (Object recommendedItem : outputFromRecommender) 
			    {
			      //System.out.print(recommendedItem.getValue());
			      System.out.print('\t');
			      value = String.valueOf(recommendedItem);//recommendedItem.getValue());
			      IHICData dataObject = rulesHelper.getHICData(value);
			      rulesHelper.getRules(dataObject);
			      //System.out.print(recommendedItem.toString());
			   //   System.out.print(recommendedItem.getItemID());
			     // System.out.println("");
			    }
			/*for(int i=0; i<outputFromRecommender.size();i++)
			{
			IHICData dataObject = rulesHelper.getHICData(outputFromRecommender);
			rulesHelper.getRules(dataObject1);
			}*/
			/*IHICData dataObject2 = rulesHelper.getHICData("8");
			rulesHelper.getRules(dataObject2);
			IHICData dataObject3 = rulesHelper.getHICData("9");
			rulesHelper.getRules(dataObject3);*/
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return result;
	}
	public RuleComponentHelper()
	{
		ruleComponent = new RuleComponent();
		ruleComponent.getInstanceOfLoggingComponent();
		String masterPageLocation = PropertyUtil.setUpProperties("RULEFILE_LOCATION");
		try 
		{
			// Should be called only once
			ruleComponent.buildRete(masterPageLocation);
		}
		catch (ComponentException e) 
		{
			e.printStackTrace();
		}
	}
	
	public List<String> getRules(IHICData dataObject) throws Exception
	{
		List<String> resultList = new ArrayList<String>();
		try
		{
			Object[] facts = {dataObject};
			List<IRuleClass> ruleClassList = new ArrayList<IRuleClass>();
			ruleClassList = ruleComponent.executeRules(facts);  // Should be called every time a fact arrives
			if(ruleClassList.size()!=0)
				result.append("<p> For dataObject-- "+dataObject.getUniqueID().toString()+" Rule count meeting this condition! \n</p>");
			else
				result.append("<p> For dataObject-- "+dataObject.getUniqueID().toString()+ " No rule meets this condition. \n</p>");
			for (int i=0; i<ruleClassList.size();i++)
			{
				IRuleClass rule = ruleClassList.get(i);
				Consequence con = (Consequence)rule.getConsequenceList().get(0);
				resultList.add(con.getConsequenceString());
			}
		} 
		catch (ComponentException e) 
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return resultList;
	}

	private IHICData getHICData(String user)
	{
		Application app = new Application();
		app.setApplicationName("RecommendationEngine");
		IHICData hicData = new HICData();
		IData data = new Data();
		data.setStatus("newfact");
		data.setUserId(user);
		hicData.setUniqueID(user);
		hicData.setApplication(app);
		hicData.setData(data);		
		return hicData;
	}
}
