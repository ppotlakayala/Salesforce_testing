package com.sonar.java.rules;

import java.util.List;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.VariableTree;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

@Rule(key = "UseStandardLengthVariableNameRule",
  name = "UseStandardLengthVariableNameRule",
  description = "The rule expects to have standard length variable name i.e the length should be between 2 and 10",
  tags = {"coding-guideline"},
  priority = Priority.MINOR)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ARCHITECTURE_CHANGEABILITY)
@SqaleConstantRemediation("10min")
public class UseStandardLengthVariableNameRule extends BaseTreeVisitor implements JavaFileScanner {

  private static final String DEFAULT_VALUE = "StandardLengthVariables";

  private JavaFileScannerContext context;

  @RuleProperty(
    defaultValue = DEFAULT_VALUE,
    description = "Use Standard Length Variable Name")
  protected String name;

  public void scanFile(JavaFileScannerContext context) {
    this.context = context;

    scan(context.getTree());

  }

  @Override
	public void visitVariable(VariableTree tree) {
		
	  	String variableName = tree.simpleName().name();
	  	System.out.println("Scanning the variable : " + variableName);
	  	
	  	if(!(variableName.length() >=2 && variableName.length()<=10)) {
	  		context.reportIssue(this, tree, "Variable length is either too short or too long");
		  }
	  
		super.visitVariable(tree);
	}
}