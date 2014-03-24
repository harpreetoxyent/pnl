package com.oxymedical.component.rulesComponent.parser.javacc;

import java.io.FileInputStream;
import java.io.PrintStream;

import com.oxymedical.component.rulesComponent.RuleComponent;

// Referenced classes of package com.oxymedical.component.rulesComponent.parser.javacc:
//            RuleParser, SimpleNode
public class TestParser
{

    public TestParser()
    {
    }

    public static void main(String args[])
    {
        try
        {
            RuleParser parser = new RuleParser(new FileInputStream("D:/hic_svn/src/main/components/java/rules/src/de/stoneone/component/rulesCompone" +
"nt/test/rulexml.xml"
));
            SimpleNode root = parser.parse();
            root.dump("");
            RuleComponent.logger.log(0,(new StringBuilder("root = ")).append(root.jjtGetNumChildren()).toString());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
