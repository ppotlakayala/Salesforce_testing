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

@Rule(key = "VariableNameNotSUM",
  name = "VariableNameNotSUM",
  description = "The rule expects not to have any variable name as \"SUM\" in either UPPER or lower case",
  tags = {"coding-guideline"},
  priority = Priority.MAJOR)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ARCHITECTURE_CHANGEABILITY)
@SqaleConstantRemediation("20min")
public class VariableNameNotSUM extends BaseTreeVisitor implements JavaFileScanner {

  private static final String DEFAULT_VALUE = "VariableNameNotSUM";

  private JavaFileScannerContext context;

  @RuleProperty(
    defaultValue = DEFAULT_VALUE,
    description = "The rule expects not to have any variable name as \"SUM\" in either UPPER or lower case")
  protected String name;

  public void scanFile(JavaFileScannerContext context) {
    this.context = context;

    scan(context.getTree());

  }

  @Override
	public void visitVariable(VariableTree tree) {
		
	  	String variableName = tree.simpleName().name();
	  	System.out.println("Scanning the variable : " + variableName);
	  	
	  	if(variableName.equalsIgnoreCase("sum")) {
	  		context.reportIssue(this, tree, "Variable name declared as sum");
		  }
	  
		super.visitVariable(tree);
	}
}