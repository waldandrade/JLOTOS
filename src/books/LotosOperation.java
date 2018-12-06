package books;

import java.util.ArrayList;
import java.util.List;

import bus.compiling.exceptions.SyntaticErrorException;


public class LotosOperation {
	

	public String operatorRegularExpression = "";
	
	public String operador = "";
	
	public String operatorType = SINGLE_OPERAND;
	
	public static final String RIGHT_OPERAND = "RIGHT_OPERAND";
	
	public static final String LEFT_AND_RIGHT_OPERAND = "LEFT_AND_RIGHT_OPERAND";
	
	public static final String LEFT_OPERAND = "LEFT_OPERAND";
	
	public static final String PARAMETER_OPERAND = "PARAMETER_OPERAND";
	
	public static final String SINGLE_OPERAND = "SINGLE_OPERAND";
	
	public int numberOfArguments = 0;
	
	public List<OperationPart> operatorTokens = new ArrayList<OperationPart>();
	
	public List<String> lotosSorts = null;
	
	public String returnSort = "";
	
	public void LotosOperator(){
		
		
	}
	
	public boolean hasSimgleOperand(){
		if(operatorType.equals(SINGLE_OPERAND))return true;
		else return false;
	}
	
	public void addToken(String token, int tokenId) throws SyntaticErrorException{
		
		OperationPart operationPart = null;
		
		boolean flag = true;
		
		if(token.equals("_")){
			if(operatorTokens.size()==0){
				operationPart = new OperationPart(token, OperationPart.LEFT_UNDER);
				this.operatorType = LEFT_OPERAND;
			}else if(operatorTokens.size()==1){
				/*
				 * Se um underline surge na segunda posição, significa que um operando a direita
				 * é necessário. Mas se o primeira parte também foi um underline, então significa que
				 * foi colocado como operador uma sequencia de dois underlines seguidos
				 * "__", que é um erro sintático.
				 * 
				 * */
				if(operatorType.equals(SINGLE_OPERAND)){
				operationPart = new OperationPart(token, OperationPart.RIGHT_UNDER);
				this.operatorType = RIGHT_OPERAND;
				}else throw new SyntaticErrorException("Bad location of underline.", tokenId);
			}else if(operatorTokens.size()==2){
				operationPart = new OperationPart(token, OperationPart.RIGHT_UNDER);
				this.operatorType = LEFT_AND_RIGHT_OPERAND;
			}else{
				throw new SyntaticErrorException("Bad location of underline.", tokenId);
			}
		}else{
			if(operatorTokens.size()==0){
				operationPart = new OperationPart(token, OperationPart.OPERAND);
			}else if(operatorTokens.size()==1){
				if(operatorType.equals(LEFT_OPERAND))
				operationPart = new OperationPart(token, OperationPart.OPERAND);
				else throw new SyntaticErrorException("Bad location of operand.", tokenId);
			}else if(operatorTokens.size()>1){
				flag = false;
				if(operatorType.equals(LEFT_OPERAND))
				operatorTokens.get(operatorTokens.size()-1).operationPartString+=token;
			}else{
				throw new SyntaticErrorException("Bad location of operand.", tokenId);
			}
			operador += token;
		}
		
		if(flag)
		operatorTokens.add(0,operationPart);
		
	}
	
	public void generateRegularExpression(){
		
	}
	
	public void addSort(String sort){
		if(lotosSorts == null){
			lotosSorts = new ArrayList<String>();
		}
		if(operatorType.equals(LotosOperation.SINGLE_OPERAND))
			operatorType = LotosOperation.PARAMETER_OPERAND;
		lotosSorts.add(sort);
		numberOfArguments++;
	}
	
	public void setReturnSort(String returnSort){
		this.returnSort = returnSort;
	}

	public class OperationPart {
		

		public static final String RIGHT_UNDER = "RIGHT_UNDER";
		
		public static final String LEFT_UNDER = "LEFT_UNDER";
		
		public static final String OPERAND = "OPERAND";
		
		public String operationPartType = "";
		
		public String operationPartString = "";
		
		public OperationPart(String operationPartString, String operationPartType){
			
			this.operationPartType = operationPartType;
			this.operationPartString = operationPartString;
			
		}

	}
}
