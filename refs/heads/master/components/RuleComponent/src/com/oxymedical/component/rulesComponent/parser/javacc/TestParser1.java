package com.oxymedical.component.rulesComponent.parser.javacc;

import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;

import com.oxymedical.component.rulesComponent.ICondition;
import com.oxymedical.component.rulesComponent.RuleComponent;
import com.oxymedical.component.rulesComponent.rule.elements.*;

// Referenced classes of package com.oxymedical.component.rulesComponent.parser.javacc:
//            RuleParser, SimpleNode

public class TestParser1
{


    public TestParser1()
    {
    }

    public static void main(String args[])
    {
        try
        {
            RuleParser parser = new RuleParser(new FileInputStream("C://erl2/RSet1.erl"));
            SimpleNode root = parser.parse();
            root.dump("");
            RuleSet ruleSet1 = parser.getRuleSet();
            RuleComponent.logger.log(0,(new StringBuilder("Rule set Name *******  = ")).append(ruleSet1.getName()).toString());
            List varList = ruleSet1.getVariables();
            Variables var;
            for(Iterator it = varList.iterator(); it.hasNext();  RuleComponent.logger.log(0,(new StringBuilder("ID *******  = ")).append(var.getId()).toString()))
            {
                var = (Variables)it.next();
                RuleComponent.logger.log(0,(new StringBuilder("Type *******  = ")).append(var.getType()).toString());
            }

            List ruleList = ruleSet1.getRules();
            Rule rule;
            for(Iterator it = ruleList.iterator(); it.hasNext();  RuleComponent.logger.log(0,(new StringBuilder("url = ")).append(rule.getUrl()).toString()))
            {
                rule = (Rule)it.next();
                RuleComponent.logger.log(0,(new StringBuilder("Rule name = ")).append(rule.getName()).toString());
                RuleComponent.logger.log(0,(new StringBuilder("salience of rule  = ")).append(rule.getSalience()).toString());
                ICondition condition = rule.getCondition();
                List consequenceList = rule.getConsequenceList();
                RuleComponent.logger.log(0,(new StringBuilder("condition string  = ")).append(condition.getConditionString()).toString());
                Consequence conq;
                for(Iterator it2 = consequenceList.iterator(); it2.hasNext();  RuleComponent.logger.log(0,(new StringBuilder("consequence  = ")).append(conq.getConsequenceString()).toString()))
                {
                    conq = (Consequence)it2.next();
                }

            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}